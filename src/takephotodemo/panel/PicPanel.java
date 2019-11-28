package takephotodemo.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import takephotodemo.util.PicUtil;

/**
 * @author jinghuaixin
 * @date 2019/03/11
 * @Description:用于显示截取以后的图片
 */
public class PicPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BufferedImage image;
    private int width = PicUtil.getPicWidth();
    private int height = PicUtil.getPicHeigth();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, width, height, null);
    }

    public PicPanel(BufferedImage image) {
        super();
        this.image = image;
    }

    public PicPanel(BufferedImage image, int width, int height) {
        super();
        this.image = image;
        this.width = width;
        this.height = height;
    }

}
