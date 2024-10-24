package com.zouhu.springboot.file.common.controller;

import com.zouhu.springboot.file.common.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 *
 * @author zouhu
 * @data 2024-10-23 21:37
 */
@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final FileService fileService;

    /**
     * 上传文件自动绑定到 MultipartFile 对象中
     *
     * @param file 上传文件
     * @return
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return fileService.uploadFile(file);
    }
}
