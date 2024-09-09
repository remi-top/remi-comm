package ai.remi.comm.file.storage;

import ai.remi.comm.exception.custom.BusinessException;
import ai.remi.comm.file.config.FileServerProperties;
import ai.remi.comm.file.storage.platform.AliyunOssStorage;
import ai.remi.comm.file.storage.platform.LocalStorage;
import ai.remi.comm.file.storage.platform.MinioStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 默认文件上传平台工厂
 *
 * @Author: hao.ding@insentek.com
 * @Date: 2024/1/25 13:50
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class IStorageFactory implements IFileStorageProvider {

    private final FileServerProperties serverProperties;

    @Override
    public IFileStorage getStorage() {
        StorageType storageType = serverProperties.getType();
        return switch (Objects.requireNonNull(storageType)) {
            case local -> new LocalStorage(serverProperties.getLocal());
            case minio -> new MinioStorage(serverProperties.getMinio());
//            case qiniu -> new QiniuStorage(serverProperties.getQiniu());
            case aliyunOSS -> new AliyunOssStorage(serverProperties.getAliyunOss());
            default -> throw new BusinessException("不支持的存储平台");
        };
    }
}
