package the.one.base.base.view;

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

import the.one.net.entity.PageInfoBean;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public interface BaseDataView<T> extends BaseView {

    void onComplete(List<T> data);
    void onComplete(List<T> data, PageInfoBean pageInfoBean);
    void onComplete(List<T> data, PageInfoBean pageInfoBean,String emptyString);
    void onComplete(List<T> data, PageInfoBean pageInfoBean, String emptyString, String btnString, View.OnClickListener listener);

    void refresh();
    void onFail(Exception e);
    void onFail(String errorMsg);

    void onNormal();


}
