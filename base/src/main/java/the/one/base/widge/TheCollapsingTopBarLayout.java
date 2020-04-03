package the.one.base.widge;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;

import com.google.android.material.appbar.AppBarLayout;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;


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
 * @date 2019/10/11 0011
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class TheCollapsingTopBarLayout extends QMUICollapsingTopBarLayout {

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    public interface AppBarStateChangeListener {
        void onStateChanged(State state, int offset);
    }

    private AppBarStateChangeListener mStateChangeListener;
    private TheCollapsingTopBarLayout.OffsetUpdateListener mOnOffsetChangedListener;


    public void setStateChangeListener(AppBarStateChangeListener stateChangeListener) {
        this.mStateChangeListener = stateChangeListener;
    }

    public TheCollapsingTopBarLayout(Context context) {
        super(context);
    }

    public TheCollapsingTopBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TheCollapsingTopBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // Add an OnOffsetChangedListener if possible
        final ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            if (mOnOffsetChangedListener == null) {
                mOnOffsetChangedListener = new TheCollapsingTopBarLayout.OffsetUpdateListener();
            }
            ((AppBarLayout) parent).addOnOffsetChangedListener(mOnOffsetChangedListener);
        }
    }

    private State mCurrentState = State.IDLE;

    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        OffsetUpdateListener() {
        }

        @Override
        public void onOffsetChanged(AppBarLayout layout, int verticalOffset) {
            if (null != mStateChangeListener) {
                State state ;
                if (verticalOffset == 0) {
                    state = State.EXPANDED;
                } else if (Math.abs(verticalOffset) >= layout.getTotalScrollRange()) {
                    state = State.COLLAPSED;
                } else {
                    state = State.IDLE;
                }
                if(state != mCurrentState){
                    mCurrentState = state;
                    mStateChangeListener.onStateChanged(mCurrentState, verticalOffset);
                }
            }
        }
    }

}
