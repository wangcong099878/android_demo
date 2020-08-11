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
import android.view.View;

import com.qmuiteam.qmui.skin.QMUISkinHelper;

import the.one.base.R;

/**
 * @author The one
 * @date 2019/8/13 0013
 * @describe 获取全局设置的TopBar的背景是不是白色背景，然后设置默认的状态栏模式
 * @email 625805189@qq.com
 * @remark
 */
public class StatusBarUtil {


    public static boolean isWhiteBg(View view) {
        if (null == view) return false;
        int bgColor;
        try {
            bgColor = QMUISkinHelper.getSkinColor(view, R.attr.qmui_skin_support_topbar_bg);
        } catch (Exception e) {
            bgColor = Color.WHITE;
        }
        return bgColor == -1;
    }

}
