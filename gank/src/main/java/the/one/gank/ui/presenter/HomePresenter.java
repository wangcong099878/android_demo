package the.one.gank.ui.presenter;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rxjava.rxlife.RxLife;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.ExceptionHelper;
import the.one.base.util.JsonUtil;
import the.one.gank.bean.GankBean;
import the.one.gank.bean.HomeBean;
import the.one.gank.constant.NetUrlConstant;
import the.one.gank.ui.view.HomeView;


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
public class HomePresenter extends BasePresenter<HomeView> {
    private static final String TAG = "HomePresenter";

    public static final int TYPE_TODAY = 0;
    public static final int TYPE_WELFARE = 1;

    public void getData(final int type) {
        String url = type == TYPE_TODAY ? NetUrlConstant.TODAY : NetUrlConstant.GANK_CATEGORY + NetUrlConstant.WELFARE + "/" + NetUrlConstant.COUNT + "/2";
        RxHttp.get(url)
                .asString()
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.as(this))
                .subscribe(s -> {
                    //请求成功
                    onResponse(s,type);
                }, throwable -> {
                    //请求失败
                    showErrorPage(ExceptionHelper.handleNetworkException(throwable), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getView().showLoadingPage();
                            getData(type);
                        }
                    });
                });
    }

    private void onResponse(String response,int type) {
        if (isViewAttached()) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                boolean error = jsonObject.getBoolean("error");
                if (error) {
                    getView().showErrorPage("请求失败");
                } else {
                    String result = jsonObject.getString("results");
                    if (type == TYPE_TODAY) {
                        HomeBean homeBean = JsonUtil.fromJson(result, HomeBean.class);
                        getView().onTodayComplete(homeBean);
                    } else {
                        List<GankBean> gankBeans = new Gson().fromJson(result,
                                new TypeToken<List<GankBean>>() {
                                }.getType());
                        getView().onWelfareComplete(gankBeans);
                    }
                }
            } catch (JSONException e) {
                showErrorPage("解析错误");
                e.printStackTrace();
            }
        }
    }
}
