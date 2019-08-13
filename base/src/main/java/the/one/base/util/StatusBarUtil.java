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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.qmuiteam.qmui.util.QMUIResHelper;

import the.one.base.R;

/**
 * @author The one
 * @date 2019/8/13 0013
 * @describe 根据TopBar的背景判断是否translucent
 * @email 625805189@qq.com
 * @remark
 */
public class StatusBarUtil {

    public static boolean isTranslucent(Context context){
        Drawable bgDrawable;
        int bgColor;
        try {
            TypedArray array = context.obtainStyledAttributes(null, R.styleable.QMUITopBar, R.attr.QMUITopBarStyle, 0);
            bgColor = array.getColor(R.styleable.QMUITopBar_qmui_topbar_bg_color, Color.WHITE);
            bgDrawable = QMUIResHelper.getAttrDrawable(context, R.attr.qmui_topbar_bg_drawable);
            array.recycle();
        }catch (Exception e){
            bgDrawable = null;
            bgColor = Color.WHITE;
        }
       return null !=bgDrawable || bgColor!= Color.WHITE;
    }

}
