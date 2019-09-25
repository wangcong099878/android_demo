package the.one.base.widge.overscroll;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.qmuiteam.qmui.widget.QMUIObservableScrollView;

import java.util.ArrayList;
import java.util.List;

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

/**
 * @author The one
 * @date 2019/9/25 0025
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */


/**
 * https://github.com/Mixiaoxiao/OverScroll-Everywhere
 *
 * @author Mixiaoxiao 2016-08-31
 */
public class OverScrollScrollView extends ScrollView implements OverScrollDelegate.OverScrollable {

    private int mScrollOffset = 0;

    private List<OnScrollChangedListener> mOnScrollChangedListeners;

    private OverScrollDelegate mOverScrollDelegate;

    // ===========================================================
    // Constructors
    // ===========================================================
    public OverScrollScrollView(Context context) {
        super(context);
        createOverScrollDelegate(context);
    }

    public OverScrollScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createOverScrollDelegate(context);
    }

    public OverScrollScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createOverScrollDelegate(context);
    }

    public void addOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        if (mOnScrollChangedListeners == null) {
            mOnScrollChangedListeners = new ArrayList<>();
        }
        if (mOnScrollChangedListeners.contains(onScrollChangedListener)) {
            return;
        }
        mOnScrollChangedListeners.add(onScrollChangedListener);
    }

    public void removeOnScrollChangedListener(QMUIObservableScrollView.OnScrollChangedListener onScrollChangedListener) {
        if (mOnScrollChangedListeners == null) {
            return;
        }
        mOnScrollChangedListeners.remove(onScrollChangedListener);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OverScrollScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        createOverScrollDelegate(context);
    }

    // ===========================================================
    // createOverScrollDelegate
    // ===========================================================
    private void createOverScrollDelegate(Context context) {
        mOverScrollDelegate = new OverScrollDelegate(this);
    }

    // ===========================================================
    // Delegate
    // ===========================================================
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mOverScrollDelegate.onInterceptTouchEvent(ev)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mOverScrollDelegate.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        mOverScrollDelegate.draw(canvas);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                   int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return mOverScrollDelegate.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    // ===========================================================
    // OverScrollable, aim to call view internal methods
    // ===========================================================

    @Override
    public void absorbGlows(int velocityX, int velocityY) {

    }

    @Override
    public int superComputeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override
    public int superComputeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }

    @Override
    public int superComputeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    public void superOnTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
    }

    @Override
    public void superDraw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public boolean superAwakenScrollBars() {
        return super.awakenScrollBars();
    }

    @Override
    public boolean superOverScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                     int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
    }

    @Override
    public View getOverScrollableView() {
        return this;
    }

    @Override
    public OverScrollDelegate getOverScrollDelegate() {
        return mOverScrollDelegate;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollOffset = t;
        if (mOnScrollChangedListeners != null && !mOnScrollChangedListeners.isEmpty()) {
            for (OnScrollChangedListener listener : mOnScrollChangedListeners) {
                listener.onScrollChanged( l, t, oldl, oldt);
            }
        }
    }

    public int getScrollOffset() {
        return mScrollOffset;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

}