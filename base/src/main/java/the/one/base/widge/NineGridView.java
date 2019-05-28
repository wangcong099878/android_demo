package the.one.base.widge;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import the.one.base.base.activity.PhotoWatchActivity;
import the.one.base.util.GlideUtil;

/**
 * 描述：
 * 作者：HMY
 * 时间：2016/5/12
 */
public class NineGridView extends NineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;

    public NineGridView(Activity context) {
        super(context);
    }

    public NineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, final String url, final int parentWidth) {
//        Glide.with(mContext).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                int w = resource.getWidth();
//                int h = resource.getHeight();
//                int newW;
//                int newH;
//                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
//                    newW = parentWidth / 2;
//                    newH = newW * 5 / 3;
//                } else if (h < w) {//h:w = 2:3
//                    newW = parentWidth * 2 / 3;
//                    newH = newW * 2 / 3;
//                } else {//newH:h = newW :w
//                    newW = parentWidth / 2;
//                    newH = h * newW / w;
//                }
//                setOneImageLayoutParams(imageView,newW,newH);
//            }
//        });
        return true;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        GlideUtil.load(mContext,url,imageView);
    }

    @Override
    protected void onClickImage(int position, View view, String url, ArrayList<String> urlList) {
        PhotoWatchActivity.startThisActivity((Activity) mContext,view,urlList,position);
    }

}
