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

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.qmuiteam.qmui.skin.QMUISkinHelper;

import the.one.base.R;

/**
 * @author The one
 * @date 2019/8/13 0013
 * @describe 获取全局设置的TopBar的背景
 * @email 625805189@qq.com
 * @remark
 */
public class StatusBarUtil {

    private static final String TAG = "TopBarUtil";

    public static boolean isWhiteBg(View view) {
        if (null == view) return false;
        int bgColor;
        try {
            bgColor = QMUISkinHelper.getSkinColor(view, R.attr.qmui_skin_support_topbar_bg);
        } catch (Exception e) {
            Log.e(TAG, "isWhiteBg: Exception "+e.getLocalizedMessage() );
            bgColor = Color.WHITE;
        }
        Log.e(TAG, "isWhiteBg: "+bgColor );
        // color < 0  drawable > 0 , 小于一定值的灰色也默认是白色背景
        return bgColor > -10000000 && bgColor<0;
    }

}
