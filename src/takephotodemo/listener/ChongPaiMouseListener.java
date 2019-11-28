package takephotodemo.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import takephotodemo.pojo.Camera;

/**
 * @author jinghuaixin
 * @date 2019/03/11
 * @Description:重拍按钮的listener
 */
public class ChongPaiMouseListener extends MouseAdapter {

    private Camera camera;

    @Override
    public void mouseClicked(MouseEvent e) {
        /**
         * 再次动态获取图像
         */
        camera.setState(true);
    }

    public ChongPaiMouseListener(Camera camera) {
        super();
        this.camera = camera;
    }

    public ChongPaiMouseListener() {
        super();
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

}
