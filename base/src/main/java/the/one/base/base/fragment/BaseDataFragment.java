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

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUICenterGravityRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.List;

import the.one.base.R;
import the.one.base.base.view.BaseDataView;
import the.one.base.util.NetworkFailUtil;
import the.one.base.widge.WWPullRefreshLayout;
import the.one.net.entity.PageInfoBean;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseDataFragment<T> extends BaseFragment
        implements BaseDataView<T>, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener, QMUIPullRefreshLayout.OnPullListener {

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

    protected RecyclerView recycleView;
    protected BaseQuickAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected WWPullRefreshLayout pullLayout;

    public PageInfoBean pageInfoBean;
    public String empty_str = "无数据";

    public int page = 1;
    public boolean isFirst = true;
    public boolean isHeadFresh = false;
    public boolean isLoadMore = false;

    @Override
    protected int getContentViewId() {
        return R.layout.base_recyclerview;
    }
    
    @Override
    protected void initView(View rootView) {
        recycleView = rootView.findViewById(R.id.recycle_view);
        pullLayout = rootView.findViewById(R.id.pullLayout);
        adapter = getAdapter();
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (isLoadMore) {
                    page++;
                    isLoadMore = false;
                    requestServer();
                }
            }
        }, recycleView);
        adapter.disableLoadMoreIfNotFullPage();
        // 打开动画效果
        adapter.openLoadAnimation();
        // 动画一直执行
        adapter.isFirstOnly(true);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setNotDoAnimationCount(10);
        if (null != pullLayout) {
            pullLayout.setDragRate(0.5f);
            pullLayout.setRefreshOffsetCalculator(new QMUICenterGravityRefreshOffsetCalculator());
            pullLayout.setOnPullListener(this);
        }
        initRecycleView(recycleView, setType(), adapter);
    }

    protected void initRecycleView(RecyclerView recycleView, int type, BaseQuickAdapter adapter) {
        switch (type) {
            case TYPE_LIST:
                layoutManager = new LinearLayoutManager(getActivity());
                break;
            case TYPE_GRID:
                layoutManager = new GridLayoutManager(getActivity(), setColumn());
                break;
            case TYPE_STAGGERED:
                layoutManager = new StaggeredGridLayoutManager(setColumn(), StaggeredGridLayoutManager.VERTICAL);
                ((StaggeredGridLayoutManager) layoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                break;
        }
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(adapter);
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
        requestServer();
    }

    @Override
    public void onComplete(List<T> data) {
        onComplete(data, null, empty_str);
    }

    @Override
    public void onComplete(List<T> data, PageInfoBean pageInfoBean) {
        onComplete(data, pageInfoBean, empty_str);
    }

    @Override
    public void onComplete(List<T> data, PageInfoBean pageInfoBean, String emptyString) {
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
            adapter.loadMoreFail();
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
    public void onComplete(List<T> data, PageInfoBean pageInfoBean, String emptyStr, String btnString, View.OnClickListener listener) {
        if (null == data || data.size() == 0) {
            if (isFirst || isHeadFresh) {
                showEmptyPage(emptyStr, btnString, listener);
            } else {
                adapter.loadMoreEnd();
            }
        } else {
            if (isFirst) {
                showView(flBottomLayout);
                showView(flTopLayout);
                adapter.setNewData(data);
                showContentPage();
                isFirst = false;
            } else if (isHeadFresh) {
                adapter.setNewData(data);
                onHeadFreshSuccess();
            } else {
                adapter.addData(data);
            }
            setPageInfo(pageInfoBean);
        }
    }

    /**
     * 头部刷新成功
     */
    public void onHeadFreshSuccess() {
        isHeadFresh = false;
        if (pullLayout != null) pullLayout.finishRefresh();
    }

    /**
     * 设置页数信息
     *
     * @param mPageInfo
     */
    public void setPageInfo(PageInfoBean mPageInfo) {
        this.pageInfoBean = mPageInfo;
        if (null == mPageInfo) {
            isLoadMore = true;
            adapter.loadMoreComplete();
            return;
        }
        if (mPageInfo.getPageTotalCount() > mPageInfo.getPage()) {
            isLoadMore = true;
            adapter.loadMoreComplete();
        } else {
            adapter.loadMoreEnd();
            isLoadMore = false;
        }
    }

    /**
     * 普通模式（ 直接设置数据,不需要刷新和加载 - 在requestServer设置数据后再调用此方法 ）
     */
    @Override
    public void onNormal() {
        adapter.loadMoreEnd(true);
        if (null != pullLayout)
            pullLayout.setEnabled(false);
        showView(flBottomLayout);
        showView(flTopLayout);
        showContentPage();
    }

}
