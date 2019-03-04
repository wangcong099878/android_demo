package the.one.demo.view;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

import the.one.base.util.GlideUtil;
import the.one.base.widge.NineGridLayout;
import the.one.base.widge.RatioImageView;

public class NineImageLayout extends NineGridLayout {


    public NineImageLayout(Context context) {
        super(context);
    }

    public NineImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(RatioImageView imageView, String url, int parentWidth) {
        GlideUtil.load(getContext(),url,imageView);
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        GlideUtil.load(getContext(),url,imageView);
    }

    @Override
    protected void onClickImage(int position, String url, List<String> urlList) {

    }
}
