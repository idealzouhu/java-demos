package com.zouhu.minio.service.serviceImpl;

import com.zouhu.minio.service.OSSFileService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author zouhu
 * @version 1.0
 * @data 2024-10-26 16:07
 */
@AllArgsConstructor
@Service
public class OssFileServiceImpl implements OSSFileService {

    private final MinioClient minioClient;

    @Override
    public void uploadFile(String bucketName, String objectName, String filePath, String contentType) {
        // 输入验证
        if (bucketName == null || bucketName.isEmpty() || objectName == null || objectName.isEmpty() || filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        try {
            // 检查桶是否已存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // 如果桶不存在，则创建新桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("Bucket created successfully.");
            } else {
                System.out.println("Bucket already exists.");
            }

            // 上传文件
            try (InputStream fileStream = Files.newInputStream(Paths.get(filePath))) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .stream(fileStream, fileStream.available(), -1)
                                .contentType("text/plain")
                                .build()
                );
                System.out.println("File uploaded successfully.");
            }

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
