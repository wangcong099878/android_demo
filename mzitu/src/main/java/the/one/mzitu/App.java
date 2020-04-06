package the.one.mzitu;

import the.one.base.BaseApplication;
import the.one.mzitu.ui.activity.LauncherActivity;

public class App extends BaseApplication {
    @Override
    protected Class getStartActivity() {
        return LauncherActivity.class;
    }
}
