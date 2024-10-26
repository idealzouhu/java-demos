package com.zouhu.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 测试 Minio 的简单案例
 *
 * @author zouhu
 * @version 1.0
 * @data 2024-10-26 15:55
 */
public class MinioExample {

    public static void main(String[] args) {
        try {
            // 创建 MinIO 客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000") // MinIO 服务器的 URL
                    .credentials("minio", "minio123") // 访问密钥和密钥
                    .build();

            // 检查桶 example-bucket 是否已存在
            String bucketName = "example-bucket";
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // 如果桶不存在，则创建新桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("Bucket created successfully.");
            } else {
                System.out.println("Bucket already exists.");
            }

            // 上传文件
            // 该文件作为 objectName 到 MinIO 的桶 example-bucket 中
            String filePath = "D:\\test.txt";
            String objectName = "my-file.txt";
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
