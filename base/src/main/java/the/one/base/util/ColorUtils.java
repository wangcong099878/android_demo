package the.one.base.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;

import the.one.base.R;

/**
 * 一些公共的方法
 */
public class ColorUtils {

    private static TypedArray colorArray;

    /**
     * 返回联系人姓名的图标背景色
     *
     * @param name    联系人的姓名
     * @param context
     * @return
     */
    public static int getBackgroundColorId(String name, Context context) {
        int bgColor = -1;
        int defaultColor = context.getResources().getColor(
                R.color.mz_round_colorfulbg_color);
        if (null == colorArray)
            colorArray = context.getResources().obtainTypedArray(R.array.mc_colorful_round);
        int index = 0;

        if (!TextUtils.isEmpty(name))
            index = Math.abs(name.hashCode()) % (colorArray.length());
        if (index < colorArray.length())
            bgColor = colorArray.getColor(index, defaultColor);
        return bgColor;
    }

}
