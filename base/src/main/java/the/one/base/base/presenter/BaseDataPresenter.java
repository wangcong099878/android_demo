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

import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.List;

import the.one.base.Interface.IPageInfo;
import the.one.base.base.view.BaseDataView;

/**
 * @author The one
 * @date 2019/6/3 0003
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BaseDataPresenter<T> extends BasePresenter<BaseDataView<T>> {


    protected void onComplete(List<T> data) {
        onComplete(data, null);
    }

    protected void onComplete(List<T> data, IPageInfo pageInfoBean) {
        onComplete(data, pageInfoBean, "");
    }

    protected void onComplete(List<T> data, IPageInfo pageInfoBean, String emptyString) {
        onComplete(data, pageInfoBean, emptyString, "", null);
    }

    protected void onComplete(List<T> data, IPageInfo pageInfoBean, String emptyString, String btnString, View.OnClickListener listener) {
        if (isViewAttached())
            getView().onComplete(data);
    }

    protected void onFail(String error) {
        if (isViewAttached()) {
            getView().onFail(error);
        }
    }

}
