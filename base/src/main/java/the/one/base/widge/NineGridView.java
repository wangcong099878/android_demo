package the.one.base.widge;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import the.one.base.R;
import the.one.base.util.ImagePreviewUtil;
import the.one.base.util.glide.GlideUtil;

/**
 * 描述：
 * 作者：HMY
 * 时间：2016/5/12
 */
public class NineGridView extends NineGridLayout {

    private static final String TAG = "NineGridView";

    protected final int MAX_W_H_RATIO = 3;

    protected int getMaxHeight() {
        return 650;
    }

    public NineGridView(Context context) {
        super(context);
    }

    public NineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, final String url, final int parentWidth) {
        Glide.with(mContext).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                int newW;
                int newH;
                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                    newW = parentWidth / 2;
                    newH = newW * 5 / 3;
                } else if (h < w) {//h:w = 2:3
                    newW = parentWidth * 2 / 3;
                    newH = newW * 2 / 3;
                } else {//newH:h = newW :w
                    newW = parentWidth / 2;
                    newH = h * newW / w;
                    if (newH > getMaxHeight()) {
                        newH = getMaxHeight();
                    }
                }
                setOneImageLayoutParams(imageView, newW, newH);
                Glide.with(getContext())
                        .load(url)
                        .transition(new DrawableTransitionOptions().crossFade())// 渐入渐出效果
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.pa_shape)
                                .override(newW, newH)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(imageView);
            }
        });
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        GlideUtil.load(mContext, url, imageView);
    }

    @Override
    protected void onClickImage(int position, View view, String url, ArrayList<String> urlList) {
        ImagePreviewUtil.start((Activity) mContext, urlList, position);
    }

}
