package the.one.base.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import the.one.base.R;

/**
 * @author The one
 * @date 2018/5/3 0003
 * @describe 对 {@link QMUIDialog} 的使用做了一些简单封装
 * @email 625805189@qq.com
 * @remark 如需使用 {@link AppMourningThemeUtil},但是不是从此工具了创建的{@link QMUIDialog}或者其他{@link Dialog}，需要手动调用{@link #notifyTheme(Dialog)}
 */

public class QMUIDialogUtil {

    /**
     * 如果使用EditTextDialog需要输入是小数模式，则用这个
     */
    public static final int TYPE_NUMBER_DECIMAL = 8194;

    /**
     * 由于QMUIDialog不好处理，所以只能这样一个个处理了
     * @param dialog
     */
    public static void notifyTheme(Dialog dialog) {
        if (null != dialog)
            AppMourningThemeUtil.notify(dialog.getWindow());
    }

    public static QMUIDialog showSimpleDialog(final Context context, final String title, final String content) {
        return   showPositiveDialog(context, title, content, "", null, "确认", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 消息类型对话框
     *
     * @param context          上下文
     * @param title            标题
     * @param content          内容
     * @param rightBtnListener 右Btn监听
     * @return
     */
    public static QMUIDialog showSimpleDialog(final Context context, final String title, final String content, QMUIDialogAction.ActionListener rightBtnListener) {
        return   showPositiveDialog(context, title, content, "取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        }, "确定", rightBtnListener);
    }

    public static QMUIDialog showPositiveDialog(final Context context, final String title, final String content, final String btnLeftString, final QMUIDialogAction.ActionListener leftBtnListener, final String btnRightString, final QMUIDialogAction.ActionListener rightBtnListener) {
        QMUIDialog.MessageDialogBuilder builder = new QMUIDialog.MessageDialogBuilder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(content)) {
            builder.setMessage(content);
        }
        if (!TextUtils.isEmpty(btnLeftString)) {
            builder.addAction(btnLeftString, leftBtnListener);
        }
        if (!TextUtils.isEmpty(btnRightString)) {
            builder.addAction(0, btnRightString, QMUIDialogAction.ACTION_PROP_NEGATIVE, rightBtnListener);
        }
        QMUIDialog dialog = builder.show();
        notifyTheme(dialog);
        return dialog;
    }


    public static QMUIDialog showNegativeDialog(final Context context, final String title, final CharSequence content, final String btnLeftString, final String btnRightString, final QMUIDialogAction.ActionListener rightBtnListener) {
        return showNegativeDialog(context, title, content, btnLeftString, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        }, btnRightString, rightBtnListener);
    }

    /**
     * @param context
     * @param title
     * @param content
     * @param btnLeftString
     * @param leftBtnListener
     * @param btnRightString
     * @param rightBtnListener
     */
    public static QMUIDialog showNegativeDialog(final Context context, final String title, final CharSequence content, final CharSequence btnLeftString, final QMUIDialogAction.ActionListener leftBtnListener, final String btnRightString, final QMUIDialogAction.ActionListener rightBtnListener) {
        QMUIDialog dialog = new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(content)
                .addAction(btnLeftString, leftBtnListener)
                .addAction(0, btnRightString, QMUIDialogAction.ACTION_PROP_NEGATIVE, rightBtnListener)
                .show();
        notifyTheme(dialog);
        return dialog;
    }

    public static QMUIDialog showMenuDialog(Context context, String title, List<String> list, DialogInterface.OnClickListener listener) {
        return showMenuDialog(context, title, list2Array(list), listener);
    }

    public static QMUIDialog showMenuDialog(Context context, List<String> list, DialogInterface.OnClickListener listener) {
        return showMenuDialog(context, "", list2Array(list), listener);
    }

    public static QMUIDialog showMenuDialog(Context context, String[] items, DialogInterface.OnClickListener listener) {
        return showMenuDialog(context, "", items, listener);
    }

    /**
     * 单选菜单类型对话框
     *
     * @param context
     * @param items
     * @param listener
     * @return
     */
    public static QMUIDialog showMenuDialog(Context context, String title, String[] items, DialogInterface.OnClickListener listener) {
        QMUIDialog dialog = new QMUIDialog.MenuDialogBuilder(context)
                .setTitle(title)
                .addItems(items, listener).show();
        notifyTheme(dialog);
        return dialog;
    }

    /**
     * 单选菜单类型对话框
     *
     * @param context
     * @param list
     * @param checkedIndex
     * @param listener
     */
    public static QMUIDialog showSingleChoiceDialog(Context context, List<String> list, final int checkedIndex, DialogInterface.OnClickListener listener) {
        return showSingleChoiceDialog(context, list2Array(list), checkedIndex, listener);
    }

    private static String[] list2Array(List<String> list) {
        String[] items = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            items[i] = list.get(i);
        }
        return items;
    }

