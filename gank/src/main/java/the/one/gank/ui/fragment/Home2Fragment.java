package the.one.gank.ui.fragment;

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
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.ui.activity.BaseWebExplorerActivity;
import the.one.base.ui.fragment.BaseDataFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.OffsetLinearLayoutManager;
import the.one.base.util.QMUIStatusBarHelper;
import the.one.gank.R;
import the.one.gank.bean.BannerBean;
import the.one.gank.bean.GankBean;
import the.one.gank.bean.HomeBean;
import the.one.gank.bean.HomeSection;
import the.one.gank.constant.NetUrlConstant;
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
public class Home2Fragment extends BaseDataFragment<HomeSection> implements HomeView {

    private BannerViewPager<BannerBean, BannerViewHolder> mBannerViewPager;
    private HomePresenter presenter;
    private int mBannerHeight;
    private List<HomeSection> sections;
    private List<BannerBean> mBannerBeanData;
    private QMUIQQFaceView mTitleView;
    private String mTitleStr = "";
    private boolean isLightMode = true;
    private boolean isDark;
    private boolean isLight;
    private boolean isVisible;

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
    protected void initView(View rootView) {
        mBannerHeight = QMUIDisplayHelper.dp2px(_mActivity, 250);
        mTopLayout.setBackgroundColor(getColorr(R.color.qmui_config_color_transparent));
        mTitleView = mTopLayout.getTopBar().getTitleView();
        mTitleView.getPaint().setFakeBoldText(true);
        super.initView(rootView);
        initBanner();
        setMargins(mStatusLayout, 0, 0, 0, 0);
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
                .setIndicatorSliderColor(getColorr(R.color.white), QMUIResHelper.getAttrColor(_mActivity, R.attr.config_color))
                .setHolderCreator(() -> new BannerViewHolder())
                .setOnPageClickListener(position -> {
                    if (null != mBannerBeanData && mBannerBeanData.size() > 0) {
                        BannerBean bannerBean = mBannerBeanData.get(position);
                        BaseWebExplorerActivity.newInstance(_mActivity, bannerBean.getTitle(), bannerBean.getUrl());
                    }
                });
        adapter.addHeaderView(mBannerViewPager);
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
                float percent;
                if (y >= height) percent = 1;
                else
                    percent = y / (height * 1.0f);
                isLightMode = percent > 0.5;
                if (isLightMode && isDark) {
                    setStatusBarLightMode();
                } else if (!isLightMode && isLight) {
                    setStatusBarDarkMode();
                }
                mTitleView.setTextColor(QMUIColorHelper.setColorAlpha(getColorr(R.color.qmui_config_color_gray_1), percent));
                mTopLayout.updateBottomSeparatorColor(QMUIColorHelper.setColorAlpha(getColorr(R.color.qmui_config_color_separator), percent));

                // 两种写法
                // 1
                mTopLayout.setBackgroundColor(QMUIColorHelper.setColorAlpha(getColorr(R.color.qmui_config_color_white), percent));
                // 2
                //mTopLayout.setBackgroundAlpha((int) (percent * 255));

                updateTitle(offsetLinearLayoutManager);
            }
        };
    }

    private void updateTitle(OffsetLinearLayoutManager linearLayoutManager) {
        if (null == linearLayoutManager) return;
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        if (position > 0) {
            HomeSection section = sections.get(position - 1);
            if (null != section) {
                setTitle(section.header);
            }
        } else {
            setTitle("今日最新干货");
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
        presenter.getTodayData();
        if (null == mBannerBeanData || mBannerBeanData.size() == 0)
            presenter.getBanner();
    }

    @Override
    public void onWelfareComplete(final List<BannerBean> data) {
        mBannerBeanData = data;
        if (null != mBannerViewPager) {
            mBannerViewPager.create(data);
            mBannerViewPager.startLoop();
        }
    }


    @Override
    public void onTodayComplete(final HomeBean resultsBean) {
        sections = new ArrayList<>();
        parseSection(resultsBean.Android, NetUrlConstant.ANDROID);
        parseSection(resultsBean.iOS, NetUrlConstant.IOS);
        parseSection(resultsBean.App, NetUrlConstant.APP);
        parseSection(resultsBean.relax, NetUrlConstant.RELAX);
        parseSection(resultsBean.front, NetUrlConstant.FRONT);
        parseSection(resultsBean.extension, NetUrlConstant.EXTENSION);
        parseSection(resultsBean.recommend, NetUrlConstant.RECOMMEND);
        parseSection(resultsBean.welfare, NetUrlConstant.WELFARE);
        onComplete(sections);
        showContentPage();
        setStatusBarDarkMode();
        onNormal();
    }

    private void setStatusBarDarkMode() {
        isDark = true;
        isLight = false;
        if (isVisible)
            QMUIStatusBarHelper.setStatusBarDarkMode(_mActivity);
    }

    private void setStatusBarLightMode() {
        isDark = false;
        isLight = true;
        if (isVisible)
            QMUIStatusBarHelper.setStatusBarLightMode(_mActivity);
    }

    private void parseSection(List<GankBean> gankBeans, String head) {
        sections.add(new HomeSection(head));
        for (int i = 0; i < gankBeans.size(); i++) {
            sections.add(new HomeSection(head, gankBeans.get(i)));
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new HomePresenter();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    public void onPause() {
        super.onPause();
        isVisible = false;
        if (mBannerViewPager != null) {
            mBannerViewPager.stopLoop();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onLazyResume() {
        isVisible = true;
        super.onLazyResume();
        if (mBannerViewPager != null)
            mBannerViewPager.startLoop();
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        HomeSection itemSection = (HomeSection) adapter.getItem(position);
        GankBean bean = itemSection.t;
        BaseWebExplorerActivity.newInstance(_mActivity, bean.getDesc(), bean.getUrl());
    }

    @Override
    public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        return false;
    }

}
