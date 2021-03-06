package the.one.base.ui.fragment;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUICenterGravityRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import the.one.base.Interface.IPageInfo;
import the.one.base.Interface.OnTopBarDoubleClickListener;
import the.one.base.R;
import the.one.base.ui.view.BaseDataView;
import the.one.base.util.NetworkFailUtil;
import the.one.base.widge.TheLoadMoreView;
import the.one.base.widge.decoration.SpacesItemDecoration;
import the.one.base.widge.pullrefresh.PullRefreshLayout;

import static androidx.recyclerview.widget.RecyclerView.LayoutManager;
import static androidx.recyclerview.widget.RecyclerView.OnScrollListener;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe 列表类型Fragment基类
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseListFragment<T> extends BaseFragment
        implements BaseDataView<T>, OnItemClickListener, OnItemLongClickListener,
        QMUIPullRefreshLayout.OnPullListener, OnTopBarDoubleClickListener {

    /**
     * 列表
     */
    public final static int TYPE_LIST = 1;
    /**
     * 网格
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
        return 12;
    }

    /**
     * 是否需要间距
     *
     * @return
     */
    protected boolean isNeedSpace() {
        return setType() != TYPE_LIST;
    }

    /**
     * 是否显示已经到最后的View
     *
     * @return
     */
    protected boolean isShowLoadMoreEnd() {
        return true;
    }

    protected RecyclerView recycleView;
    protected BaseQuickAdapter adapter;
    protected PullRefreshLayout pullLayout;

    public IPageInfo pageInfoBean;
    public String empty_str = "无数据";

    public int page = 1;
    public boolean isFirst = true;
    public boolean isHeadFresh = false;

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
        adapter.getLoadMoreModule().setLoadMoreView(new TheLoadMoreView());
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

    /**
     * 初始化RecycleView
     *
     * @param recycleView
     * @param type
     * @param adapter
     */
    protected void initRecycleView(RecyclerView recycleView, int type, BaseQuickAdapter adapter) {
        if (isNeedSpace())
            recycleView.addItemDecoration(getSpacesItemDecoration());
        recycleView.setLayoutManager(getLayoutManager(type));
        if (null != getOnScrollListener())
            recycleView.addOnScrollListener(getOnScrollListener());
        recycleView.setAdapter(adapter);
    }

    /**
     * 设置间距
     *
     * @return
     */
    protected SpacesItemDecoration getSpacesItemDecoration() {
        int space = QMUIDisplayHelper.dp2px(_mActivity, setSpacing());
        return new SpacesItemDecoration(setType() == TYPE_LIST ? 1 : setColumn(), adapter.getHeaderLayoutCount(), space);
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
                layoutManager = new LinearLayoutManager(getActivity());
                break;
        }
        return layoutManager;
    }

    protected OnScrollListener getOnScrollListener() {
        return null;
    }

    @Override
    protected void onLazyInit() {
        onFirstLoading();
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
    public void onFirstLoading() {
        showLoadingPage();
        page = 1;
        isFirst = true;
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
                onFirstLoading();
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
                    onFirstLoading();
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
        showContentPage();
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
            adapter.getLoadMoreModule().loadMoreEnd(!isShowLoadMoreEnd());
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
        if (null != recycleView && null != adapter)
            recycleView.smoothScrollToPosition(0);
    }

}
