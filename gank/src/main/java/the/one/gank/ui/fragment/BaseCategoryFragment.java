package the.one.gank.ui.fragment;

import android.view.Gravity;
import android.view.View;

import com.qmuiteam.qmui.qqface.QMUIQQFaceView;
import com.qmuiteam.qmui.util.QMUIColorHelper;

import the.one.base.ui.fragment.BaseTitleTabFragment;
import the.one.gank.R;


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
public abstract class BaseCategoryFragment extends BaseTitleTabFragment {

    private QMUIQQFaceView mTitle;

    @Override
    protected boolean isNeedChangeStatusBarMode() {
        return true;
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return true;
    }

    /**
     * @return 是否需要折叠TitleBar
     */
    @Override
    protected boolean isFoldTitleBar() {
        return true;
    }

    /**
     * @return 是否显示Elevation（Z轴的高度）
     */
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
        //把继承改为BaseTabOnTitleFragment注释掉下面的代码试试
        mTitle =  mTopLayout.setTitle("GankType");
        mTopLayout.setTitleGravity(Gravity.CENTER);
        mTitle.setTextColor(getColor(R.color.qmui_config_color_gray_1));
        mTitle.getPaint().setFakeBoldText(true);
    }

}
