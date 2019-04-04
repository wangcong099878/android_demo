package the.one.demo.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import the.one.base.base.activity.PhotoWatchActivity;
import the.one.base.util.GlideUtil;
import the.one.base.widge.NineGridLayout;
import the.one.base.widge.RatioImageView;

public class NineImageLayout extends NineGridLayout {
    protected static final int MAX_W_H_RATIO = 3;

    public NineImageLayout(Context context) {
        super(context);
    }

    public NineImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url,final int parentWidth) {
        GlideUtil.load(getContext(),url,imageView);
        return true;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        GlideUtil.load(getContext(),url,imageView);
    }

    @Override
    protected void onClickImage(int position, View view, String url, ArrayList<String> urlList) {
        PhotoWatchActivity.startThisActivity((Activity) getContext(),view,urlList,position);

    }

}
