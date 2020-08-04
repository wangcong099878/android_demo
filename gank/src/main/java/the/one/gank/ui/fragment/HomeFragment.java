package the.one.gank.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIAppBarLayout;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.section.QMUISection;
import com.qmuiteam.qmui.widget.section.QMUIStickySectionAdapter;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.holder.HolderCreator;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import butterknife.BindView;
import the.one.base.ui.activity.BaseWebExplorerActivity;
import the.one.base.ui.fragment.BaseSectionLayoutFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.QMUIStatusBarHelper;
import the.one.gank.R;
import the.one.gank.bean.BannerBean;
import the.one.gank.bean.GankBean;
import the.one.gank.bean.HomeBean;
import the.one.gank.bean.HomeHeadSection;
import the.one.gank.bean.HomeItemSection;
import the.one.gank.constant.NetUrlConstant;
import the.one.gank.ui.adapter.BannerViewHolder;
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
public class HomeFragment extends BaseSectionLayoutFragment implements HomeView, QMUICollapsingTopBarLayout.OnOffsetUpdateListener {

    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    @BindView(R.id.appbar_layout)
    QMUIAppBarLayout appbarLayout;
    @BindView(R.id.banner_view)
    BannerViewPager<BannerBean, BannerViewHolder> mBannerViewPager;
    private HomeAdapter mAdapter;

    private HomePresenter presenter;
    private List<QMUISection<HomeHeadSection, HomeItemSection>> sections;

    private boolean isLightMode = false;
    private boolean isOnResume = false;

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    protected boolean isNeedChangeStatusBarMode() {
        return true;
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return isLightMode;
    }

    @Override
    protected boolean isStickyHeader() {
        return true;
    }

    @Override
    protected void initView(View rootView) {
        mStatusLayout = rootView.findViewById(R.id.status_layout);
        mSectionLayout = rootView.findViewById(the.one.base.R.id.section_layout);
        mCollapsingTopBarLayout.setCollapsedTitleTextColor(getColorr(R.color.qmui_config_color_gray_2));
        mCollapsingTopBarLayout.setExpandedTitleColor(getColorr(R.color.qmui_config_color_transparent));
        mCollapsingTopBarLayout.setTitle("今日最新干货");
        mCollapsingTopBarLayout.addOnOffsetUpdateListener(this);
        initStickyLayout();
        initData();
    }

    private float X = 0.8f;

    @Override
    public void onOffsetChanged(QMUICollapsingTopBarLayout layout, int offset, float expandFraction) {
        // mBannerViewPager 的轮播会触发这个方法，所以当offset==0的时候不做处理
        if (!isOnResume || offset == 0) return;
        boolean isLight =expandFraction > X;
        if (!isLightMode && isLight) {
            setStatusBarMode(true);
        } else if (isLightMode && !isLight) {
            setStatusBarMode(false);
        }
    }


    @Override
    protected QMUIStickySectionAdapter createAdapter() {
        return mAdapter = new HomeAdapter();
    }

    @Override
    protected void requestServer() {
        presenter.getTodayData();
        presenter.getBanner();
    }

    @Override
    public void onBannerComplete(final List<BannerBean> data) {
        mBannerViewPager
                .setIndicatorGravity(IndicatorGravity.END)
                .setIndicatorSliderColor(getColorr(R.color.white), QMUIResHelper.getAttrColor(_mActivity, R.attr.app_skin_primary_color))
                .setHolderCreator(new HolderCreator<BannerViewHolder>() {
                    @Override
                    public BannerViewHolder createViewHolder() {
                        return new BannerViewHolder();
                    }
                })
                .setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
                    @Override
                    public void onPageClick(int position) {
                        BannerBean bannerBean = data.get(position);
                        BaseWebExplorerActivity.newInstance(_mActivity, bannerBean.getTitle(), bannerBean.getUrl());
                    }
                })
                .create(data);
    }

    @Override
    public void onHotComplete(List<GankBean> data) {

    }

    @Override
    public void onTodayComplete(final HomeBean resultsBean) {
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
        showContentPage();
        QMUIStatusBarHelper.setStatusBarDarkMode(_mActivity);
    }

    /**
     * 更改状态栏模式
     * 由于只有折叠和展开时才会调用，所以在这里对轮播也进行处理下
     * @param isLight
     * @remark 显示的时候才做更改
     */
    private void setStatusBarMode(boolean isLight) {
        isLightMode = isLight;
        if (isOnResume) {
            setBannerStatue(isLight);
            updateStatusBarMode(isLight);
        }
    }

    /**
     * 设置轮播状态
     * @param start
     */
    private void setBannerStatue(boolean start){
        if (mBannerViewPager != null) {
            if(start){
                mBannerViewPager.stopLoop();
            }else{
                mBannerViewPager.startLoop();
            }
        }
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void showContentPage() {
        showView(appbarLayout);
        goneView(mStatusLayout);
        super.showContentPage();
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

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onLazyResume() {
        isOnResume = true;
        super.onLazyResume();
        setBannerStatue(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    public void onPause() {
        isOnResume = false;
        super.onPause();
        setBannerStatue(false);
    }

}
