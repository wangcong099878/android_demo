package the.one.demo.ui.sample;

import android.Manifest;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import the.one.base.Interface.ReadContactCompleteListener;
import the.one.base.base.fragment.BaseLetterSearchFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.model.Contact;
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
        final RxPermissions permissions = new RxPermissions(this);
        permissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CONTACTS
                )
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean)
                            requestServer();
                        else {
                            QMUIDialogUtil.showSimpleDialog(_mActivity, "What?", "不给权限怎么加？");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
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
