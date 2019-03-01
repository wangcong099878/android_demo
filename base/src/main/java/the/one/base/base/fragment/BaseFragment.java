package the.one.base.base.fragment;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.arch.QMUIFragment;
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
import the.one.base.widge.MyTopBarLayout;
import the.one.base.widge.ProgressDialog;
import the.one.base.widge.StatusLayout;
import the.one.library.callback.Callback;
import the.one.library.entity.PageInfoBean;


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
public abstract class BaseFragment extends QMUIFragment implements BaseView ,LifecycleObserver {

    public final String TAG = this.getClass().getSimpleName();

    protected abstract int getContentViewId();

    /**
     * 获取Presenter实例，子类实现
     */
    public abstract BasePresenter getPresenter();

    protected abstract void initView(View rootView);

    /**
     * 动画结束后请求数据，如果是ViewPager里的Fragment,则要返回false
     * @return
     */
    protected boolean onAnimationEndInit() {
        return true;
    }
    protected void onLazyInit(){}

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    protected boolean showTitleBar() {
        return true;
    }

    protected StatusLayout mStatusLayout;
    protected MyTopBarLayout mTopLayout;
    protected QMUITipDialog loadingDialog;
    protected the.one.base.widge.ProgressDialog progressDialog;
    protected Activity _mActivity;

    private Unbinder mUnbinder;

    @Override
    protected boolean canDragBack() {
        return false;
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 150);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLazyViewLifecycleOwner().getLifecycle().addObserver(this);
    }

    protected boolean mIsFirstLayInit = false;

    /**
     * 懒加载一般用在ViewPager里的Fragment,一般的加载要放在onAnimationEnd（）里面
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onLazyResume(){
        if(!onAnimationEndInit()&&!mIsFirstLayInit){
            mIsFirstLayInit = true;
            onLazyInit();
        }
    }

    @Override
    protected View onCreateView() {
        View mBody = getView(getContentViewId());
        View mRootView;
        _mActivity = getActivity();
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
        mUnbinder = ButterKnife.bind(this, mBody);
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        initView(mRootView);
        return mRootView;
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
                popBackStack();
            }
        });
    }

    public void startBrotherFragment(BaseFragment fragment) {
        startFragment(fragment);
    }

    @Override
    public void showLoadingDialog(String msg) {
        loadingDialog = QMUIDialogUtil.LoadingTipsDialog(getActivity(), msg);
        loadingDialog.show();

    }

    @Override
    public void hideLoadingDialog() {
        if (null != loadingDialog && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }

    @Override
    public void showProgressDialog(int percent) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setProgress(percent);
    }

    @Override
    public void showProgressDialog(int percent, int total) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setProgress(percent, total);
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(getActivity(), msg);
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
        showErrorPage(getDrawablee(R.drawable.status_loading_view_loading_fail), title, content, btnString, listener);
    }

    @Override
    public void showErrorPage(Drawable drawable, String title, String content, String btnString, View.OnClickListener listener) {
        if (null != mStatusLayout)
            mStatusLayout.showError(drawable, title, content, btnString, listener);
    }

    public View getView(int layoutId) {
        return LayoutInflater.from(getActivity()).inflate(layoutId, null);
    }

    public Drawable getDrawablee(int id) {
        return ContextCompat.getDrawable(getActivity(), id);
    }

    public String getStringg(int id) {
        return getResources().getString(id);
    }

    public int getColorr(int id) {
        return ContextCompat.getColor(getActivity(), id);
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
        startActivity(new Intent(getActivity(), c));
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
        GlideUtil.load(_mActivity, url, imageView);
    }

    public void showLog(String str) {
        Log.e(TAG, str);
    }

    public void showSuccessTips(String tips) {
        QMUIDialogUtil.SuccessTipsDialog(_mActivity, tips);
    }

    public void showSuccessExit(String tips) {
        QMUIDialogUtil.SuccessTipsDialog(_mActivity, tips, new QMUIDialogUtil.OnTipsDialogDismissListener() {
            @Override
            public void onDismiss() {
                successPop();
            }
        });
    }

    protected void successPop() {
        EventBusUtil.sendSuccessEvent(successType);
        popBackStack();
    }

    public void showFailTips(String tips) {
        QMUIDialogUtil.FailTipsDialog(_mActivity, tips);
    }

    protected int successType;
    public Callback<String> simpleCallBack = new Callback<String>() {
        @Override
        public void onSuccess(String response, String msg, PageInfoBean pageInfoBean) {
            showSuccessExit(msg);
        }

        @Override
        public void onFailure(int resultCode, String errorMsg) {
            showFailTips(errorMsg);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            hideLoadingDialog();
        }
    };

    public void finish() {
        popBackStack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) getPresenter().detachView();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        if (null != mUnbinder) {
            try {
                mUnbinder.unbind();
            } catch (Exception e) {

            }
        }
    }
}
