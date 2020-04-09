package the.one.gank.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.qqface.QMUIQQFaceView;
import com.qmuiteam.qmui.util.QMUIColorHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.holder.HolderCreator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.base.activity.BaseWebExplorerActivity;
import the.one.base.base.fragment.BaseDataFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.OffsetLinearLayoutManager;
import the.one.gank.R;
import the.one.gank.bean.Banner;
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
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class Home2Fragment extends BaseDataFragment<HomeSection> implements HomeView {

    BannerViewPager<Banner, BannerViewHolder> mBannerViewPager;
    private HomePresenter presenter;
    private int mBannerHeight;
    private List<HomeSection> sections;
    private QMUIQQFaceView mTitleView;
    private String mTitleStr = "";


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
        return true;
    }

    @Override
    protected void initView(View rootView) {
        mBannerHeight = QMUIDisplayHelper.dp2px(_mActivity, 250);
        mTopLayout.setBackgroundColor(getColorr(R.color.white));
        mTitleView = mTopLayout.setTitle("");
        mTitleView.getPaint().setFakeBoldText(true);
        super.initView(rootView);
        recycleView.setItemViewCacheSize(50);
        mStatusLayout.setFitsSystemWindows(false);
        setMargins(mStatusLayout, 0, 0, 0, 0);
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

                // 变化到一半的时候就改变颜色
                mTitleView.setTextColor(getColorr(percent > 0.5 ? R.color.qmui_config_color_gray_1 : R.color.qmui_config_color_white));
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
        Home2Adapter adapter = new Home2Adapter();
        mBannerViewPager = new BannerViewPager<Banner, BannerViewHolder>(_mActivity);
        mBannerViewPager.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mBannerHeight));
        adapter.addHeaderView(mBannerViewPager);
        return adapter;
    }

    @Override
    protected void requestServer() {
        presenter.getTodayData();
        presenter.getBanner();
    }

    @Override
    public void onWelfareComplete(final List<Banner> data) {
        mBannerViewPager
                .setIndicatorGravity(IndicatorGravity.END)
                .setIndicatorSliderColor(getColorr(R.color.white), QMUIResHelper.getAttrColor(_mActivity, R.attr.config_color))
                .setHolderCreator(new HolderCreator<BannerViewHolder>() {
                    @Override
                    public BannerViewHolder createViewHolder() {
                        return new BannerViewHolder();
                    }
                })
                .setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
                    @Override
                    public void onPageClick(int position) {
                        Banner banner = data.get(position);
                        BaseWebExplorerActivity.newInstance(_mActivity, banner.getTitle(), banner.getUrl());
                    }
                })
                .create(data);
        mBannerViewPager.startLoop();
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
        onNormal();
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
        if (mBannerViewPager != null) {
            mBannerViewPager.stopLoop();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onLazyResume() {
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
