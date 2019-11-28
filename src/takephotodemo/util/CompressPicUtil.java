package takephotodemo.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片压缩工具类
 * 
 * @author jinghuaixin
 * @date 2019/11/28
 */
public class CompressPicUtil {

    /**
     * 图片压缩
     * 
     * @param image BufferedImage对象
     * @param file 文件对象，写入到磁盘
     * @param reSize 重新调整的大小，按长宽缩放
     * @param reVolume 重新调整占用的内存大小
     * @throws IOException
     */
    public static void compressPic(BufferedImage image, File file, double reSize, float reVolume) throws IOException {
        Thumbnails.of(image).scale(reSize).outputQuality(reVolume).toFile(file);
    }
}
