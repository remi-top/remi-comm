package ai.remi.comm.file.storage;

/**
 * 存储平台类型
 *
 * @Author: dianjiuxyz@gmail.com
 * @Date: 2024/6/7 16:01
 */
public enum StorageType {

    /**
     * 本地
     */
    local,
    /**
     * 阿里云OSS
     */
    aliyunOSS,
    /**
     * 七牛云
     */
    qiniu,
    /**
     * MinIO
     */
    minio;
}
