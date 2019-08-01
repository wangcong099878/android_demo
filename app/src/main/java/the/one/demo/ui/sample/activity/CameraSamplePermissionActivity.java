package the.one.demo.ui.sample.activity;

import android.view.View;

import the.one.base.base.activity.BaseCameraPermissionActivity;
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
 * @date 2019/7/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class CameraSamplePermissionActivity extends BaseCameraPermissionActivity {

    @Override
    protected void onGranted() {
        // 把需要拥有权限才能做的操作放在这里
        showToast("权限已拥有");
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initView(View mRootView) {
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
