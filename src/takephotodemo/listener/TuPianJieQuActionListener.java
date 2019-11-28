package takephotodemo.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import takephotodemo.main.TakePhoto;
import takephotodemo.panel.MyFrame;
import takephotodemo.panel.MyPanel;
import takephotodemo.util.CompressPicUtil;
import takephotodemo.util.LogUtil;
import takephotodemo.util.PicUtil;
import takephotodemo.util.ProductFileNameSeq;

/**
 * 截取图片并保存，重要的类
 * 
 * @author jinghuaixin
 * @date 2019/11/28
 */
public class TuPianJieQuActionListener implements ActionListener {

    private MyFrame frame;

    private BufferedImage image;

    private MyPanel panle;

    @Override
    public void actionPerformed(ActionEvent event) {
        String shangChuan = "上传";
        String quXiao = "取消";
        if (quXiao.equals(event.getActionCommand())) { // 点击了取消按钮
            // frame.dispose();
            frame.getCf().setVisible(true);
            frame.dispose();
        } else if (shangChuan.equals(event.getActionCommand())) {
            try {
                // 删除前一个保存的备份文件
                for (Component co : frame.getCf().getRootPane().getContentPane().getComponents()) {
                    if (co instanceof MyPanel) {
                        ((MyPanel)co).setFileName("touxiang.jpg");// 设置图像路径，显示默认图片
                    }
                }
                String fileName = PicUtil.getBackupFileName();
                if (null != fileName && !"touxiang.jpg".equals(fileName)) {
                    File file = new File(PicUtil.getBackupFileFullPath());
                    if (file != null) {
                        file.delete();
                        ProductFileNameSeq.changeFileNameNext();
                    }
                }
                File file = new File(PicUtil.getSaveFileFullPath());
                // 写出到文件
                // ImageIO.write(this.image, PicUtil.getFilesuffix(), file);
                // 压缩写成
                CompressPicUtil.compressPic(image, file, 0.5, 0.5f);
                frame.dispose();
                panle.updateUI();// 更新画布panel

                // 转换成BASE64编码，可以通过网络传输
                /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                   ImageIO.write(this.image, PicUtil.getFilesuffix(), baos);
                byte[] bytes = baos.toByteArray();// 转换成字节
                
                String png_base64 = "data:image/png;base64," + Base64.encodeBase64String(bytes);// 转换成base64串
                png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");// 删除 \r\n
                // this.frame.getMap().put("photo", png_base64);
                System.out.println(png_base64);
                */

                // 退出
                // exit();
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.getLog().error("图片转base64格式出现异常!", e);
            }
        }
    }

    /**
     * 退出照相
     */
    private void exit() {
        // 隐藏照相窗口
        TakePhoto.getCanvas().dispose();
        frame.dispose();
        // System.exit(0);
    }

    public TuPianJieQuActionListener(MyFrame frame) {
        super();
        this.frame = frame;
    }

    public TuPianJieQuActionListener(MyFrame frame, BufferedImage image) {
        super();
        this.frame = frame;
        this.image = image;
    }

    /**
     * @param mf
     * @param img
     * @param myPanel
     */
    public TuPianJieQuActionListener(MyFrame mf, BufferedImage img, MyPanel myPanel) {
        this(mf, img);
        this.panle = myPanel;
    }

    public MyFrame getFrame() {
        return frame;
    }

    public void setFrame(MyFrame frame) {
        this.frame = frame;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
