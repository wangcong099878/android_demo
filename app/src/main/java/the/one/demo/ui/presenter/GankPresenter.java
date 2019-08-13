package the.one.demo.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.base.view.BaseDataView;
import the.one.demo.Constant;
import the.one.demo.ui.bean.GankBean;
import the.one.net.entity.PageInfoBean;


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
public class GankPresenter extends BasePresenter<BaseDataView<GankBean>> {

    private static final String TAG = "WelfarePresenter";

    public void getData(final Context context, final String type, final int page) {
        Log.e(TAG, "getData: url = "+ Constant.GANK_CATEGORY + type + "/" + Constant.COUNT + "/" + page);
        OkHttpUtils.get().url(Constant.GANK_CATEGORY + type + "/" + Constant.COUNT + "/" + page).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: " );
                if (isViewAttached())
                    getView().onFail(e);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse: " );
                if (isViewAttached()) {
                    Log.e(TAG, "onResponse: isViewAttached" );
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
                            if (type != Constant.WELFARE) {
                                getView().onComplete(itemData,null,"无"+type+"相关数据");
                            } else
                                parseSize(context, itemData, null);
                        }

                    } catch (JSONException e) {
                        getView().onFail("解析错误");
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void parseSize(Context context, final List<GankBean> data,final PageInfoBean pageInfoBean) {
        for (int i = 0; i < data.size(); i++) {
            final int position = i;
            final GankBean item = data.get(i);
            Glide.with(context).asBitmap().load(item.getUrl()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    item.setWidth(resource.getWidth());
                    item.setHeight(resource.getHeight());
                    if (position == data.size() - 1) {
                        getView().onComplete(data, pageInfoBean);
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    if (position == data.size() - 1) {
                        getView().onComplete(data, pageInfoBean);
                    }
                    super.onLoadFailed(errorDrawable);
                }
            });
        }
    }
}
