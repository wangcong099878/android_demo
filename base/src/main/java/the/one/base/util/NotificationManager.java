package the.one.base.util;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @author The one
 * @date 2018/9/20 0020
 * @describe 通知管理器
 * @email 625805189@qq.com
 * @remark 根据自行需要更改 ID 和 名称
 */
public class NotificationManager {

    /**
     * 重要等级渠道ID
     */
    public static final String LEVEL_HIGH_CHANNEL_ID ="high";
    /**
     * 重要等级渠道名称
     */
    public static final String LEVEL_HIGH_CHANNEL_NAME ="重要消息";
    /**
     * 默认等级渠道ID
     */
    public static final String LEVEL_DEFAULT_CHANNEL_ID ="default";
    /**
     * 默认等级渠道名称
     */
    public static final String LEVEL_DEFAULT_CHANNEL_NAME ="默认消息";

    public static NotificationManager theNotificationManager;
    public static Context mContext;

    private android.app.NotificationManager NotificationManager;


    public static NotificationManager getInstance(Context context){
        if(null == theNotificationManager)
            theNotificationManager = new NotificationManager();
        mContext = context;
        return theNotificationManager;
    }

    /**
     * 注册 8.0 通知栏等级，应用开始时调用
     */
    public void register(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(LEVEL_HIGH_CHANNEL_ID, LEVEL_HIGH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            createNotificationChannel(LEVEL_DEFAULT_CHANNEL_ID, LEVEL_DEFAULT_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        getNotificationManager().createNotificationChannel(channel);
    }

    public android.app.NotificationManager getNotificationManager(){
        if(null == NotificationManager)
            NotificationManager = (android.app.NotificationManager) mContext.getSystemService(
                    NOTIFICATION_SERVICE);
        return NotificationManager;
    }

    /**
     * 删除通知渠道
     * 会在通知设置界面显示所有被删除的通知渠道数量，会导致不美观，不推荐使用
     * @param id
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteNotificationChannel(String id){
        getNotificationManager().deleteNotificationChannel(id);
    }

    /**
     * 创建通知
     *
     * @param ID           渠道ID
     * @param LEVEL        通知等级
     * @param ticker       提示语
     * @param title        标题
     * @param content      内容
     * @param showWhen     是否显示时间
     * @param smallIcon    小ICON
     */
    public NotificationCompat.Builder createNotification(int ID, String LEVEL, String ticker, String title, String content, boolean showWhen, int smallIcon){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, LEVEL);
        if(null !=ticker) builder.setTicker(ticker);
        if(null !=title) builder.setContentTitle(title);
        if(null !=content) builder.setContentText(content);
        if(showWhen) builder.setWhen(System.currentTimeMillis());
        builder.setOnlyAlertOnce(true);
        setNotificationIcon(builder,smallIcon);

        getNotificationManager().notify(ID, builder.build());
        return builder;
    }

    /**
     *
     * @return
     */
    public NotificationCompat.Builder createNotification(int ID, String LEVEL, String ticker, String title, String content, int smallIcon){
        return createNotification(ID,LEVEL,ticker,title,content,true,smallIcon);
    }

    /**
     * 设置通知栏Icon
     * @param builder
     */
    private void setNotificationIcon(NotificationCompat.Builder builder, int id){
        //解决5.0系统通知栏白色Icon的问题
        Drawable appIcon = getAppIcon(mContext);
        Bitmap drawableToBitmap = null;
        if (appIcon != null) {
            drawableToBitmap = drawableToBitmap(appIcon);
        }
        if (drawableToBitmap != null) {
            builder.setSmallIcon(id);
            builder.setLargeIcon(drawableToBitmap);
        } else {
            builder.setSmallIcon(mContext.getApplicationInfo().icon);
        }
    }


    /**
     * 合成更新的Icon
     *
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 获取App的Icon
     *
     * @param context
     * @return
     */
    public Drawable getAppIcon(Context context) {
        try {
            return context.getPackageManager().getApplicationIcon(context.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新
     * @param ID
     * @param builder
     */
    public void notify(int ID,NotificationCompat.Builder builder){
        getNotificationManager().notify(ID, builder.build());
    }
}
