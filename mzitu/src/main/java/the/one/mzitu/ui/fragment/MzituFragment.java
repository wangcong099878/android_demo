package the.one.mzitu.ui.fragment;

import android.view.View;

import com.qmuiteam.qmui.qqface.QMUIQQFaceView;

import java.util.ArrayList;

import the.one.base.ui.fragment.BaseFragment;
import the.one.base.ui.fragment.BaseTitleTabFragment;
import the.one.mzitu.R;
import the.one.mzitu.constant.MzituConstant;


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
    protected boolean isExitFragment() {
        return true;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        QMUIQQFaceView tvTitle = mTopLayout.setTitle("妹子图");
        tvTitle.setTextColor(getColorr(R.color.qmui_config_color_gray_1));
        tvTitle.getPaint().setFakeBoldText(true);
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return true;
    }

    @Override
    protected void addTabs() {
        for (int i = 0; i < MzituConstant.welfareTitle.length; i++) {
            addTab(MzituConstant.welfareTitle[i]);
        }
    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        for (int i = 0; i < MzituConstant.welfareUrl.length; i++) {
            fragments.add(MzituItemFragment.newInstance(MzituConstant.welfareUrl[i]));
        }
    }
}
