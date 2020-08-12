package the.one.gank.ui.fragment;

import java.util.ArrayList;

import the.one.base.ui.fragment.BaseFragment;
import the.one.base.ui.fragment.BaseHomeFragment;
import the.one.gank.R;
import the.one.gank.util.APIVersionUtil;


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
    protected boolean isViewPagerSwipe() {
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
        if (APIVersionUtil.isV2()) {
            fragments.add(new HomeV2FragmentK());
            fragments.add(new CategoryV2Fragment());
        } else {
            fragments.add(new HomeFragment());
            fragments.add(new CategoryFragment());
        }
        fragments.add(new MyFragment());
    }

}
