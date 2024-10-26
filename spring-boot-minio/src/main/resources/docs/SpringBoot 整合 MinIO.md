## MinIoClientConfig 配置类

```
@Configuration
public class MinIoClientConfig {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return  MinioClient.builder()
                .endpoint(endpoint) // MinIO 服务器的 URL
                .credentials(accessKey, secretKey) // 访问密钥和密钥
                .build();
    }
}
```



## Minio 服务

#### 接口

```
public interface OSSFileService {
    /**
     * 上传文件
     *
     * @param bucketName 存储桶名称
     * @param objectName 文件名
     * @param filePath 对象名字
     * @param contentType 文件类型
     *
     * @return
     */
    void uploadFile(String bucketName, String objectName, String filePath, String contentType);
}
```



### 实现类

```java
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
```









## 参考资料

[【Minio】SpringBoot 整合 Minio（案例代码拿来即用）_minio springboot 代码-CSDN博客](https://blog.csdn.net/weixin_43657300/article/details/129435898)