package the.one.anastasia.base;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUICenterGravityRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import the.one.anastasia.R;
import the.one.anastasia.adapter.EntityAdapter;
import the.one.anastasia.bean.PageInfoBean;
import the.one.anastasia.view.GridViewWithHeaderAndFooter;
import the.one.anastasia.view.ProgressWheel;

public abstract class BaseDataFragment<T> extends BaseFragment {

    public final int TYPE_LIST = 1;
    public final int TYPE_GRID = 2;

    public List<T> datas ;
    public FrameLayout bottomLayout;
    private GridViewWithHeaderAndFooter gridView;
    public ListView listView;
    private EntityAdapter adapter;
    private QMUIPullRefreshLayout pullLayoutGrid = null;
    private QMUIPullRefreshLayout pullLayoutList = null;
    private View mFooterView;
    private View mHeadView;
    private ProgressWheel progressWheel;
    private TextView textView;
    private OnScrollListener onScrollListener;
    private OnItemClick onItemClick;
    private OnItemLongClick onItemLongClick;
    public int page = 1;
    public boolean isFirst = true;
    public boolean isHeadFresh = false;
    public boolean LOAD_MORE = false;
    private String footer_end;
    private String footerErrorString = "加载失败，点击重新加载";

    protected int setType() {
        return TYPE_LIST;
    }

    protected View getHeadView() {
        return null;
    }

    protected int getBottomLayout() {
        return 0;
    }

    protected abstract EntityAdapter setAdapter();

    protected abstract void requestServer();

    protected abstract void onItemClick(final T data);

    protected abstract void onItemLongClick(final T data);

    protected int setNumColumns() {
        return 2;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_base_data;
    }

    @Override
    protected boolean isShowNoNet() {
        return true;
    }

