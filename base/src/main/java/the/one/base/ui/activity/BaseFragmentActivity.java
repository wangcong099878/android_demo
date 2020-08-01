package the.one.base.ui.activity;

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

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.qmuiteam.qmui.skin.QMUISkinManager;

import the.one.base.R;
import the.one.base.ui.fragment.BaseFragment;
import the.one.base.util.AppMourningThemeUtil;
import the.one.base.util.SkinSpUtil;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseFragmentActivity extends QMUIFragmentActivity {

    public final String TAG = this.getClass().getSimpleName();

    protected abstract BaseFragment getFirstFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.scale_enter, R.anim.slide_still);
        if (SkinSpUtil.isQMUISkinManger())
            setSkinManager(QMUISkinManager.defaultInstance(this));
        super.onCreate(savedInstanceState);
        AppMourningThemeUtil.notify(getWindow());
        if (savedInstanceState == null) {
            BaseFragment fragment = getFirstFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }
}