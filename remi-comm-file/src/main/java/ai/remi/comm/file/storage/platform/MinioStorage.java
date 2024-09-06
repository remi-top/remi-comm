package ai.remi.comm.file.storage.platform;

import ai.remi.comm.exception.custom.BusinessException;
import ai.remi.comm.file.config.FileServerProperties;
import ai.remi.comm.file.constant.CommonConstant;
import ai.remi.comm.file.domian.FileBo;
import ai.remi.comm.file.storage.IFileStorage;
import ai.remi.comm.file.util.ResponseUtils;
import ai.remi.comm.util.string.StringUtils;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Minio文件上传
 *
 * @Author: dianjiuxyz@gmail.com
 * @Date: 2024/1/25 10:05
 */
@Slf4j
public class MinioStorage implements IFileStorage {

    private final MinioClient minioClient;
    private final String endPoint;
    private final String bucket;

    public MinioStorage(FileServerProperties.MinioProperties config) {
        try {
            String accessKey = config.getAccessKey();
            String secretKey = config.getSecretKey();
            String endPoint = config.getEndpoint();
            String bucket = config.getBucket();
            this.minioClient = MinioClient.builder()
                    .credentials(accessKey, secretKey)
                    .endpoint(endPoint)
                    .build();
            this.endPoint = endPoint;
            this.bucket = bucket;
        } catch (Exception e) {
            log.error("[Minio] MinioClient build failed: {}", e.getMessage());
            throw new BusinessException("请检查Minio配置是否正确");
        }
    }

    @Override
    public boolean bucketExists(String bucket) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            log.error("[Minio] bucketExists Exception:{}", e.getMessage());
        }
        return false;
    }

    @Override
    public void makeBucket(String bucket) {
        try {
            if (!bucketExists(bucket)) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
                log.info("[Minio] makeBucket success bucketName:{}", bucket);
            }
        } catch (Exception e) {
            log.error("[Minio] makeBucket Exception:{}", e.getMessage());
            throw new BusinessException("创建存储桶失败");
        }
    }

    @Override
    public FileBo upload(MultipartFile file) {
        makeBucket(bucket);
        FileBo fileBo = FileBo.build(file);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileBo.getFileName())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(putObjectArgs);
            String url = getUrl(fileBo.getFileName());
            fileBo.setUrl(url);
            return fileBo;
        } catch (Exception e) {
            log.error("[Minio] file upload failed: {}", e.getMessage());
            throw new BusinessException("文件上传失败");
        }
    }

    @Override
    public void delete(String objectName) {
        if (StringUtils.isEmpty(objectName)) {
            throw new BusinessException("文件删除失败, 文件对象为空");
        }
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build();
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("[Minio] file delete failed: {}", e.getMessage());
            throw new BusinessException("文件删除失败");
        }
    }

    @Override
    public void download(String objectName, HttpServletResponse response) {
        if (StringUtils.isEmpty(objectName)) {
            throw new BusinessException("文件下载失败, 文件对象为空");
        }
        try {
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build();
            GetObjectResponse is = minioClient.getObject(getObjectArgs);
            ResponseUtils.write(is, objectName, response);
            log.info("[Minio] file download success, object:{}", objectName);
        } catch (Exception e) {
            log.error("[Minio] file download failed: {}", e.getMessage());
            throw new BusinessException("文件下载失败");
        }
    }

    @Override
    public String getUrl(String objectName) {
        return endPoint + CommonConstant.DIR_SPLIT + bucket + CommonConstant.DIR_SPLIT + objectName;
    }
}

