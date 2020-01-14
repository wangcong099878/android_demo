package the.one.base.widge;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;


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
 * @date 2020/1/8 0008
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ThePopupWindow extends PopupWindow {

    public enum AnimStyle {
        TOP_2_BOTTOM,
        BOTTOM_2_TOP,
        LEFT_2_RIGHT,
        RIGHT_2_LEFT;
    }

    /**
     * 根布局
     */
    private View mRootView;
    /**
     * 内容层
     */
    private View mContentView;
    /**
     * 停靠位置
     */
    private View mAnchorView;

    /**
     * 动画效果
     */
    private AnimStyle mAnimStyle = AnimStyle.TOP_2_BOTTOM;

    /**
     * 进入动画
     */
    private TranslateAnimation mEnterAnim;
    /**
     * 退出动画
     */
    private TranslateAnimation mExitAnim;

    /**
     * 进出动画默认时间
     */
    private int mDefaultTime = 500;

    /**
     * 进入动画显示时间
     */
    private long mEnterTime = mDefaultTime;

    /**
     * 退出动画显示时间
     */
    private long mExitTime = mDefaultTime;

    public ThePopupWindow(Context context, View mRootView, View mContentView, View mAnchorView) {
        super(context);
        this.mRootView = mRootView;
        this.mContentView = mContentView;
        this.mAnchorView = mAnchorView;
        init();
    }

    private void init() {
        setContentView(mContentView);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int height = mRootView.getHeight() - mAnchorView.getHeight();
        int width = mRootView.getWidth();
        setWidth(width);
        setHeight(height);
    }

    private void initAnim() {
        int[] enter = {0, 0, 0, 0};
        int[] exit = {0, 0, 0, 0};
        switch (mAnimStyle) {
            case TOP_2_BOTTOM:
                enter[2] = -1;
                exit[3] = -1;
                break;
            case BOTTOM_2_TOP:
                enter[2] = 1;
                exit[3] = 1;
                break;
            case LEFT_2_RIGHT:
                enter[0] = -1;
                exit[1] = -1;
                break;
            case RIGHT_2_LEFT:
                enter[0] = 1;
                exit[1] = 1;
                break;
        }
        mEnterAnim = getAnimation(enter);
        mExitAnim = getAnimation(exit);
        mEnterAnim.setDuration(mEnterTime);
        mExitAnim.setDuration(mExitTime);
    }

    private TranslateAnimation getAnimation(int[] anim) {
        return new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, anim[0],
                TranslateAnimation.RELATIVE_TO_SELF, anim[1],
                TranslateAnimation.RELATIVE_TO_SELF, anim[2],
                TranslateAnimation.RELATIVE_TO_SELF, anim[3]);
    }

    public void show() {
        initAnim();
        showAsDropDown(mAnchorView);
        mContentView.startAnimation(mEnterAnim);
    }

    public void hide() {
        hide(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void hide(Animation.AnimationListener listener) {
        mExitAnim.setAnimationListener(listener);
        mContentView.startAnimation(mExitAnim);
    }

    public AnimStyle getAnimStyle() {
        return mAnimStyle;
    }

    public void setAnimStyle(AnimStyle mAnimStyle) {
        this.mAnimStyle = mAnimStyle;
    }

    public TranslateAnimation getEnterAnim() {
        return mEnterAnim;
    }

    public void setEnterAnim(TranslateAnimation mEnterAnim) {
        this.mEnterAnim = mEnterAnim;
    }

    public TranslateAnimation getExitAnim() {
        return mExitAnim;
    }

    public void setExitAnim(TranslateAnimation mExitAnim) {
        this.mExitAnim = mExitAnim;
    }

    public int getDefaultTime() {
        return mDefaultTime;
    }

    public void setDefaultTime(int mDefaultTime) {
        this.mDefaultTime = mDefaultTime;
    }

    public long getEnterTime() {
        return mEnterTime;
    }

    public void setEnterTime(long mEnterTime) {
        this.mEnterTime = mEnterTime;
    }

    public long getExitTime() {
        return mExitTime;
    }

    public void setExitTime(long mExitTime) {
        this.mExitTime = mExitTime;
    }
}
