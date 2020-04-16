package the.one.base.ui.fragment;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIViewHelper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.Interface.IPageInfo;
import the.one.base.Interface.ImageSnap;
import the.one.base.R;
import the.one.base.adapter.ImageSnapAdapter;

/**
 * BaseImageSnapFragment基本上就是RC的结构，只是让其一个item一页的显示
 * 可以当做一个图片查看器，最重要的是可以加载更多，数据是分页的
 * 包含了图片加载进度显示
 * 这个最开始的需求是一个分类里有很多图片，以前都是外部显示小图，
 * 然后点击后查看大图，这个时候只能显示已经加载的数据，滑动到最后一条数据时也应该继续显示
 * 实体需 implements {@link ImageSnap}
 */
public abstract class BaseImageSnapFragment<T extends ImageSnap> extends BaseDataFragment<T> implements
        ImageSnapAdapter.OnImageClickListener<T> {

    protected ImageSnapAdapter<T> mImageSnapAdapter;
    protected PagerSnapHelper mPagerSnapHelper;
    protected LinearLayoutManager mPagerLayoutManager;
    protected int mBgColor;
    protected int mTextColor;

    /**
     * 当滑动改变后需要对当前的数据进行处理
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
        mTopLayout.setBackgroundColor(mBgColor);
        mTopLayout.getTopBar().getTitleView().setTextColor(mTextColor);
        mTopLayout.addLeftImageButton(isStatusBarLightMode()?R.drawable.mz_titlebar_ic_back_dark:R.drawable.mz_titlebar_ic_back_light, R.id.topbar_left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setMargins(mStatusLayout, 0, 0, 0, 0);
        mStatusLayout.setFitsSystemWindows(false);
    }

    protected void updateBgColor(boolean isWhite){
        mBgColor = getColorr(isWhite?R.color.qmui_config_color_white:R.color.we_chat_black);
        mTextColor = getColorr(isWhite?R.color.qmui_config_color_gray_1:R.color.qmui_config_color_white);

        mRootView.setBackgroundColor(mBgColor);
        mStatusLayout.setBackgroundColor(mBgColor);
        recycleView.setBackgroundColor(getColorr(isWhite?R.color.qmui_config_color_background:R.color.qmui_config_color_black));
        mImageSnapAdapter.setWhiteBg(isWhite);
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

    protected void updateOrientation(){
        mPagerLayoutManager.setOrientation(getOrientation());
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

    @Override
    public void onClick(T data) {
        // 点击后隐藏或者显示 TopBarLayout和状态栏
        boolean isVisible = mTopLayout.getVisibility() == View.VISIBLE;
        mTopLayout.setVisibility(isVisible?View.GONE:View.VISIBLE);
        if(isVisible){
            QMUIViewHelper.fadeOut(mTopLayout,500,null,true);
        }else{
            QMUIViewHelper.fadeIn(mTopLayout,500,null,true);
        }
        setStatusBarVisible(!isVisible);
        if(isStatusBarLightMode()){
            // 如果是白色模式，点击后将变成黑色
            updateBgColor(!isVisible);
        }
    }

    @Override
    public void onVideoClick(T data) {
    }

    @Override
    public boolean onLongClick(T data) {
        return false;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return true;
    }

    /**
     * 更改进出动画效果 QMUIFragment提供
     * @return
     */
    @Override
    public TransitionConfig onFetchTransitionConfig() {
        return SCALE_TRANSITION_CONFIG;
    }

    /**
     * 调整状态栏的显示
     * @param show
     */
    protected void setStatusBarVisible(boolean show){
        Window window = getBaseFragmentActivity().getWindow();
        if(null == window) return;
        if(show){
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        }else{
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 销毁时显示状态栏
        setStatusBarVisible(true);
    }
}
