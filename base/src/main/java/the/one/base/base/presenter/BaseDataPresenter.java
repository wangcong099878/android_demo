package the.one.base.base.presenter;

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

import java.util.List;

import the.one.base.base.view.BaseDataView;
import the.one.net.callback.ListCallback;
import the.one.net.entity.PageInfoBean;

/**
 * @author The one
 * @date 2019/6/3 0003
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BaseDataPresenter<T> extends BasePresenter<BaseDataView<T>> {

    protected ListCallback<List<T>> callback = new ListCallback<List<T>>() {
        @Override
        public void onSuccess(List<T> response, String msg, PageInfoBean pageInfoBean) {
            if(isViewAttached()){
                getView().onComplete(response,pageInfoBean);
            }
        }

        @Override
        public void onFailure(int resultCode, String errorMsg) {
            if(isViewAttached()){
                getView().onFail(errorMsg);
            }
        }
    };

}
