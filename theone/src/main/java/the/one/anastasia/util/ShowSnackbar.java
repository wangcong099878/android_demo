package the.one.anastasia.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 *  一个小弹窗控件
 */

public class ShowSnackbar {
    public static Snackbar snackbar;

    public static void showSnackBar(View view, String content) {
        snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showSnackBar(View view, String content, int time) {
        snackbar = Snackbar.make(view, content, time);
        snackbar.show();
    }

//    Snackbar snackbar = Snackbar.make(v, "Snackbar", Snackbar.LENGTH_LONG)
//            .setAction("确定", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {


//                }
//            });
//    //设置提示文字颜色
//    Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
//        ((TextView) snackbarLayout.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));
//        snackbar.show();

    /**
     *    http://www.ymapk.com/article-100-1.html
     */
}
