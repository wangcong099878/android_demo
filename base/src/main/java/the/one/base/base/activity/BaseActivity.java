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
import the.one.base.widge.ProgressDialog;


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

    protected QMUITipDialog loadingDialog;
    protected the.one.base.widge.ProgressDialog progressDialog;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mRootView = getView(getContentViewId());
        setContentView(mRootView);
        unbinder = ButterKnife.bind(this);
        if (getPresenter() != null) {
            getPresenter().attachView(this);
        }
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
    public void showProgressDialog(String msg) {
        showProgressDialog(0,100,msg);
    }

    @Override
    public void showProgressDialog(int percent) {
        showProgressDialog(percent,100);
    }

    @Override
    public void showProgressDialog(int percent, int total) {
        showProgressDialog(percent,total,"上传中");
    }

    @Override
    public void showProgressDialog(int percent, int total,String msg) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(msg);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }
        if (progressDialog.isShowing())
            progressDialog.setProgress(percent,total);
    }

    @Override
    public void hideProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(msg);
    }

    @Override
    public void showContentPage() {
    }

    @Override
    public void showLoadingPage() {
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

    public void startActivityFinishCurrent(Class c,boolean finish) {
        startActivity(new Intent(this, c));
        if(finish) finish();
    }

    public void startActivity(Class c) {
        startActivityFinishCurrent(c,false);
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
