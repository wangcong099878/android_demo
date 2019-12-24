package the.one.demo.ui.sample.fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.presenter.BasePresenter;
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
 * @date 2019/9/27 0027
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class TestFragment extends BaseFragment {

//    @BindView(R.id.wave_view)
//    WaveView mWaveView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initView(View rootView) {
        EditText editText = new EditText(_mActivity);
        editText.setBackground(null);
        editText.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        mTopLayout.setCenterView(editText);
        addTopBarBackBtn();

//        mWaveView.setDuration(10000);
//        mWaveView.setStyle(Paint.Style.FILL);
//        mWaveView.setColor(Color.RED);
//        mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
//        mWaveView.start();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
