package the.one.demo.ui.sample.fragment;

import android.view.View;

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
 * @describe 测试用的
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
        // 左右两边的内容要放在前面
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
