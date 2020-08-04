package the.one.gank.ui.fragment;

import java.util.ArrayList;

import the.one.base.ui.fragment.BaseFragment;
import the.one.gank.constant.NetUrlConstant;


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
public class CategoryFragment extends BaseCategoryFragment {

    @Override
    protected boolean showTitleBar() {
        return true;
    }

    @Override
    protected void addTabs() {
        for (int i = 0; i < NetUrlConstant.title.length; i++) {
            if (NetUrlConstant.title[i].equals(NetUrlConstant.IOS))
                addTab("IOS");
            else
                addTab(NetUrlConstant.title[i]);
        }
    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        for (int i = 0; i < NetUrlConstant.title.length; i++) {
            fragments.add(BaseGankFragment.newInstance(GankFragment.class,NetUrlConstant.title[i]));
        }
    }
}
