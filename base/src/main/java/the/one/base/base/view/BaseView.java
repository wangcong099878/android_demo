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

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public interface BaseView {

    /**
     * 显示加载弹窗
     *
     * @param msg
     */
    void showLoadingDialog(String msg);
    /**
     * 关闭加载弹窗
     *
     */
    void hideLoadingDialog();

    /**
     * 显示进度弹窗
     *
     * @param tips
     */
    void showProgressDialog(String msg);

    /**
     * 显示进度弹窗
     *
     * @param percent
     */
    void showProgressDialog(int percent);

    /**
     * 显示进度弹窗
     *
     * @param percent
     */
    void showProgressDialog(int percent, int total);

    /**
     * 显示进度弹窗
     *
     * @param percent
     */
    void showProgressDialog(int percent, int total,String msg);

    /**
     * 关闭进度弹窗
     */
    void hideProgressDialog();

    /**
     * 显示提示
     *
     * @param msg
     */
    void showToast(String msg);

    /**
     * 显示内容层
     */
    void showContentPage();

    /**
     * 显示正在加载view
     */
    void showLoadingPage();

    /**
     * 显示空白层
     */
    void showEmptyPage(String title);

    /**
     * 显示空白层
     */
    void showEmptyPage(String title, View.OnClickListener listener);
    /**
     * 显示空白层
     */
    void showEmptyPage(String title, String btnString, View.OnClickListener listener);

    /**
     * 显示空白层
     */
    void showEmptyPage(String title, String content, String btnString, View.OnClickListener listener);

    /**
     * 显示空白层
     */
    void showEmptyPage(Drawable drawable, String title, String content, String btnString, View.OnClickListener listener);

    /**
     * 显示空白层
     */
    void showErrorPage(String title);

    /**
     * 显示空白层
     */
    void showErrorPage(String title, View.OnClickListener listener);
    /**
     * 显示空白层
     */
    void showErrorPage(String title, String btnString, View.OnClickListener listener);

    /**
     * 显示空白层
     */
    void showErrorPage(String title, String content, String btnString, View.OnClickListener listener);

    /**
     * 显示错误层
     */
    void showErrorPage(Drawable drawable, String title, String content, String btnString, View.OnClickListener listener);

    void showSuccessTips(String msg);

    void showFailTips(String msg);
}
