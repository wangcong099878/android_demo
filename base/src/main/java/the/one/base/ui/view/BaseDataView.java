package the.one.base.ui.view;

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

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public interface BaseDataView<T> extends BaseView {

    void onFirstLoading();

    void onComplete(List<T> data);
    void onComplete(List<T> data, IPageInfo pageInfoBean);
    void onComplete(List<T> data, IPageInfo pageInfoBean,String emptyString);
    void onComplete(List<T> data, IPageInfo pageInfoBean, String emptyString, String btnString, View.OnClickListener listener);

    void onFail(Exception e);
    void onFail(String errorMsg);

    void onNormal();


}
