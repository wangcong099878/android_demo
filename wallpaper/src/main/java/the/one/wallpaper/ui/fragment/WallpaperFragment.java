package the.one.wallpaper.ui.fragment;

import android.view.View;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.ArrayList;
import java.util.List;

import the.one.base.Interface.IOnKeyBackClickListener;
import the.one.base.ui.fragment.BaseFragment;
import the.one.base.ui.fragment.BaseTabOnTitleFragment;
import the.one.wallpaper.R;
import the.one.wallpaper.constant.WallpaperConstant;

public class WallpaperFragment extends BaseTabOnTitleFragment  {

    @Override
    protected boolean isExitFragment() {
        return true;
    }

    @Override
    protected boolean isTabFromNet() {
        return true;
    }

    @Override
    protected void requestServer() {
       requestPermission();
    }

    @Override
    protected void startInit() {
        super.startInit();
        mTopLayout.addRightImageButton(R.drawable.mz_titlebar_ic_setting_dark,R.id.topbar_right_button1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startFragment(new SettingFragment());
                    }
                });
    }

    @Override
    protected void addTabs() {
        for (String title: WallpaperConstant.TYPE_TITLES){
            addTab(title);
        }
    }

    @Override
    protected void addFragment(ArrayList<BaseFragment> fragments) {
        for (int type:WallpaperConstant.TYPES){
            fragments.add(WallpaperItemFragment.newInstance(type));
        }
    }

    private void requestPermission() {
        XXPermissions.with(_mActivity)
                .permission(Permission.Group.STORAGE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            startInit();
                        } else {
                            requestPermission();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            showFailDialog("权限被禁止，请在设置里打开权限。");
                        } else {
                            showToast("权限被禁止，请手动打开");
                        }
                    }
                });
    }

    private void showFailDialog(String tips) {
        new QMUIDialog.MessageDialogBuilder(_mActivity)
                .setTitle("提示")
                .setMessage(tips)
                .addAction("退出", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .addAction(0, "重新获取", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        requestPermission();
                    }
                })
                .setCanceledOnTouchOutside(false)
                .show()
                .setOnKeyListener(new IOnKeyBackClickListener());
    }

}
