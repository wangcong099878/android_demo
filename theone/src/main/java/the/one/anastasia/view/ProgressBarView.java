package the.one.anastasia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tian on 2016/2/3.
 */
public class ProgressBarView extends View {
    private int barHeight=0;
    private int barWidth =0;
    private Paint grayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectF;
    private float progress=0;
    private RectF progressRectf;

    public ProgressBarView(Context context) {
        super(context);
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bluePaint.setAntiAlias(true);
        bluePaint.setColor(Color.parseColor("#29B6F6"));
        grayPaint.setAntiAlias(true);
        grayPaint.setColor(Color.parseColor("#E4E4E4"));
        barWidth =w;
        barHeight=h;
        rectF=new RectF(0,0, barWidth,barHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        progressRectf=new RectF(0,0, barWidth*progress/100f,barHeight);
        canvas.drawRect(rectF,grayPaint);
        canvas.drawRect(progressRectf,bluePaint);
    }
    public void setProgress(float progress)
    {
        this.progress=progress;
        invalidate();
    }
}
