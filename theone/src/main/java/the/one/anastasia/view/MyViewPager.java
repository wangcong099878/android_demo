package the.one.anastasia.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 解决左右滑动冲突的问题
 */
public class MyViewPager extends ViewPager {

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean touched = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                touched=true;
                break;
            case MotionEvent.ACTION_MOVE:
                touched=true;
                break;
            case MotionEvent.ACTION_UP:
                touched=false;
                break;
            case MotionEvent.ACTION_CANCEL:
                touched=true;
                break;
        }
        return super.onTouchEvent(ev);
    }

    public boolean isTouched() {
        return touched;
    }
}
