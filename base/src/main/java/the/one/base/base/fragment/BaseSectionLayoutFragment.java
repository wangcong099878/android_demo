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

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionLayout;

import the.one.base.R;
import the.one.base.widge.WWPullRefreshLayout;

/**
 * @author The one
 * @date 2019/2/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseSectionLayoutFragment<H extends QMUISection.Model<H>, T extends QMUISection.Model<T>>
        extends BaseFragment implements QMUIStickySectionAdapter.Callback<H, T>, QMUIPullRefreshLayout.OnPullListener {

    /**
     * List
     */
    public final static int TYPE_LIST = 1;
    /**
     * Grid
     */
    public final static int TYPE_GRID = 2;


    protected QMUIStickySectionLayout mSectionLayout;
    protected WWPullRefreshLayout mPullRefreshLayout;

    protected RecyclerView.LayoutManager mLayoutManager;
    protected QMUIStickySectionAdapter<H, T, QMUIStickySectionAdapter.ViewHolder> mAdapter;

    protected int page = 1;

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

    protected boolean isStickyHeader(){return true;}

    /**
     * 设置适配器
     *
     * @return
     */
    protected abstract QMUIStickySectionAdapter<H, T, QMUIStickySectionAdapter.ViewHolder> createAdapter();

    protected abstract void requestServer();

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_section_layout;
    }

    @Override
    protected void initView(View rootView) {
        mSectionLayout = rootView.findViewById(R.id.section_layout);
        mPullRefreshLayout = rootView.findViewById(R.id.pullLayout);

        initRefreshLayout();
        initStickyLayout();
        initData();
        if (null != mTopLayout && mTopLayout.getVisibility() != View.VISIBLE) {
            setMargins(mStatusLayout, 0, 0, 0, 0);
        }
    }

    @Override
    protected void onLazyInit() {
            requestServer();
    }

    protected void initRefreshLayout() {
        mPullRefreshLayout.setChildScrollUpCallback(new QMUIPullRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(QMUIPullRefreshLayout parent, @Nullable View child) {
                return QMUIPullRefreshLayout.defaultCanScrollUp(mSectionLayout.getRecyclerView());
            }
        });
        mPullRefreshLayout.setOnPullListener(this);
    }

    protected void initStickyLayout() {
        mLayoutManager = createLayoutManager();
        mSectionLayout.getRecyclerView().setLayoutManager(mLayoutManager);
    }

    protected RecyclerView.LayoutManager createLayoutManager() {
        if (setType() == TYPE_LIST) {
            return new LinearLayoutManager(getContext()) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    return new RecyclerView.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            };
        } else {
            final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), setColumn());
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int i) {
                    return mAdapter.getItemIndex(i) < 0 ? layoutManager.getSpanCount() : 1;
                }
            });
            return layoutManager;
        }
    }

    protected void initData() {
        mAdapter = createAdapter();
        mAdapter.setCallback(this);
        mSectionLayout.setAdapter(mAdapter,isStickyHeader());
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
        requestServer();
    }
}
