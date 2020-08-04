package the.one.gank.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.qqface.QMUIQQFaceView;
import com.qmuiteam.qmui.util.QMUIColorHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.constant.DataConstant;
import the.one.base.model.SamplePageInfoBean;
import the.one.base.ui.activity.BaseWebExplorerActivity;
import the.one.base.ui.fragment.BaseListFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.QMUIStatusBarHelper;
import the.one.base.util.ViewUtil;
import the.one.base.widge.OffsetLinearLayoutManager;
import the.one.gank.R;
import the.one.gank.bean.BannerBean;
import the.one.gank.bean.GankBean;
import the.one.gank.bean.HomeBean;
import the.one.gank.bean.HomeSection;
import the.one.gank.constant.NetUrlConstant;
import the.one.gank.constant.NetUrlConstantV2;
import the.one.gank.ui.adapter.BannerViewHolder;
import the.one.gank.ui.adapter.Home2Adapter;
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
 * @describe 主页第二种样式
 * @email 625805189@qq.com
 * @remark
 */
public class HomeV2Fragment extends BaseListFragment<HomeSection> implements HomeView {

    private HomeV2Fragment() {
    }

    public static HomeV2Fragment newInstance(boolean isV2) {
        HomeV2Fragment fragment = new HomeV2Fragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(DataConstant.TYPE, isV2);
        fragment.setArguments(bundle);
        return fragment;
    }


    private BannerViewPager<BannerBean, BannerViewHolder> mBannerViewPager;
    private HomePresenter presenter;
    private int mBannerHeight;
//    private List<HomeSection> sections = new ArrayList<>();
    private List<BannerBean> mBannerBeanData;
    private QMUIQQFaceView mTitleView;
    private String mTitleStr = "";
    private boolean isLightMode = true;
    private boolean isVisible;
    private int mStatusBarHeight;

    private boolean isV2;

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
    protected boolean isNeedSpace() {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new HomePresenter();
    }

    @Override
    protected void initView(View rootView) {
        isV2 = getArguments().getBoolean(DataConstant.TYPE);
        mBannerHeight = QMUIDisplayHelper.dp2px(_mActivity, 250);
        mStatusBarHeight = QMUIStatusBarHelper.getStatusbarHeight(_mActivity) / 2;
        mTopLayout.setBackgroundColor(getColorr(R.color.qmui_config_color_transparent));
        mTitleView = mTopLayout.getTopBar().getTitleView();
        mTitleView.getPaint().setFakeBoldText(true);
        super.initView(rootView);
        initBanner();
        ViewUtil.setMargins(mStatusLayout, 0, 0, 0, 0);
        mStatusLayout.setFitsSystemWindows(false);
        recycleView.setItemViewCacheSize(50);
    }

