package the.one.demo.ui.fragment;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;

import the.one.base.Interface.ReadContactCompleteListener;
import the.one.base.model.Contact;
import the.one.base.ui.fragment.BaseLetterSearchFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.ContactUtil;
import the.one.base.util.QMUIDialogUtil;


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
 * @date 2019/1/18 0018
 * @describe 手机联系人列表
 * @email 625805189@qq.com
 * @remark
 */
public class AddressBookFragment extends BaseLetterSearchFragment<Contact> {

    private static List<Contact> contacts;

    @Override
    protected String getTitleString() {
        return "联系人";
    }

    @Override
    protected void onItemClick(Contact contact) {

    }

    @Override
    protected void onConfirmSelect(List<Contact> selects) {
        QMUIDialogUtil.showLongMessageDialog(_mActivity, "已选择联系人", selects.toString(), "确定", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onLazyInit() {
        checkPermission();
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        XXPermissions.with(_mActivity)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .permission(Permission.READ_EXTERNAL_STORAGE)
                .permission(Permission.READ_PHONE_STATE)
                .permission(Permission.READ_CONTACTS)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            requestServer();
                        } else {
                            checkPermission();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            showToast("权限被禁止，请在设置里打开权限。");
                        } else {
                            showToast("权限被禁止，请手动打开");
                        }
                    }
                });
    }

    private void requestServer() {
        if (null != contacts) {
            notifyData(contacts, "没有联系人", "", null);
            return;
        }
        showLoadingDialog("加载中");
        new Thread() {
            @Override
            public void run() {
                super.run();
                ContactUtil.getInstance().getContactList(_mActivity, new ReadContactCompleteListener() {
                    @Override
                    public void onComplete(final List<Contact> contacts) {
                        AddressBookFragment.contacts = contacts;
                        _mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideLoadingDialog();
                                notifyData(contacts, "没有联系人", "", null);
                            }
                        });
                    }
                });
            }
        }.start();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }


}
