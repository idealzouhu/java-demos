package com.zouhu.minio.service.serviceImpl;

import com.zouhu.minio.service.OSSFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OssFileServiceImplTest {
    @Autowired
    private OSSFileService ossFileService;

    @Test
    void uploadFile() {
        String bucketName = "test-bucket";
        String objectName = "test-file.txt";
        String filePath = "D:\\test.txt"; // 确保这个文件存在
        String contentType = "text/plain";

        ossFileService.uploadFile(bucketName, objectName, filePath, contentType);
    }
}