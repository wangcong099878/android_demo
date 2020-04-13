package the.one.base.ui.presenter;

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

import java.util.List;

import the.one.base.Interface.IPageInfo;
import the.one.base.ui.view.BaseDataView;

/**
 * @author The one
 * @date 2019/6/3 0003
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BaseDataPresenter<T> extends BasePresenter<BaseDataView<T>> {


    protected void onComplete(List<T> data) {
        if (isViewAttached())
            getView().onComplete(data);
    }

    protected void onComplete(List<T> data, IPageInfo pageInfoBean) {
        if (isViewAttached())
            getView().onComplete(data, pageInfoBean);
    }

    protected void onComplete(List<T> data, IPageInfo pageInfoBean, String emptyString) {
        if (isViewAttached())
            getView().onComplete(data, pageInfoBean, emptyString);
    }

    protected void onComplete(List<T> data, IPageInfo pageInfoBean, String emptyString, String btnString, View.OnClickListener listener) {
        if (isViewAttached())
            getView().onComplete(data, pageInfoBean, emptyString, btnString, listener);
    }

    protected void onFail(String error) {
        if (isViewAttached()) {
            getView().onFail(error);
        }
    }

}
