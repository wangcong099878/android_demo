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

    private static void setViewsVisible(int visible,View... views){
        for (View view:views){
            if(null != view && view.getVisibility() != visible){
                view.setVisibility(visible);
            }
        }
    }

}
