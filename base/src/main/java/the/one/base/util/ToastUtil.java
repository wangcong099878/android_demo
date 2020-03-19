package the.one.base.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import the.one.base.BaseApplication;

/**
 */
public class ToastUtil {

    private static Toast mToast;
    private static Toast mLongToast;
    @SuppressLint("ShowToast")
    public static void showToast(CharSequence msg){
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void showLongToast(CharSequence msg){
        if (mLongToast == null) {
            mLongToast = Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_LONG);
        } else {
            mLongToast.setText(msg);
        }
        mLongToast.show();
    }

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void show(int msgResId) {
        show(msgResId, false);
    }

    public static void show(int msgResId, boolean timeLong) {
        show(BaseApplication.getInstance().getString(msgResId), timeLong);
    }

    public static void show(CharSequence msg) {
        show(msg, false);
    }

    public static void show(final CharSequence msg, final boolean timeLong) {
        runOnUiThread(() -> {
            if (mToast != null) {
                mToast.cancel();
            }
            int duration = timeLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
            mToast = Toast.makeText(BaseApplication.getInstance(), msg, duration);
            mToast.show();
        });
    }

    private static void runOnUiThread(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

}
