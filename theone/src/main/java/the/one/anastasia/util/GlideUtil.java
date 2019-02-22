package the.one.anastasia.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import the.one.anastasia.R;

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
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)// 异常占位图
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);// 禁用缓存功能

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
