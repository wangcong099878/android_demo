package the.one.gank.ui.fragment;

import java.util.ArrayList;

import the.one.base.ui.fragment.BaseFragment;
import the.one.base.ui.fragment.BaseHomeFragment;
import the.one.gank.R;


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
public class GankIndexFragment extends BaseHomeFragment {

    @Override
    protected boolean isExitFragment() {
        return true;
    }

    @Override
    protected boolean isNeedChangeStatusBarMode() {
        return false;
    }

    @Override
    protected boolean setViewPagerSwipe() {
        return false;
    }

    @Override
    protected void addTabs() {
        addTab(R.drawable.ic_home_normal, R.drawable.ic_home_selected, "主页");
        addTab(R.drawable.ic_classification_normal, R.drawable.ic_classification_selected, "分类");
        addTab(R.drawable.ic_mine_normal, R.drawable.ic_mine_selected, "我的");
    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        fragments.add(new Home2Fragment());
        fragments.add(new CategoryFragment());
        fragments.add(new MyFragment());
    }

}
