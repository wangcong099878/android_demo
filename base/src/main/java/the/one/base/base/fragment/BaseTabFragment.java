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

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeAnchor;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeRule;

import java.util.ArrayList;

import the.one.base.R;
import the.one.base.adapter.TabFragmentAdapter;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.IndicatorUtil;
import the.one.base.widge.MyTopBarLayout;
import the.one.base.widge.QMUITabSegment;
import the.one.base.widge.indicator.ColorFlipPagerTitleView;

/**
 * @author The one
 * @date 2018/12/29 0029
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseTabFragment extends BaseFragment implements QMUITabSegment.OnTabSelectedListener, MyTopBarLayout.OnTopBarDoubleClickListener {

    protected ArrayList<BaseFragment> fragments;
    protected ArrayList<QMUITabSegment.Tab> mTabs;
    protected QMUITabSegment mTabSegment;

    protected MagicIndicator mMagicIndicator;
    protected CommonNavigator mCommonNavigator;
    protected QMUIViewPager mViewPager;
    protected TabFragmentAdapter<BaseFragment> pageAdapter;
    protected int INDEX = 0;

    /**
     * 当Tab的标题是从网络获取时
     * 重写该方法返回true
     * 并重写initData()方法进行网络加载
     * 成功获取后调用startInit()
     *
     * @return
     */
    protected boolean tabFromNet() {
        return false;
    }

    /**
     * ViewPager是否可以滑动
     *
     * @return
     */
    protected boolean setViewPagerSwipe() {
        return true;
    }

    /**
     * @return
     */
    protected boolean isAdjustMode() {
        return false;
    }

    protected boolean isDestroyItem() {
        return true;
    }

    protected void requestServer() {
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(View rootView) {
        if (null != mTabSegment)
            mTabSegment.setShowAnimation(false);
        fragments = new ArrayList<>();
        mTabs = new ArrayList<>();
        normalColor = QMUIResHelper.getAttrColor(_mActivity, R.attr.tab_normal_color);
        selectColor = QMUIResHelper.getAttrColor(_mActivity, R.attr.tab_select_color);
        if (!tabFromNet()) {
            startInit();
        } else {
            requestServer();
        }
    }

    protected void startInit() {
        addTabs();
        addFragment(fragments);
        initTabAndPager();
        //添加双击监听
        if (null != mTopLayout && mTopLayout.getVisibility() == View.VISIBLE) {
            mTopLayout.setOnTopBarDoubleClickListener(this);
        }
    }

    protected int normalColor;
    protected int selectColor;

    protected void initIndicator() {
        mCommonNavigator = new CommonNavigator(_mActivity);
        mCommonNavigator.setScrollPivotX(0.65f);
        mCommonNavigator.setAdjustMode(isAdjustMode());
        mCommonNavigator.setAdapter(getNavigatorAdapter());
        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    protected void initTabAndPager() {
        pageAdapter = new TabFragmentAdapter<>(getChildFragmentManager(), fragments, isDestroyItem());
        mViewPager.setAdapter(pageAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                INDEX = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setSwipeable(setViewPagerSwipe());
        if (null != mTabSegment) {
            for (QMUITabSegment.Tab tab : mTabs) {
                mTabSegment.addTab(tab);
            }
            mTabSegment.addOnTabSelectedListener(this);
            mTabSegment.setupWithViewPager(mViewPager, false);
        }
        if (null != mMagicIndicator)
            initIndicator();
        showContentPage();
    }

    @SuppressLint("SetTextI18n")
    public IPagerTitleView getTitleView(Context context, final int index) {
        final BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
        SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
        simplePagerTitleView.setText(mTabs.get(index).getText());
        simplePagerTitleView.setTextSize(16);
        simplePagerTitleView.setNormalColor(normalColor);
        simplePagerTitleView.setSelectedColor(selectColor);
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(index);
            }
        });
        badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);
        TextView badgeTextView = new TextView(context, null, com.qmuiteam.qmui.R.attr.qmui_tab_sign_count_view);
        goneView(badgeTextView);
        badgePagerTitleView.setBadgeView(badgeTextView);
        badgePagerTitleView.setAutoCancelBadge(false);
        return badgePagerTitleView;
    }

    public IPagerIndicator getIndicator(Context context) {
        return IndicatorUtil.getWrapPagerIndicator(context, ContextCompat.getColor(context, R.color.qmui_config_color_background));
    }

    protected CommonNavigatorAdapter getNavigatorAdapter() {
        return new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTabs == null ? 0 : mTabs.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                return BaseTabFragment.this.getTitleView(context, index);
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return BaseTabFragment.this.getIndicator(context);
            }
        };
    }

    /**
     * 显示角标数量
     *
     * @param index 位置
     * @param count 数量
     */
    protected void showSignCount(int index, int count) {
        BadgePagerTitleView badgePagerTitleView = (BadgePagerTitleView) mCommonNavigator.getPagerTitleView(index);
        TextView tvCount = (TextView) badgePagerTitleView.getBadgeView();
        if (count > 0) {
            boolean beyond = count > 99;
            tvCount.setText(beyond ? "99+" : count + "");
            badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -UIUtil.dip2px(_mActivity, beyond?20:12)));
            badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, -UIUtil.dip2px(_mActivity, 3)));
            showView(tvCount);
        } else {
            goneView(tvCount);
        }
    }

    @Override
    public void onTabSelected(int index) {
        INDEX = index;
    }

    @Override
    public void onTabUnselected(int index) {

    }

    @Override
    public void onTabReselected(int index) {

    }

    @Override
    public void onDoubleTap(int index) {

    }

    protected abstract void addTabs();

    /**
     * 初始化fragment
     *
     * @param fragments
     */
    protected abstract void addFragment(ArrayList<BaseFragment> fragments);

    protected void addTab(String title) {
        mTabs.add(new QMUITabSegment.Tab(title));
    }

    protected void addTab(int normal, int select, String title) {
        mTabs.add(new QMUITabSegment.Tab(getDrawablee(normal), getDrawablee(select), title, false));
    }

    @Override
    public void onDoubleClicked(View v) {
        Fragment fragment = pageAdapter.getItem(INDEX);
        if (fragment instanceof BaseDataFragment) {
            ((BaseDataFragment) fragment).onDoubleClicked(null);
        }
    }
}
