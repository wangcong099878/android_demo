package the.one.base.widge.tagsegment;

import android.view.ViewGroup;

import com.daimajia.androidanimations.library.YoYo;
import com.qmuiteam.qmui.widget.tab.QMUITabAdapter;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;
import com.qmuiteam.qmui.widget.tab.QMUITabView;

import the.one.base.constant.Anim;

public class TheTabAdapter extends QMUITabAdapter {

    public TheTabAdapter(QMUITabSegment tabSegment, ViewGroup parentView) {
        super(tabSegment, parentView);
    }

    @Override
    protected QMUITabView createView(ViewGroup parentView) {
        return new QMUITabView(parentView.getContext());
    }

    @Override
    public void onClick(QMUITabView view) {
        super.onClick(view);
        YoYo.with(Anim.ZoomIn.getYoyo()).duration(200).playOn(view);
    }
}
