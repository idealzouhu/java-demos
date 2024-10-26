package com.zouhu.springboot.file.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * 文件上传和下载的具体实现
 *
 * @author zouhu
 * @data 2024-10-23 21:41
 */
@Service
public class FileService {

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            return "上传失败，因为文件是空的。";
        }

        // 获取文件名和保存路径
        String fileName = file.getOriginalFilename();
        File dest = new File(uploadPath, fileName);

        // 确保目录存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        // 保存文件
        try {
            file.transferTo(dest);
            return "文件上传成功：" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败。";
        }
    }

    /**
     * 下载文件
     *
     * 根据文件名构建文件路径，并检查文件是否存在如果文件不存在，则返回404未找到的响应
     * 如果文件存在，将文件作为资源包装，并设置HTTP响应头以提示浏览器下载文件
     *
     * @param fileName 要下载的文件名
     * @return 包含文件资源的响应实体，如果文件不存在则为404响应
     */
    public ResponseEntity<Resource> downloadFile(String fileName) {
        // 构建文件路径
        File file = new File(uploadPath, fileName);

        // 检查文件是否存在
        if (!file.exists()) {
            // 文件不存在，返回404未找到
            return ResponseEntity.notFound().build();
        }

        // 将文件包装为资源
        Resource resource = new FileSystemResource(file);

        // 创建HTTP响应头，用于指定文件下载
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        // 返回包含文件资源和响应头的响应实体
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