    /**
     * 单选菜单类型对话框
     *
     * @param context
     * @param items
     * @param checkedIndex
     * @param listener
     */
    public static QMUIDialog showSingleChoiceDialog(Context context, CharSequence[] items, final int checkedIndex, DialogInterface.OnClickListener listener) {
        QMUIDialog dialog = new QMUIDialog.CheckableDialogBuilder(context)
                .setCheckedIndex(checkedIndex)
                .addItems(items, listener)
                .show();
        notifyTheme(dialog);
        return dialog;
    }

    /**
     * 多选菜单类型对话框
     *
     * @param context
     * @param items
     * @param checkedItems
     * @param rightBtnString
     * @param onMultiChoiceConfirmClickListener
     */
    public static QMUIDialog showMultiChoiceDialog(Context context, final String[] items, final int[] checkedItems, String rightBtnString, final OnMultiChoiceConfirmClickListener onMultiChoiceConfirmClickListener) {
        return showMultiChoiceDialog(context, items, checkedItems, "取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                }
                , rightBtnString, onMultiChoiceConfirmClickListener);
    }

    /**
     * 多选菜单类型对话框
     *
     * @param context
     * @param items
     * @param checkedItems
     * @param rightBtnString
     * @param onMultiChoiceConfirmClickListener
     */
    public static QMUIDialog showMultiChoiceDialog(Context context, final String[] items, final int[] checkedItems,String title, String rightBtnString, final OnMultiChoiceConfirmClickListener onMultiChoiceConfirmClickListener) {
        return showMultiChoiceDialog(context, items, checkedItems,title, "取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                }
                , rightBtnString, onMultiChoiceConfirmClickListener);
    }

    /**
     * 多选菜单类型对话框
     *
     * @param context
     * @param items
     * @param checkedItems
     * @param leftBtnString
     * @param leftBtnListener
     * @param rightBtnString
     * @param onMultiChoiceConfirmClickListener
     */
    public static QMUIDialog showMultiChoiceDialog(Context context, final String[] items, final int[] checkedItems, String leftBtnString, QMUIDialogAction.ActionListener leftBtnListener, String rightBtnString, final OnMultiChoiceConfirmClickListener onMultiChoiceConfirmClickListener) {
        return showMultiChoiceDialog(context,items,checkedItems,"",leftBtnString,leftBtnListener,rightBtnString,onMultiChoiceConfirmClickListener);
    }

