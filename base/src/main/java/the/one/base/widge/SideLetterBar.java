package the.one.base.widge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import the.one.base.R;
import the.one.base.util.ColorUtils;

public class SideLetterBar extends View {
    public static  String[] b = {"↑", "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int choose = -1;
    private Paint paint = new Paint();
    private boolean showBg = false;
    private OnLetterChangedListener onLetterChangedListener;
    private CircleTextView overlay;

    private int mWidth;//宽度
    private int mHeight;//去除padding后的高度

    private int normalColor;
    private int selectColor;

    public SideLetterBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        normalColor= getResources().getColor(R.color.qmui_config_color_gray_6);
        selectColor= getResources().getColor(R.color.qmui_config_color_gray_3);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SideLetterBar(Context context) {
        this(context,null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h - getPaddingTop() - getPaddingBottom();
        mWidth = w;
    }

    /**
     * 设置悬浮的textview
     * @param overlay
     */


    public void setOverlay(CircleTextView overlay){
        this.overlay = overlay;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBg) {
            canvas.drawColor(Color.TRANSPARENT);
        }
        //画出索引字母
        int singleHeight = mHeight / b.length;
        for (int i = 0; i < b.length; i++) {
            paint.setTextSize(getResources().getDimension(R.dimen.side_letter_bar_letter_size));
            paint.setColor(showBg?selectColor:normalColor);
            paint.setAntiAlias(true);
            //如果选中颜色变深
            if (i == choose) {
                paint.setColor(selectColor);
                paint.setFakeBoldText(true);  //加粗
            }
            //计算相应字母的距离居中
            float xPos = mWidth / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();
        }

    }

//    设置中间显示的结果
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnLetterChangedListener listener = onLetterChangedListener;
        //相应高度的比例乘以字符数组长度就是我们要的字母
        final int c = (int) (y / getHeight() * b.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                        setOverLay(b[c]);
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                        setOverLay(b[c]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg = false;
                choose = -1;
                invalidate();
                if (overlay != null){
                    overlay.setVisibility(GONE);
                }
                break;
        }
        return true;
    }

    private void setOverLay(String s){
        if (overlay != null){
            overlay.setVisibility(VISIBLE);
            overlay.setText(s);
            overlay.setBackColor( ColorUtils.getBackgroundColorId(s, getContext()));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setBars(String[] bars){
        b = bars;
        invalidate();
    }

    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        this.onLetterChangedListener = onLetterChangedListener;
    }

    public interface OnLetterChangedListener {
        void onLetterChanged(String letter);
    }

}
