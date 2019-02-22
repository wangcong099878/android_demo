package the.one.anastasia.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import the.one.anastasia.base.BaseFragment;

/**
 * Created by liufei on 2017/12/1.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {
    private List<String> tabs;
    private List<BaseFragment> fragments;

    public TabFragmentAdapter(FragmentManager fm,List<BaseFragment> fragments,List<String> tabs) {
        super(fm);
        this.fragments = fragments;
        this.tabs = tabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
