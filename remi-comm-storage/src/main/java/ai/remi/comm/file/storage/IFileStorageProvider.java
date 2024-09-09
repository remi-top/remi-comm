package ai.remi.comm.file.storage;

/**
 * 文件上传对象工厂
 *
 * @Author: dianjiuxyz@gmail.com
 * @Date: 2024/1/25 14:23
 */
public interface IFileStorageProvider {
    IFileStorage getStorage();
}
