package the.one.base;

import android.annotation.SuppressLint;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.zhy.http.okhttp.OkHttpUtils;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import rxhttp.HttpSender;
import the.one.base.ui.activity.CrashActivity;
import the.one.base.util.NotificationManager;
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
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public  class BaseApplication extends MultiDexApplication {

    @SuppressLint("StaticFieldLeak")
    protected static Context context;

    public static Context getInstance() {
        return context;
    }

    protected  Class getStartActivity(){
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initCrashConfig();
        MultiDex.install(this);
        QMUISwipeBackActivityManager.init(this);
        NotificationManager.getInstance(this).register();
        SpUtil.init(this);
        initOkHttpUtils();
        initLogger();
    }

    /**
     * OkHttpUtils适配https
     */
    protected void initOkHttpUtils(){
        OkHttpUtils.initClient(HttpSender.newOkClientBuilder().build());
    }

    /**
     * @TODO 初始化异常捕捉配置
     * @remark https://github.com/Ereza/CustomActivityOnCrash
     */
    protected void initCrashConfig(){
        CrashUtil.install(this);
        if(null == getStartActivity()) return;
        CrashConfig.Builder.create()
                .backgroundMode(CrashConfig.BACKGROUND_MODE_SHOW_CUSTOM)
                .enabled(true)
                .trackActivities(true)
                .minTimeBetweenCrashesMs(2000)
                // 重启的 Activity
                .restartActivity(getStartActivity())
                // 错误的 Activity
                .errorActivity(CrashActivity.class)
                // 设置监听器
//                .eventListener(new YourCustomEventListener())
                .apply();
    }

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
