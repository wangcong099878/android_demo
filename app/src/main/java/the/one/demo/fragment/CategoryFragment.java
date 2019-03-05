package the.one.demo.fragment;

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
public class CategoryFragment extends BaseTitleTabFragment {

    private String[] title = { Constant.ANDROID, Constant.APP,
            Constant.IOS, Constant.EXTENSION, Constant.RECOMMEND, Constant.FRONT, Constant.RELAX,Constant.WELFARE};

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mTopLayout.setTitle("GankType").getPaint().setFakeBoldText(true);
        mTopLayout.setBackgroundDividerEnabled(false);
    }

    @Override
    protected void addTabs() {
        for (int i = 0; i < title.length; i++) {
            if (title[i].equals(Constant.IOS))
                addTab("IOS");
            else
                addTab(title[i]);
        }
    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        for (int i = 0; i < title.length; i++) {
            fragments.add(GankFragment.newInstance(title[i]));
        }
    }
}
