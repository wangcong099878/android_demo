package the.one.wallpaper.ui.activity;

import the.one.base.base.activity.SampleLauncherActivity;

public class LauncherActivity extends SampleLauncherActivity {
    @Override
    protected Class getMainActivity() {
        return WallpaperActivity.class;
    }
}