package the.one.base.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.UUID;

import androidx.core.app.NotificationCompat;
import okhttp3.Call;
import the.one.base.R;
import the.one.base.constant.DataConstant;
import the.one.base.model.Download;
import the.one.base.util.AppInfoManager;
import the.one.base.util.BroadCastUtil;
import the.one.base.util.FileDirectoryUtil;
import the.one.base.util.NetFailUtil;
import the.one.base.util.NotificationManager;
import the.one.base.util.ToastUtil;

public class DownloadService extends Service {

    public static final String TAG = "DownloadService";

    public static final String URL = "url";
    public static final String DOWNLOAD_OK = "download_ok";
    public static final String DOWNLOAD_ERROR = "download_error";
    public static final String DOWNLOAD_ERROR_MSG = "download_error_msg";
    public static final String UPDATE_PROGRESS = "update_progress";
    public static final String UPDATE_PROGRESS_CURRENT = "update_progress_current";
    public static final String UPDATE_PROGRESS_TOTAL = "update_progress_total";
    public static final String UPDATE_PROGRESS_PERCENT = "update_progress_percent";

    public static  int NOTIFICATION_ID = UUID.randomUUID().hashCode();

    private Download mDownload;
    private int oldPercent = 0;

    private NotificationManager theNotificationManager;
    private NotificationCompat.Builder mBuilder;


    public DownloadService() {
    }

    public static void startDown(Activity activity, Download download) {
        Intent intent = new Intent(activity, DownloadService.class);
        intent.putExtra(DataConstant.DATA, download);
        activity.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent && null == mDownload) {
            NOTIFICATION_ID = UUID.randomUUID().hashCode();
            mDownload = intent.getParcelableExtra(DataConstant.DATA);
            initNotification();
            startDown();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("CheckResult")
    private void startDown() {
        final String destFile = FileDirectoryUtil.getDownloadPath() +(TextUtils.isEmpty(mDownload.getDestFileDir()) ? "" : File.separator+mDownload.getDestFileDir());
        OkHttpUtils
                .get()
                .url(mDownload.getUrl())
                .tag(mDownload.getUrl())
                .build()
                .execute(new FileCallBack(destFile, mDownload.getName()) {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        BroadCastUtil.send(DownloadService.this, DOWNLOAD_ERROR, DOWNLOAD_ERROR_MSG, e.getMessage());
                        updateNotification("下载失败", false);
                        ToastUtil.showLongToast(NetFailUtil.getFailString(e));
                        File file = new File(destFile,mDownload.getName());
                        if(file.exists()){
                            file.delete();
                        }
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        int percent = (int) (progress * 100);
                        // 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿
                        if (percent != oldPercent) {
                            oldPercent = percent;
                            updateProgress(percent);
                            Intent intent = new Intent();
                            intent.setAction(UPDATE_PROGRESS);
                            intent.putExtra(UPDATE_PROGRESS_PERCENT, percent);
                            sendBroadcast(intent);
                        }
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        if (mDownload.isUpdateApk()) {
                            AppInfoManager.installApk(DownloadService.this, response);
                        } else if (mDownload.isImage()) {
                            updateLocationFile(response);
                        }
                        BroadCastUtil.send(DownloadService.this, DOWNLOAD_OK);
                        updateNotification("下载完成", true);
                    }
                });
    }

    /**
     * 初始化通知栏
     */
    private void initNotification() {
        theNotificationManager = NotificationManager.getInstance(this);
        mBuilder = theNotificationManager.createNotification(NOTIFICATION_ID,
                NotificationManager.LEVEL_DEFAULT_CHANNEL_ID,
                "开始下载",
                "开始下载",
                mDownload.getUrl(),
                R.drawable.service_down);
        mBuilder.setOngoing(true);

        //设置通知栏常住(服务前台运行)
        startForeground(NOTIFICATION_ID, mBuilder.build());
    }

    /**
     * 更新通知栏的进度(下载中)
     *
     * @param progress
     */
    private void updateProgress(int progress) {
        mBuilder.setContentTitle("下载中").setContentText(progress + "%").setProgress
                (100, progress, false);
        theNotificationManager.notify(NOTIFICATION_ID, mBuilder);
    }

    /**
     * 更新通知栏的状态
     */
    private void updateNotification(String title, boolean isFinish) {
        //设置通知栏常住(服务退出前台运行转后台)
        stopForeground(true);
        NotificationCompat.Builder mBuilder = theNotificationManager.createNotification(NOTIFICATION_ID,
                NotificationManager.LEVEL_DEFAULT_CHANNEL_ID,
                title,
                title,
                mDownload.getUrl(),
                isFinish ? R.drawable.service_down : R.drawable.service_down_finish);
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(isFinish);
        Notification build = mBuilder.build();
        build.flags = Notification.FLAG_AUTO_CANCEL;
        theNotificationManager.notify(NOTIFICATION_ID, mBuilder);
        stopSelf();
    }

    /**
     * 通知系统刷新文件
     */
    private void updateLocationFile(File file) {
        MimeTypeMap mtm = MimeTypeMap.getSingleton();
        MediaScannerConnection.scanFile(getApplicationContext(),
                new String[]{file.toString()},
                new String[]{mtm.getMimeTypeFromExtension(file.toString().substring(file.toString().lastIndexOf(".") + 1))},
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(final String path, final Uri uri) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString(DataConstant.DATA2, path);
                        message.setData(bundle);
                        handlerToast.sendMessage(message);
                    }
                });
    }


    @SuppressLint("HandlerLeak")
    private Handler handlerToast = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            ToastUtil.showLongToast("保存到 " + bundle.getString(DataConstant.DATA2));
        }
    };

    @Override
    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(mDownload.getUrl());
        handlerToast = null;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
