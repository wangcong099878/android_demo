package the.one.base.util;

import android.annotation.SuppressLint;
import android.widget.Toast;

import the.one.base.BaseApplication;

/**
 */
public class ToastUtil {

    private static Toast mToast;
    @SuppressLint("ShowToast")
    public static void showToast(CharSequence msg){
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
