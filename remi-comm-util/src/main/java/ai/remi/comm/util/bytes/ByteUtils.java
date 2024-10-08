package ai.remi.comm.util.bytes;

/**
 * 字节转换
 *
 * @author dianjiu
 * @date 2020-09-19 23:21
 */
public class ByteUtils {

    private static final int UNIT = 1024;
    private static final long KB = 1024;
    private static final long MB = KB * 1024;
    private static final long GB = MB * 1024;

    /**
     * 格式化字节大小
     *
     * @param byteSize 字节大小
     * @return {@link String}
     */
    public static String formatByteSize(long byteSize) {

        if (byteSize <= -1) {
            return String.valueOf(byteSize);
        }

        double size = 1.0 * byteSize;

        String type;
        if ((int) Math.floor(size / UNIT) <= 0) { // 不足1KB
            type = "B";
            return format(size, type);
        }

        size = size / UNIT;
        if ((int) Math.floor(size / UNIT) <= 0) { // 不足1MB
            type = "KB";
            return format(size, type);
        }

        size = size / UNIT;
        if ((int) Math.floor(size / UNIT) <= 0) { // 不足1GB
            type = "MB";
            return format(size, type);
        }

        size = size / UNIT;
        if ((int) Math.floor(size / UNIT) <= 0) { // 不足1TB
            type = "GB";
            return format(size, type);
        }

        size = size / UNIT;
        if ((int) Math.floor(size / UNIT) <= 0) { // 不足1PB
            type = "TB";
            return format(size, type);
        }

        size = size / UNIT;
        if ((int) Math.floor(size / UNIT) <= 0) {
            type = "PB";
            return format(size, type);
        }
        return ">PB";
    }

    /**
     * 格式化字节大小为指定单位
     *
     * @param size 字节大小
     * @param type 单位类型
     * @return {@link String}
     */
    private static String format(double size, String type) {
        int precision;

        if (size * 100 % 10 > 0) {
            precision = 2;
        } else if (size * 10 % 10 > 0) {
            precision = 1;
        } else {
            precision = 0;
        }

        String formatStr = "%." + precision + "f";

        if ("KB".equals(type)) {
            return String.format(formatStr, (size)) + "KB";
        } else if ("MB".equals(type)) {
            return String.format(formatStr, (size)) + "MB";
        } else if ("GB".equals(type)) {
            return String.format(formatStr, (size)) + "GB";
        } else if ("TB".equals(type)) {
            return String.format(formatStr, (size)) + "TB";
        } else if ("PB".equals(type)) {
            return String.format(formatStr, (size)) + "PB";
        }
        return String.format(formatStr, (size)) + "B";
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public static String convertFileSizeToString(long size) {
        float fileSize = convertFileSize(size);
        if (size >= GB) {
            return String.format("%.1f GB" , fileSize);
        } else if (size >= MB) {
            return String.format(fileSize > 100 ? "%.0f MB" : "%.1f MB" , fileSize);
        } else if (size >= KB) {
            float f = (float) size / KB;
            return String.format(fileSize > 100 ? "%.0f KB" : "%.1f KB" , fileSize);
        } else {
            return String.format("%d B" , size);
        }
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public static float convertFileSize(long size) {
        if (size >= GB) {
            return (float) size / GB;
        } else if (size >= MB) {
            return (float) size / MB;
        } else if (size >= KB) {
            return (float) size / KB;
        } else {
            return size;
        }
    }
}
