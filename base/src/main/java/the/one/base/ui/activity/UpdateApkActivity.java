package the.one.base.ui.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.File;
import java.util.List;

import the.one.base.Interface.IApkUpdate;
import the.one.base.R;
import the.one.base.constant.DataConstant;
import the.one.base.model.Download;
import the.one.base.model.UpdateApkBean;
import the.one.base.service.DownloadService;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.AppInfoManager;
import the.one.base.util.FileDirectoryUtil;
import the.one.base.widge.ProgressButton;

/**
 * @author The one
 * @date 2019/10/21 0028
 * @describe APK更新
 * @email 625805189@qq.com
 * @remark 更改自 https://github.com/MZCretin/AutoUpdateProject
 */
public class UpdateApkActivity extends BaseActivity {

    public static void startDown(Context activity, IApkUpdate update) {
        if (null == activity || null == update) return;
        UpdateApkBean updateApkBean = new UpdateApkBean(update);
        Intent intent = new Intent(activity, UpdateApkActivity.class);
        intent.putExtra(DataConstant.DATA, updateApkBean);
        activity.startActivity(intent);
    }

    private TextView tvMsg;
    private ProgressButton tvDownload;
    private ImageView ivClose;
    private TextView tvVersion;

    public UpdateApkBean downloadInfo;
    private IntentFilter filter;

    private String downloadName;

    private String STATUS_START = "立即更新";
    private String STATUS_DOWNING = "下载中";
    private String STATUS_INSTALL = "立即安装";
    private String STATUS_RE_DOWNLOAD = "重新下载";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_update_type10;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView(View mRootView) {
        overridePendingTransition(R.anim.scale_enter, R.anim.slide_still);
        initFilter();
        downloadInfo = getIntent().getParcelableExtra(DataConstant.DATA);
        tvMsg = findViewById(R.id.tv_content);
        tvDownload = findViewById(R.id.tv_update);
        ivClose = findViewById(R.id.iv_close);
        tvVersion = findViewById(R.id.tv_version);
        tvMsg.setText(downloadInfo.getUpdateLog());
        tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvVersion.setText("v" + downloadInfo.getVerName());
        if (downloadInfo.isForce()) {
            ivClose.setVisibility(View.GONE);
        } else {
            ivClose.setVisibility(View.VISIBLE);
        }
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = tvDownload.getCurrentText();
                if (!TextUtils.isEmpty(action)) {
                    if (action.equals(STATUS_START) || action.equals(STATUS_RE_DOWNLOAD)) {
                        // 开始下载 重新下载
                        requestPermission();
                    } else if (action.equals(STATUS_INSTALL)) {
                        AppInfoManager.installApk(UpdateApkActivity.this, checkIsDownload());
                    }
                }
                //右边的按钮
            }
        });
        downloadName = AppInfoManager.getAppName(this) + "_" + downloadInfo.getVerName() + ".apk";

        tvDownload.setButtonRadius(tvDownload.getHeight() / 2);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    private File checkIsDownload() {
        File file = new File(FileDirectoryUtil.getUpdateAPKDownloadPath(), downloadName);
        if (file.exists()) {
            // 判断服务器APK大小，可能没有返回大小
            if (downloadInfo.getApkSize() > 0) {
                // 如果不相同就删掉
                if (downloadInfo.getApkSize() != file.length()) {
                    file.delete();
                    return null;
                }
            }
            tvDownload.setCurrentText(STATUS_INSTALL);
            return file;
        } else {
            tvDownload.setCurrentText(STATUS_START);
        }
        tvDownload.setState(ProgressButton.NORMAL);
        return null;
    }

    /**
     * 执行下载
     */
    @SuppressLint("SetTextI18n")
    private void doDownload() {
        Download download = new Download(downloadInfo.getUrl(), downloadName);
        download.setUpdateApk(true);
        DownloadService.startDown(this, download);
        tvDownload.setState(ProgressButton.DOWNLOADING);
        tvDownload.setCurrentText(STATUS_DOWNING);
        tvDownload.setProgressText(STATUS_DOWNING, tvDownload.getProgress());
    }

    /**
     * 获取权限
     */
    public void requestPermission() {
        XXPermissions.with(this)
                .permission(Permission.Group.STORAGE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            doDownload();
                        } else {
                            requestPermission();
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

    @Override
    protected void doOnBackPressed() {
        if (downloadInfo != null) {
            if (!(downloadInfo.isForce())) {
                super.onBackPressed();
            }
        } else
            super.doOnBackPressed();
    }

    /**
     * 接收Service发送的进度数据
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case DownloadService.DOWNLOAD_OK:
                    // 下载完成
                    tvDownload.setCurrentText(STATUS_INSTALL);
                    break;
                case DownloadService.DOWNLOAD_ERROR:
                    // 下载失败
                    String error = intent.getStringExtra(DownloadService.DOWNLOAD_ERROR_MSG);
                    showFailTips(error);
                    tvDownload.setCurrentText(STATUS_RE_DOWNLOAD);
                    break;
                case DownloadService.UPDATE_PROGRESS:
                    // 更新进度
                    int percent = intent.getIntExtra(DownloadService.UPDATE_PROGRESS_PERCENT, 0);
                    tvDownload.setProgressText(STATUS_DOWNING, percent);
                    break;
            }
        }
    };

    private void register() {
        registerReceiver(mReceiver, filter);
    }

    private void unRegister() {
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        register();
        checkIsDownload();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegister();
    }

    private void initFilter() {
        filter = new IntentFilter();
        filter.addAction(DownloadService.DOWNLOAD_OK);
        filter.addAction(DownloadService.DOWNLOAD_ERROR);
        filter.addAction(DownloadService.DOWNLOAD_ERROR_MSG);
        filter.addAction(DownloadService.UPDATE_PROGRESS);
        filter.addAction(DownloadService.UPDATE_PROGRESS_PERCENT);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_still,R.anim.scale_exit);
    }

}
