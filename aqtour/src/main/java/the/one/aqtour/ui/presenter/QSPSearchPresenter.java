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


import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
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
        request(QSPConstant.getSearchUrl(search, page), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if(isViewAttached()){
                    getView().onFail(e);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                if(isViewAttached()){
                    getView().onSuccess(QSPSoupUtil.parseSearchList(response));
                }
            }
        });
    }

}
