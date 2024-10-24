package com.zouhu.springboot.file.advance.server;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;


/**
 * 服务端支持分片下载
 *
 * @author zouhu
 * @data 2024-10-24 14:51
 */
@RestController
public class FileChunkDownloadController {
    private static final String FILE_DIRECTORY = "D:\\Program Files\\";

    // 处理文件下载请求的方法
    @GetMapping("/download-chunk")
    public ResponseEntity<StreamingResponseBody> downloadFile(
            @RequestParam String fileName,
            @RequestHeader(value = HttpHeaders.RANGE, required = false) String range) throws IOException {

        // 根据文件名构建文件对象
        File file = new File(FILE_DIRECTORY, fileName);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 获取文件长度, 初始化下载的起始和结束位置
        long fileLength = file.length();
        long start = 0;
        long end = fileLength - 1;

        // 处理 Range 请求
        if (range != null) {
            // 解析 Range 请求中的起始和结束位置
            String[] ranges = range.replace("bytes=", "").split("-");
            start = Long.parseLong(ranges[0]);
            if (ranges.length > 1 && !ranges[1].isEmpty()) {
                end = Long.parseLong(ranges[1]);
            }
        }

        // 确保请求的范围合法
        if (start > end || start >= fileLength) {
            // 如果请求范围不合法，返回 416 REQUESTED RANGE NOT SATISFIABLE
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
                    .header(HttpHeaders.CONTENT_RANGE, "bytes */" + fileLength)
                    .build();
        }

        // 如果结束位置超出文件长度，调整结束位置
        if (end >= fileLength) {
            end = fileLength - 1;
        }

        // 设置内容长度
        long contentLength = end - start + 1;

        // 使用 final 关键字定义的变量
        final long finalStart = start;
        final long finalEnd = end;
        final long finalContentLength = contentLength;

        // 创建 StreamingResponseBody 对象
        StreamingResponseBody responseBody = outputStream -> {
            try (InputStream inputStream = new FileInputStream(file)) {
                inputStream.skip(finalStart); // 跳过起始位置

                byte[] buffer = new byte[1024];
                int bytesRead;
                long bytesToRead = finalContentLength;

                while (bytesToRead > 0 && (bytesRead = inputStream.read(buffer, 0, (int) Math.min(buffer.length, bytesToRead))) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    bytesToRead -= bytesRead;
                }
            } catch (IOException e) {
                // 打印异常信息
                e.printStackTrace();
            }
        };

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_RANGE, "bytes " + finalStart + "-" + finalEnd + "/" + fileLength)
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(finalContentLength))
                .body(responseBody);
    }
}
