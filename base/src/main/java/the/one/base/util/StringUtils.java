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
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.qmuiteam.qmui.span.QMUICustomTypefaceSpan;

import the.one.base.R;
import the.one.base.widge.MyImageSpan;

/**
 * @author The one
 * @date 2019/1/7 0007
 * @describe 字符串工具
 * @email 625805189@qq.com
 * @remark 更多请看 https://blog.csdn.net/u012551350/article/details/51722893
 */
public class StringUtils {

   public enum Type {
        ForegroundColorSpan, //前景色
        BackgroundColorSpan, //背景色
        StrikeThroughSpan, // 删除线
        UnderlineSpan, // 下划线
        SuperscriptSpan, // 上标
        SubscriptSpan, // 下标
       RelativeSizeSpan, // 字号
        BOLD, // 粗体
        ITALIC, // 斜体
        BOLD_ITALIC // 粗体加斜体
    }

    /**
     * 特殊字体 人民币符号
     */
    public static Typeface TYPEFACE_RMB;

//    static {
//        try {
//            Typeface tmpRmb = Typeface.createFromAsset(getContext().getAssets(),
//                    "fonts/iconfont.ttf");
//            TYPEFACE_RMB = Typeface.create(tmpRmb, Typeface.NORMAL);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void RMBString(Context context, String price, TextView textView){
        RMBString(context,"",price,textView);
    }

    /**
     * RMB符号处理
     * @param context
     * @param left 人民币左边的文字
     * @param right 人民币右边的文字
     * @param textView
     * @remark 如果没有自定义字体，则此方法无作用.... 如需自定义字体，用注释里的方法对TYPEFACE_RMB进行处理。
     */
    public static void RMBString(Context context,String left,String right, TextView textView){
        String price = context.getResources().getString(R.string.spanUtils_rmb);
        int startIndex = left.length();
        SpannableString customTypefaceText = new SpannableString(left+price + right);
        customTypefaceText.setSpan(new QMUICustomTypefaceSpan("", TYPEFACE_RMB), startIndex,startIndex+ price.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(customTypefaceText);
    }

    /**
     * 给一段文字中某一节更改颜色
     * @param content 整段内容
     * @param targetString 需要变颜色的内容
     * @param color 颜色
     * @param textView 显示的TextView
     * @remark 组合方法 content = xxx + targetString + xxx ;
     */
    public static void ForegroundColorString(String content, String targetString, int color,TextView textView){
        textView.setText(SpannableForegroundColorString(content,targetString,color));
    }
    /**
     * Spanned.SPAN_INCLUSIVE_EXCLUSIVE 从起始下标到终了下标，包括起始下标
     * Spanned.SPAN_INCLUSIVE_INCLUSIVE 从起始下标到终了下标，同时包括起始下标和终了下标
     * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 从起始下标到终了下标，但都不包括起始下标和终了下标
     * Spanned.SPAN_EXCLUSIVE_INCLUSIVE 从起始下标到终了下标，包括终了下标
     * /

    /**
     * 给一段文字中某一节的前景色
     * @param content
     * @param targetString
     * @param color
     * @return
     *
     */
    public static SpannableString SpannableForegroundColorString( String content, String targetString, int color) {
        SpannableString spannableString = new SpannableString(content);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        int start = content.indexOf(targetString);
        int end = start+targetString.length();
        spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 给一段文字中某一节的前景色
     * @param content
     * @param targetString
     * @param color
     * @return
     *

     *
     */
    public static SpannableString SpannableBackgroundColorString( String content, String targetString, int color) {
        SpannableString spannableString = new SpannableString(content);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan (color);
        int start = content.indexOf(targetString);
        int end = start+targetString.length();
        spannableString.setSpan(backgroundColorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    /**
     * 给文字末端加入Drawable
     * @param content
     * @param drawable
     */
    public static SpannableString ImageSpanString(String content, Drawable drawable){
        content = content +"  ";
        int index = content.length();
       return ImageSpanString(content,drawable,index-1,index);
    }

    /**
     * 把文字自定地方替换成Drawable
     * @param content 内容
     * @param drawable 图片
     * @param start 起始位置
     * @param end 结束位置
     */
    public static SpannableString ImageSpanString(String content, Drawable drawable,int start,int end){
        SpannableString spannableString = new SpannableString(content);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        spannableString.setSpan(new MyImageSpan(drawable), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static  SpannableString RelativeSizeSpannableString( String content, String targetString,float fontSize) {
        return SpannableString(Type.RelativeSizeSpan,content,targetString,-1,fontSize);
    }

    public static  SpannableString SpannableString(Type type, String content, String targetString) {
        return SpannableString(type,content,targetString,-1,0);
    }

    public static  SpannableString SpannableString(Type type, String content, String targetString,int color) {
        return SpannableString(type,content,targetString,color,0);
    }

    /**
     *
     * @param type
     * @param content 全部内容
     * @param targetString 需要处理的内容
     * @param color 颜色 可无
     * @param fontSize 字体大小 可无
     * @return
     */
    public static SpannableString SpannableString(Type type, String content, String targetString, int color,float fontSize) {
        SpannableString spannableString = new SpannableString(content);
        int start = content.indexOf(targetString);
        int end = start+targetString.length();
        CharacterStyle characterStyle = new ForegroundColorSpan(color);
        switch (type){
            case ForegroundColorSpan:
                characterStyle = new ForegroundColorSpan(color);
                break;
            case BackgroundColorSpan:
                characterStyle = new BackgroundColorSpan (color);
                break;
            case StrikeThroughSpan:
                characterStyle = new StrikethroughSpan();
                break;
            case UnderlineSpan:
                characterStyle = new UnderlineSpan();
                break;
            case SuperscriptSpan:
                characterStyle = new SuperscriptSpan();
                break;
            case SubscriptSpan:
                characterStyle = new SubscriptSpan();
                break;
            case RelativeSizeSpan:
                characterStyle = new RelativeSizeSpan(fontSize);
                break;
            case BOLD:
                characterStyle = new StyleSpan(Typeface.BOLD);
                break;
            case ITALIC:
                characterStyle = new StyleSpan(Typeface.ITALIC);
                break;
            case BOLD_ITALIC:
                characterStyle = new StyleSpan(Typeface.BOLD_ITALIC);
                break;
        }
        spannableString.setSpan(characterStyle, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


}
