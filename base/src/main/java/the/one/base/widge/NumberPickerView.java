package the.one.base.widge;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import the.one.base.R;

/**
 * Author        Hule  hu.le@cesgroup.com.cn
 * Date          2016/12/22 15:52
 * Description:  TODO: 自定义商城点击+或-实现数字框的数字增加或减少
 */

public class NumberPickerView extends LinearLayout implements View.OnClickListener, TextWatcher {

    private static final String TAG = "NumberPickerView";

    //当前输入框可输入的值（默认为不限制）
    private double maxValue = Float.MAX_VALUE;

    //当前的库存量（默认为不限制）
    private double currentInventory = Float.MAX_VALUE;

    //默认字体的大小
    private int textDefaultSize = 14;

    // 中间输入框的‘输入值
    private EditText mNumText;

    //默认输入框最小值
    private double minDefaultNum = 0.0f;

    // 监听事件(负责警戒值回调)
    private OnClickInputListener onClickInputListener;

    /**
     * 监听数值是否变化
     */
    private OnNumberChangeListener onNumberChangeListener;

    public NumberPickerView(Context context) {
        super(context);
    }

    public NumberPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initNumberPickerView(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void initNumberPickerView(final Context context, AttributeSet attrs) {
        //加载定义好的布局文件
        LayoutInflater.from(context).inflate(R.layout.number_button, this);
        LinearLayout mRoot = (LinearLayout) findViewById(R.id.root);
        QMUIRoundButton subText =  findViewById(R.id.button_sub);
        QMUIRoundButton addText =  findViewById(R.id.button_add);
        mNumText = (EditText) findViewById(R.id.middle_count);

        subText.setChangeAlphaWhenPress(true);
        addText.setChangeAlphaWhenPress(true);
        //添加监听事件
        addText.setOnClickListener(this);
        subText.setOnClickListener(this);
        mNumText.setOnClickListener(this);
        mNumText.addTextChangedListener(this);
        mNumText.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                mNumText.setHint("");
                QMUIKeyboardHelper.showKeyboard(mNumText,false);
            }else{
                setCurrentNum(getNumText());
            }
        });
        //默认两位小数
        mNumText.setFilters(new InputFilter[]{new MoneyValueFilter()});

//        //获取自定义属性的相关内容
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberButton);
//        // 背景
//        int resourceId = typedArray.getResourceId(R.styleable.NumberButton_backgroud, R.color.qmui_config_color_white);
//        int addResourceId = typedArray.getResourceId(R.styleable.NumberButton_addBackground, R.color.divider_color);
//        int subResourceId = typedArray.getResourceId(R.styleable.NumberButton_subBackground, R.color.divider_color);
//        // 水平分割线
//        Drawable dividerDrawable = typedArray.getDrawable(R.styleable.NumberButton_individer);
//        //中间的编辑框是否可编辑
//        boolean aBoolean = typedArray.getBoolean(R.styleable.NumberButton_editable, true);
//        //+和-文本的宽度 geDiemension返回float getDimensionPixelSize四舍五入+  getDimensionPixeloffset四舍五入-
//        int buttonWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_buttonWidth, dip2px(context, 40));
//        //+和-文本的颜色
//        int textColor = typedArray.getColor(R.styleable.NumberButton_textColorr, 0xff000000);
//        //+和-文本的字体大小
//        int textSize = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textSize, sp2px(context, 14));
//        // 中间显示数量的按钮宽度
//        final int editextWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_editextWidth, dip2px(context, 50));
//        //必须调用这个，因为自定义View会随着Activity创建频繁的创建array
//        typedArray.recycle();
//
//        //设置输入框是否可用
//        setEditable(aBoolean);
        //初始化控件颜色
//        mRoot.setBackgroundResource(resourceId);
//        mRoot.setDividerDrawable(dividerDrawable);
//        subText.setBackgroundResource(subResourceId);
//        addText.setBackgroundResource(addResourceId);
//        addText.setTextColor(textColor);
//        subText.setTextColor(textColor);
//        mNumText.setTextColor(textColor);

        //初始化字体,注意默认的是px单位，要转换
