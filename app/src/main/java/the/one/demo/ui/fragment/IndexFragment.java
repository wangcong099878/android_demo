package the.one.demo.ui.fragment;

import java.util.ArrayList;

import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.fragment.BaseHomeFragment;
import the.one.demo.R;


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
public class IndexFragment extends BaseHomeFragment {

    @Override
    protected boolean setViewPagerSwipe() {
        return false;
    }

    @Override
    protected boolean isExitFragment() {
        return true;
    }

    @Override
    protected void addTabs() {
        addTab( R.drawable.ic_home_normal,R.drawable.ic_home_selected,"主页");
        addTab(R.drawable.ic_classification_normal,R.drawable.ic_classification_selected,"分类");
//        addTab(R.drawable.ic_classification_normal,R.drawable.ic_classification_selected,"福利");
        addTab(R.drawable.ic_mine_normal,R.drawable.ic_mine_selected,"我的");
    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        fragments.add(new HomeFragment());
        fragments.add(new CategoryFragment());
//        fragments.add(new WelfareFragment());
        fragments.add(new MyFragment());
//        fragments.add(new TestFragment());
    }

}
