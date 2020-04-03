package the.one.base.widge.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

/**
 * @author cginechen
 * @date 2016-12-11
 */

public class PullRefreshLayout extends QMUIPullRefreshLayout {

    public static final int STYLE_QMUI = 0;
    public static final int STYLE_WW = 1;
    public static final int STYLE_FLYME = 2;

    private static int mStyle = STYLE_FLYME;

    public PullRefreshLayout(Context context) {
        super(context);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static void setLoadingStyle(int style) {
        mStyle = style;
    }

    @Override
    protected View createRefreshView() {
        if (mStyle == STYLE_WW) {
            return new WWLoadingView(getContext());
        } else if (mStyle == STYLE_FLYME) {
            return new FlymeStyleLoadingView(getContext());
        } else {
            return new RefreshView(getContext());
        }
    }

    @Override
    protected int calculateTargetOffset(int target, int targetInitOffset, int targetRefreshOffset, boolean enableOverPull) {
        return super.calculateTargetOffset(target, targetInitOffset, 350, false);
    }

}
