package the.one.base.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.arch.SwipeBackLayout;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import the.one.base.R;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.ui.view.BaseView;
import the.one.base.util.AppMourningThemeUtil;
import the.one.base.util.EventBusUtil;
import the.one.base.util.QMUIDialogUtil;
import the.one.base.util.QMUIStatusBarHelper;
import the.one.base.util.SkinSpUtil;
import the.one.base.util.StatusBarUtil;
import the.one.base.util.ToastUtil;
import the.one.base.util.ViewUtil;
import the.one.base.util.glide.GlideUtil;
import the.one.base.widge.ProgressDialog;
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

    protected View mRootView;
    protected StatusLayout mStatusLayout;
    protected QMUITipDialog loadingDialog;
    protected the.one.base.widge.ProgressDialog progressDialog;
    private Unbinder unbinder;

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

    /**
     * @TODO 需要显示点击返回显示 是否退出
     */
    protected boolean isExitActivity() {
        return false;
    }

    /**
     * 自动根据TopBar的背景颜色判断
     *
     * @return
     */
    protected boolean isStatusBarLightMode() {
        return StatusBarUtil.isWhiteBg(mRootView);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (SkinSpUtil.isQMUISkinManger())
            setSkinManager(QMUISkinManager.defaultInstance(this));
        overridePendingTransition(R.anim.scale_enter, R.anim.slide_still);
        super.onCreate(savedInstanceState);
        AppMourningThemeUtil.notify(getWindow());
        mRootView = getView(getContentViewId());
        updateStatusBarMode();
        setContentView(mRootView);
        unbinder = ButterKnife.bind(this);
        if (getPresenter() != null) {
            getPresenter().attachView(this, this);
        }
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        initView(mRootView);
    }

    @Override
    protected int backViewInitOffset(Context context, int dragDirection, int moveEdge) {
        if (moveEdge == SwipeBackLayout.EDGE_TOP || moveEdge == SwipeBackLayout.EDGE_BOTTOM) {
            return 0;
        }
        return QMUIDisplayHelper.dp2px(context, 200);
    }

    @Override
    public void showLoadingDialog(String msg) {
        loadingDialog = QMUIDialogUtil.LoadingTipsDialog(this, msg);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (null != loadingDialog && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }

    @Override
    public void showProgressDialog(String msg) {
        showProgressDialog(0, 100, msg);
    }

    @Override
    public void showProgressDialog(int percent) {
        showProgressDialog(percent, 100);
    }

    @Override
    public void showProgressDialog(int percent, int total) {
        showProgressDialog(percent, total, "上传中");
    }

    @Override
    public void showProgressDialog(int percent, int total, String msg) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(msg);
        progressDialog.setProgress(percent, total);
        if (!progressDialog.isShowing())
            progressDialog.show();

    }

    @Override
    public void hideProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(msg);
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
            mStatusLayout.showEmpty(drawable, title, content, btnString, listener);
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
        showErrorPage(getDrawablee(R.drawable.status_loading_view_loading_fail), title, content, btnString, listener);
    }

    @Override
    public void showErrorPage(Drawable drawable, String title, String content, String btnString, View.OnClickListener listener) {
        if (null != mStatusLayout)
            mStatusLayout.showError(drawable, title, content, btnString, listener);
    }


    @Override
    public void startActivity(Class c) {
        startActivity(c, false);
    }

    @Override
    public void startActivity(Class c, boolean finish) {
        startActivity(new Intent(this, c));
        if (finish) finish();
    }


    @Override
    public void showSuccessExit(String tips) {
        showSuccessExit(tips, -1);
    }

    @Override
    public void showSuccessExit(String tips, int type) {
        QMUIDialogUtil.SuccessTipsDialog(this, tips, new QMUIDialogUtil.OnTipsDialogDismissListener() {
            @Override
            public void onDismiss() {
                EventBusUtil.sendSuccessEvent(type);
                finish();
            }
        });
    }

    @Override
    public void showSuccessTips(String tips) {
        QMUIDialogUtil.SuccessTipsDialog(this, tips);
    }

    @Override
    public void showFailTips(String tips) {
        QMUIDialogUtil.FailTipsDialog(this, tips);
    }


    @Override
    protected void onDestroy() {
        if (getPresenter() != null) getPresenter().detachView();
        super.onDestroy();
        if (isRegisterEventBus()) EventBusUtil.unregister(this);
        try {
            unbinder.unbind();
        } catch (Exception e) {
        }
    }

    private long exitTime = 0;

    @Override
    protected void doOnBackPressed() {
        if (isExitActivity()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                showToast("再点一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return;
        }
        super.doOnBackPressed();
    }
    /***   Util  ***/

    /**
     * 更新状态栏模式
     *
     * @remark LIGHT 白色背景黑色图标  DARK 深色背景 白色图标
     */
    protected void updateStatusBarMode() {
        if (isStatusBarLightMode()) {
            QMUIStatusBarHelper.translucent(this);
            QMUIStatusBarHelper.setStatusBarLightMode(this);
        } else {
            QMUIStatusBarHelper.translucent(this, getColorr(R.color.qmui_config_color_transparent));
            QMUIStatusBarHelper.setStatusBarDarkMode(this);
        }
    }

    /**
     * 获取View
     *
     * @param layoutId 布局id
     * @return
     */
    protected View getView(int layoutId) {
        return LayoutInflater.from(this).inflate(layoutId, null, false);
    }

    /**
     * 获取Drawable
     *
     * @param id drawable id
     * @return
     */
    protected Drawable getDrawablee(int id) {
        return ContextCompat.getDrawable(this, id);
    }

    /**
     * 获取Color
     *
     * @param id color id
     * @return
     */
    protected int getColorr(int id) {
        return ContextCompat.getColor(this, id);
    }

    /**
     * 获取EditText String
     *
     * @param editText
     * @return
     */
    protected String getEditTextString(EditText editText) {
        return editText.getText().toString();
    }

    /**
     * 获取TextView String
     *
     * @param textView
     * @return
     */
    protected String getTextViewString(TextView textView) {
        return textView.getText().toString();
    }

    /**
     * 设置View为 {@link View.GONE}状态
     *
     * @param views
     */
    protected void goneView(View... views) {
        ViewUtil.goneViews(views);
    }

    /**
     * 设置View为 {@link View.VISIBLE}状态
     *
     * @param views
     */
    protected void showView(View... views) {
        ViewUtil.showViews(views);
    }

    /**
     * 判断内容是否为空并作出提示
     *
     * @param content 需要判断的内容
     * @param tips    提示语句
     * @return true 为空  false 不为空
     */
    protected boolean isContentEmpty(String content, String tips) {
        if (TextUtils.isEmpty(content))
            return true;
        else {
            if (null != tips)
                showFailTips(tips + "不能为空");
            return false;
        }
    }

    /**
     * 显示图片
     *
     * @param url
     * @param imageView
     */
    protected void loadImage(String url, ImageView imageView) {
        GlideUtil.load(this, url, imageView);
    }

}
