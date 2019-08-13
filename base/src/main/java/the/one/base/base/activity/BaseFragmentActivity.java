package the.one.base.base.activity;

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

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import the.one.base.R;
import the.one.base.base.fragment.BaseFragment;
import the.one.base.util.StatusBarUtil;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseFragmentActivity extends QMUIFragmentActivity {

    protected abstract BaseFragment getBaseFragment();

    protected boolean LightMode() {
        return false;
    }

    protected boolean isTranslucent(){
        return true;
    }

    @Override
    protected int getContextViewId() {
        return R.id.main_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LightMode())
            QMUIStatusBarHelper.setStatusBarLightMode(this);
        else
            QMUIStatusBarHelper.setStatusBarDarkMode(this);
        if(isTranslucent()&& StatusBarUtil.isTranslucent(this)){
            QMUIStatusBarHelper.translucent(this, ContextCompat.getColor(this,R.color.qmui_config_color_transparent));
        }
        if (savedInstanceState == null) {
            BaseFragment fragment = getBaseFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }
}