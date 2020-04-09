package the.one.base.base.fragment;

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

import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUICenterGravityRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import the.one.base.Interface.IPageInfo;
import the.one.base.Interface.OnTopBarDoubleClickListener;
import the.one.base.R;
import the.one.base.base.view.BaseDataView;
import the.one.base.util.NetworkFailUtil;
import the.one.base.util.OffsetLinearLayoutManager;
import the.one.base.widge.decoration.SpacesItemDecoration;
import the.one.base.widge.pullrefresh.PullRefreshLayout;

import static androidx.recyclerview.widget.RecyclerView.LayoutManager;
import static androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseDataFragment<T> extends BaseFragment
        implements BaseDataView<T>, OnItemClickListener, OnItemLongClickListener,
        QMUIPullRefreshLayout.OnPullListener, OnTopBarDoubleClickListener {

    /**
     * List
     */
    public final static int TYPE_LIST = 1;
    /**
     * Grid
     */
    public final static int TYPE_GRID = 2;
    /**
     * 瀑布流
     */
    public final static int TYPE_STAGGERED = 3;

    protected abstract BaseQuickAdapter getAdapter();

    protected abstract void requestServer();

    /**
     * 设置要显示的类型，默认List
     *
     * @return
     */
    protected int setType() {
        return TYPE_LIST;
    }

    /**
     * 设置列数
     *
     * @return
     */
    protected int setColumn() {
        return 2;
    }

    /**
     * 间距
     *
     * @return
     */
    protected int setSpacing() {
        return 14;
    }

    /**
     * 是否需要间距
     *
     * @return
     */
    protected boolean isNeedSpace() {
        return setType()!=TYPE_LIST;
    }

    protected RecyclerView recycleView;
    protected BaseQuickAdapter adapter;
    protected PullRefreshLayout pullLayout;

    public IPageInfo pageInfoBean;
    public String empty_str = "无数据";

    public int page = 1;
    public boolean isFirst = true;
    public boolean isHeadFresh = false;
    protected boolean isGlidePause = true;

    @Override
    protected int getContentViewId() {
        return R.layout.base_recyclerview;
    }

    @Override
    protected void initView(View rootView) {
        recycleView = rootView.findViewById(R.id.recycle_view);
        pullLayout = rootView.findViewById(R.id.pullLayout);
        adapter = getAdapter();
        initPullLayout();
        initAdapter();
        initRecycleView(recycleView, setType(), adapter);
        //添加双击监听
        if (null != mTopLayout && mTopLayout.getVisibility() == View.VISIBLE) {
            mTopLayout.setOnTopBarDoubleClickListener(this);
        }
    }

    protected void initPullLayout() {
        if (null != pullLayout) {
            pullLayout.setDragRate(0.5f);
            pullLayout.setRefreshOffsetCalculator(new QMUICenterGravityRefreshOffsetCalculator());
            pullLayout.setOnPullListener(this);
            pullLayout.setEnabled(false);
        }
    }

    protected void initAdapter() {
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                requestServer();
            }
        });
        adapter.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
        // 打开动画效果
        adapter.setAnimationEnable(true);
        // 动画一直执行
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
    }

    protected void initRecycleView(RecyclerView recycleView, int type, BaseQuickAdapter adapter) {
        if (isNeedSpace())
            recycleView.addItemDecoration(new SpacesItemDecoration(adapter.getHeaderLayoutCount(), QMUIDisplayHelper.dp2px(_mActivity, setSpacing()), setType() == TYPE_LIST ? 1 : setColumn()));
        recycleView.setLayoutManager(getLayoutManager(type));
        recycleView.addOnScrollListener(getOnScrollListener());
        recycleView.setAdapter(adapter);
    }

    protected LayoutManager getLayoutManager(int type) {
        LayoutManager layoutManager;
        switch (type) {
            case TYPE_GRID:
                layoutManager = new GridLayoutManager(getActivity(), setColumn());
                break;
            case TYPE_STAGGERED:
                layoutManager = new StaggeredGridLayoutManager(setColumn(), StaggeredGridLayoutManager.VERTICAL);
                ((StaggeredGridLayoutManager) layoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                break;
            default:
                layoutManager = new OffsetLinearLayoutManager(getActivity());
                break;
        }
        return layoutManager;
    }

    protected OnScrollListener getOnScrollListener() {
        return new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {
                    isGlidePause = false;
                    Glide.with(_mActivity).resumeRequests();
                } else if (!isGlidePause) {
                    isGlidePause = true;
                    Glide.with(_mActivity).pauseRequests();
                }
            }
        };
    }

    @Override
    protected void onLazyInit() {
        refresh();
    }

    @Override
    public void onMoveTarget(int offset) {

    }

    @Override
    public void onMoveRefreshView(int offset) {

    }

    @Override
    public void onRefresh() {
        page = 1;
        isHeadFresh = true;
        requestServer();
    }

    @Override
    public void refresh() {
        page = 1;
        isFirst = true;
        showLoadingPage();
        recycleView.scrollToPosition(0);
        requestServer();
    }

    @Override
    public void onComplete(List<T> data) {
        onComplete(data, null, empty_str);
    }

    @Override
    public void onComplete(List<T> data, IPageInfo pageInfoBean) {
        onComplete(data, pageInfoBean, empty_str);
    }

    @Override
    public void onComplete(List<T> data, IPageInfo pageInfoBean, String emptyString) {
        onComplete(data, pageInfoBean, emptyString, "刷新试试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
    }

    @Override
    public void onFail(Exception e) {
        onFail(NetworkFailUtil.getFailString(e));
    }

    /**
     * 请求失败
     * 判断是什么情况下的失败
     *
     * @param errorMsg
     */
    @Override
    public void onFail(String errorMsg) {
        if (isFirst) {
            showErrorPage(errorMsg, "刷新试试", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLoadingPage();
                    requestServer();
                }
            });
        } else if (isHeadFresh) {
            showToast(errorMsg);
            onHeadFreshSuccess();
        } else {
            adapter.getLoadMoreModule().loadMoreFail();
        }
    }

    /**
     * 数据请求成功后调用此方法
     *
     * @param data
     * @param pageInfoBean
     * @param emptyStr     空页面提示语
     */
    @Override
    public void onComplete(List<T> data, IPageInfo pageInfoBean, String emptyStr, String btnString, View.OnClickListener listener) {
        if (null == data || data.size() == 0) {
            if (isFirst || isHeadFresh) {
                adapter.setNewInstance(data);
                if (isHeadFresh) onHeadFreshSuccess();
                showEmptyPage(emptyStr, btnString, listener);
            } else {
                adapter.getLoadMoreModule().loadMoreEnd();
            }
        } else {
            if (isFirst) {
                onFirstComplete(data);
            } else if (isHeadFresh) {
                onHeadFreshComplete(data);
            } else {
                adapter.addData(data);
            }
            setPageInfo(pageInfoBean);
        }
    }

    protected void onFirstComplete(List<T> data) {
        showView(flBottomLayout);
        showView(flTopLayout);
        adapter.setNewInstance(data);
        showContentPage();
        setPullLayoutEnabled(true);
        isFirst = false;
    }

    protected void onHeadFreshComplete(List<T> data) {
        adapter.setNewInstance(data);
        onHeadFreshSuccess();
    }

    /**
     * 头部刷新成功
     */
    public void onHeadFreshSuccess() {
        isHeadFresh = false;
        setPullLayoutEnabled(true);
    }

    /**
     * 设置页数信息
     *
     * @param mPageInfo
     */
    public void setPageInfo(IPageInfo mPageInfo) {
        this.pageInfoBean = mPageInfo;
        if (null == mPageInfo || mPageInfo.getPageTotalCount() > mPageInfo.getCurrentPage()) {
            page++;
            adapter.getLoadMoreModule().loadMoreComplete();
        } else {
            adapter.getLoadMoreModule().loadMoreEnd(mPageInfo.getPageTotalCount() == 1);
        }
    }

    protected void setPullLayoutEnabled(boolean enabled) {
        if (null != pullLayout) {
            pullLayout.setEnabled(enabled);
            pullLayout.finishRefresh();
        }
    }

    /**
     * 普通模式（ 直接设置数据,不需要刷新和加载 - 在requestServer设置数据后再调用此方法 ）
     */
    @Override
    public void onNormal() {
        adapter.getLoadMoreModule().loadMoreEnd(true);
        setPullLayoutEnabled(false);
        showView(flBottomLayout);
        showView(flTopLayout);
        showContentPage();
    }

    @Override
    public void onDoubleClicked(View v) {
        if (null != recycleView)
            recycleView.smoothScrollToPosition(0);
    }

}
