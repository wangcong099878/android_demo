package the.one.gank.ui.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rxjava.rxlife.RxLife;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import rxhttp.wrapper.param.RxHttp;
import the.one.base.Interface.OnError;
import the.one.base.ui.presenter.BaseDataPresenter;
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


    public void getData(final Context context, final String type, final int page) {
        String url = NetUrlConstant.GANK_CATEGORY + type + "/" + NetUrlConstant.COUNT + "/" + page;
        Log.e(TAG, "getData: "+url );
        RxHttp.get(url)
                .asResponseOldList(GankBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(s -> {
                    //请求成功
                    if (!type.equals(NetUrlConstant.WELFARE)) {
                        onComplete(s, null, "无" + type + "相关数据");
                    } else
                        parseSize(context, s);
                }, (OnError) error -> {
                    //请求失败
                    onFail(error.getErrorMsg());
                });
    }

    /**
     * 获取图片的宽高，剔除掉图片不存在的
     * @param context
     * @param data
     */
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
