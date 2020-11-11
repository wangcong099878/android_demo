package the.one.aqtour.ui.activity;


import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.litepal.tablemanager.Connector;

import java.io.File;
import java.util.List;

import rxhttp.wrapper.callback.Function;
import rxhttp.wrapper.param.Param;
import rxhttp.wrapper.param.RxHttp;
import the.one.aqtour.m3u8downloader.M3U8Downloader;
import the.one.aqtour.m3u8downloader.M3U8DownloaderConfig;
import the.one.aqtour.ui.fragment.QSPIndexFragment;
import the.one.base.Interface.IOnKeyBackClickListener;
import the.one.base.ui.activity.BaseFragmentActivity;
import the.one.base.ui.fragment.BaseFragment;
import the.one.base.util.FileDirectoryUtil;

import static the.one.base.util.ToastUtil.showToast;


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
public class IndexActivity extends BaseFragmentActivity {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36";

    @Override
    protected BaseFragment getFirstFragment() {
        requestPermission();
        RxHttp.setOnParamAssembly(new Function<Param<?>, Param<?>>() {
            @Override
            public Param apply(Param p) throws Exception {
                //添加公共请求头
                return p.addHeader("User-Agent", USER_AGENT);
            }
        });
        return new QSPIndexFragment();
    }

    private void requestPermission() {
        XXPermissions.with(this)
                .permission(Permission.Group.STORAGE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            // 数据库
                            Connector.getDatabase();
                            initM3U8Download();
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
