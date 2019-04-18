package the.one.demo.ui.fragment;

import android.view.View;

import java.util.ArrayList;

import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.fragment.BaseTabOnTitleFragment;
import the.one.demo.Constant;


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
public class CategoryFragment extends BaseTabOnTitleFragment {

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
//        把继承改为BaseTitleTabFragment注释掉下面的代码
//        mTopLayout.setTitle("GankType").getPaint().setFakeBoldText(true);
//        mTopLayout.setBackgroundDividerEnabled(false);
    }

    @Override
    protected void addTabs() {
        for (int i = 0; i < Constant.title.length; i++) {
            if (Constant.title[i].equals(Constant.IOS))
                addTab("IOS");
            else
                addTab(Constant.title[i]);
        }
    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        for (int i = 0; i < Constant.title.length; i++) {
            fragments.add(GankFragment.newInstance(Constant.title[i]));
        }
    }
}
