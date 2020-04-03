package the.one.base.util;

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

import android.graphics.Rect;
import android.os.Build;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.PopupWindow;

import the.one.base.R;

/**
 * @author The one
 * @date 2019/1/7 0007
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class PopupWindowUtil {

    /**
     * android 7.0及以上PopupWindow位置弹窗位置异常
     *
     * @param pw     popupWindow
     * @param anchor v
     */
    public static void showAsDropDown(@NonNull final PopupWindow pw,@NonNull  final View anchor) {
        showAsDropDown(pw, anchor, 0, 0, R.style.PopAnimStyle);
    }

    /**
     * android 7.0及以上PopupWindow位置弹窗位置异常
     *
     * @param pw     popupWindow
     * @param anchor v
     */
    public static void showAsDropDown(@NonNull final PopupWindow pw,@NonNull final View anchor, int PopAnimStyle) {
        showAsDropDown(pw, anchor, 0, 0, PopAnimStyle);
    }

    /**
     * android 7.0及以上PopupWindow位置弹窗位置异常
     *
     * @param pw           popupWindow
     * @param anchor       v
     * @param xoff         x轴偏移
     * @param yoff         y轴偏移
     * @param PopAnimStyle 动画效果
     */
    public static void showAsDropDown(@NonNull final PopupWindow pw, @NonNull final View anchor, final int xoff, final int yoff, int PopAnimStyle) {
        pw.setAnimationStyle(PopAnimStyle);
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(anchor,xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }

}
