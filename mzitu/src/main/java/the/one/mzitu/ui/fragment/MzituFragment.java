package the.one.mzitu.ui.fragment;

import android.view.View;

import com.qmuiteam.qmui.qqface.QMUIQQFaceView;
import com.qmuiteam.qmui.util.QMUIColorHelper;

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

    private QMUIQQFaceView mTitle;

    @Override
    protected boolean isExitFragment() {
        return true;
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return true;
    }

    /**
     * @return 需要折叠TitleBar
     */
    @Override
    protected boolean isFoldTitleBar() {
        return true;
    }

    @Override
    protected boolean showElevation() {
        return true;
    }

    /**
     * 需要自行处理TitleBar里的内容随着滑动的透明度
     * @param percent
     */
    @Override
    public void onScrollChanged(float percent) {
        mTitle.setTextColor(QMUIColorHelper.setColorAlpha(getColor(R.color.qmui_config_color_gray_1),percent));
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mTitle = mTopLayout.setTitle("妹子图");
        mTitle.getPaint().setFakeBoldText(true);
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
