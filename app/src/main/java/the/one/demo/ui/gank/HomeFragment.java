package the.one.demo.ui.gank;

import android.view.View;
import android.widget.ImageView;

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
import the.one.base.widge.QMUICollapsingTopBarLayout;
import the.one.demo.NetUrlConstant;
import the.one.demo.R;
import the.one.demo.adapter.HomeAdapter;
import the.one.demo.bean.GankBean;
import the.one.demo.bean.HomeBean;
import the.one.demo.bean.HomeHeadSection;
import the.one.demo.bean.HomeItemSection;
import the.one.demo.ui.presenter.HomePresenter;
import the.one.demo.ui.view.HomeView;


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

    @BindView(R.id.topbar)
    QMUITopBar topbar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout collapsingTopbarLayout;

    private HomePresenter presenter;
    private List<QMUISection<HomeHeadSection, HomeItemSection>> sections;
    private List<GankBean> welfare;

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected boolean isStickyHeader() { return true; }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mSectionLayout = rootView.findViewById(the.one.base.R.id.section_layout);
        collapsingTopbarLayout.setCollapsedTitleTextColor(getColorr(R.color.qmui_config_color_gray_2));
        collapsingTopbarLayout.setExpandedTitleColor(getColorr(R.color.qmui_config_color_white));
        collapsingTopbarLayout.setStateChangeListener(new QMUICollapsingTopBarLayout.AppBarStateChangeListener() {
            @Override
            public void onStateChanged(QMUICollapsingTopBarLayout.State state, int offset) {

            }
        });
        initStickyLayout();
        initData();
    }

    @Override
    protected QMUIStickySectionAdapter createAdapter() {
        return new HomeAdapter();
    }

    @Override
    protected void requestServer() {
        presenter.getData(HomePresenter.TYPE_WELFARE);
    }

    @Override
    public void onWelfareComplete(List<GankBean> data) {
        presenter.getData(HomePresenter.TYPE_TODAY);
        welfare = data;
        setWelfare();
    }

    private void setWelfare(){
        if(null != welfare){
            int size = welfare.size();
            if(size>0){
                int index = (int)(Math.random()*(size));
                GankBean gankBean = welfare.get(index);
                GlideUtil.load(_mActivity,gankBean.getUrl(),ivHead);
            }
        }
    }

    @Override
    public void onTodayComplete(HomeBean resultsBean) {
        collapsingTopbarLayout.setTitle("今日最新干货");
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
        BaseWebExplorerActivity.newInstance(_mActivity,itemSection.content, itemSection.url);
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
