package takephotodemo.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.bytedeco.javacv.CanvasFrame;

import takephotodemo.util.LogUtil;
import takephotodemo.util.PicUtil;

/**
 * 关闭拍照主窗口的listener
 * 
 * @author jinghuaixin
 * @date 2019/03/11
 * @Description:
 */
public class GuanBiMouseListener extends MouseAdapter implements MouseListener {

    private CanvasFrame frame;

    public GuanBiMouseListener(CanvasFrame frame) {
        super();
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            PicUtil.hiddeZhaoXiang(frame);
            System.exit(0);
        } catch (Exception e1) {
            e1.printStackTrace();
            LogUtil.getLog().error("退出照相系统异常!", e1);
        }
    }
}
