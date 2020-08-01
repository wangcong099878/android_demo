package the.one.base;

import android.annotation.SuppressLint;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;
import okhttp3.OkHttpClient;
import rxhttp.HttpSender;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.cahce.CacheMode;
import rxhttp.wrapper.cookie.CookieStore;
import rxhttp.wrapper.ssl.SSLSocketFactoryImpl;
import rxhttp.wrapper.ssl.X509TrustManagerImpl;
import the.one.base.ui.activity.BaseCrashActivity;
import the.one.base.util.FileDirectoryUtil;
import the.one.base.util.NotificationManager;
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
     * @return  启动Activity
     */
    protected Class getStartActivity() {
        return null;
    }

    /**
     * 异常捕获时显示的Activity
     * @return  异常显示Activity
     */
    protected Class getCrashActivity() {
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
    protected boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * RxHttp缓存模式
     *
     * @return 默认仅网络
     */
    protected CacheMode getRxHttpCacheMode() {
        return CacheMode.ONLY_NETWORK;
    }

    /**
     * RxHttp缓存时长
     *
     * @return 默认24小时
     */
    protected long getRxHttpCacheValidTime() {
        return 24 * 60 * 60 * 1000;
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
        initHttp();
        initLogger();
    }

    /**
     * OkHttpUtils适配https
     * RxHttp设置缓存路径
     */
    protected void initHttp() {
        OkHttpClient client = getDefaultOkHttpClient();
        HttpSender.init(client, isDebug());
        //设置缓存目录为：Android/data/{app包名目录}/cache/RxHttpCache
        File cacheDir = new File(FileDirectoryUtil.getRxHttPCachePath());
        //设置最大缓存为10M，缓存有效时长为一个小时，这里全局不做缓存处理，某些需要缓存的请求单独设置
        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024, getRxHttpCacheMode(), getRxHttpCacheValidTime());
        OkHttpUtils.initClient(client);
    }

    /**
     * 获取默认的OkHttpClient
     */
    protected OkHttpClient getDefaultOkHttpClient() {
        X509TrustManager trustAllCert = new X509TrustManagerImpl();
        SSLSocketFactory sslSocketFactory = new SSLSocketFactoryImpl(trustAllCert);
        return new OkHttpClient.Builder()
                .cookieJar(new CookieStore(new File(FileDirectoryUtil.getCachePath(), "RxHttpCookie"), false))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory, trustAllCert) //添加信任证书
                .hostnameVerifier((hostname, session) -> true) //忽略host验证
                .build();
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
