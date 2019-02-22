package the.one.anastasia.base;


import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import the.one.anastasia.R;
import the.one.anastasia.adapter.TabFragmentAdapter;
import the.one.anastasia.view.MyViewPager;

public abstract class BaseTabOnTopActivity extends BaseActivity {
    private ArrayList<BaseFragment> fragments;
    private ArrayList<String> menuLists;
    private TabFragmentAdapter pageAdapter;
    public SmartTabLayout tabLayout;

    @Override
    protected void onTitleBarClickListener(View v, int action, String extra) {
        onTitleBarBack(action);
    }

    @Override
    protected int getBodyLayout() {
        return R.layout.activity_base_tab_on_top;
    }

    @Override
    protected void initView() {
        titleBar.setCenterCustomView(R.layout.center_tab_layout);
        tabLayout = (SmartTabLayout) titleBar.getCenterCustomView();
        MyViewPager viewPager = (MyViewPager) findViewById(R.id.viewPager);
        fragments = new ArrayList<>();
        menuLists = new ArrayList<>();
        addMenu(menuLists);
        addFragment(fragments);

        pageAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments,menuLists);
        viewPager.setAdapter(pageAdapter);
        tabLayout.setViewPager(viewPager);
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
