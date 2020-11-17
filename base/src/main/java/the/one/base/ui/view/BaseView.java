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
    void showLoadingDialog(CharSequence msg);
    /**
     * 关闭加载弹窗
     *
     */
    void hideLoadingDialog();

    /**
     * 显示进度弹窗
     *
     * @param msg
     */
    void showProgressDialog(CharSequence msg);

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
    void showProgressDialog(int percent, int total,CharSequence msg);

    /**
     * 关闭进度弹窗
     */
    void hideProgressDialog();

    /**
     * 显示提示
     *
     * @param msg
     */
    void showToast(CharSequence msg);

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
    void showEmptyPage(CharSequence title);

    /**
     * 显示空白层
     */
    void showEmptyPage(CharSequence title, View.OnClickListener listener);
    /**
     * 显示空白层
     */
    void showEmptyPage(CharSequence title, CharSequence btnString, View.OnClickListener listener);

    /**
     * 显示空白层
     */
    void showEmptyPage(CharSequence title, CharSequence content, CharSequence btnString, View.OnClickListener listener);

    /**
     * 显示空白层
     */
    void showEmptyPage(Drawable drawable, CharSequence title, CharSequence content, CharSequence btnString, View.OnClickListener listener);

    /**
     * 显示空白层
     */
    void showErrorPage(CharSequence title);

    /**
     * 显示空白层
     */
    void showErrorPage(CharSequence title, View.OnClickListener listener);
    /**
     * 显示空白层
     */
    void showErrorPage(CharSequence title, CharSequence btnString, View.OnClickListener listener);

    /**
     * 显示空白层
     */
    void showErrorPage(CharSequence title, CharSequence content, CharSequence btnString, View.OnClickListener listener);

    /**
     * 显示错误层
     */
    void showErrorPage(Drawable drawable, CharSequence title, CharSequence content, CharSequence btnString, View.OnClickListener listener);

    /**
     * 显示成功提示
     */
    void showSuccessTips(CharSequence msg);

    /**
     * 显示成功后退出
     * @param tips
     */
    void showSuccessExit(CharSequence tips);

    /**
     * 显示成功后退出
     * @param tips
     */
    void showSuccessExit(CharSequence tips,int type);

    /**
     * 显示错误提示
     */
    void showFailTips(CharSequence msg);

    /**
     * 关闭
     */
    void finish();

    /**
     *
     * @param c 目标Activity
     */
    void startActivity(Class c);

    /**
     *
     * @param c 目标Activity
     * @param finishCurrent 是否关闭当前
     */
    void startActivity(Class c,boolean finishCurrent);
}
