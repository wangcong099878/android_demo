package the.one.demo.fragment.bottomIndex;

import java.util.ArrayList;

import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.fragment.BaseHomeFragment;
import the.one.demo.R;
import the.one.demo.fragment.SimpleDataFragment;


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
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SimpleHomeFragment extends BaseHomeFragment {


    @Override
    protected void addTabs() {
        addTab(R.drawable.nav_home, R.drawable.nav_home_activated, "主页");
        addTab(R.drawable.nav_cart, R.drawable.nav_cart_activate, "采购");
        addTab(R.drawable.nav_out_car, R.drawable.nav_out_car_activate, "出库");
        addTab(R.drawable.nav_personal, R.drawable.nav_personal_activated, "我的");
    }


    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        fragments.add(SimpleBottomItemFragment.getInstance("主页"));
        fragments.add(SimpleBottomItemFragment.getInstance("采购"));
        fragments.add(SimpleBottomItemFragment.getInstance("出库"));
        fragments.add(SimpleBottomItemFragment.getInstance("我的"));
    }


}
