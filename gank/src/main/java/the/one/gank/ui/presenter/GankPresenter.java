package the.one.gank.ui.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rxjava.rxlife.RxLife;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;
import the.one.base.base.presenter.BaseDataPresenter;
import the.one.base.util.ExceptionHelper;
import the.one.gank.bean.GankBean;
import the.one.gank.constant.NetUrlConstant;


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
public class GankPresenter extends BaseDataPresenter<GankBean> {


    @SuppressLint("CheckResult")
    public void getData(final Context context, final String type, final int page) {
        String url = NetUrlConstant.GANK_CATEGORY + type + "/" + NetUrlConstant.COUNT + "/" + page;

        RxHttp.get(url)
                .asString()
                .observeOn(AndroidSchedulers.mainThread()) //指定在主线程回调
                .as(RxLife.as(this))
                .subscribe(s -> {
                    //请求成功
                    onSuccess(s, type, context);
                }, throwable -> {
                    //请求失败
                    onFail(ExceptionHelper.handleNetworkException(throwable));
                });
    }

    private void onSuccess(String response, String type, Context context) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            boolean error = jsonObject.getBoolean("error");
            if (error) {
                getView().onFail("错误");
            } else {
                String personObject = jsonObject.getString("results");
                List<GankBean> itemData = new Gson().fromJson(personObject,
                        new TypeToken<List<GankBean>>() {
                        }.getType());
                if (!type.equals(NetUrlConstant.WELFARE)) {
                    onComplete(itemData, null, "无" + type + "相关数据");
                } else
                    parseSize(context, itemData);
            }

        } catch (JSONException e) {
            onFail("解析错误");
            e.printStackTrace();
        }
    }

    private void parseSize(Context context, final List<GankBean> data) {
        final List<GankBean> gankBeans = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            final int position = i;
            final GankBean item = data.get(i);
            Glide.with(context).asBitmap().load(item.getUrl()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    item.setWidth(resource.getWidth());
                    item.setHeight(resource.getHeight());
                    //只添加图片存在的
                    gankBeans.add(item);
                    check();
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    check();
                    super.onLoadFailed(errorDrawable);
                }

                private void check() {
                    if (position == data.size() - 1) {
                        onComplete(gankBeans, null);
                    }
                }
            });
        }
    }
}
