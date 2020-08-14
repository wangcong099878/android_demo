package the.one.mzitu;

import android.app.Activity;

import rxhttp.wrapper.cahce.CacheMode;
import the.one.base.BaseApplication;
import the.one.mzitu.ui.activity.LauncherActivity;

public class App extends BaseApplication {

    @Override
    protected Class<? extends Activity> getStartActivity() {
        return LauncherActivity.class;
    }

    @Override
    protected CacheMode getRxHttpCacheMode() {
        return CacheMode.READ_CACHE_FAILED_REQUEST_NETWORK;
    }

}
