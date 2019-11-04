package the.one.base.base.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

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
        int screenHeight = QMUIDisplayHelper.getScreenHeight(getContext());
        int statusBarHeight = QMUIDisplayHelper.getStatusBarHeight(getContext());
        int dialogHeight = screenHeight - statusBarHeight;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
    }

    protected abstract int getLayout();
    protected abstract void initView(View view);

    public boolean ViewIsVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public void ViewGone(View... views) {
        for (View view: views){
            if(null != view && view.getVisibility() !=View.GONE)
                view.setVisibility(View.GONE);
        }
    }

    public void ViewShow(View... views) {
        for (View view: views){
            if(null != view && view.getVisibility() !=View.VISIBLE)
                view.setVisibility(View.VISIBLE);
        }
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
