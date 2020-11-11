package the.one.demo.ui.activity;

import android.os.Bundle;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.List;

import the.one.base.ui.activity.BaseFragmentActivity;
import the.one.base.ui.fragment.BaseFragment;
import the.one.demo.skin.SkinManager;
import the.one.demo.ui.fragment.SampleFragment;


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
 * @date 2020/1/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SampleActivity extends BaseFragmentActivity implements QMUISkinManager.OnSkinChangeListener {

    @Override
    protected void onStart() {
        super.onStart();
        if (getSkinManager() != null) {
            getSkinManager().addSkinChangeListener(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getSkinManager() != null) {
            getSkinManager().removeSkinChangeListener(this);
        }
    }

    @Override
    public void onSkinChange(QMUISkinManager skinManager, int oldSkin, int newSkin) {
        if (newSkin == SkinManager.SKIN_WHITE) {
            QMUIStatusBarHelper.setStatusBarLightMode(SampleActivity.this);
        } else {
            QMUIStatusBarHelper.setStatusBarDarkMode(SampleActivity.this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
    }

    protected void requestPermission() {
        XXPermissions.with(this)
                .permission(Permission.Group.STORAGE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {

                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return new SampleFragment();
    }

}
