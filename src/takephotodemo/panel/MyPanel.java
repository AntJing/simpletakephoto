package takephotodemo.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.CanvasFrame;

import takephotodemo.listener.TuPianJieQuActionListener;
import takephotodemo.util.LogUtil;
import takephotodemo.util.PicUtil;

/**
 * @author jinghuaixin
 * @date 2019/03/11
 * @Description:拍完照片后显示整张照片的panel
 */
public class MyPanel extends JPanel implements MouseMotionListener, MouseListener {

    private CanvasFrame cf;

    private Map<String, Object> map;

    private MyFrame mf;

    private static final int leftX = 20;
    private static final int topY = 0;
    private static final int rightX = leftX + 640;
    private static final int bottomY = 480;
    private static final Point topLeft = new Point(leftX, topY);
    private static final Point bottomRight = new Point(rightX, bottomY);

    private static final int pic_width = PicUtil.getPicWidth();
    private static final int pic_height = PicUtil.getPicHeigth();

    private Point mousePoint;
    private double mousewidthLeft;
    private double mouseheightLeft;
    private double mousewidthRight;
    private double mouseheightRight;

    private JButton jianQie;
    private Mat mat;

    private static final long serialVersionUID = 1L;

    private BufferedImage image = null;
    /**
     * 内置图片，显示在没有图片时的背景
     */
    private String fileName = "touxiang.jpg";
    /** 矩形的起点 左上角* */
    private Point rectStart = null;

    /** 矩形的终点 右下角* */
    private Point rectStop = null;

    /** 是否绘制虚线矩形* */
    private boolean dottedTag = true;

    public MyPanel() {
        this.setBackground(Color.WHITE);
        this.setSize(1000, 1000);
        rectStart = new Point(leftX, 0);
        rectStop = new Point(pic_width, pic_height);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    public MyPanel(CanvasFrame cf) {
        this();
        this.cf = cf;
    }

    public MyPanel(CanvasFrame cf, Map<String, Object> map) {
        this();
        this.cf = cf;
        this.map = map;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            if ("touxiang.jpg".equals(fileName)) {
                image = ImageIO.read(new File("resources/touxiang.jpg"));
            } else {
                image = ImageIO.read(new File(PicUtil.getBackupFileFullPath()));
            }
            g.drawImage(image, 20, 0, 640, 480, null);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.getLog().error("获取默认图片异常!", e);
        }
        Graphics2D g2 = (Graphics2D)g;

        // ** 起点终点的最小值作为起点* *//*
        Point min = new Point(0, 0);
        min.x = Math.min(rectStop.x, rectStart.x);
        min.y = Math.min(rectStop.y, rectStart.y);

        g2.setStroke(new BasicStroke());
        g2.setColor(Color.RED);// new Color(198, 214, 239)
        g.drawRect(min.x, min.y, pic_width, pic_height);
    }

    /** *鼠标拖动 */
    public void mouseDragged(MouseEvent arg0) {
        /** 随着鼠标的拖动 改变终点* */
        mousePoint = arg0.getPoint();

        if (isContainsMouse()) {
            // 默认不改变位置
            double startX = mousePoint.getX() - mousewidthLeft;
            double startY = mousePoint.getY() - mouseheightLeft;
            double stopX = mousePoint.getX() + mousewidthRight;
            double stopY = mousePoint.getY() + mouseheightRight;
            if (mousePoint.getY() - topLeft.getY() <= mouseheightLeft) {// 上移的时候越界
                startY = topLeft.getY();
            }
            if (bottomRight.getY() - mousePoint.getY() <= mouseheightRight) {// 下移越界
                stopY = bottomRight.getY();
                startY = bottomRight.getY() - pic_height - 5;
            }
            if (mousePoint.getX() - topLeft.getX() <= mousewidthLeft) {// 左移越界
                startX = topLeft.getX();
            }
            if (bottomRight.getX() - mousePoint.getX() <= mousewidthRight) {// 右移越界
                stopX = bottomRight.getX();
                startX = bottomRight.getX() - pic_width - 20;
            }
            rectStart.setLocation(startX, startY);
            rectStop.setLocation(stopX, stopY);
            this.repaint();
        }

    }

    public void mouseMoved(MouseEvent arg0) {

    }

    /** 鼠标按键在组件上单击（按下并释放）时调用* */
    public void mouseClicked(MouseEvent e) {
        if (2 == e.getClickCount()) {// 如果
            if (mf != null) {
                mf.dispose();
            }
            Point min = new Point(0, 0);
            min.x = Math.min(rectStop.x, rectStart.x);
            min.y = Math.min(rectStop.y, rectStart.y);
            BufferedImage img = image.getSubimage(min.x - 20, min.y, pic_width, pic_height);
            PicPanel picPanel = new PicPanel(img, pic_width, pic_height);
            mf = new MyFrame(picPanel, pic_width, pic_height + 70, this.getCf(), this.getMap());
            mf.getBtn_shangChuan().addActionListener(new TuPianJieQuActionListener(mf, img, this));
        }
    }

