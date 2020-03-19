package the.one.aqtour.ui.presenter;

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


import com.rxjava.rxlife.RxLife;
import com.zhy.http.okhttp.callback.StringCallback;

import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.Call;
import rxhttp.wrapper.param.RxHttp;
import the.one.aqtour.constant.QSPConstant;
import the.one.aqtour.util.QSPSoupUtil;

/**
 * @author The one
 * @date 2020/1/8 0008
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class QSPSearchPresenter extends BaseVideoPresenter {

    public void getSearchVideoList(String search,int page){
        RxHttp.get(QSPConstant.getSearchUrl(search, page))
            .asString()
            .observeOn(AndroidSchedulers.mainThread())
            .as(RxLife.as(this))
            .subscribe(s -> {
                //请求成功
                getView().onSuccess(QSPSoupUtil.parseSearchList(s));
            }, throwable -> {
                //请求失败
               onFail(throwable.getMessage());
            });

    }

}
