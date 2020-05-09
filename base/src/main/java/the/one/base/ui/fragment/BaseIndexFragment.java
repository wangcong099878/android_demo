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

import android.view.View;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import the.one.base.R;
import the.one.base.model.TabBean;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.widge.NavigationBar;

/**
 * @author The one
 * @date 2020/4/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseIndexFragment extends BaseFragment {

    protected NavigationBar mNavigationBar;
    protected List<Fragment> fragments = new ArrayList<>();
    protected List<TabBean> tabBeans = new ArrayList<>();

    protected abstract void initData(List<TabBean> tabBeans,List<Fragment> fragments);

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragmnet_home_style;
    }

    @Override
    protected void initView(View rootView) {
        mNavigationBar = rootView.findViewById(R.id.navigationBar);
        initData(tabBeans,fragments);
        mNavigationBar =  mNavigationBar.fragmentList(fragments)
                .setTabBeans(tabBeans)
                .fragmentManager(getChildFragmentManager());
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
