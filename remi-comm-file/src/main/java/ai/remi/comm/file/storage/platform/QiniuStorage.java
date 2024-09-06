package ai.remi.comm.file.storage.platform;

import ai.remi.comm.file.domian.FileBo;
import ai.remi.comm.file.storage.IFileStorage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 七牛云存储实现
 * TODO 待实现
 */
public class QiniuStorage implements IFileStorage {
    @Override
    public boolean bucketExists(String bucket) {
        return false;
    }

    @Override
    public void makeBucket(String bucket) {

    }

    @Override
    public FileBo upload(MultipartFile file) {
        return null;
    }

    @Override
    public void delete(String url) {

    }

    @Override
    public void download(String url, HttpServletResponse response) {

    }

    @Override
    public String getUrl(String objectName) {
        return "";
    }
}
