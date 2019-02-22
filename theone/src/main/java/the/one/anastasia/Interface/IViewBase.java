package the.one.anastasia.Interface;

import android.graphics.drawable.Drawable;
import android.view.View;

import the.one.anastasia.base.BaseActivity;

public interface IViewBase {

    /**
     * 弹出进度条对话框
     *
     * @param text 文本内容
     */
    void showProgressDialog(String text);

    /**
     * 隐藏进度条对话框
     */
    void hideProgressDialog();

    /**
     * 显示加载界面
     */
    void showLoadingPage();

    /**
     * 显示内容界面
     */
    void showContentPage();
    /**
     * 显示为空的界面
     */
    void showEmptyPage();

    /**
     * 显示为空的界面
     * @param emptyImageDrawable 图片
     */
    void showEmptyPage(Drawable emptyImageDrawable);

    /**
     *
     * @param text
     */
    void showEmptyPage(String text);
    /**
     * 显示为空的界面
     * @param emptyImageDrawable 图片
     * @param emptyTextTitle 标题
     * @param emptyTextContent 内容
     */
    void showEmptyPage(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent);

    /**
     * 显示错误界面
     * @param listener 点击事件
     */
    void showErrorPage(View.OnClickListener listener);

    /**
     * 显示错误界面
     * @param errorDrawable 图片
     * @param listener  点击事件
     */
    void showErrorPage(Drawable errorDrawable, View.OnClickListener listener);

    /**
     *显示错误界面
     * @param errorImageDrawable 图片
     * @param errorTextTitle 标题
     * @param errorTextContent 内容
     * @param listener 点击事件
     */
    void showErrorPage(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent, String btnString,View.OnClickListener listener);

    /**
     *必须登录才能看到列表
     * @param errorImageDrawable 图片
     * @param errorTextTitle 标题
     * @param errorTextContent 内容
     * @param listener 点击事件
     */
    void showMustLogin(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent, View.OnClickListener listener);

    /**
     * 获取baseActivity
     * @return
     */
    BaseActivity getBaseActivity();

}
