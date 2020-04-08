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

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.View;
import android.view.Window;

/**
 * @author The one
 * @date 2020/4/8 0008
 * @describe 黑白化 （暂且命名为 哀悼主题 吧）
 * @email 625805189@qq.com
 * @remark
 */
public class AppMourningThemeUtil {

    private static final String MOURNING_THEME = "mourning.theme";

    public static void notify(Window window) {
        if (isMourningTheme() && null != window) {
            Paint paint = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            window.getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        }
    }

    public static void setMourningTheme(boolean mourning) {
        SpUtil.getInstance().putBoolean(MOURNING_THEME, mourning);
    }

    public static boolean isMourningTheme() {
        return SpUtil.getInstance().getBoolean(MOURNING_THEME);
    }

}
