package takephotodemo.panel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bytedeco.javacv.CanvasFrame;

import takephotodemo.listener.TuPianJieQuActionListener;
import takephotodemo.util.PicUtil;

/**
 * 用于显示截图的Frame
 * 
 * @author jinghuaixin
 * @date 2019/03/11
 * @Description:
 */
public class MyFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private CanvasFrame cf;
    private Map<String, Object> map;

    private PicPanel picPanel;
    private int width = PicUtil.getPicWidth();
    private int height = PicUtil.getPicHeigth();

    private JButton btn_shangChuan;
    private JButton btn_quXiao;

    /**
     * @param picPanel
     * @param width
     * @param height
     * @param myPanel
     * @throws HeadlessException
     */
    public MyFrame(PicPanel picPanel, int width, int height, CanvasFrame cf, Map<String, Object> map)
        throws HeadlessException {
        super();
        this.picPanel = picPanel;
        this.width = width;
        this.height = height;
        this.cf = cf;
        this.map = map;

        Container c = this.getContentPane();
        c.add(this.picPanel, BorderLayout.CENTER);
        this.setSize(this.width, this.height);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        this.setTitle("照片预览");

        JPanel panel = new JPanel();
        btn_shangChuan = new JButton("上传");

        btn_quXiao = new JButton("取消");
        btn_quXiao.addActionListener(new TuPianJieQuActionListener(this));
        panel.add(btn_shangChuan, BorderLayout.SOUTH);
        panel.add(btn_quXiao, BorderLayout.SOUTH);
        c.add(panel, BorderLayout.SOUTH);

        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public PicPanel getPicPanel() {
        return picPanel;
    }

    public void setPicPanel(PicPanel picPanel) {
        this.picPanel = picPanel;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public JButton getBtn_shangChuan() {
        return btn_shangChuan;
    }

    public void setBtn_shangChuan(JButton btn_shangChuan) {
        this.btn_shangChuan = btn_shangChuan;
    }

    public JButton getBtn_quXiao() {
        return btn_quXiao;
    }

    public void setBtn_quXiao(JButton btn_quXiao) {
        this.btn_quXiao = btn_quXiao;
    }

    public CanvasFrame getCf() {
        return cf;
    }

    public void setCf(CanvasFrame cf) {
        this.cf = cf;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
