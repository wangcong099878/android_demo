package the.one.gank;


import rxhttp.wrapper.cahce.CacheMode;
import the.one.base.BaseApplication;
import the.one.gank.net.ResponseBuilder;
import the.one.gank.ui.activity.LauncherActivity;

public class App extends BaseApplication {

    @Override
    protected boolean isDebug() {
        return  true;
    }

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
    protected CacheMode getRxHttpCacheMode() {
        return CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK;
    }

}