    private void initBanner() {
        mBannerViewPager = (BannerViewPager) getView(R.layout.banner_viewpager);
        // 直接new出来不知道为什么轮播无效
//                mBannerViewPager = new BannerViewPager<>(_mActivity);
        // getView得到后，虽然布局里设置了高度，不知道为什么无效，这里重新设置一次
        mBannerViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mBannerHeight));
        mBannerViewPager
                .setIndicatorGravity(IndicatorGravity.END)
                .setIndicatorSliderColor(getColorr(R.color.white), QMUIResHelper.getAttrColor(_mActivity, R.attr.app_skin_primary_color))
                .setHolderCreator(() -> new BannerViewHolder())
                .setOnPageClickListener(position -> {
                    if (null != mBannerBeanData && mBannerBeanData.size() > 0) {
                        BannerBean bannerBean = mBannerBeanData.get(position);
                        BaseWebExplorerActivity.newInstance(_mActivity, bannerBean.getTitle(), bannerBean.getUrl());
                    }
                });
        adapter.addHeaderView(mBannerViewPager);
    }

    /**
     * 更改状态栏模式
     * 由于只有折叠和展开时才会调用，所以在这里对轮播也进行处理下
     * @param isLight
     * @remark 显示的时候才做更改
     */
    private void setStatusBarMode(boolean isLight) {
        isLightMode = isLight;
        if (isVisible) {
            setBannerStatus(isLight);
            updateStatusBarMode(isLight);
        }
    }

    /**
     * 设置轮播状态
     * @param start
     */
    private void setBannerStatus(boolean start){
        if (mBannerViewPager != null) {
            if(start){
                mBannerViewPager.stopLoop();
            }else{
                mBannerViewPager.startLoop();
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    public void onPause() {
        super.onPause();
        isVisible = false;
        setBannerStatus(false);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onLazyResume() {
        isVisible = true;
        super.onLazyResume();
        setBannerStatus(true);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager(int type) {
        return new OffsetLinearLayoutManager(_mActivity);
    }

    @Override
    protected RecyclerView.OnScrollListener getOnScrollListener() {
        return new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                OffsetLinearLayoutManager offsetLinearLayoutManager = (OffsetLinearLayoutManager) recyclerView.getLayoutManager();
                int y = offsetLinearLayoutManager.computeVerticalScrollOffset(null);
                int height = mBannerHeight - mTopLayout.getHeight();
//                int height = mBannerHeight/2;
                float percent;
                if (y >= height) percent = 1;
                else
                    percent = y / (height * 1.0f);
                boolean isLight = percent > 0.7;
                if (!isLightMode && isLight) {
                    setStatusBarMode(true);
                } else if (isLightMode && !isLight) {
                    setStatusBarMode(false);
                }
//                setBannerStatus(percent == 1);
                mTitleView.setTextColor(QMUIColorHelper.setColorAlpha(getColorr(R.color.qmui_config_color_gray_1), percent));
                mTopLayout.updateBottomSeparatorColor(QMUIColorHelper.setColorAlpha(getColorr(R.color.qmui_config_color_separator), percent));

                // 两种写法
                // 1
                mTopLayout.setBackgroundColor(QMUIColorHelper.setColorAlpha(getColorr(R.color.qmui_config_color_white), percent));
                // 2
                //mTopLayout.setBackgroundAlpha((int) (percent * 255));

                updateTitle();
            }
        };
    }

    @Override
    public void onMoveTarget(int offset) {
        if (offset > mStatusBarHeight && !isLightMode) {
            setStatusBarMode(true);
        } else if (offset < mStatusBarHeight && isLightMode) {
            setStatusBarMode(false);
        }
    }

    private void updateTitle() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recycleView.getLayoutManager();
        if (null == linearLayoutManager) return;
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        if (position > 0) {
            HomeSection section = (HomeSection) adapter.getData().get(position);
            if (null != section) {
                setTitle(section.header);
            }
        } else {
            setTitle("本周最热");
        }
    }

    private void setTitle(String title) {
        if (null != mTitleView && !mTitleStr.equals(title)) {
            mTitleStr = title;
            mTitleView.setText(mTitleStr);
        }
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new Home2Adapter();
    }

    @Override
    protected void requestServer() {
        if (isV2)
            presenter.getWeekHot(isFirst,NetUrlConstantV2.HOT_TYPE_LIKES, getCategory(page));
        else
            presenter.getTodayData();
    }

    private String getCategory(int page) {
        if (page == 1) {
            return NetUrlConstantV2.CATEGORY_ARTICLE;
        } else if (page == 2) {
            return NetUrlConstantV2.CATEGORY_GANHUO;
        }
        return NetUrlConstantV2.CATEGORY_GIRL;
    }

    @Override
    public void onBannerComplete(final List<BannerBean> data) {
        mBannerBeanData = data;
        if (null != mBannerViewPager) {
            mBannerViewPager.create(data);
            mBannerViewPager.startLoop();
            setStatusBarMode(false);
        }
    }

    @Override
    public void onHotComplete(List<GankBean> data) {
        List<HomeSection> sections = new ArrayList<>();
        parseSection(sections, data, getCategory(page));
        onParseComplete(sections, new SamplePageInfoBean(3, page));
    }

    @Override
    public void onTodayComplete(final HomeBean resultsBean) {
        List<HomeSection> sections = new ArrayList<>();
        parseSection(sections, resultsBean.Android, NetUrlConstant.ANDROID);
        parseSection(sections, resultsBean.iOS, NetUrlConstant.IOS);
        parseSection(sections, resultsBean.App, NetUrlConstant.APP);
        parseSection(sections, resultsBean.relax, NetUrlConstant.RELAX);
        parseSection(sections, resultsBean.front, NetUrlConstant.FRONT);
        parseSection(sections, resultsBean.extension, NetUrlConstant.EXTENSION);
        parseSection(sections, resultsBean.recommend, NetUrlConstant.RECOMMEND);
        parseSection(sections, resultsBean.welfare, NetUrlConstant.WELFARE);
        onParseComplete(sections, new SamplePageInfoBean(1, 1));
    }

    @Override
    public void onError(String msg) {
        onFail(msg);
    }

    private void onParseComplete(List<HomeSection> sections, SamplePageInfoBean pageInfoBean) {
        onComplete(sections, pageInfoBean);
        if (null == mBannerBeanData || mBannerBeanData.size() == 0)
            presenter.getBanner();
    }

    private void parseSection(List<HomeSection> sections, List<GankBean> gankBeans, String head) {
        sections.add(new HomeSection(head));
        for (int i = 0; i < gankBeans.size(); i++) {
            sections.add(new HomeSection(head, gankBeans.get(i)));
        }
    }


    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        HomeSection itemSection = (HomeSection) adapter.getItem(position);
        GankBean bean = itemSection.t;
        if (itemSection.header.equals(NetUrlConstantV2.CATEGORY_GIRL)) return;
        BaseWebExplorerActivity.newInstance(_mActivity, bean.getDesc(), bean.getUrl());
    }

    @Override
    public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        return false;
    }



}
