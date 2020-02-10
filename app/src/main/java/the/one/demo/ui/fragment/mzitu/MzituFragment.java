package the.one.demo.ui.fragment.mzitu;

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
public class MzituFragment extends BaseTitleTabFragment {

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        addTopBarBackBtn(R.drawable.mz_titlebar_ic_back_dark);
        TextView tvTitle = mTopLayout.setTitle("妹子图");
        tvTitle.setTextColor(getColorr(R.color.qmui_config_color_gray_1));
        tvTitle.getPaint().setFakeBoldText(true);
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return true;
    }

    @Override
    protected void addTabs() {
        for (int i = 0; i < NetUrlConstant.welfareTitle.length; i++) {
            addTab(NetUrlConstant.welfareTitle[i]);
        }
    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        for (int i = 0; i < NetUrlConstant.welfareUrl.length; i++) {
            fragments.add(MzituItemFragment.newInstance(NetUrlConstant.welfareUrl[i]));
        }
    }
}
