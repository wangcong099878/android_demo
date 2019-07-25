package the.one.demo.ui.fragment;

import android.view.View;

import java.util.ArrayList;

import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.fragment.BaseTitleTabFragment;
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
public class WelfareFragment extends BaseTitleTabFragment {

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
//        把继承改为BaseTabOnTitleFragment注释掉下面的代码
        mTopLayout.setTitle("福利").getPaint().setFakeBoldText(true);
    }

    @Override
    protected void addTabs() {
        for (int i = 0; i < Constant.welfareTitle.length; i++) {
                addTab(Constant.welfareTitle[i]);
        }
    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        for (int i = 0; i < Constant.welfareUrl.length; i++) {
            fragments.add(WelfareItemFragment.newInstance(Constant.welfareUrl[i]));
        }
    }
}
