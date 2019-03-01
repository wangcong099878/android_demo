package the.one.base.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;


/**
 * @author The one
 * @date 2018/8/7 0007
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class GlideUtil {

    public static void load(Context context,
                            String url,
                            ImageView imageView,
                            RequestOptions options) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void load(Context context,
                            String url,
                            ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存

        Glide.with(context)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())// 渐入渐出效果
                .apply(options)
                .into(imageView);
    }
}
