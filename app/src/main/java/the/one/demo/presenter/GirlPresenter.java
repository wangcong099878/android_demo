package the.one.demo.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.base.view.BaseDataView;
import the.one.demo.Constant;
import the.one.demo.model.GirlItemData;
import the.one.demo.util.JsoupUtil;


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
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class GirlPresenter extends BasePresenter<BaseDataView<GirlItemData>> {

    private static final String TAG = "GirlPresenter";

    public void getData(final Context context, int type, int page) {
        OkHttpUtils.get().url(Constant.GIRLS_URL).addParams("cid", type + "").addParams("pager_offset", page + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (isViewAttached())
                    getView().onFail(e);
            }

            @Override
            public void onResponse(String response, int id) {
                if (isViewAttached())
                    setSize(context, JsoupUtil.parseGirls(response));
            }
        });
    }

    private void setSize(Context context, final List<GirlItemData> data) {
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                final int position = i;
                final GirlItemData item = data.get(i);
                Glide.with(context).asBitmap().load(item.getUrl()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        item.setWidth(resource.getWidth());
                        item.setHeight(resource.getHeight());
                        if (position == data.size() - 1) {
                            getView().onComplete(data);
                        }
                    }
                });
            }
        }
    }
}
