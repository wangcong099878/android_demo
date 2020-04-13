package the.one.aqtour.ui.activity;

import android.Manifest;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.litepal.tablemanager.Connector;

import java.io.File;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import the.one.aqtour.m3u8downloader.M3U8Downloader;
import the.one.aqtour.m3u8downloader.M3U8DownloaderConfig;
import the.one.aqtour.ui.fragment.QSPIndexFragment;
import the.one.base.Interface.IOnKeyBackClickListener;
import the.one.base.ui.activity.BaseFragmentActivity;
import the.one.base.ui.fragment.BaseFragment;
import the.one.base.util.FileDirectoryUtil;


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
 * @date 2019/4/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class IndexActivity extends BaseFragmentActivity implements  Observer<Boolean> {

    @Override
    protected BaseFragment getFirstFragment() {
        requestPermission();
        return new QSPIndexFragment();
    }


    private void requestPermission() {
        final RxPermissions permissions = new RxPermissions(this);
        permissions
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Boolean aBoolean) {
        if (aBoolean) {
            // 数据库
            Connector.getDatabase();
            initM3U8Download();
        } else {
            showFailDialog("权限被禁止，请在设置里打开权限。");
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    private String encryptKey = "5282E6434B7B56A295EC11F861FA0EAD";

    private void initM3U8Download(){
        M3U8DownloaderConfig
                .build(getApplicationContext())
                .setThreadCount(3)
                .setSaveDir(FileDirectoryUtil.getDownloadPath()+ File.separator + "M3U8Download")
                .setDebugMode(true)
        ;
        try {
           M3U8Downloader.getInstance().setEncryptKey(encryptKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}
