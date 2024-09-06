package ai.remi.comm.file.config;

import ai.remi.comm.file.storage.StorageType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dianjiu【公众号 点九开源】
 * @email startdis@njydsz.com
 * @desc 文件服务器配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "remi.files-server")
public class FileServerProperties {

    /**
     * 上传类型-默认local
     */
    private StorageType type = StorageType.local;

    /**
     * 本地上传配置
     */
    private LocalProperties local;

    /**
     * 七牛配置
     */
    private QiniuProperties qiniu;

    /**
     * oss配置
     */
    private AliyunOssProperties aliyunOss;

    /**
     * minio配置
     */
    private MinioProperties minio;


    @Data
    public static class LocalProperties {

        private String directory;

        private String endPoint;

        private String nginxUrl;
    }

    @Data
    public static class AliyunOssProperties {

        private String accessKey;

        private String secretKey;

        private String endpoint;

        private String bucket;
    }

    @Data
    public static class QiniuProperties {

        private String accessKey;

        private String secretKey;

        private String bucket;

        private String path;
    }

    @Data
    public static class MinioProperties {

        private String accessKey;

        private String secretKey;

        private String endpoint;

        private String bucket;
    }
}
