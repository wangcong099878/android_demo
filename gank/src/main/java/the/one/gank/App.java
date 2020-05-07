package the.one.gank;


import the.one.base.BaseApplication;
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
}
