package the.one.demo.widget.fireflower;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import java.util.LinkedList;


/**
 * Created by wayww on 2016/10/25.
 */

public class FireworkView extends View {

    private final String TAG = this.getClass().getSimpleName();
    private LinkedList<Firework> fireworks = new LinkedList<>();

    public FireworkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FireworkView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FireworkView(Context context) {
        super(context);
    }

    /**
     * 用LinkedList<Firework>保存正在动画的Firework，
     * 如果里面Firework的数量不为0就不断地重绘view以实现动画，为0时不重绘。
     *
     */
    public void launch(float x, float y,int size,int duration){
        final Firework firework = new Firework(new Firework.Location(x, y),size, duration);
        firework.addAnimationEndListener(new Firework.AnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                //动画结束后把firework移除，当没有firework时不会刷新页面
                fireworks.remove(firework);
            }
        });
        fireworks.add(firework);
        firework.fire();
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i =0 ; i<fireworks.size(); i++){
            fireworks.get(i).draw(canvas);
        }
        if (fireworks.size()>0)
            invalidate();
    }


}
