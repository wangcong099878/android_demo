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
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.TextView;

import com.qmuiteam.qmui.span.QMUICustomTypefaceSpan;

import the.one.base.R;

/**
 * @author The one
 * @date 2019/1/7 0007
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class StringUtils {
    /**
     * 特殊字体 人民币符号
     */
    public static Typeface TYPEFACE_RMB;

    public static void RMBString(Context context, String price, TextView textView){
        SpannableString customTypefaceText = new SpannableString(context.getResources().getString(R.string.spanUtils_rmb) + price);
        customTypefaceText.setSpan(new QMUICustomTypefaceSpan("", TYPEFACE_RMB), 0, context.getString(R.string.spanUtils_rmb).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(customTypefaceText);
    }

}
