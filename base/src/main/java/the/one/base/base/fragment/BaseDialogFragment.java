package the.one.base.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import the.one.base.R;
import the.one.base.util.QMUIDialogUtil;

/**
 * @author The one
 * @date 2018/10/31 0031
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public  abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        assert window != null;
        window.requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        window.getAttributes().windowAnimations = R.style.PopAnimStyle;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 获取屏幕高度，动态设置，解决状态栏变黑问题
        int screenHeight = getScreenHeight(getActivity());
        int statusBarHeight = getStatusBarHeight(getContext());
        int dialogHeight = screenHeight - statusBarHeight;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
    }

    private int getScreenHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    private int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    protected abstract int getLayout();
    protected abstract void initView(View view);

    public boolean ViewIsVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public void ViewGone(View view) {
        view.setVisibility(View.GONE);
    }

    public void ViewShow(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public String getTextString(TextView textView) {
        return textView.getText().toString();
    }

    public boolean strIsEmpty(String str, String tips) {
        if (str.isEmpty()) {
            showFailTips(tips);
            return true;
        }
        return false;
    }

    public void showFailTips(String tips) {
        QMUIDialogUtil.FailTipsDialog(getContext(), tips);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(),null);
        initView(view);
        return view;
    }

}