    /**
     * 多选菜单类型对话框
     *
     * @param context
     * @param items
     * @param checkedItems
     * @param leftBtnString
     * @param leftBtnListener
     * @param rightBtnString
     * @param onMultiChoiceConfirmClickListener
     */
    public static QMUIDialog showMultiChoiceDialog(Context context, final String[] items, final int[] checkedItems,String title, String leftBtnString, QMUIDialogAction.ActionListener leftBtnListener, String rightBtnString, final OnMultiChoiceConfirmClickListener onMultiChoiceConfirmClickListener) {
        final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(context)
                .setTitle(title)
                .setCheckedItems(checkedItems)
                .addItems(items, null);
        builder.addAction(leftBtnString, leftBtnListener);
        builder.addAction(rightBtnString, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                onMultiChoiceConfirmClickListener.getCheckedItemIndexes(dialog, builder.getCheckedItemIndexes());
            }
        });
        QMUIDialog dialog = builder.show();
        notifyTheme(dialog);
        return dialog;
    }

    /**
     * 多选菜单类型对话框(item 数量很多)
     *
     * @param context
     * @param items
     * @param checkedItems
     * @param OnItemClickListener
     * @param rightString
     * @param onMultiChoiceConfirmClickListener
     */
    public static QMUIDialog showNumerousMultiChoiceDialog(Context context, final String[] items, int[] checkedItems, DialogInterface.OnClickListener OnItemClickListener, String rightString, final OnMultiChoiceConfirmClickListener onMultiChoiceConfirmClickListener) {
        return showNumerousMultiChoiceDialog(context, items, checkedItems, OnItemClickListener, "取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        }, rightString, onMultiChoiceConfirmClickListener);
    }

    /**
     * 多选菜单类型对话框(item 数量很多)
     *
     * @param context
     * @param items
     * @param checkedItems
     * @param OnItemClickListener
     * @param leftBtnString
     * @param leftClickListener
     * @param rightString
     */
    public static QMUIDialog showNumerousMultiChoiceDialog(Context context, final String[] items, int[] checkedItems, DialogInterface.OnClickListener OnItemClickListener, String leftBtnString, QMUIDialogAction.ActionListener leftClickListener, String rightString, final OnMultiChoiceConfirmClickListener onMultiChoiceConfirmClickListener) {
        final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(context)
                .setCheckedItems(checkedItems)
                .addItems(items, OnItemClickListener);
        builder.addAction(leftBtnString, leftClickListener);
        builder.addAction(0, rightString, QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                onMultiChoiceConfirmClickListener.getCheckedItemIndexes(dialog, builder.getCheckedItemIndexes());
            }
        });
        QMUIDialog dialog = builder.show();
        notifyTheme(dialog);
        return dialog;
    }

    public interface OnMultiChoiceConfirmClickListener {
        void getCheckedItemIndexes(QMUIDialog dialog, int[] checkedItems);
    }

    /**
     * 带输入框的对话框
     *
     * @param context
     * @param title
     * @param hint
     * @param listener
     */

    public static QMUIDialog showEditTextDialog(final Context context, String title,String content, String hint, final OnEditTextConfirmClickListener listener) {
        return showEditTextDialog(context, title,content, hint, "取消", "确定", listener);
    }

    public static QMUIDialog showEditTextDialog(final Context context, String title, String hint, String leftText, String rightText, final OnEditTextConfirmClickListener listener) {
        return showEditTextDialog(context, title,"", hint, leftText, rightText, listener);
    }

    public static QMUIDialog showEditTextDialog(final Context context, String title,String content, String hint, String leftText, String rightText, final OnEditTextConfirmClickListener listener) {
        return showEditTextDialog(context, InputType.TYPE_CLASS_TEXT, title,content, hint, leftText, rightText, listener);
    }

    public static QMUIDialog showEditTextDialog(final Context context, int inputType, String title, String hint, String leftText, String rightText, final OnEditTextConfirmClickListener listener) {
        return showEditTextDialog(context, inputType, title,"", hint, leftText, rightText, listener);
    }

    /**
     * 带输入框的对话框
     *
     * @param context
     * @param title
     * @param hint
     * @param leftText
     * @param rightText
     * @param listener
     */
    public static QMUIDialog showEditTextDialog(final Context context, int inputType, String title,String content, String hint, String leftText, String rightText, final OnEditTextConfirmClickListener listener) {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context);
        QMUIDialog dialog = builder.setTitle(title)
                .setDefaultText(content)
                .setPlaceholder(hint)
                .setInputType(inputType)
                .addAction(leftText, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        CharSequence text = builder.getEditText().getText();
                        listener.getEditText(dialog, text.toString(), index);
                    }
                })
                .addAction(0, rightText, QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        listener.getEditText(dialog, text.toString(), index);
                    }
                }).show();
        if(!TextUtils.isEmpty(content)){
            builder.getEditText().setSelection(content.length());
        }
        notifyTheme(dialog);
        return dialog;
    }

    public interface OnEditTextConfirmClickListener {
        void getEditText(QMUIDialog dialog, String content, int index);
    }

    /**
     * 消息类型对话框 (很长文案)
     *
     * @param context
     * @param title
     * @param content
     * @param confirm
     * @param listener
     */
    public static QMUIDialog showLongMessageDialog(Context context, String title, String content,String cancle,QMUIDialogAction.ActionListener cancelListener, String confirm, QMUIDialogAction.ActionListener listener) {
        QMUIDialog dialog = new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(content)
                .addAction(cancle, cancelListener)
                .addAction(0, confirm, QMUIDialogAction.ACTION_PROP_NEGATIVE, listener)
                .show();
        notifyTheme(dialog);
        return dialog;
    }

    /**
     * 消息类型对话框 (很长文案)
     *
     * @param context
     * @param title
     * @param content
     * @param confirm
     * @param listener
     */
    public static QMUIDialog showLongMessageDialog(Context context, String title, String content, String confirm, QMUIDialogAction.ActionListener listener) {
        QMUIDialog dialog = new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(content)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, confirm, QMUIDialogAction.ACTION_PROP_NEGATIVE, listener)
                .show();
        notifyTheme(dialog);
        return dialog;
    }


    /**
     * 高度适应键盘升降的对话框
     *
     * @param context
     * @param title
     * @param content
     * @param leftString
     * @param leftListener
     * @param rightString
     * @param rightListener
     */
    public static void showAutoDialog(Context context, String title, String content, String leftString, QMUIDialogAction.ActionListener leftListener, String rightString, QMUIDialogAction.ActionListener rightListener) {
        QMAutoTestDialogBuilder autoTestDialogBuilder = (QMAutoTestDialogBuilder) new QMAutoTestDialogBuilder(context, title, content)
                .addAction(leftString, leftListener)
                .addAction(0, rightString, QMUIDialogAction.ACTION_PROP_NEGATIVE, rightListener);
        autoTestDialogBuilder.show();
        QMUIKeyboardHelper.showKeyboard(autoTestDialogBuilder.getEditText(), true);
    }

    static class QMAutoTestDialogBuilder extends QMUIDialog.AutoResizeDialogBuilder {
        private EditText mEditText;
        private String mTitle;
        private String mContent;

        public QMAutoTestDialogBuilder(Context context, String title, String content) {
            super(context);
            mTitle = title;
            mContent = content;
        }

        public EditText getEditText() {
            return mEditText;
        }

        @Override
        public View onBuildContent(@NonNull QMUIDialog dialog, @NonNull Context context) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int padding = QMUIDisplayHelper.dp2px(context, 20);
            layout.setPadding(padding, padding, padding, padding);
            mEditText = new EditText(context);
//            QMUIViewHelper.setBackgroundKeepingPadding(mEditText, QMUIResHelper.getAttrDrawable(mContext, R.attr.qmui_list_item_bg_with_border_bottom));
            mEditText.setHint(mTitle);
            LinearLayout.LayoutParams editTextLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIDisplayHelper.dpToPx(50));
            editTextLP.bottomMargin = QMUIDisplayHelper.dp2px(context, 15);
            mEditText.setLayoutParams(editTextLP);
            layout.addView(mEditText);
            TextView content = new TextView(context);
            content.setLineSpacing(QMUIDisplayHelper.dp2px(context, 4), 1.0f);
            content.setText(mContent);
            content.setTextColor(ContextCompat.getColor(context, R.color.qmui_config_color_gray_1));
            content.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(content);
            return layout;
        }

    }

    public static void showTipsDialog(Context context, int Type, String tips, OnTipsDialogDismissListener listener) {
        showTips(new QMUITipDialog.Builder(context)
                .setIconType(Type)
                .setTipWord(tips)
                .create(), listener);
    }

    public static void SuccessTipsDialog(Context context, String tips) {
        SuccessTipsDialog(context, tips, null);
    }

    public static void SuccessTipsDialog(Context context, String tips, OnTipsDialogDismissListener listener) {
        showTips(new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(tips)
                .create(), listener);
    }

    public static void FailTipsDialog(Context context, String tips) {
        FailTipsDialog(context, tips, null);
    }

    public static void FailTipsDialog(Context context, String tips, OnTipsDialogDismissListener listener) {
        if(null == tips) tips = "错误";
        showTips(new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(tips)
                .create(), listener);
    }

    public static QMUITipDialog LoadingTipsDialog(Context context, String tips) {
        return new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(tips)
                .create();
    }

    public static QMUITipDialog WarningTipsDialog(Context context, String tips) {
        return new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord(tips)
                .create();
    }

    public static void showTips(final QMUITipDialog dialog, final OnTipsDialogDismissListener listener) {
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                if (null != listener)
                    listener.onDismiss();
            }
        }, 1000);

    }

    public interface OnTipsDialogDismissListener {
        void onDismiss();
    }
}
