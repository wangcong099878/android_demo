package the.one.base.https;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import rxhttp.HttpSender;


// 注意这个注解一定要加上，HttpGlideModule是自定义的名字
@GlideModule
public final class OkHttpGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        // 注意这里用我们刚才现有的Client实例传入即可
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(HttpSender.getOkHttpClient()));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
