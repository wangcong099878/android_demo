package the.one.base.ui.fragment;

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
import android.text.TextUtils;
import android.view.View;

import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.skin.defaultAttr.QMUISkinSimpleDefaultAttrProvider;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.qmuiteam.qmui.widget.tab.QMUITab;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeAnchor;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeRule;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import the.one.base.Interface.OnTopBarDoubleClickListener;
import the.one.base.R;
import the.one.base.adapter.TabFragmentAdapter;
import the.one.base.model.TabBean;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.IndicatorUtil;
import the.one.base.widge.SkinPagerTitleView;

/**
 * @author The one
 * @date 2018/12/29 0029
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseTabFragment extends BaseFragment implements QMUITabSegment.OnTabSelectedListener, OnTopBarDoubleClickListener, ViewPager.OnPageChangeListener {

    protected ArrayList<BaseFragment> fragments;
    protected ArrayList<TabBean> mTabs;
    protected QMUITabSegment mTabSegment;


    protected MagicIndicator mMagicIndicator;
    protected CommonNavigator mCommonNavigator;
    protected QMUIViewPager mViewPager;
    protected TabFragmentAdapter<BaseFragment> pageAdapter;
    protected int normalColor;
    protected int selectColor;
    protected int INDEX = 0;

    /**
     * 当Tab的标题是从网络获取时
     * 重写该方法返回true
     * 并重写initData()方法进行网络加载
     * 成功获取后调用startInit()
     *
     * @return
     */
    protected boolean isTabFromNet() {
        return false;
    }

    /**
     * ViewPager是否可以滑动
     *
     * @return
     */
    protected boolean isViewPagerSwipe() {
        return true;
    }

    /**
     * ViewPager 切换是否有动画
     *
     * @return
     */
    protected boolean isSmoothScroll() {
        return true;
    }

    protected boolean skinChangeWithTintColor() {
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
        normalColor = QMUIResHelper.getAttrColor(_mActivity, R.attr.qmui_skin_support_tab_normal_color);
        selectColor = QMUIResHelper.getAttrColor(_mActivity, R.attr.qmui_skin_support_tab_selected_color);
        //添加双击监听
        if (null != mTopLayout && mTopLayout.getVisibility() == View.VISIBLE) {
            mTopLayout.setOnTopBarDoubleClickListener(this);
        }
    }

    @Override
    protected void onLazyInit() {
        super.onLazyInit();
        if (!isTabFromNet()) {
            startInit();
        } else {
            requestServer();
        }
    }

    protected void startInit() {
        fragments = new ArrayList<>();
        mTabs = new ArrayList<>();
        addTabs();
        addFragment(fragments);
        initTabAndPager();
    }

    protected void initTabAndPager() {
        pageAdapter = new TabFragmentAdapter<>(getChildFragmentManager(), fragments, isDestroyItem());
        mViewPager.setAdapter(pageAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setSwipeable(isViewPagerSwipe());
        initSegment();
        initIndicator();
        showContentPage();
    }

    protected void initSegment() {
        if (null != mTabSegment) {
            QMUITabBuilder builder = mTabSegment.tabBuilder();
            builder.skinChangeWithTintColor(skinChangeWithTintColor());
            for (TabBean tab : mTabs) {
                mTabSegment.addTab(createQMUITab(builder, tab));
            }
            mTabSegment.addOnTabSelectedListener(this);
            mTabSegment.setupWithViewPager(mViewPager, false);
        }
    }

    protected void initIndicator() {
        if (null != mMagicIndicator) {
            mCommonNavigator = new CommonNavigator(_mActivity);
            mCommonNavigator.setScrollPivotX(0.65f);
            mCommonNavigator.setAdjustMode(isAdjustMode());
            mCommonNavigator.setAdapter(getNavigatorAdapter());
            mMagicIndicator.setNavigator(mCommonNavigator);
            ViewPagerHelper.bind(mMagicIndicator, mViewPager);
        }
    }

    @SuppressLint("SetTextI18n")
    public IPagerTitleView getTitleView(Context context, final int index) {
        final BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
        SkinPagerTitleView simplePagerTitleView = new SkinPagerTitleView(context);
        simplePagerTitleView.setText(mTabs.get(index).getTitle());
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
        QMUIRoundButton badgeButton = new QMUIRoundButton(context, null, R.attr.qmui_tab_sign_count_view);
        QMUISkinSimpleDefaultAttrProvider skinProvider = new QMUISkinSimpleDefaultAttrProvider();
        skinProvider.setDefaultSkinAttr(
                QMUISkinValueBuilder.BACKGROUND, R.attr.qmui_skin_support_tab_sign_count_view_bg_color);
        skinProvider.setDefaultSkinAttr(
                QMUISkinValueBuilder.TEXT_COLOR, R.attr.qmui_skin_support_tab_sign_count_view_text_color);
        badgeButton.setTag(R.id.qmui_skin_default_attr_provider, skinProvider);
        goneView(badgeButton);
        badgePagerTitleView.setBadgeView(badgeButton);
        badgePagerTitleView.setAutoCancelBadge(false);
        return badgePagerTitleView;
    }

    public IPagerIndicator getIndicator(Context context) {
        return IndicatorUtil.getWrapPagerIndicator(_mActivity, getColorr(R.color.qmui_config_color_background));
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
        QMUIRoundButton tvCount = (QMUIRoundButton) badgePagerTitleView.getBadgeView();
        if (count > 0) {
            boolean beyond = count > 99;
            tvCount.setText(beyond ? "99+" : count + "");
            badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -UIUtil.dip2px(_mActivity, beyond ? 20 : 12)));
            badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, -UIUtil.dip2px(_mActivity, 3)));
            showView(tvCount);
        } else {
            goneView(tvCount);
        }
    }

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

    /**
     * 初始化Tabs
     */
    protected abstract void addTabs();

    /**
     * 初始化Fragments
     */
    protected abstract void addFragment(ArrayList<BaseFragment> fragments);


    /**
     * 添加只有文字的Tab
     *
     * @param title 标题
     */
    protected void addTab(String title) {
        mTabs.add(new TabBean(title));
    }

    /**
     * 添加有图标和文字的Tab
     *
     * @param normal 未选中时的图标
     * @param select 选中时的图标
     * @param title  标题
     */
    protected void addTab(int normal, int select, String title) {
        mTabs.add(new TabBean(title, normal, select));
    }

    /**
     * 创建QMUITab
     *
     * @param tabBean init得到的
     * @return
     */
    protected QMUITab createQMUITab(QMUITabBuilder builder, TabBean tabBean) {
        if (tabBean.getNormalDrawable() != -1) {
            builder.setNormalDrawable(getDrawablee(tabBean.getNormalDrawable()));
        }
        if (tabBean.getSelectDrawable() != -1) {
            builder.setSelectedDrawable(getDrawablee(tabBean.getSelectDrawable()));
        }
        if (!TextUtils.isEmpty(tabBean.getTitle())) {
            builder.setText(tabBean.getTitle());
        } else {
            builder.setText("");
        }
        return builder.build(_mActivity);
    }

    @Override
    public void onDoubleClicked(View v) {
        if (null != pageAdapter) {
            Fragment fragment = pageAdapter.getItem(INDEX);
            if (fragment instanceof BaseListFragment) {
                ((BaseListFragment) fragment).onDoubleClicked(v);
            }
        }
    }
}
