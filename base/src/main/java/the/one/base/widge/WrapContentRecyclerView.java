package the.one.base.widge;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class WrapContentRecyclerView extends RecyclerView {


    private int mMaxHeight = Integer.MAX_VALUE >> 2;

    public WrapContentRecyclerView(@NonNull Context context) {
        super(context);
    }

    public WrapContentRecyclerView(@NonNull Context context, int height) {
        super(context);
        this.mMaxHeight = height;
    }

    public WrapContentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapContentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMaxHeight(int maxHeight) {
        if(mMaxHeight != maxHeight){
            mMaxHeight = maxHeight;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(mMaxHeight,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
