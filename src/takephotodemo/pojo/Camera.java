package takephotodemo.pojo;

/**
 * 照相机实体类
 * 
 * @author DELL
 * @date 2019/11/28
 */
public class Camera {
    /**
     * 标记是否照相（保存当前动画，停止录入）
     */
    private boolean state = true;

    public boolean getState() {

        return state;

    }

    public void setState(boolean state) {

        this.state = state;

    }
}
