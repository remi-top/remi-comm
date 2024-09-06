package ai.remi.comm.file.util;

import cn.hutool.core.io.IoUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ResponseUtils {
    /**
     * 导出文件流
     *
     * @param is
     * @param objectName
     * @param response
     * @throws IOException
     */
    public static void write(InputStream is, String objectName, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(objectName, StandardCharsets.UTF_8));
        IoUtil.copy(is, outputStream);
        response.flushBuffer();
        IoUtil.close(outputStream);
        IoUtil.close(is);
    }
}
