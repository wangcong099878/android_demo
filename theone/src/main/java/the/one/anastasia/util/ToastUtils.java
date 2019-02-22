package the.one.anastasia.util;

import android.content.Context;
import android.widget.Toast;

/**
 */
public class ToastUtils {
    private static Toast mToast;
    public static void showToast(Context context, CharSequence msg){
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
