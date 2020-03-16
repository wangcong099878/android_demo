package the.one.aqtour.ui.fragment;

import android.view.View;

import java.util.ArrayList;

import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.fragment.BaseTabOnTitleFragment;

public class DownloadFragment extends BaseTabOnTitleFragment {


    @Override
    protected void initView(View rootView) {
        assert getArguments() != null;
        addTopBarBackBtn();
        super.initView(rootView);
    }

    @Override
    protected void addTabs() {
        addTab("下载中");
        addTab("已下载");
    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        fragments.add(DownloadTaskFragment.newInstance(false));
        fragments.add(DownloadTaskFragment.newInstance(true));
    }
}
