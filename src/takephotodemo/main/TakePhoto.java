package takephotodemo.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import takephotodemo.listener.ChongPaiMouseListener;
import takephotodemo.listener.GuanBiMouseListener;
import takephotodemo.listener.PaiZhaoMouseListener;
import takephotodemo.panel.MyPanel;
import takephotodemo.pojo.Camera;
import takephotodemo.util.LogUtil;

/**
 * 
 * @author jinghuaixin
 * @date 2019/11/28
 */
public class TakePhoto {
    private static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
    private static CanvasFrame canvas = null;

    private static Map<String, Object> map;

    public static void main(String[] args) {
        try {
            takePhoto();
        } catch (Exception | InterruptedException e) {
            e.printStackTrace();
            LogUtil.getLog().error("客户端照相异常!", e);
        }
    }

    public static void takePhoto() throws Exception, InterruptedException {
        int captureWidth = 640;
        int captureHeight = 480;

        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.setImageWidth(captureWidth);
        grabber.setImageHeight(captureHeight);
        try {
            grabber.start();
        } catch (Exception e) {
            LogUtil.getLog().error("照相客户端初始化异常!", e);
            JOptionPane.showMessageDialog(null, "摄像头准备失败:请检查摄像头是否正确接入!", "提示:", JOptionPane.WARNING_MESSAGE);
            if (grabber != null) {
                grabber.close();
            }
            return;
        }
        // 开始获取摄像头数据
        canvas = new CanvasFrame("照相");
        // CanvasFrame canvas = new CanvasFrame("照相");//新建一个窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);

        Camera camera = new Camera();
        MyPanel shangChuanPanel = new MyPanel(canvas, map);
        // 增加按钮
        Container con = canvas.getContentPane();

        JPanel optionPanel = new JPanel();
        JButton btn_paiZhao = new JButton("拍照");
        JButton btn_chongPai = new JButton("重拍");

        JButton btn_guanBi = new JButton("关闭");
        JLabel info = new JLabel("<html><font color='red'>(连接成功!)</font></html>");
        JPanel bott = new JPanel();
        // 向面板添加按钮
        optionPanel.add(btn_chongPai, BorderLayout.NORTH);
        optionPanel.add(btn_paiZhao, BorderLayout.NORTH);
        optionPanel.add(btn_guanBi, BorderLayout.NORTH);

        con.add(optionPanel, BorderLayout.NORTH);
        bott.add(info, BorderLayout.SOUTH);
        con.add(bott, BorderLayout.SOUTH);
        JPanel east = new JPanel();
        east.setSize(new Dimension(600, 600));
        JLabel eastlabel = new JLabel(getSapce(220));

        if (shangChuanPanel != null) {
            east.add(shangChuanPanel, BorderLayout.SOUTH);
            shangChuanPanel.add(eastlabel);

            con.add(shangChuanPanel, BorderLayout.EAST);

        }
        // 添加监听事件
        PaiZhaoMouseListener pzListener = new PaiZhaoMouseListener(converter, grabber, camera, shangChuanPanel);
        btn_paiZhao.addMouseListener(pzListener);
        btn_guanBi.addMouseListener(new GuanBiMouseListener(canvas));
        btn_chongPai.addMouseListener(new ChongPaiMouseListener(camera));
        Frame grab = grabber.grab();
        shangChuanPanel.setMat(converter.convertToMat(grab));
        while (true) {
            if (!canvas.isDisplayable()) {// 窗口是否关闭
                grabber.stop();// 停止抓取
                // System.exit(2);//退出
                break;
            }
            if (camera.getState()) {
                grab = grabber.grab();// 获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像
            }
            if (grab != null) {
                canvas.showImage(grab);
            }
            Thread.sleep(50);// 50毫秒刷新一次图像
        }
    }

    /**
     * 扩展canvasFrame的右边空间
     * 
     * @param count
     * @return
     */
    public static String getSapce(int count) {
        count = count < 0 ? 0 : count;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * @return the canvas
     */
    public static CanvasFrame getCanvas() {
        return canvas;
    }

}