//        if (textSize > 0) {
//            mNumText.setTextSize(px2sp(context, textSize));
//        } else {
//            mNumText.setTextSize(textDefaultSize);
//        }

        //设置文本框的宽高
//        if (buttonWidth > 0) {
//            LayoutParams layoutParams = new LayoutParams(buttonWidth, LayoutParams.MATCH_PARENT);
//            addText.setLayoutParams(layoutParams);
//            subText.setLayoutParams(layoutParams);
//        } else {
//            Log.d("NumPickerView", "文本采用默认的宽高");
//        }
//        //设置输入框的宽高
//        if (editextWidth > 0) {
//            LayoutParams layoutParams = new LayoutParams(editextWidth, LayoutParams.MATCH_PARENT);
//            mNumText.setLayoutParams(layoutParams);
//        } else {
//            Log.d("NumPickerView", "编辑框采用默认的宽高");
//        }
    }

    /**
     * @param editable 设置输入框是否可编辑
     */
    private void setEditable(boolean editable) {
        if (editable) {
            mNumText.setFocusable(true);
//            mNumText.setKeyListener(new DigitsKeyListener());
        } else {
            mNumText.setFocusable(false);
//            mNumText.setKeyListener(null);
        }
    }

    /**
     * @return 获取输入框的最终数字值
     */
    public double getNumText() {
        try {
            String textNum = mNumText.getText().toString().trim();
            if(TextUtils.isEmpty(textNum)){
                return 0;
            }
            return Float.parseFloat(textNum);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            mNumText.setText(String.valueOf(minDefaultNum));
            return minDefaultNum;
        }
    }

    /**
     * 设置当前的最大值，即库存的上限
     */
    public NumberPickerView setCurrentInventory(double maxInventory) {
        this.currentInventory = maxInventory;
        return this;
    }

    /**
     * @return 获取当前的库存
     */
    public double getCurrentInventory() {
        return currentInventory;
    }

    public double getMinDefaultNum() {
        return minDefaultNum;
    }

    /**
     * 设置默认的最小值
     *
     * @param minDefaultNum
     * @return
     */
    public NumberPickerView setMinDefaultNum(float minDefaultNum) {
        this.minDefaultNum = minDefaultNum;
        return this;
    }

    public double getMaxValue() {
        return maxValue;
    }

    /**
     * 最大限制量
     *
     * @param maxValue
     * @return
     */
    public NumberPickerView setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        return this;
    }


    /**
     * @param currentNum 设置当前输入框的值
     * @return NumPickerView
     */
    public NumberPickerView setCurrentNum(double currentNum) {
        if (currentNum > minDefaultNum) {
            if (currentNum <= currentInventory) {
                mNumText.setText(String.valueOf(currentNum));
            } else if (currentNum > maxValue) {
                mNumText.setText(String.valueOf(maxValue));
            } else {
                mNumText.setText(String.valueOf(currentInventory));
            }
        } else {
            mNumText.setText("");
            mNumText.setHint(String.valueOf(minDefaultNum));
        }
        return this;
    }

    public NumberPickerView setOnClickInputListener(OnClickInputListener mOnWarnListener) {
        this.onClickInputListener = mOnWarnListener;
        return this;
    }

    public NumberPickerView setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
        return this;
    }

    @Override
    public void onClick(View view) {
        int widgetId = view.getId();
        double numText = getNumText();
        if (widgetId == R.id.button_sub) {
            if (numText > minDefaultNum + 1) {
                mNumText.setText(String.valueOf(numText - 1));
            } else {
                mNumText.setText(String.valueOf(minDefaultNum));
                //小于警戒值
                warningForMinInput();
            }
        } else if (widgetId == R.id.button_add) {
            if (numText < Math.min(maxValue, currentInventory)) {
                mNumText.setText(String.valueOf(numText + 1));
            } else if (currentInventory < maxValue) {
                mNumText.setText(String.valueOf(currentInventory));
                //库存不足
                warningForInventory();
            } else {
                mNumText.setText(String.valueOf(maxValue));
                // 超过限制数量
                warningForMaxInput();
            }

        }
        mNumText.setSelection(mNumText.getText().toString().length());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            mNumText.removeTextChangedListener(this);
            String inputText = editable.toString().trim();
            if (!TextUtils.isEmpty(inputText)) {
                float inputNum = Float.parseFloat(inputText);
                if (null != onNumberChangeListener)
                    onNumberChangeListener.onChange();
                if (inputNum < minDefaultNum) {
                    mNumText.setText(String.valueOf(minDefaultNum));
                    // 小于警戒值
                    warningForMinInput();
                } else if (inputNum <= Math.min(maxValue, currentInventory)) {
                    mNumText.setText(inputText);
                } else if (inputNum >= maxValue) {
                    mNumText.setText(String.valueOf(maxValue));
                    //超过限量
                    warningForMaxInput();
                } else {
                    mNumText.setText(String.valueOf(currentInventory));
                    //库存不足
                    warningForInventory();
                }
            }
            mNumText.addTextChangedListener(this);
            mNumText.setSelection(mNumText.getText().toString().length());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    /**
     * 超过的库存限制
     * Warning for inventory.
     */
    private void warningForInventory() {
        if (onClickInputListener != null)
            onClickInputListener.onWarningForInventory(currentInventory);
    }

    /**
     * 小于最小值回调
     * Warning for inventory.
     */
    private void warningForMinInput() {
        if (onClickInputListener != null)
            onClickInputListener.onWarningMinInput(minDefaultNum);
    }

    /**
     * 查过最大值值回调
     * Warning for inventory.
     */
    private void warningForMaxInput() {
        if (onClickInputListener != null)
            onClickInputListener.onWarningMaxInput(maxValue);
    }

    /**
     * 超过警戒值回调
     */
    public interface OnClickInputListener {
        void onWarningForInventory(double inventory);

        void onWarningMinInput(double minValue);

        void onWarningMaxInput(double maxValue);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static class MoneyValueFilter extends DigitsKeyListener {


        public MoneyValueFilter() {
            super(false, true);
        }

        private int digits = 2;

        public MoneyValueFilter setDigits(int d) {
            digits = d;
            return this;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            CharSequence out = super.filter(source, start, end, dest, dstart, dend);


            // if changed, replace the source
            if (out != null) {
                source = out;
                start = 0;
                end = out.length();
            }

            int len = end - start;

            // if deleting, source is empty
            // and deleting can't break anything
            if (len == 0) {
                return source;
            }

            //以点开始的时候，自动在前面添加0
            if (source.toString().equals(".") && dstart == 0) {
                return "0.";
            }
            //如果起始位置为0,且第二位跟的不是".",则无法后续输入
            if (!source.toString().equals(".") && dest.toString().equals("0")) {
                return "";
            }

            int dlen = dest.length();

            // Find the position of the decimal .
            for (int i = 0; i < dstart; i++) {
                if (dest.charAt(i) == '.') {
                    // being here means, that a number has
                    // been inserted after the dot
                    // check if the amount of digits is right
                    return (dlen - (i + 1) + len > digits) ?
                            "" :
                            new SpannableStringBuilder(source, start, end);
                }
            }

            for (int i = start; i < end; ++i) {
                if (source.charAt(i) == '.') {
                    // being here means, dot has been inserted
                    // check if the amount of digits is right
                    if ((dlen - dend) + (end - (i + 1)) > digits)
                        return "";
                    else
                        break;  // return new SpannableStringBuilder(source, start, end);
                }
            }


            // if the dot is after the inserted part,
            // nothing can break
            return new SpannableStringBuilder(source, start, end);
        }
    }

    public interface OnNumberChangeListener {
        void onChange();
    }

}