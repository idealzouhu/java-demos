package com.zouhu.springboot.file.common.controller;

import com.zouhu.springboot.file.common.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下载文件
 *
 * @author zouhu
 * @data 2024-10-23 21:49
 */
@RequiredArgsConstructor
@RestController
public class FileDownloadController {

    private final FileService fileService;

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
        return fileService.downloadFile(fileName);
    }
}
