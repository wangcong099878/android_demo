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

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import the.one.base.R;

/**
 * @author The one
 * @date 2019/6/5 0005
 * @describe 包含相机权限操作，相机相关的界面可以继承此Activity
 * @email 625805189@qq.com
 * @remark
 */

public abstract class BaseCameraPermissionActivity extends BaseActivity {

    protected final int GET_PERMISSION_REQUEST = 100; //权限申请自定义码
    protected boolean granted = false;

    protected abstract void onGranted();

    @Override
    protected boolean isStatusBarLightMode() {
        return false;
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

    @Override
    public boolean isInSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置照相界面不休眠
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        requestPermission();
    }

    protected void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //具有权限
            granted = true;
            onGranted();
        } else {
            //不具有获取权限，需要进行权限申请
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA}, GET_PERMISSION_REQUEST);
            granted = false;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                //读写内存权限
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;//读写内存权限
                StringBuffer sb = new StringBuffer();
                if (!writeGranted) {
                    sb.append("读写");
                    sb.append("、");
                    size++;
                }
                //录音权限
                int recordPermissionResult = grantResults[1];
                boolean recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!recordPermissionGranted) {
                    sb.append("录音");
                    sb.append("、");
                    size++;
                }
                //相机权限
                int cameraPermissionResult = grantResults[2];
                boolean cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    sb.append("相机");
                    sb.append("、");
                    size++;
                }
                if (size == 0) {
                    granted = true;
                    onGranted();
                } else {
                    String result = sb.toString();
                    new QMUIDialog.MessageDialogBuilder(this)
                            .setTitle("提示")
                            .setMessage(result+"权限被禁止，点击重新获取，如点击拒绝，请到设置中打开")
                            .addAction(0, "退出", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    finish();
                                }
                            })
                            .addAction("获取权限", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                    requestPermission();
                                }
                            })
                            .show();
                }
            }
        }
    }

        @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_still,R.anim.scale_exit);
    }

}