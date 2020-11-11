package the.one.mzitu;

import android.app.Activity;

import rxhttp.wrapper.cahce.CacheMode;
import rxhttp.wrapper.param.RxHttp;
import the.one.base.BaseApplication;
import the.one.base.util.RxHttpManager;
import the.one.mzitu.ui.activity.LauncherActivity;

public class MzituApp extends BaseApplication {

    @Override
    protected Class<? extends Activity> getStartActivity() {
        return LauncherActivity.class;
    }

    @Override
    protected void initHttp(RxHttpManager.HttpBuilder builder) {
        RxHttp.init(RxHttpManager.getHttpClient(builder));
        RxHttpManager.initCacheMode(builder);
    }

    @Override
    protected RxHttpManager.HttpBuilder getHttpBuilder() {
        return super.getHttpBuilder().setCacheMode(CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK);
    }

}
