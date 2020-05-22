package the.one.base.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import the.one.base.R;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.ui.view.BaseView;
import the.one.base.util.EventBusUtil;
import the.one.base.util.QMUIDialogUtil;
import the.one.base.util.QMUIStatusBarHelper;
import the.one.base.util.StatusBarUtil;
import the.one.base.util.ToastUtil;
import the.one.base.util.ViewUtil;
import the.one.base.util.glide.GlideUtil;
import the.one.base.widge.MyTopBar;
import the.one.base.widge.MyTopBarLayout;
import the.one.base.widge.ProgressDialog;
import the.one.base.widge.StatusLayout;

import static android.view.View.NO_ID;


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
 * @describe BaseFragment
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseFragment extends QMUIFragment implements BaseView, LifecycleObserver {

    protected final String TAG = this.getClass().getSimpleName();

    /**
     * @return
     * @TODO 获取布局
     * @remark 默认是有TopBar的，如果需要整个布局是传递过来的，则将showTitleBar()返回false
     */
    protected abstract int getContentViewId();

    /**
     * @return
     * @TODO 布局是否需要TopBar
     */
    protected boolean showTitleBar() {
        return true;
    }

    /**
     * @param rootView
     * @TODO 初始化布局
     */
    protected abstract void initView(View rootView);

    /**
     * @TODO 获取Presenter实例，子类实现
     */
    public abstract BasePresenter getPresenter();

    /**
     * @return 是否要进行对状态栏的处理
     * @remark 默认当为根fragment时才进行处理
     */
    protected boolean isNeedChangeStatusBarMode() {
        return isIndexFragment;
    }

    /**
     * @return 是否设置状态栏LightMode 深色图标 白色背景
     * @remark 默认根据当前TopBarLayout的背景颜色是否为白色或是否存在渐变色背景进行判断
     */
    protected boolean isStatusBarLightMode() {
        return StatusBarUtil.isWhiteBg(getBaseFragmentActivity());
    }

    /**
     * 是否为根Fragment：  getParentFragment() 为空
     */
    protected boolean isIndexFragment = false;

    /**
     * 懒加载分两种情况
     * 1.在动画结束后开始进行加载
     * 2.当前Fragment为子Fragment时，比如ViewPager的ItemFragment,或者点击切换的，这种情况下当界面可见时才进行加载
     * <p>
     * 这里自动根据 {@link #isIndexFragment} 判断是以哪种情况进行懒加载
     */
    protected void onLazyInit() {
    }

    protected boolean mIsFirstLayInit = true;

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onLazyResume() {
        if (isNeedChangeStatusBarMode()) {
            if (isStatusBarLightMode()) {
                QMUIStatusBarHelper.translucent(getBaseFragmentActivity());
                QMUIStatusBarHelper.setStatusBarLightMode(getBaseFragmentActivity());
            } else {
                QMUIStatusBarHelper.translucent(getBaseFragmentActivity(), getColorr(R.color.qmui_config_color_transparent));
                QMUIStatusBarHelper.setStatusBarDarkMode(getBaseFragmentActivity());
            }
        }
        if (!isIndexFragment && mIsFirstLayInit) {
            mIsFirstLayInit = false;
            onLazyInit();
        }
    }

    @Override
    protected void onEnterAnimationEnd(@Nullable Animation animation) {
        super.onEnterAnimationEnd(animation);
        if (isIndexFragment && mIsFirstLayInit) {
            mIsFirstLayInit = false;
            onLazyInit();
        }
    }

    /**
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     * @TODO 是否注册事件分发
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    /**
     * @TODO 需要显示点击返回显示 是否退出
     */
    protected boolean isExitFragment() {
        return false;
    }

    /**
     * @return
     * @TODO 是否需要在周围（上、下、左、右）添加布局
     */
    protected boolean isNeedAround() {
        return false;
    }

    /**
     * @return
     * @TODO 设置头部自定义布局
     */
    protected Object getTopLayout() {
        return null;
    }

    /**
     * @return
     * @TODO 设置底部自定义布局
     */
    protected Object getBottomLayout() {
        return null;
    }

    /**
     * @return
     * @TODO 设置左边自定义布局
     */
    protected Object getLeftLayout() {
        return null;
    }

    /**
     * @return
     * @TODO 设置右边自定义布局
     */
    protected Object getRightLayout() {
        return null;
    }

    protected View mRootView;
    protected RelativeLayout rlParent;
    protected StatusLayout mStatusLayout;
    protected MyTopBarLayout mTopLayout;
    protected QMUITipDialog loadingDialog;
    protected ProgressDialog progressDialog;
    protected Activity _mActivity;

    protected FrameLayout flTopLayout;
    protected FrameLayout flBottomLayout;
    protected FrameLayout flLeftLayout;
    protected FrameLayout flRightLayout;

    private Unbinder mUnbinder;

    @Override
    protected boolean canDragBack() {
        return true;
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 400);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _mActivity = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLazyViewLifecycleOwner().getLifecycle().addObserver(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isIndexFragment = null == getParentFragment();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected View onCreateView() {
        View mBody = getView(getContentViewId());
        if (getPresenter() != null)
            getPresenter().attachView(this,this);
        if (isRegisterEventBus())
            EventBusUtil.register(this);
        if (showTitleBar()) {
            mRootView = getView(isNeedAround() ? R.layout.base_fragment_around : R.layout.base_fragment);
            mStatusLayout = mRootView.findViewById(R.id.status_layout);
            mTopLayout = mRootView.findViewById(R.id.top_layout);

            if (isNeedAround()) {
                rlParent = mRootView.findViewById(R.id.rl_parent);
                flLeftLayout = mRootView.findViewById(R.id.fl_left_layout);
                flRightLayout = mRootView.findViewById(R.id.fl_right_layout);
                flBottomLayout = mRootView.findViewById(R.id.fl_bottom_layout);
                flTopLayout = mRootView.findViewById(R.id.fl_top_layout);
                initAroundView();
            }
            mStatusLayout.addView(mBody, -1, -1);
        } else {
            mRootView = mBody;
        }

        mUnbinder = ButterKnife.bind(this, mBody);
        initView(mRootView);

        if (null != mTopLayout && mTopLayout.getVisibility() != View.VISIBLE) {
            ViewUtil.setMargins(isNeedAround() ? rlParent : mStatusLayout, 0, 0, 0, 0);
        }
        return mRootView;
    }

    protected void initAroundView() {
        setCustomLayout(flLeftLayout, getLeftLayout());
        setCustomLayout(flRightLayout, getRightLayout());
        setCustomLayout(flBottomLayout, getBottomLayout());
        setCustomLayout(flTopLayout, getTopLayout());
    }

    protected void setCustomLayout(ViewGroup parent, Object layout) {
        if (null == layout) return;
        if (layout instanceof View) {
            parent.addView((View) layout, -1, -1);
        } else if (layout instanceof Integer) {
            int id = (int) layout;
            if (id != -1)
                parent.addView(getView((Integer) layout), -1, -1);
        } else {
            throw new ClassCastException("layout must be int or view");
        }
    }

    protected void initFragmentBack(String title) {
        if (null != mTopLayout) {
            mTopLayout.setTitle(title);
            addTopBarBackBtn();
        }
    }

    protected void addTopBarBackBtn() {
        if (null != mTopLayout){
            addTopBarBackBtn(mTopLayout.getTopBar());
        }
    }

    protected void addTopBarBackBtn(MyTopBar topBar) {
        if (null != topBar)
            topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
    }

    protected void addTopBarBackBtn(int drawable){
        addTopBarBackBtn(drawable, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected void addTopBarBackBtn(int drawable, View.OnClickListener listener) {
        mTopLayout.addLeftImageButton(drawable, R.id.topbar_left_button).setOnClickListener(listener);
    }

    protected final <T extends View> T findViewByTopView(@IdRes int id) {
        return findViewByCustomView(flTopLayout, id);
    }

    protected final <T extends View> T findViewByLeftView(@IdRes int id) {
        return findViewByCustomView(flLeftLayout, id);
    }

    protected final <T extends View> T findViewByRightView(@IdRes int id) {
        return findViewByCustomView(flRightLayout, id);
    }

    protected final <T extends View> T findViewByBottomView(@IdRes int id) {
        return findViewByCustomView(flBottomLayout, id);
    }

    protected final <T extends View> T findViewByCustomView(View around, @IdRes int id) {
        if (null == around || id == NO_ID) {
            return null;
        }
        return around.findViewById(id);
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
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(msg);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
        if (progressDialog.isShowing())
            progressDialog.setProgress(percent, total);
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

    public View getView(int layoutId) {
        return LayoutInflater.from(getContext()).inflate(layoutId, null,false);
    }

    public Drawable getDrawablee(int id) {
        return ContextCompat.getDrawable(_mActivity, id);
    }

    public String getStringg(int id) {
        return getResources().getString(id);
    }

    public int getColorr(int id) {
        return ContextCompat.getColor(_mActivity, id);
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
            if (null != view && view.getVisibility() != View.VISIBLE)
                view.setVisibility(View.VISIBLE);
        }
    }

    public void startActivityFinishCurrent(Class c, boolean finish) {
        startActivity(new Intent(getActivity(), c));
        if (finish) getActivity().finish();
    }

    public void startActivity(Class c) {
        startActivityFinishCurrent(c, false);
    }

    public boolean isNotNullAndEmpty(String content, String tips) {
        if (TextUtils.isEmpty(content))
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
        Logger.e(str);
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

    protected int successType = 1;

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
                e.printStackTrace();
            }
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK&&isExitFragment()){
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                showToast("再点一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                _mActivity.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
