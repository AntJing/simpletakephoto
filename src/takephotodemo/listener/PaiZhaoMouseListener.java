package takephotodemo.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import takephotodemo.panel.MyPanel;
import takephotodemo.pojo.Camera;
import takephotodemo.util.LogUtil;
import takephotodemo.util.PicUtil;

/**
 * @author jinghuaixin
 * @date 2019/03/11
 * @Description:点击拍照按钮的listener
 */
public class PaiZhaoMouseListener extends MouseAdapter {
    private OpenCVFrameConverter.ToIplImage converter;

    private OpenCVFrameGrabber grabber;

    private Camera camera;

    private MyPanel mp;

    @Override
    public void mouseClicked(MouseEvent e) {
        // 暂停获取图像
        camera.setState(false);
        opencv_core.Mat mat = null;
        try {
            mat = converter.convertToMat(grabber.grabFrame());
        } catch (Exception e1) {
            e1.printStackTrace();
            LogUtil.getLog().error("照相错误!", e1);
        }
        opencv_imgcodecs.imwrite(PicUtil.getBackupFileFullPath(), mat);
        this.mp.setFileName(PicUtil.getBackupFileName());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
            LogUtil.getLog().error("照相错误!", e1);
        }
        this.mp.repaint();

    }

    public PaiZhaoMouseListener() {
        super();
    }

    public PaiZhaoMouseListener(ToIplImage converter, OpenCVFrameGrabber grabber) {
        super();
        this.converter = converter;
        this.grabber = grabber;
    }

    public PaiZhaoMouseListener(ToIplImage converter, OpenCVFrameGrabber grabber, Camera camera) {
        super();
        this.converter = converter;
        this.grabber = grabber;
        this.camera = camera;
    }

    public PaiZhaoMouseListener(ToIplImage converter, OpenCVFrameGrabber grabber, Camera camera, MyPanel mp) {
        super();
        this.converter = converter;
        this.grabber = grabber;
        this.camera = camera;
        this.mp = mp;
    }

    public OpenCVFrameConverter.ToIplImage getConverter() {
        return converter;
    }

    public void setConverter(OpenCVFrameConverter.ToIplImage converter) {
        this.converter = converter;
    }

    public OpenCVFrameGrabber getGrabber() {
        return grabber;
    }

    public void setGrabber(OpenCVFrameGrabber grabber) {
        this.grabber = grabber;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public MyPanel getMp() {
        return mp;
    }

    public void setMp(MyPanel mp) {
        this.mp = mp;
    }

}
