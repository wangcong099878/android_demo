package the.one.anastasia.base;

import android.support.v4.content.ContextCompat;

import com.qmuiteam.qmui.widget.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

import the.one.anastasia.R;
import the.one.anastasia.adapter.PagerAdapter;
import the.one.anastasia.util.ScreenUtils;
import the.one.anastasia.view.MyViewPager;


public abstract class BaseTabActivity extends BaseActivity {

    private ArrayList<BaseFragment> fragments;
    private List<String> menuLists;
    private QMUITabSegment mTabSegment;
    private MyViewPager mViewPager;
    private PagerAdapter pageAdapter;
    public int SELECT = 0;


    @Override
    protected int getBodyLayout() {
        return R.layout.activity_base_tab;
    }

    @Override
    protected void initView() {
        mTabSegment = findViewById(R.id.mSegment);
        mViewPager = findViewById(R.id.viewPager);
        fragments = new ArrayList<>();
        menuLists = new ArrayList<>();
        addMenu(menuLists);
        addFragment(fragments);
        initTabAndPager();
    }


    private void initTabAndPager() {
        pageAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(pageAdapter);
        for (int i = 0; i <menuLists.size() ; i++) {
            mTabSegment.addTab(new QMUITabSegment.Tab(menuLists.get(i)));
        }
        mTabSegment.setMode(QMUITabSegment.MODE_FIXED);
        //普通文字颜色
        mTabSegment.setDefaultNormalColor(ContextCompat.getColor(this, R.color.tab_normal_text_color));
        //选中时文字颜色
        mTabSegment.setDefaultSelectedColor(ContextCompat.getColor(this, R.color.status_color));
        mTabSegment.setTabTextSize((int) ScreenUtils.dp2Px(this,14));
//        //图片加文字
//        QMUITabSegment.Tab component = new QMUITabSegment.Tab(
//                ContextCompat.getDrawable(getContext(), R.mipmap.icon_tabbar_component),
//                null,
//                "Components", true
//        ); // 普通图片 选中时图片  内容  是否动态更改icon颜色，如果为true, selectedIcon将失效
//
////小红点
//        QMUITabSegment.Tab tab = mTabSegment.getTab(0); // 获取tab
//        tab.setSignCountMargin(0, -QMUIDisplayHelper.dp2px(getContext(), 4));
//        tab.showSignCountView(getContext(), 1);//不为0时红点会显示该数字作为未读数,为0时只会显示一个小红点
//
////设置文字颜色
//        component3.setTextColor(QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_blue),
//                QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_red));
//
////动态根据tab更新文案
//        mTabSegment.updateTabText(0, "动态更新文案");
//
//// 根据index完全替代tab
//        mTabSegment.replaceTab(0, component4);

        //是否有 Indicator
        mTabSegment.setHasIndicator(true);
        // Indicator的方向 ture为上方 false为下方
        mTabSegment.setIndicatorPosition(false);
        // 置 indicator的宽度是否随内容宽度变化
        mTabSegment.setIndicatorWidthAdjustContent(true);
        mTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                SELECT = index;
                mTabSegment.hideSignCountView(index);
            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {
                mTabSegment.hideSignCountView(index);
            }

            @Override
            public void onDoubleTap(int index) {

            }
        });
        mTabSegment.setupWithViewPager(mViewPager, false);

    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化选项卡
     *
     * @param menuLists
     */
    protected abstract void addMenu(List<String> menuLists);

    /**
     * 初始化fragment
     *
     * @param fragments
     */
    protected abstract void addFragment(List<BaseFragment> fragments);
}
