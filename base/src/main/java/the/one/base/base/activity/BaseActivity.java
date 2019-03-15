package the.one.base.base.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import the.one.base.R;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.base.view.BaseView;
import the.one.base.util.EventBusUtil;
import the.one.base.util.GlideUtil;
import the.one.base.util.QMUIDialogUtil;
import the.one.base.util.ToastUtil;
import the.one.base.widge.MyTopBarLayout;
import the.one.base.widge.StatusLayout;


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
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseActivity extends QMUIActivity implements BaseView {

    protected final String TAG = this.getClass().getSimpleName();

    protected abstract int getContentViewId();

    protected abstract void initView(View mRootView);

    protected boolean LightMode() {
        return false;
    }

    protected boolean showTitleBar() {
        return true;
    }

    /**
     * 获取Presenter实例，子类实现
     */
    public abstract BasePresenter getPresenter();

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    protected StatusLayout mStatusLayout;
    protected MyTopBarLayout mTopLayout;
    protected QMUITipDialog loadingDialog;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LightMode())
            QMUIStatusBarHelper.setStatusBarLightMode(this);
        View mBody = getView(getContentViewId());
        View mRootView;
        if (showTitleBar()) {
            mRootView = getView(R.layout.base_fragment);
            mStatusLayout = mRootView.findViewById(R.id.status_layout);
            mTopLayout = mRootView.findViewById(R.id.top_layout);
            mStatusLayout.addView(mBody, -1, -1);
        } else {
            mRootView = mBody;
        }
        if (getPresenter() != null) {
            getPresenter().attachView(this);
        }
        setContentView(mRootView);
        unbinder = ButterKnife.bind(this, mBody);
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        initView(mRootView);
    }

    public View getView(int layoutId) {
        return getLayoutInflater().inflate(layoutId, null);
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(this, 150);
    }

    protected void initFragmentBack(String title) {
        if (null != mTopLayout) {
            mTopLayout.setTitle(title);
            addTopBarBackBtn();
        }
    }

    protected void addTopBarBackBtn() {
        mTopLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void showLoadingDialog(String msg) {
        loadingDialog = QMUIDialogUtil.LoadingTipsDialog(this, msg);
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (null != loadingDialog && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }

    @Override
    public void showProgressDialog(int percent) {

    }

    @Override
    public void showProgressDialog(int percent, int total) {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void showContentPage() {
        if (null != mStatusLayout)
            mStatusLayout.showContent();
    }

    @Override
    public void showLoadingPage() {
        if (null != mStatusLayout)
            mStatusLayout.showLoading();
    }

    @Override
    public void showEmptyPage(String title) {
        showEmptyPage(title, "", null);
    }

    @Override
    public void showEmptyPage(String title, View.OnClickListener listener) {
        showEmptyPage(title, "刷新试试", listener);
    }

    @Override
    public void showEmptyPage(String title, String btnString, View.OnClickListener listener) {
        showEmptyPage(title, "", btnString, listener);
    }

    @Override
    public void showEmptyPage(String title, String content, String btnString, View.OnClickListener listener) {
        showEmptyPage(getDrawablee(R.drawable.status_search_result_empty), title, content, btnString, listener);
    }

    @Override
    public void showEmptyPage(Drawable drawable, String title, String content, String btnString, View.OnClickListener listener) {
        if (null != mStatusLayout)
            mStatusLayout.showError(drawable, title, content, btnString, listener);
    }

    @Override
    public void showErrorPage(String title) {
        showErrorPage(title, "", null);
    }

    @Override
    public void showErrorPage(String title, View.OnClickListener listener) {
        showErrorPage(title, "刷新试试", listener);
    }

    @Override
    public void showErrorPage(String title, String btnString, View.OnClickListener listener) {
        showErrorPage(title, "", btnString, listener);
    }

    @Override
    public void showErrorPage(String title, String content, String btnString, View.OnClickListener listener) {
        showErrorPage(getDrawablee(R.drawable.status_loading_view_network_error), title, content, btnString, listener);
    }

    @Override
    public void showErrorPage(Drawable drawable, String title, String content, String btnString, View.OnClickListener listener) {
        if (null != mStatusLayout)
            mStatusLayout.showError(drawable, title, content, btnString, listener);
    }

    @Override
    public void showSuccessTips(String msg) {
        QMUIDialogUtil.SuccessTipsDialog(this, msg);
    }

    @Override
    public void showFailTips(String msg) {
        QMUIDialogUtil.FailTipsDialog(this, msg);
    }


    public Drawable getDrawablee(int id) {
        return ContextCompat.getDrawable(this, id);
    }

    public String getStringg(int id) {
        return getResources().getString(id);
    }

    public int getColorr(int id) {
        return ContextCompat.getColor(this, id);
    }

    public String getEditTextString(EditText editText) {
        String str = editText.getText().toString();
        return str;
    }

    public String getTextViewString(TextView textView) {
        String str = textView.getText().toString();
        return str;
    }

    public void goneView(View... views) {
        for (View view : views) {
            if (null != view && view.getVisibility() != View.GONE)
                view.setVisibility(View.GONE);
        }
    }

    public void showView(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.VISIBLE)
                view.setVisibility(View.VISIBLE);
        }
    }

    public void startActivity(Class c) {
        startActivity(new Intent(this, c));
    }

    public boolean isNotNullAndEmpty(String content, String tips) {
        if (null != content && !content.isEmpty())
            return true;
        else {
            if (null != tips)
                showFailTips(tips + "不能为空");
            return false;
        }
    }

    protected void loadImage(String url, ImageView imageView) {
        GlideUtil.load(this, url, imageView);
    }

    public void showLog(String str) {
        Log.e(TAG, str);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) getPresenter().detachView();
        if (isRegisterEventBus()) EventBusUtil.unregister(this);
        try { unbinder.unbind(); } catch (Exception e) { }
    }
}
