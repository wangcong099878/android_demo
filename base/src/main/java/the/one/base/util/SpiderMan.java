package the.one.base.util;

import android.content.Context;

import the.one.base.base.activity.CrashActivity;
import the.one.base.model.CrashModel;

/**
 * @author The one
 * @date
 * @describe TODO
 * @email 625805189@qq.com
 * @remark 
 */
public class SpiderMan implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "SpiderMan";

    private static SpiderMan spiderMan;

    private static Context mContext;

    private SpiderMan() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static SpiderMan init(Context context) {
        mContext = context;
        spiderMan = new SpiderMan();
        return spiderMan;
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        CrashModel model = SpiderUtil.parseCrash(ex);
        handleException(model);
        ActivityListUtil.getInstance().cleanActivityList();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private static void handleException(CrashModel model) {
        CrashActivity.handleException(getContext(),model);
    }

    public static void show(Throwable e) {
        CrashModel model = SpiderUtil.parseCrash(e);
        handleException(model);
    }

    public static Context getContext() {
        if (mContext == null) {
            throw new NullPointerException("Please call init method in Application onCreate");
        }
        return mContext;
    }
}