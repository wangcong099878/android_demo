package the.one.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;
import okhttp3.OkHttpClient;
import rxhttp.wrapper.cahce.CacheMode;
import rxhttp.wrapper.cookie.CookieStore;
import rxhttp.wrapper.ssl.HttpsUtils;
import the.one.base.ui.activity.BaseCrashActivity;
import the.one.base.util.FileDirectoryUtil;
import the.one.base.util.NotificationManager;
import the.one.base.util.RxHttpManager;
import the.one.base.util.SkinSpUtil;
import the.one.base.util.SpUtil;
import the.one.base.util.crash.CrashConfig;
import the.one.base.util.crash.CrashUtil;


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
 * @date 2019/1/7 0007
 * @describe Application基类
 * @email 625805189@qq.com
 * @remark 需要继承此类或者manifest指向此类
 */
public class BaseApplication extends MultiDexApplication {

    @SuppressLint("StaticFieldLeak")
    protected static Context context;

    public static Context getInstance() {
        return context;
    }

    public static void setContext(Context c) {
        context = c;
    }

    /**
     * 异常捕获重启时的Activity
     *
     * @return 启动Activity
     */
    protected Class<? extends Activity> getStartActivity() {
        return null;
    }

    /**
     * 异常捕获时显示的Activity
     *
     * @return 异常显示Activity
     */
    protected Class<? extends Activity> getCrashActivity() {
        return BaseCrashActivity.class;
    }

    /**
     * 是否需要QMUI换肤功能
     *
     * @return
     */
    protected boolean isOpenQMUISkinManger() {
        return false;
    }

    /**
     * 是否为Debug模式
     *
     * @return True 会开启日志
     */
    @Deprecated
    protected boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * RxHttp缓存模式
     *
     * @return 默认仅网络
     */
    @Deprecated
    protected CacheMode getRxHttpCacheMode() {
        return CacheMode.ONLY_NETWORK;
    }

    /**
     * RxHttp缓存时长
     *
     * @return 默认24小时
     */
    @Deprecated
    protected long getRxHttpCacheValidTime() {
        return 24 * 60 * 60 * 1000;
    }

    /**
     * 获取RxHttp缓存文件夹
     * @return
     */
    @Deprecated
    protected File getRxHttpCacheFile(){
        return new File(FileDirectoryUtil.getRxHttPCachePath());
    }

    /**
     * 链接超时时间
     * @return
     */
    @Deprecated
    protected int getConnectTimeout(){
        return 10;
    }

    /**
     * 读取超时时间
     * @return
     */
    @Deprecated
    protected int getReadTimeout(){
        return 10;
    }

    /**
     * 写入超时时间
     * @return
     */
    @Deprecated
    protected int getWriteTimeout(){
        return 10;
    }

    /**
     * RxHttp是否需要Cookie
     *
     */
    @Deprecated
    protected boolean isRxHttpCookie() {
        return false;
    }

    /**
     * 获取HttpClient的配置Builder
     *
     * @return
     */
    protected RxHttpManager.HttpBuilder getHttpBuilder(){
        return new RxHttpManager.HttpBuilder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initCrashConfig();
        QMUISwipeBackActivityManager.init(this);
        NotificationManager.getInstance().register(this);
        SpUtil.init(this);
        SkinSpUtil.setQMUISkinManager(isOpenQMUISkinManger());
        initHttp(getHttpBuilder());
        initLogger();
    }

    /**
     * v 2.0.9 起，需自行对RxHttp初始化
     * 如果有用到下载服务、自动更新、必须对此方法初始化
     * 把下面两个复制过去即可
     */
    protected void initHttp(RxHttpManager.HttpBuilder builder) {
        // RxHttp.init(RxHttpManager.getHttpClient(builder));
        // RxHttpManager.initCacheMode(builder);
    }

    /**
     * 获取默认的OkHttpClient
     * @deprecated RxHttpManager.getHttpClient(builder)
     */
    @Deprecated
    protected OkHttpClient.Builder getDefaultOkHttpClientBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (isRxHttpCookie()) {
            builder.cookieJar(new CookieStore(new File(FileDirectoryUtil.getCachePath(), "RxHttpCookie"), false));
        }
        builder.connectTimeout(getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(getWriteTimeout(), TimeUnit.SECONDS)
                .sslSocketFactory(HttpsUtils.getSslSocketFactory().sSLSocketFactory, HttpsUtils.getSslSocketFactory().trustManager) //添加信任证书
                .hostnameVerifier((hostname, session) -> true); //忽略host验证;
        return builder;
    }

    /**
     * @TODO 初始化异常捕捉配置
     * @remark https://github.com/Ereza/CustomActivityOnCrash
     */
    protected void initCrashConfig() {
        CrashUtil.install(this);
        if (null == getStartActivity()) return;
        CrashConfig.Builder.create()
                .backgroundMode(CrashConfig.BACKGROUND_MODE_SHOW_CUSTOM)
                .enabled(true)
                .trackActivities(true)
                .minTimeBetweenCrashesMs(2000)
                // 重启的 Activity
                .restartActivity(getStartActivity())
                // 错误的 Activity
                .errorActivity(getCrashActivity())
                // 设置监听器
                // .eventListener(new YourCustomEventListener())
                .apply();
    }

    /**
     * 初始化日志打印工具
     */
    protected void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)               // （可选）要显示的方法行数。 默认2
                .methodOffset(7)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .tag(getString(R.string.app_name))                  //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

}
