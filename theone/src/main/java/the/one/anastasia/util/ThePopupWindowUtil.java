package the.one.anastasia.util;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/**
 * @author The one
 * @date 2018/8/20 0020
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ThePopupWindowUtil {

    public static void showAsDropDown(final PopupWindow pw, final View anchor){
        showAsDropDown(pw,anchor,0,0);
    }
    /**
     * android 7.0及以上PopupWindow位置弹窗位置异常
     * @param pw     popupWindow
     * @param anchor v
     * @param xoff   x轴偏移
     * @param yoff   y轴偏移
     */
    public static void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(anchor, xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }
}
