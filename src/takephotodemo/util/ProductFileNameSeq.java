package takephotodemo.util;

/**
 * 申请备份文件的文件名，做成单例的这样每个客户端只有一个实例
 * 
 * @author jinghuaixin
 * @date 2019/11/28
 */
public class ProductFileNameSeq {

    private static class FileNameSeq {
        private static long dyFileNameSeq = 0;
        static {
            dyFileNameSeq = System.currentTimeMillis();
        }
    }

    /**
     * 获取文件名称
     * 
     * @return
     */
    public static String getFileNameSeq() {
        return String.valueOf(FileNameSeq.dyFileNameSeq);
    }

    /**
     * 修改文件名称
     */
    public static void changeFileNameNext() {
        ++FileNameSeq.dyFileNameSeq;
    }
}
