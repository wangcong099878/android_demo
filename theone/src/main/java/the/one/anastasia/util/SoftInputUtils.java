package the.one.anastasia.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.inputmethod.InputMethodManager;

/**
 * @author The one
 * @date 2018/9/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SoftInputUtils {
    /**
     * 显示软键盘，Dialog使用
     *
     * @param activity 当前Activity
     */
    public static void showSoftInput(Activity activity) {

        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity 当前Activity
     */
    public static void hideSoftInput(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(0, InputMethodManager.RESULT_HIDDEN);
//            inputMethodManager.hideSoftInputFromWindow(
//                    activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static boolean isSoftShowing(Activity activity) {
        //获取当前屏幕内容的高度
           int screenHeight = activity.getWindow().getDecorView().getHeight();
//         获取View可见区域的bottom
          Rect rect = new Rect();
//         DecorView即为activity的顶级view
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//         考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
//         选取screenHeight*2/3进行判断
          return screenHeight*2/3 > rect.bottom;

    }

}
