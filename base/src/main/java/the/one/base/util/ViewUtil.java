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

import android.view.View;
import android.view.ViewGroup;

/**
 * @author The one
 * @date 2020/4/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ViewUtil {

    public static void showViews(View... views){
        setViewsVisible(View.VISIBLE,views);
    }

    public static void goneViews(View... views){
        setViewsVisible(View.GONE,views);
    }

    public static void invisibleViews(View... views){
        setViewsVisible(View.INVISIBLE,views);
    }

    public static void setViewsVisible(boolean visible,View... views){
        if(visible) showViews(views);
        else goneViews(views);
    }

    private static void setViewsVisible(int visible,View... views){
        for (View view:views){
            if(null != view && view.getVisibility() != visible){
                view.setVisibility(visible);
            }
        }
    }

    public static boolean isVisible(View view) {
        return checkVisible(View.VISIBLE,view);
    }

    public static boolean isInVisible(View view) {
        return checkVisible(View.INVISIBLE,view);
    }

    public static boolean isGone(View view) {
        return checkVisible(View.GONE,view);
    }

    public static boolean checkVisible(int status,View view){
        return null != view && view.getVisibility() ==  status;
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (null != v && v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

}
