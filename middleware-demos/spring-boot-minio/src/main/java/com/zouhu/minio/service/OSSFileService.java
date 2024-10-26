package com.zouhu.minio.service;

/**
 * @author zouhu
 * @version 1.0
 * @data 2024-10-26 16:06
 */
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
