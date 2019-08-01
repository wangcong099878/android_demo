package the.one.demo.ui.sample;

import android.view.View;

import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import butterknife.BindView;
import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.widge.TheCheckBox;
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
 * @date 2019/7/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class TheCheckBoxFragment extends BaseFragment implements TheCheckBox.OnCheckChangedListener {

    @BindView(R.id.check_box)
    TheCheckBox mTheCheckBox;
    @BindView(R.id.ll_simple)
    QMUILinearLayout llSimple;
    @BindView(R.id.ll_round)
    QMUILinearLayout llRound;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_checkbox;
    }

    @Override
    protected void initView(View rootView) {
        initFragmentBack("TheCheckBoxFragment");
//        mCheckBox.setIsCheckDrawable();
//        mCheckBox.setUnCheckDrawable();
        mTheCheckBox.setOnCheckChangedListener(this);
        // 继承TextView,相当于设置DrawablePadding
        mTheCheckBox.setTextPadding(5);
        setRadiusAndShadow(llSimple,llRound);

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckChanged(boolean check) {

    }

    protected void setRadiusAndShadow(QMUILinearLayout... linearLayouts){
        for (QMUILinearLayout linearLayout:linearLayouts){
            linearLayout.setRadiusAndShadow(getPx(7),
                    getPx(5),
                    0.75f);
        }

    }

    private int getPx(int dp){
      return   QMUIDisplayHelper.dp2px(_mActivity,dp);
    }

}
