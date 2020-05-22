package the.one.base.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Window window = getDialog().getWindow();
        assert window != null;
        window.getAttributes().windowAnimations = R.style.PopAnimStyle;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 获取屏幕高度，动态设置，解决状态栏变黑问题
        int screenHeight = QMUIDisplayHelper.getScreenHeight(getContext());
        int statusBarHeight = QMUIDisplayHelper.getStatusBarHeight(getContext());
        int dialogHeight = screenHeight - statusBarHeight;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(),null);
        initView(view);
        return view;
    }

    protected abstract int getLayout();
    protected abstract void initView(View view);

    protected String getTextString(TextView textView) {
        return textView.getText().toString();
    }

    protected boolean strIsEmpty(String str, String tips) {
        if (TextUtils.isEmpty(str)) {
            showFailTips(tips);
            return true;
        }
        return false;
    }

    protected void showFailTips(String tips) {
        QMUIDialogUtil.FailTipsDialog(getContext(), tips);
    }

}
