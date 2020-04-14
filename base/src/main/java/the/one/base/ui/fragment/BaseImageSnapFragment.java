package the.one.base.ui.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

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

    /**
     * 是否全屏 默认在TopBar下面，如果为全屏则延伸至状态栏
     *
     * @return
     */
    protected boolean isFullScreen() {
        return true;
    }


    protected void onScrollChanged(T item, int position) {

    }

    /**
     * 滑动方向
     *
     * @return
     */
    protected int getOrientation() {
        return RecyclerView.HORIZONTAL;
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return false;
    }

    @Override
    protected boolean isNeedSpace() {
        return false;
    }

    @Override
    protected void initView(View rootView) {
        rootView.setBackgroundColor(getColorr(R.color.we_chat_black));
        mStatusLayout.setBackgroundColor(getColorr(R.color.we_chat_black));
        mTopLayout.setBackgroundColor(getColorr(R.color.qmui_config_color_transparent));
        mTopLayout.getTopBar().getTitleView().setTextColor(getColorr(R.color.white));
        mTopLayout.addLeftImageButton(R.drawable.mz_titlebar_ic_back_light, R.id.topbar_left_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        super.initView(rootView);
        updateStatusView();
    }

    protected void updateStatusView(){
        if (isFullScreen()) {
            setMargins(mStatusLayout, 0, 0, 0, 0);
            mStatusLayout.setFitsSystemWindows(false);
        }else{
            mStatusLayout.setFitsSystemWindows(true);
            setMargins(mStatusLayout, 0, QMUIResHelper.getAttrDimen(_mActivity,R.attr.qmui_topbar_height)+ QMUIStatusBarHelper.getStatusbarHeight(_mActivity), 0, 0);
        }
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        mImageSnapAdapter = new ImageSnapAdapter<T>();
        mImageSnapAdapter.setOnImageClickListener(this);
        return mImageSnapAdapter;
    }

    @Override
    protected void initRecycleView(RecyclerView recycleView, int type, BaseQuickAdapter adapter) {
        recycleView.setBackgroundColor(getColorr(R.color.we_chat_black));
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
        mStatusLayout.getLoadingTipsView().setTextColor(getColorr(R.color.white));
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return true;
    }

}
