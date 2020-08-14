package the.one.wallpaper;

import android.app.Activity;

import the.one.base.BaseApplication;
import the.one.wallpaper.ui.activity.LauncherActivity;

public class App extends BaseApplication {

    @Override
    protected Class<? extends Activity> getStartActivity() {
        return LauncherActivity.class;
    }
}
