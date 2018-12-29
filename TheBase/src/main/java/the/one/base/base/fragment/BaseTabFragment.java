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

import android.view.View;

import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import java.util.ArrayList;

import the.one.base.R;
import the.one.base.adapter.TabFragmentAdapter;
import the.one.base.base.presenter.BasePresenter;

/**
 * @author The one
 * @date 2018/12/29 0029
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseTabFragment extends BaseFragment {

    protected ArrayList<BaseFragment> fragments;
    protected ArrayList<QMUITabSegment.Tab> mTabs;
    protected QMUITabSegment mTabSegment;
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

    protected void requestServer() {
    }

    @Override
    protected int getContentViewId() {
        return R.layout.base_title_tab_viewpager_layout;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(View rootView) {
        fragments = new ArrayList<>();
        mTabs = new ArrayList<>();
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
    }

    protected void initTabAndPager() {
        for (QMUITabSegment.Tab tab : mTabs) {
            mTabSegment.addTab(tab);
        }
        //是否有 Indicator
        mTabSegment.setHasIndicator(true);
        // Indicator的方向 ture为上方 false为下方
        mTabSegment.setIndicatorPosition(false);
        // 置 indicator的宽度是否随内容宽度变化
        mTabSegment.setIndicatorWidthAdjustContent(true);
        pageAdapter = new TabFragmentAdapter<>(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setSwipeable(setViewPagerSwipe());
        mTabSegment.setupWithViewPager(mViewPager, false);
        mTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
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
        });

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
}