    @Override
    protected void initView(View rootView) {

        footer_end = getResources().getString(R.string.footer_end);
        mHeadView = getHeadView();

        bottomLayout = rootView.findViewById(R.id.bottom_layout);
        if(0 != getBottomLayout())
            bottomLayout.addView(getView(getBottomLayout()));
        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.footer_loading_view, null);
        progressWheel = mFooterView.findViewById(R.id.ptr_classic_header_rotate_view_progressbar);
        textView = mFooterView.findViewById(R.id.tv_des);
        mFooterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textView.getText().toString().equals(footerErrorString)) {
                    ViewShow(progressWheel);
                    textView.setText("加载中");
                    LOAD_MORE = true;
                    requestServer();
                }
            }
        });

        onScrollListener = new OnScrollListener();
        onItemClick = new OnItemClick();
        onItemLongClick = new OnItemLongClick();

        datas  = new ArrayList<>();

        int type = setType();
        if (type == TYPE_LIST) {
            listView = rootView.findViewById(R.id.list_view);
            pullLayoutList = rootView.findViewById(R.id.pullLayout_list);
            listView = rootView.findViewById(R.id.list_view);
            setPullListener(pullLayoutList);

            if (mHeadView != null)
                listView.addHeaderView(mHeadView);
            listView.addFooterView(mFooterView);
            setListener(listView);
            ViewShow(pullLayoutList);
        } else if (type == TYPE_GRID) {
            pullLayoutGrid = rootView.findViewById(R.id.pullLayout_grid);
            gridView = rootView.findViewById(R.id.grid_view);
            setPullListener(pullLayoutGrid);

            if (mHeadView != null)
                gridView.addHeaderView(mHeadView);
            gridView.addFooterView(mFooterView);
            setListener(gridView);
            ViewShow(pullLayoutGrid);
        } else {
            showEmptyPage("参数传递错误");
            return;
        }
        firstRequest();
    }

    public void firstRequest(){
        isFirst = true;
        showLoadingPage();
        requestServer();
    }

    private void setPullListener(QMUIPullRefreshLayout pullLayout) {
        //下拉到一定距离后不动
//        mPullRefreshLayout.setRefreshOffsetCalculator(new QMUIDefaultRefreshOffsetCalculator());
        //一直跟着下拉的效果
//        mPullRefreshLayout.setRefreshOffsetCalculator(new QMUIFollowRefreshOffsetCalculator());
        //处于下拉区域的中间的效果
        pullLayout.setRefreshOffsetCalculator(new QMUICenterGravityRefreshOffsetCalculator());

        pullLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {

            @Override
            public void onMoveTarget(int offset) { }

            @Override
            public void onMoveRefreshView(int offset) { }

            @Override
            public void onRefresh() {
                page = 1;
                isHeadFresh = true;
                requestServer();
            }
        });
    }

    /**
     * 设置监听
     *
     * @param view
     */
    private void setListener(AbsListView view) {
        view.setAdapter(adapter = setAdapter());
        view.setOnScrollListener(onScrollListener);
        view.setOnItemClickListener(onItemClick);
        view.setOnItemLongClickListener(onItemLongClick);
    }

    public class OnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {
            // 当不滚动时
            if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                // 判断是否滚动到底部
                if (absListView.getLastVisiblePosition() == absListView.getCount() - 1 && LOAD_MORE) {
                    //页数增加1
                    page++;
                    //这里要设为false，等待上一次数据请求结束之后才能继续，避免重复操作，导致数据不断加载，page错误
                    LOAD_MORE = false;
                    //数据请求
                    requestServer();
                }
            }
        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {

        }
    }

    public class OnItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            BaseDataFragment.this.onItemClick(datas.get(i));
        }
    }

    public class OnItemLongClick implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            BaseDataFragment.this.onItemLongClick(datas.get(i));
            return false;
        }
    }

    /**
     * 数据请求成功后调用此方法
     *
     * @param data
     * @param pageInfoBean
     */
    public void onComplete(List<T> data, PageInfoBean pageInfoBean) {
        onComplete(data, pageInfoBean, "无数据","刷新试试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstRequest();
            }
        });
    }

    public void onComplete(List<T> data, PageInfoBean pageInfoBean,String emptyString) {
        onComplete(data, pageInfoBean, emptyString,"刷新试试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstRequest();
            }
        });
    }

    /**
     * 数据请求成功后调用此方法
     *
     * @param data
     * @param pageInfoBean
     * @param emptyStr     空页面提示语
     */
    public void onComplete(List<T> data, PageInfoBean pageInfoBean, String emptyStr,String btnString,View.OnClickListener listener) {
        if (null == data || data.size() == 0) {
        } else {
            if (isFirst) {
                showContentPage();
                adapter.addHeadData(data);
                isFirst = false;
                isHeadFresh = false;
            } else if (isHeadFresh) {
                isHeadFresh = false;
                adapter.addHeadData(data);
                datas.clear();
                onHeadFreshSuccess();
            } else {
                adapter.addFootData(data);
            }
            datas.addAll(data);
            if (page == 1&&datas.size()>0){
                listView.smoothScrollToPosition(0);
            }
            setPageInfo(pageInfoBean);
        }
    }

    /**
     * 网络请求失败时调用此方法
     * @param errorMsg
     */
    public void onFail(String errorMsg) {
        showToast(errorMsg);
        if (isFirst) {
            showErrorPage(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firstRequest();
                }
            });
        } else if (isHeadFresh) {
            onHeadFreshSuccess();
        } else {
            LOAD_MORE = true;
            setFootText(footerErrorString);
        }
    }

    /**
     * 设置尾部文字（ 加载错误或者没有更多 )
     *
     * @param str
     */
    public void setFootText(String str) {
        ViewGone(progressWheel);
        textView.setText(str);
    }

    /**
     * 头部刷新成功
     */
    public void onHeadFreshSuccess() {
        isHeadFresh = false;
        if (pullLayoutGrid != null) {
            pullLayoutGrid.finishRefresh();
        }
        if (pullLayoutList != null) {
            pullLayoutList.finishRefresh();
        }
    }

    /**
     * 移除尾部
     */
    public void removeFooterView() {
        if (listView != null)
            listView.removeFooterView(mFooterView);
        if (gridView != null)
            gridView.removeFooterView(mFooterView);
    }

    /**
     * 设置页数信息
     * @param mPageInfo
     */
    public void setPageInfo(PageInfoBean mPageInfo) {
        if (mPageInfo.getPageTotalCount() <= mPageInfo.getPage()) {
            setFootText(footer_end);
            LOAD_MORE = false;
        } else {
            ViewShow(progressWheel);
            textView.setText("加载中");
            LOAD_MORE = true;
        }
    }

    /**
     * 设置最后一页时尾部文字
     * @param footer_end
     */
    public void setFooter_end(String footer_end) {
        this.footer_end = footer_end;
    }

    @Override
    protected void initData() {

    }
}
