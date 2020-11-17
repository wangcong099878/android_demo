package the.one.base.ui.fragment;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.Interface.IPageInfo;
import the.one.base.Interface.ImageSnap;
import the.one.base.R;
import the.one.base.adapter.ImageSnapAdapter;
import the.one.base.model.PopupItem;
import the.one.base.util.DownloadUtil;
import the.one.base.util.QMUIBottomSheetUtil;
import the.one.base.util.ViewUtil;

/**
 * BaseImageSnapFragment基本上就是RC的结构，只是让其一个item一页的显示
 * 可以当做一个图片查看器，最重要的是可以加载更多，数据是分页的
 * 包含了图片加载进度显示
 * 这个最开始的需求是一个分类里有很多图片，以前都是外部显示小图，
 * 然后点击后查看大图，这个时候只能显示已经加载的数据，滑动到最后一条数据时也应该继续显示
 * 实体需 implements {@link ImageSnap}
 */
public abstract class BaseImageSnapFragment<T extends ImageSnap> extends BaseListFragment<T>
        implements ImageSnapAdapter.OnImageClickListener<T>, QMUIBottomSheetUtil.OnSheetItemClickListener {

    protected ImageSnapAdapter<T> mImageSnapAdapter;
    protected PagerSnapHelper mPagerSnapHelper;
    protected LinearLayoutManager mPagerLayoutManager;
    protected QMUIAlphaImageButton mBackBtn;
    protected int mBgColor;
    protected int mTextColor;

    protected QMUIBottomSheet mBottomSheet;
    protected List<PopupItem> mSheetItems = new ArrayList<>();

    protected T mData;
    protected int mSheetClickPosition;
    protected String mSheetClickTag;

    protected final String TAG_DOWNLOAD = "下载";

    /**
     * 当滑动改变后需要对当前的数据进行处理
     *
     * @param item
     * @param position
     */
    protected abstract void onScrollChanged(T item, int position);

    /**
     * 滑动方向
     *
     * @return
     */
    protected int getOrientation() {
        return RecyclerView.HORIZONTAL;
    }

    /**
     * 用这个切换白的还是黑色背景
     *
     * @return
     */
    @Override
    protected boolean isStatusBarLightMode() {
        return super.isStatusBarLightMode();
    }

    @Override
    protected boolean isNeedSpace() {
        return false;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        // 这个界面只提供黑白两种，不随主题色变化
        mTopLayout.setNeedChangedWithTheme(false);
        updateBgColor(isStatusBarLightMode());
        ViewUtil.setMargins(mStatusLayout, 0, 0, 0, 0);
        mStatusLayout.setFitsSystemWindows(false);
        initSheetItems(mSheetItems);
    }

    /**
     * 这里默认给了一个下载功能
     */
    protected void initSheetItems(List<PopupItem> sheetItems) {
        sheetItems.add(new PopupItem(TAG_DOWNLOAD, R.drawable.icon_more_operation_save));
    }

    /**
     * 根据主题更换颜色
     *
     * @param isLight 亮色背景，黑色状态栏图标字体
     */
    protected void updateBgColor(boolean isLight) {
        mBgColor = getColor(isLight ? R.color.qmui_config_color_white : R.color.we_chat_black);
        mTextColor = getColor(isLight ? R.color.qmui_config_color_gray_1 : R.color.qmui_config_color_white);

        mRootView.setBackgroundColor(mBgColor);
        mStatusLayout.setBackgroundColor(mBgColor);
        recycleView.setBackgroundColor(mBgColor);
        mImageSnapAdapter.setWhiteBg(isLight);

        mTopLayout.setBackgroundColor(mBgColor);
        mTopLayout.getTopBar().getTitleView().setTextColor(mTextColor);
        initTopBarBackBtn(isLight);
        if (null != mStatusLayout && null != mStatusLayout.getLoadingTipsView()) {
            mStatusLayout.getLoadingTipsView().setTextColor(mTextColor);
        }
        updateStatusBarMode(isLight);
    }

    protected void initTopBarBackBtn(boolean isLightMode){
        int backRes = isLightMode ? R.drawable.mz_titlebar_ic_back_dark : R.drawable.mz_titlebar_ic_back_light;
        if (null == mBackBtn) {
            mBackBtn = mTopLayout.addLeftImageButton(backRes, R.id.topbar_left_button);
            mBackBtn.setOnClickListener(v -> onBackPressed());
        } else {
            mBackBtn.setImageResource(backRes);
        }
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        adapter.setAnimationEnable(true);
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn);
        adapter.setAnimationFirstOnly(false);
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        mImageSnapAdapter = new ImageSnapAdapter<T>();
        mImageSnapAdapter.setOnImageClickListener(this);
        return mImageSnapAdapter;
    }

    @Override
    protected void initRecycleView(RecyclerView recycleView, int type, BaseQuickAdapter adapter) {
        super.initRecycleView(recycleView, type, adapter);
        mPagerSnapHelper = new PagerSnapHelper();
        mPagerLayoutManager = new LinearLayoutManager(_mActivity, getOrientation(), false);
        recycleView.setLayoutManager(mPagerLayoutManager);
        mPagerSnapHelper.attachToRecyclerView(recycleView);
    }

    @Override
    public void setPageInfo(IPageInfo mPageInfo) {
        super.setPageInfo(mPageInfo);
        // 这种模式默认把下拉刷新关闭
        setPullLayoutEnabled(false);
    }

    @Override
    public void showLoadingPage() {
        super.showLoadingPage();
        mStatusLayout.getLoadingTipsView().setTextColor(mTextColor);
    }

    protected RecyclerView.OnScrollListener getOnScrollListener() {
        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert linearLayoutManager != null;
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                if (adapter.getData().size() > position)
                    onScrollChanged((T) adapter.getData().get(position), position);
            }
        };
    }


    /**
     * 点击图片
     *
     * @param data
     */
    @Override
    public void onImageClick(T data) {
        // 点击后隐藏或者显示 TopBarLayout和 状态栏
        final boolean isVisible = ViewUtil.isVisible(mTopLayout);
        if (isStatusBarLightMode()) {
            // 如果是白色模式，点击后将变成黑色
            updateBgColor(!isVisible);
        }
        setStatusBarVisible(!isVisible);
        ViewUtil.setViewsVisible(!isVisible,mTopLayout);
//        if (isVisible) {
//            QMUIViewHelper.fadeOut(mTopLayout, 500, null, true);
//        } else {
//            QMUIViewHelper.fadeIn(mTopLayout, 500, null, true);
//        }
    }

    protected void showBottomSheetDialog(T data) {
        mData = data;
        if (null == mBottomSheet) {
            mBottomSheet = QMUIBottomSheetUtil.showGridBottomSheet(_mActivity, mSheetItems, 4, true, this);
        }
        mBottomSheet.show();
    }

    protected void requestPermission() {
        XXPermissions.with(_mActivity)
                .permission(Permission.Group.STORAGE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            onPermissionComplete(mData, mSheetClickTag, mSheetClickPosition);
                        } else {
                            requestPermission();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            showToast("权限被禁止，请在设置里打开权限。");
                        } else {
                            showToast("权限被禁止，请手动打开");
                        }
                    }
                });
    }

    protected void onPermissionComplete(T data, String tag, int position) {
        if (tag.equals(TAG_DOWNLOAD)) {
            // 下载
            DownloadUtil.startDownload(_mActivity, data);
        }
    }

    @Override
    public void onSheetItemClick(int position, String title, QMUIBottomSheet dialog, View itemView) {
        mSheetClickPosition = position;
        mSheetClickTag = title;
        requestPermission();
        dialog.dismiss();
    }

    /**
     * 更改进出动画效果 QMUIFragment提供
     *
     * @return
     */
    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    /**
     * 调整状态栏的显示
     *
     * @param show
     */
    protected void setStatusBarVisible(boolean show) {
        Window window = getBaseFragmentActivity().getWindow();
        if (null == window) return;
        if (show) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 销毁时显示状态栏
        setStatusBarVisible(true);
    }

}
