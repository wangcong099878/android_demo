package the.one.demo.widget.fireflower;

/**
 * Created by wayww on 2016/10/25.
 */

//烟花的小火花，存放颜色，飞行方向，飞行速度这三个变量。
public class Element {
    //颜色
    public int color;
    //方向
    public Double direction;
    //速度
    public float speed;
    public float x = 0;
    public float y = 0;

    public Element(int color, Double direction, float speed){
        this.color = color;
        this.direction = direction;
        this.speed = speed;
    }
}
