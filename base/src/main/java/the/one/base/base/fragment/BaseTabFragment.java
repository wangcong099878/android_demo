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

import android.support.v4.view.ViewPager;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUIViewPager;

import java.util.ArrayList;

import the.one.base.adapter.TabFragmentAdapter;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.widge.QMUITabSegment;

/**
 * @author The one
 * @date 2018/12/29 0029
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseTabFragment extends BaseFragment implements QMUITabSegment.OnTabSelectedListener {

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
        mTabSegment.setShowAnimation(false);
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
        pageAdapter = new TabFragmentAdapter<>(getChildFragmentManager(), fragments, isDestroyItem());
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setSwipeable(setViewPagerSwipe());
        for (QMUITabSegment.Tab tab : mTabs) {
            mTabSegment.addTab(tab);
        }
        mTabSegment.addOnTabSelectedListener(this);
        mTabSegment.setupWithViewPager(mViewPager, false);
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
}