    /** 鼠标按键在组件上按下时调用 */
    public void mousePressed(MouseEvent arg0) {
        // 记录鼠标位置
        mousePoint = arg0.getPoint();
        if (isContainsMouse()) {
            mouseheightLeft = mousePoint.getY() - rectStart.getY();
            mousewidthLeft = mousePoint.getX() - rectStart.getX();
            mouseheightRight = rectStop.getY() - mousePoint.getY();
            mousewidthRight = rectStop.getX() - mousePoint.getX();
        }
        // 判断鼠标位置是否在图形内部

    }

    /** 鼠标按钮在组件上释放时调用* */
    public void mouseReleased(MouseEvent arg0) {
        Point mousePoint = arg0.getPoint();
        if (isContainsMouse()) {
            boolean isChange = false;
            // 默认不改变位置
            if (mousePoint.getY() - topLeft.getY() <= mouseheightLeft) {// 上移的时候越界
                rectStart.y = (int)topLeft.getY();
                isChange = true;
            }
            if (bottomRight.getY() - mousePoint.getY() <= mouseheightRight) {// 下移越界
                rectStop.y = (int)bottomRight.getY();
                rectStart.y = (int)(bottomRight.getY() - pic_height - 5);
                isChange = true;
            }
            if (mousePoint.getX() - topLeft.getX() <= mousewidthLeft) {// 左移越界
                rectStart.x = (int)topLeft.getX();
                isChange = true;
            }
            if (bottomRight.getX() - mousePoint.getX() <= mousewidthRight) {// 右移越界
                rectStop.x = (int)bottomRight.getX();
                rectStart.x = (int)(bottomRight.getX() - pic_width - 20);
            }
            if (isChange)
                this.repaint();
        }

    }

    /** 鼠标进入到组件上时调用* */
    public void mouseEntered(MouseEvent arg0) {

    }

    /** 鼠标离开组件时调用* */
    public void mouseExited(MouseEvent arg0) {

    }

    public MyPanel(JButton jianQie, Mat mat) {
        super();
        this.jianQie = jianQie;
        this.mat = mat;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Point getRectStart() {
        return rectStart;
    }

    public void setRectStart(Point rectStart) {
        this.rectStart = rectStart;
    }

    public Point getRectStop() {
        return rectStop;
    }

    public void setRectStop(Point rectStop) {
        this.rectStop = rectStop;
    }

    public boolean isDottedTag() {
        return dottedTag;
    }

    public void setDottedTag(boolean dottedTag) {
        this.dottedTag = dottedTag;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public JButton getJianQie() {
        return jianQie;
    }

    public void setJianQie(JButton jianQie) {
        this.jianQie = jianQie;
    }

    public Mat getMat() {
        return mat;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }

    private boolean isContainsMouse() {
        boolean isContains = false;

        if ((mousePoint.getX() > rectStart.getX() && mousePoint.getY() > rectStart.getY())
            && (mousePoint.getX() < rectStop.getX() && mousePoint.getY() < rectStop.getY())) {
            isContains = true;
        }

        return isContains;
    }

    public Point getMousePoint() {
        return mousePoint;
    }

    public void setMousePoint(Point mousePoint) {
        this.mousePoint = mousePoint;
    }

    public double getMousewidthLeft() {
        return mousewidthLeft;
    }

    public void setMousewidthLeft(double mousewidthLeft) {
        this.mousewidthLeft = mousewidthLeft;
    }

    public double getMouseheightLeft() {
        return mouseheightLeft;
    }

    public void setMouseheightLeft(double mouseheightLeft) {
        this.mouseheightLeft = mouseheightLeft;
    }

    public double getMousewidthRight() {
        return mousewidthRight;
    }

    public void setMousewidthRight(double mousewidthRight) {
        this.mousewidthRight = mousewidthRight;
    }

    public double getMouseheightRight() {
        return mouseheightRight;
    }

    public void setMouseheightRight(double mouseheightRight) {
        this.mouseheightRight = mouseheightRight;
    }

    public static int getLeftx() {
        return leftX;
    }

    public static int getTopy() {
        return topY;
    }

    public static int getRightx() {
        return rightX;
    }

    public static int getBottomy() {
        return bottomY;
    }

    public static Point getTopleft() {
        return topLeft;
    }

    public static Point getBottomright() {
        return bottomRight;
    }

    public static int getPicWidth() {
        return pic_width;
    }

    public static int getPicHeight() {
        return pic_height;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CanvasFrame getCf() {
        return cf;
    }

    public void setCf(CanvasFrame cf) {
        this.cf = cf;
    }

    public MyFrame getMf() {
        return mf;
    }

    public void setMf(MyFrame mf) {
        this.mf = mf;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}
