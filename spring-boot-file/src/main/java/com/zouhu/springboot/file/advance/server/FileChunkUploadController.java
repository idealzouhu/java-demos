package com.zouhu.springboot.file.advance.server;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 服务端处理文件上传
 * <p>
 *     服务端接收分片，保存到指定目录，并在所有分片上传完成后合并它们。
 * </p>
 *
 * @author zouhu
 * @data 2024-10-24 13:58
 */
@RequiredArgsConstructor
@RestController
public class FileChunkUploadController {
    // 文件临时目录，用于保存上传的分片文件
    private static final String TEMP_DIR = "D:\\Learning\\java-demos\\spring-boot-file\\src\\main\\resources\\advance\\temp\\";

    // 文件上传目录，用于保存合并后的文件
    private static final String UPLOAD_DIR = "D:\\Learning\\java-demos\\spring-boot-file\\src\\main\\resources\\advance\\upload\\";


    /**
     * 处理单个分片上传请求
     * <p>
     *     当文件较大或网络条件不稳定时，客户端可以将文件分割成多个分片分别上传
     *     这个方法负责接收单个分片，并将其保存到临时目录当所有分片上传完成后，将它们合并成一个完整的文件
     * </p>
     *
     * @param chunk 分片文件，包含文件的一部分
     * @param fileName 原始文件名，用于合并分片时命名
     * @param chunkNumber 当前分片的编号，从1开始
     * @param totalChunks 总分片数，用于判断是否所有分片都已上传
     * @return 分片上传的状态信息
     * @throws IOException 如果文件操作失败
     */
    @PostMapping("/upload-chunk")
    public ResponseEntity<String> uploadChunk(@RequestParam("chunk") MultipartFile chunk,
                                              @RequestParam("fileName") String fileName,
                                              @RequestParam("chunkNumber") int chunkNumber,
                                              @RequestParam("totalChunks") int totalChunks) throws IOException {

        // 保存分片到临时目录
        File tempFile = new File(TEMP_DIR + fileName + "_" + chunkNumber);
        chunk.transferTo(tempFile);

        // 检查是否所有分片都已上传
        if (isAllChunksUploaded(fileName, totalChunks)) {
            mergeChunks(fileName, totalChunks);
        }

        return ResponseEntity.ok("Chunk " + chunkNumber + " uploaded");
    }

    // 判断是否所有分片都上传完毕
    private boolean isAllChunksUploaded(String fileName, int totalChunks) {
        for (int i = 1; i <= totalChunks; i++) {
            File file = new File(TEMP_DIR + fileName + "_" + i);
            if (!file.exists()) {
                return false;
            }
        }
        return true;
    }

    // 合并所有分片
    private void mergeChunks(String fileName, int totalChunks) throws IOException {
        File mergedFile = new File(UPLOAD_DIR + fileName);
        try (FileOutputStream fos = new FileOutputStream(mergedFile, true)) {
            for (int i = 1; i <= totalChunks; i++) {
                File chunkFile = new File(TEMP_DIR + fileName + "_" + i);
                try (FileInputStream fis = new FileInputStream(chunkFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
                chunkFile.delete(); // 删除分片
            }
        }
    }
}
