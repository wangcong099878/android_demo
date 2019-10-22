package the.one.base.util;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

/**
 * @author The one
 * @date 2018/11/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class InputFilterMinMax implements InputFilter{

    private static final String TAG = "InputFilterMinMax";

    private int min, max;
    private Context context;
    private String tips;


    public InputFilterMinMax(Context context, int max, String tips) {
        this.context = context;
        this.min = 0;
        this.max = max;
        this.tips = tips;
    }

    public InputFilterMinMax(Context context, int min, int max, String tips) {
        this.context = context;
        this.min = min;
        this.max = max;
        this.tips = tips;
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input)){
                Log.e(TAG, "filter:  true" + input);
                return null;
            }else{
               ToastUtil.showToast(tips);
            }
        } catch (Exception nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
