package the.one.demo.widget.fireflower;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wayww on 2016/10/25.
 */

//烟花，控制整个烟花的动画，计算小火花的位置并绘制小火花。
public class Firework  {

    private final  String TAG = this.getClass().getSimpleName();

    private final static int DEFAULT_ELEMENT_COUNT = 20;
    private final static float DEFAULT_ELEMENT_SIZE = 13;//烟花大小
    private final static float DEFAULT_LAUNCH_SPEED = 20;
    private final static float DEFAULT_WIND_SPEED = 6;
    private final static float DEFAULT_GRAVITY = 6;

    private Paint mPaint;

    private int count ;     //count of element
    private int duration;   //持续时间 烟花的大小
    private int[] colors;   //烟花颜色种类
    private int color;      //颜色

    private float launchSpeed;      //发射速度
    private float windSpeed;        //风速

    private float gravity;
    private int windDirection = 0;      //1 or -1 决定风的方向，1为吹向右边，-1为左边
    private Location location;
    private float elementSize;

    private ValueAnimator animator;
    private float animatorValue;

    private ArrayList<Element> elements = new ArrayList<>();
    private AnimationEndListener listener;

    public Firework(Location location,int size,int duration){
        this.location = location;
        this.duration = duration;
        colors = baseColors;
        gravity = DEFAULT_GRAVITY;
        elementSize = size;
        launchSpeed = DEFAULT_LAUNCH_SPEED;
        windSpeed = DEFAULT_WIND_SPEED;
        count = DEFAULT_ELEMENT_COUNT;
        init();
    }

    private void init(){
        Random random = new Random();
        color = colors[random.nextInt(colors.length)];
        //给每个火花设定一个随机的方向 0-180
        for (int i = 0 ; i<count ; i++){
            elements.add(new Element(color, Math.toRadians(random.nextInt(360)), random.nextFloat()*launchSpeed));
        }
        mPaint = new Paint();
        mPaint.setColor(color);
      //  BlurMaskFilter maskFilter = new BlurMaskFilter(2, BlurMaskFilter.Blur.NORMAL);
      //  mPaint.setMaskFilter(maskFilter);

    }

    /**
     * 用一个ValueAnimator实现动画。
     * 由于发射速度是衰减的，所以animator = ValueAnimator.ofFloat(1,0);
     * 同时设定一个new AccelerateInterpolator(2)，即加速度是增长的。
     */
    public void fire(){
        animator = ValueAnimator.ofFloat(1,0);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator(2));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatorValue = (float) valueAnimator.getAnimatedValue();
                //计算每个火花的位置
                for (Element element : elements){
                    element.x = (float) (element.x + Math.cos(element.direction)*element.speed*animatorValue + windSpeed*windDirection);
                    element.y = (float) (element.y - Math.sin(element.direction)*element.speed*animatorValue + gravity*(1-animatorValue));
                }
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onAnimationEnd();
            }
        });
        animator.start();
    }

    public void setCount(int count){
        this.count = count;
    }

    public void setColors(int colors[]){
        this.colors = colors;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public void addAnimationEndListener(AnimationEndListener listener){
        this.listener = listener;
    }

    public void draw(Canvas canvas){
        mPaint.setAlpha((int) (225*animatorValue));
        for (Element element : elements){
            canvas.drawCircle(location.x + element.x, location.y + element.y, elementSize, mPaint);
        }
    }

    private static final int[] baseColors = {0xFFFF43,0x00E500,0x44CEF6,0xFF0040,0xFF00FFB7,0x008CFF
            ,0xFF5286,0x562CFF,0x2C9DFF,0x00FFFF,0x00FF77,0x11FF00,0xFFB536,0xFF4618,0xFF334B,0x9CFA18};

    interface AnimationEndListener{
        void onAnimationEnd();
    }

    static class Location{
        public float x;
        public float y;
        public Location(float x, float y){
            this.x = x;
            this.y = y;
        }
    }
}
