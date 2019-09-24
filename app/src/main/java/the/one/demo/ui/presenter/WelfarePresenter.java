package the.one.demo.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.base.view.BaseDataView;
import the.one.demo.NetUrlConstant;
import the.one.demo.bean.GankBean;


//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

/**
 * @author The one
 * @date 2019/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class WelfarePresenter extends BasePresenter<BaseDataView<GankBean>> {

    private static final String TAG = "WelfarePresenter";

    public void getData(final Context context,String url, final int page) {
        final String fakeRefer = NetUrlConstant.WELFARE_BASE_URL + url + "/";
        url = fakeRefer + "page/" + page;
        Log.e(TAG, "getData: "+url );
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (isViewAttached())
                    getView().onFail(e);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse: "+response );
                if (isViewAttached()) {
                    List<GankBean> gankBeans = new ArrayList<>();
                    Document doc = Jsoup.parse(response);
                    Element total = doc.select("div.postlist").first();
                    Elements items = total.select("li");
                    for (Element element : items) {
                        GankBean gankBean = new GankBean();
                        gankBean.setUrl(element.select("img").first().attr("data-original"));
                        gankBean.setLink(element.select("a[href]").attr("href"));
                        gankBean.setRefer(fakeRefer);
                        gankBeans.add(gankBean);
                    }
//                    getView().onComplete(gankBeans);
                    parseSize(context,gankBeans);
                }
            }
        });
    }

    private void parseSize(Context context, final List<GankBean> data) {
        for (int i = 0; i < data.size(); i++) {
            final int position = i;
            final GankBean item = data.get(i);
            GlideUrl glideUrl = new GlideUrl(item.getUrl(), new LazyHeaders.Builder()
                    .addHeader("Referer", item.getRefer())
                    .build());
            Glide.with(context).asBitmap().load(glideUrl).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    item.setWidth(resource.getWidth());
                    item.setHeight(resource.getHeight());
                    if (position == data.size() - 1) {
                        getView().onComplete(data);
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    if (position == data.size() - 1) {
                        getView().onComplete(data);
                    }
                    super.onLoadFailed(errorDrawable);
                }
            });
        }
    }
}
