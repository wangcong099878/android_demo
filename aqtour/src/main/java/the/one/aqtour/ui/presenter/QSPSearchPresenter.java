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


import android.view.View;

import com.rxjava.rxlife.RxLife;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;
import the.one.aqtour.constant.QSPConstant;
import the.one.aqtour.util.QSPSoupUtil;
import the.one.base.Interface.OnError;

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
            .as(RxLife.asOnMain(this))
            .subscribe(s -> {
                //请求成功
                getView().onSuccess(QSPSoupUtil.parseSearchList(s));
            }, (OnError) error -> {
                //请求失败
               onFail(error.getErrorMsg(), new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       getSearchVideoList(search,page);
                   }
               });
            });

    }

}
