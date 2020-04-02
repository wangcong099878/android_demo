package the.one.gank.ui.presenter;

import android.util.Log;
import android.view.View;

import com.rxjava.rxlife.RxLife;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.ExceptionHelper;
import the.one.base.util.JsonUtil;
import the.one.gank.bean.Banner;
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


    public void getTodayData() {
        RxHttp.get(NetUrlConstant.TODAY)
                .asResponseOld(HomeBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.as(this))
                .subscribe(s -> {
                    //请求成功
                    getView().onTodayComplete(s);
                }, throwable -> {
                    //请求失败
                    showErrorPage(ExceptionHelper.handleNetworkException(throwable), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getView().showLoadingPage();
                            getTodayData();
                        }
                    });
                });
    }

    public void getBanner() {
        RxHttp.get("https://gank.io/api/v2/banner")
                .asResponseList(Banner.class)
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLife.as(this))
                .subscribe(s -> {
                    //请求成功
                    getView().onWelfareComplete(s.getData());
                }, throwable -> {
                    //请求失败
                    Log.e(TAG, "getBanner: fail" );
                    onFail(throwable.getLocalizedMessage());
                });

    }

}
