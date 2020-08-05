package the.one.base.ui.fragment;

import android.Manifest;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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
        implements ImageSnapAdapter.OnImageClickListener<T>, Observer<Boolean>, QMUIBottomSheetUtil.OnSheetItemClickListener {

    protected ImageSnapAdapter<T> mImageSnapAdapter;
    protected PagerSnapHelper mPagerSnapHelper;
    protected LinearLayoutManager mPagerLayoutManager;
    protected QMUIAlphaImageButton mBackBtn;
    protected int mBgColor;
    protected int mTextColor;

    protected QMUIBottomSheet mBottomSheet;
    protected List<PopupItem> mSheetItems = new ArrayList<>();

    protected ImageSnap mData;
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

    /*
     * 根据主题更换颜色
     */
    protected void updateBgColor(boolean isWhite) {
        mBgColor = getColor(isWhite ? R.color.qmui_config_color_white : R.color.we_chat_black);
        mTextColor = getColor(isWhite ? R.color.qmui_config_color_gray_1 : R.color.qmui_config_color_white);

        mRootView.setBackgroundColor(mBgColor);
        mStatusLayout.setBackgroundColor(mBgColor);
        recycleView.setBackgroundColor(getColor(isWhite ? R.color.qmui_config_color_background : R.color.qmui_config_color_black));
        mImageSnapAdapter.setWhiteBg(isWhite);

        mTopLayout.setBackgroundColor(mBgColor);
        mTopLayout.getTopBar().getTitleView().setTextColor(mTextColor);
        int backRes = isWhite ? R.drawable.mz_titlebar_ic_back_dark : R.drawable.mz_titlebar_ic_back_light;
        if (null == mBackBtn) {
            mBackBtn = mTopLayout.addLeftImageButton(backRes, R.id.topbar_left_button);
            mBackBtn.setOnClickListener(v -> onBackPressed());
        } else {
            mBackBtn.setImageDrawable(getDrawable(backRes));
        }
        if (null != mStatusLayout && null != mStatusLayout.getLoadingTipsView()) {
            mStatusLayout.getLoadingTipsView().setTextColor(mTextColor);
        }
        onLazyResume();
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
     * @param data
     */
    @Override
    public void onImageClick(T data) {
        // 点击后隐藏或者显示 TopBarLayout和状态栏
        boolean isVisible = mTopLayout.getVisibility() == View.VISIBLE;
        mTopLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        if (isVisible) {
            QMUIViewHelper.fadeOut(mTopLayout, 500, null, true);
        } else {
            QMUIViewHelper.fadeIn(mTopLayout, 500, null, true);
        }
        setStatusBarVisible(!isVisible);
        if (isStatusBarLightMode()) {
            // 如果是白色模式，点击后将变成黑色
            updateBgColor(!isVisible);
        }
    }

    protected void showBottomSheetDialog(ImageSnap data){
        mData = data;
        if (null == mBottomSheet) {
            mBottomSheet = QMUIBottomSheetUtil.showGridBottomSheet(_mActivity, mSheetItems, 4, true, this);
        }
        mBottomSheet.show();
    }

    protected void requestPermission() {
        new RxPermissions(this)
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET)
                .subscribe(this);
    }

    protected void onPermissionComplete(ImageSnap data,String tag,int position){
        if(tag.equals(TAG_DOWNLOAD)){
            // 下载
            DownloadUtil.startDownload(_mActivity,data);
        }
    }

    @Override
    public void onSheetItemClick(int position, String title, QMUIBottomSheet dialog, View itemView) {
        mSheetClickPosition = position;
        mSheetClickTag = title;
        requestPermission();
        dialog.dismiss();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Boolean aBoolean) {
        if (aBoolean) {
            onPermissionComplete(mData,mSheetClickTag,mSheetClickPosition);
        } else {
            showToast(getString(R.string.no_permissioin_tips));
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

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
