package the.one.anastasia.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * 类名称：ReflectionTextView
 * 类描述：设置textView倒影效果
 *
 */

public class ReflectionTextView extends android.support.v7.widget.AppCompatTextView {

    private Matrix mMatrix;
    private Paint mPaint;

    public ReflectionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mMatrix = new Matrix();
        mMatrix.preScale(1, -1);
        //这句是关闭硬件加速，启用软件加速，如果报相关错误可以尝试注释这句代码，反正楼主注释掉这句话是启动不起来
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),
                             (int) (getMeasuredHeight() * 1.67));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        setDrawingCacheEnabled(true);
        Bitmap originalImage = getDrawingCache();
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                                                     0, Math.min(width, originalImage.getWidth()), height, mMatrix, false);
        Paint paint = new Paint();
        paint.setAlpha(100);
        canvas.drawBitmap(reflectionImage, 0, 0, paint);
        if (mPaint == null) {
            mPaint = new Paint();
            //阴影的效果可以自己根据需要设定
            LinearGradient shader = new LinearGradient(0, height/2, 0,
                                                       height, Color.WHITE, Color.GREEN,
                                                       Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }
        canvas.drawRect(0, height / 2f, width, height, mPaint);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start,
                                 int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        buildDrawingCache();
        postInvalidate();
        //每次更新TextView后遗留上次的残影，所以在这里每次刷新TextView后清楚DrawingCache
        destroyDrawingCache();
    }
}

