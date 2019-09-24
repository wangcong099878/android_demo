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
public class WelfareFragment extends BaseTitleTabFragment {

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        addTopBarBackBtn(R.drawable.mz_titlebar_ic_back_dark, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView tvTitle = mTopLayout.setTitle("福利");
        tvTitle.setTextColor(getColorr(R.color.qmui_config_color_gray_1));
        tvTitle.getPaint().setFakeBoldText(true);
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
            fragments.add(WelfareItemFragment.newInstance(NetUrlConstant.welfareUrl[i]));
        }
    }
}
