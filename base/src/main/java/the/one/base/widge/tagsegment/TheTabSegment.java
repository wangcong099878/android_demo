package the.one.base.widge.tagsegment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.tab.QMUITabAdapter;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

public class TheTabSegment extends QMUITabSegment {

    public TheTabSegment(Context context) {
        super(context);
    }

    public TheTabSegment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TheTabSegment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected QMUITabAdapter createTabAdapter(ViewGroup tabParentView) {
        return new TheTabAdapter(this, tabParentView);
    }

}
