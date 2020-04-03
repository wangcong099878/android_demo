package the.one.gank;


import the.one.base.BaseApplication;
import the.one.gank.ui.activity.LauncherActivity;

public class App extends BaseApplication {

    @Override
    protected Class getStartActivity() {
        return LauncherActivity.class;
    }
}
