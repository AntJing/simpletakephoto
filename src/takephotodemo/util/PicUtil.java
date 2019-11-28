package takephotodemo.util;

import java.io.File;

import org.bytedeco.javacv.CanvasFrame;

/**
 * 
 * @author jinghuaixin
 * @date 2019/11/28
 */
public class PicUtil {
    /**
     * 文件目录
     */
    private static final String path = System.getProperty("user.dir");
    /**
     * 文件后缀
     */
    private static final String fileSuffix = "png";

    /**
     * 照片宽度
     */
    private static final int PIC_WIDTH = 300;
    /**
     * 照片长度
     */
    private static final int PIC_HEIGTH = 369;

    /**
     * 获取目录
     * 
     * @return
     */
    public static String getPath() {
        return path;

    }

    /**
     * 获取文件name
     * 
     * @return
     */
    public static String getFileName() {
        return new StringBuilder().append(System.currentTimeMillis()).append(".").append(fileSuffix).toString();
    }

    /**
     * 得到副本文件名称
     * 
     * @return
     */
    public static String getBackupFileName() {
        return new StringBuilder().append(ProductFileNameSeq.getFileNameSeq()).append(".").append(fileSuffix)
            .toString();
    }

    /**
     * 得到文件的全路径
     * 
     * @return
     */
    public static String getSaveFileFullPath() {
        return new StringBuilder().append(path).append(File.separator).append(getFileName()).toString();
    }

    /**
     * 得到保存的第一份副本文件的路径
     * 
     * @return
     */
    public static String getBackupFileFullPath() {
        return new StringBuilder().append(path).append(File.separator).append(getBackupFileName()).toString();
    }

    public static String getFilesuffix() {
        return fileSuffix;
    }

    public static void main(String[] args) {
        System.out.println(path);
    }

    /**
     * 隐藏照相窗口
     * 
     * @param frame
     * @throws Exception
     */
    public static void hiddeZhaoXiang(CanvasFrame frame) throws Exception {
        frame.dispose();
    }

    public static int getPicWidth() {
        return PIC_WIDTH;
    }

    public static int getPicHeigth() {
        return PIC_HEIGTH;
    }

}
