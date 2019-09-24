package the.one.demo.ui.gank;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.fragment.BaseTitleTabFragment;
import the.one.demo.NetUrlConstant;
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
public class CategoryFragment extends BaseTitleTabFragment {

    @Override
    protected boolean onAnimationEndInit() {
        return false;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
//        把继承改为BaseTabOnTitleFragment注释掉下面的代码试试
        TextView mTitle =  mTopLayout.setTitle("GankType");
        mTitle.setTextColor(getColorr(R.color.qmui_config_color_gray_1));
        mTitle.getPaint().setFakeBoldText(true);
        // 这里不用注释掉,更换继承这里要改成true(是否有分割线)
        mTopLayout.setBackgroundDividerEnabled(false);
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
            fragments.add(GankFragment.newInstance(NetUrlConstant.title[i]));
        }
    }
}
