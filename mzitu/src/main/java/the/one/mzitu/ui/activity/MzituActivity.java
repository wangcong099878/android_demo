package the.one.mzitu.ui.activity;

import android.os.Bundle;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;

import the.one.base.Interface.IOnKeyBackClickListener;
import the.one.base.ui.activity.BaseFragmentActivity;
import the.one.base.ui.fragment.BaseFragment;
import the.one.mzitu.ui.fragment.MzituFragment;

import static the.one.base.util.ToastUtil.showToast;

public class MzituActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
    }

    private void requestPermission() {
        XXPermissions.with(this)
                .permission(Permission.Group.STORAGE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
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
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("提示")
                .setMessage(tips)
                .addAction("关闭", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "退出", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setCanceledOnTouchOutside(false)
                .show()
                .setOnKeyListener(new IOnKeyBackClickListener());
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return new MzituFragment();
    }

}
