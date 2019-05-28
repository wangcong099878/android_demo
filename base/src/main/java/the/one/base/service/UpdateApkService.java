package the.one.base.service;

import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.UUID;

import okhttp3.Call;
import the.one.base.R;
import the.one.base.util.AppInfoManager;
import the.one.base.util.BroadCastUtil;
import the.one.base.util.FileDirectoryUtil;
import the.one.base.util.NotificationManager;

public class UpdateApkService extends Service {

    public static final String TAG = "DOWNLOAD";

    public static final String URL = "url";
    public static final String DOWNLOAD_OK = "download_ok";
    public static final String DOWNLOAD_ERROR = "download_error";
    public static final String DOWNLOAD_ERROR_MSG = "download_error_msg";
    public static final String UPDATE_PROGRESS = "update_progress";
    public static final String UPDATE_PROGRESS_CURRENT = "update_progress_current";
    public static final String UPDATE_PROGRESS_TOTAL = "update_progress_total";
    public static final String UPDATE_PROGRESS_PERCENT = "update_progress_percent";

    private static final int NOTIFICATION_ID = UUID.randomUUID().hashCode();
    private String mUrl = null;

    private int oldPercent = 0;

    private NotificationManager theNotificationManager;
    private NotificationCompat.Builder mBuilder;

    public UpdateApkService() {
    }

    public static void startDown(Activity activity, String url) {
        Intent intent = new Intent(activity, UpdateApkService.class);
        intent.putExtra(URL, url);
        activity.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            if (null == mUrl) {
                mUrl = intent.getStringExtra(URL);
                initNotify();
                startDown();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDown() {
        OkHttpUtils
                .get()
                .url(mUrl)
                .tag(TAG)
                .build()
                .execute(new FileCallBack(FileDirectoryUtil.getDownloadPath(), FileDirectoryUtil.getBuilder().getUpdateApkName()) {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        BroadCastUtil.send(UpdateApkService.this, DOWNLOAD_ERROR, DOWNLOAD_ERROR_MSG, e.getMessage());
                        updateNotification("下载失败", false);
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        // 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿
                        int percent = (int) (progress * 100);
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
                        AppInfoManager.installApk(UpdateApkService.this, response);
                        BroadCastUtil.send(UpdateApkService.this, DOWNLOAD_OK);
                        updateNotification("下载完成", true);
                    }
                });
    }

    /**
     * 初始化通知栏
     */
    private void initNotify() {
        theNotificationManager = NotificationManager.getInstance(this);
        mBuilder = theNotificationManager.createNotification(NOTIFICATION_ID,
                NotificationManager.LEVEL_DEFAULT_CHANNEL_ID,
                "开始下载",
                "正在下载",
                null,
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
        mBuilder.setContentText(progress + "%").setProgress
                (100, progress, false);
        theNotificationManager.notify(NOTIFICATION_ID, mBuilder);
    }

    /**
     * 更新通知栏的状态
     */
    private void updateNotification(String title, boolean isFinish) {
        Log.e(TAG, "updateError: ");
        //设置通知栏常住(服务退出前台运行转后台)
        stopForeground(true);
        NotificationCompat.Builder mBuilder = theNotificationManager.createNotification(NOTIFICATION_ID,
                NotificationManager.LEVEL_DEFAULT_CHANNEL_ID,
                title,
                title,
                null,
                isFinish ? R.drawable.service_down : R.drawable.service_down_finish);
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true);
        Notification build = mBuilder.build();
        build.flags = Notification.FLAG_AUTO_CANCEL;
        theNotificationManager.notify(NOTIFICATION_ID, mBuilder);
        stopSelf();
    }
    /**
     * 获取默认的通知栏事件
     *
     * @return
     */
//    public PendingIntent getDefaultIntent() {
//        // 设置启动的程序，如果存在则找出，否则新的启动
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.setComponent(new ComponentName(this,UpdateAppActivity.class));//用ComponentName得到class对象
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式，两种情况
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), PendingIntent
//                .FLAG_UPDATE_CURRENT);
//        return pendingIntent;
//    }
    @Override
    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(TAG);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
