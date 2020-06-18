package the.one.gank;


import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;

import okhttp3.OkHttpClient;
import rxhttp.HttpSender;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.cahce.CacheMode;
import the.one.base.BaseApplication;
import the.one.base.BuildConfig;
import the.one.base.util.FileDirectoryUtil;
import the.one.gank.net.ResponseBuilder;
import the.one.gank.ui.activity.LauncherActivity;

public class App extends BaseApplication {

    @Override
    protected Class getStartActivity() {
        return LauncherActivity.class;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ResponseBuilder
                .getBuilder()
                .setEventStr("status")
                .setMsgStr("msg")
                .setDataStr("data")
                .setSuccessCode(100);
    }

    @Override
    protected void initHttp() {
        OkHttpClient client = getDefaultOkHttpClient();
        HttpSender.init(client, BuildConfig.DEBUG);
        //设置缓存目录为：Android/data/{app包名目录}/cache/RxHttpCache
        File cacheDir = new File(FileDirectoryUtil.getCachePath(), "RxHttpCache");
        //设置最大缓存为10M，缓存有效时长为一个小时，这里全局不做缓存处理，某些需要缓存的请求单独设置
        RxHttpPlugins.setCache(cacheDir, 10 * 1024 * 1024, CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK, 60 * 60 * 1000);
        OkHttpUtils.initClient(client);
    }
}
