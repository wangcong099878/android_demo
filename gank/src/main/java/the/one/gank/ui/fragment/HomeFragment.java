package the.one.gank.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import com.qmuiteam.qmui.widget.QMUIAppBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import the.one.base.base.activity.BaseWebExplorerActivity;
import the.one.base.base.fragment.BaseSectionLayoutFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.GlideUtil;
import the.one.base.widge.TheCollapsingTopBarLayout;
import the.one.gank.R;
import the.one.gank.bean.GankBean;
import the.one.gank.bean.HomeBean;
import the.one.gank.bean.HomeHeadSection;
import the.one.gank.bean.HomeItemSection;
import the.one.gank.constant.NetUrlConstant;
import the.one.gank.ui.adapter.HomeAdapter;
import the.one.gank.ui.presenter.HomePresenter;
import the.one.gank.ui.view.HomeView;


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
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class HomeFragment extends BaseSectionLayoutFragment implements HomeView {

    private final int STATE_COLLAPSED = 0;
    private final int STATE_EXPANDED = 1;

    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.collapsing_topbar_layout)
    TheCollapsingTopBarLayout mCollapsingTopBarLayout;
    @BindView(R.id.appbar_layout)
    QMUIAppBarLayout appbarLayout;

    private HomePresenter presenter;
    private List<QMUISection<HomeHeadSection, HomeItemSection>> sections;
    private List<GankBean> welfare;

//    private boolean isCollapsed = false;
//
//    public boolean isCollapsed() {
//        return isCollapsed;
//    }
//
//    @Override
//    protected boolean isNeedChangeStatusBarMode() {
//        return true;
//    }
//
//    @Override
//    protected boolean isStatusBarLightMode() {
//        return isCollapsed;
//    }

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mStatusLayout = rootView.findViewById(R.id.status_layout);
        mSectionLayout = rootView.findViewById(the.one.base.R.id.section_layout);
        mCollapsingTopBarLayout.setCollapsedTitleTextColor(getColorr(R.color.qmui_config_color_gray_2));
        mCollapsingTopBarLayout.setExpandedTitleColor(getColorr(R.color.qmui_config_color_white));
//        mCollapsingTopBarLayout.setStateChangeListener(new TheCollapsingTopBarLayout.AppBarStateChangeListener() {
//            @Override
//            public void onStateChanged(TheCollapsingTopBarLayout.State state, int offset) {
//                if (state == TheCollapsingTopBarLayout.State.COLLAPSED) {
//                    isCollapsed = true;
//                    QMUIStatusBarHelper.setStatusBarLightMode(_mActivity);
//                } else if (state == TheCollapsingTopBarLayout.State.EXPANDED) {
//                    isCollapsed = false;
//                    QMUIStatusBarHelper.setStatusBarDarkMode(_mActivity);
//                }
//            }
//        });
        initStickyLayout();
        initData();
    }

    @Override
    protected QMUIStickySectionAdapter createAdapter() {
        return new HomeAdapter();
    }

    @Override
    protected void requestServer() {
        showLoadingPage();
        presenter.getData(HomePresenter.TYPE_TODAY);
    }

    @Override
    public void onWelfareComplete(final List<GankBean> data) {
        welfare = data;
        setWelfare();

    }

    private void setWelfare() {
        if (null != welfare) {
            int size = welfare.size();
            if (size > 0) {
                int index = (int) (Math.random() * (size));
                GankBean gankBean = welfare.get(index);
                GlideUtil.load(_mActivity, gankBean.getUrl(), ivHead);
            }
        }
    }

    @Override
    public void onTodayComplete(final HomeBean resultsBean) {
        presenter.getData(HomePresenter.TYPE_WELFARE);
        mCollapsingTopBarLayout.setTitle("今日最新干货");
        sections = new ArrayList<>();
        sections.add(parseSection(resultsBean.Android, NetUrlConstant.ANDROID));
        sections.add(parseSection(resultsBean.iOS, NetUrlConstant.IOS));
        sections.add(parseSection(resultsBean.App, NetUrlConstant.APP));
        sections.add(parseSection(resultsBean.relax, NetUrlConstant.RELAX));
        sections.add(parseSection(resultsBean.front, NetUrlConstant.FRONT));
        sections.add(parseSection(resultsBean.extension, NetUrlConstant.EXTENSION));
        sections.add(parseSection(resultsBean.recommend, NetUrlConstant.RECOMMEND));
        sections.add(parseSection(resultsBean.welfare, NetUrlConstant.WELFARE));
        mAdapter.setData(sections);
        showView(appbarLayout);
        goneView(mStatusLayout);
        showContentPage();
    }

    private QMUISection<HomeHeadSection, HomeItemSection> parseSection(List<GankBean> gankBeans, String head) {
        List<HomeItemSection> itemSections = new ArrayList<>();
        for (int i = 0; i < gankBeans.size(); i++) {
            GankBean gankBean = gankBeans.get(i);
            itemSections.add(new HomeItemSection(gankBean.getDesc(), gankBean.getImages(), gankBean.getWho(), gankBean.getUrl()));
        }
        return new QMUISection<HomeHeadSection, HomeItemSection>(new HomeHeadSection(head), itemSections);
    }

    @Override
    public void loadMore(QMUISection section, boolean loadMoreBefore) {

    }

    @Override
    public void onItemClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
        HomeItemSection itemSection = (HomeItemSection) mAdapter.getSectionItem(position);
        BaseWebExplorerActivity.newInstance(_mActivity, itemSection.content, itemSection.url);
    }

    @Override
    public boolean onItemLongClick(QMUIStickySectionAdapter.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new HomePresenter();
    }

    @Override
    public void onPause() {
        super.onPause();
        setWelfare();
    }

}
