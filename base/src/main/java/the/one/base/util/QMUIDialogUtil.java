package the.one.base.util;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

import the.one.base.R;

/**
 * @author The one
 * @date 2018/5/3 0003
 * @describe QMUI - Dialog
 * @email 625805189@qq.com
 * @remark
 */

public class QMUIDialogUtil {

    /**
     * 如果使用EditTextDialog需要输入是小数模式，则用这个
     */
    public static final int TYPE_NUMBER_DECIMAL = 8194;

    public static void showSimpleDialog(final Context context, final String title, final String content) {
        showPositiveDialog(context, title, content,"",null,"确认", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        } );
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
    public static void showSimpleDialog(final Context context, final String title, final String content, QMUIDialogAction.ActionListener rightBtnListener) {

        showPositiveDialog(context, title, content, "取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        }, "确定", rightBtnListener);
    }

    public static void showPositiveDialog(final Context context, final String title, final String content, final String btnLeftString, final QMUIDialogAction.ActionListener leftBtnListener, final String btnRightString, final QMUIDialogAction.ActionListener rightBtnListener){
        new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(content)
                .addAction(btnLeftString, leftBtnListener)
                .addAction(0,btnRightString, QMUIDialogAction.ACTION_PROP_NEGATIVE, rightBtnListener).show();
    }


    public static void showNegativeDialog(final Context context, final String title, final String content, final String btnLeftString, final String btnRightString, final QMUIDialogAction.ActionListener rightBtnListener){
        showNegativeDialog(context, title, content, btnLeftString, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        },btnRightString,rightBtnListener);
    }

    /**
     *
     * @param context
     * @param title
     * @param content
     * @param btnLeftString
     * @param leftBtnListener
     * @param btnRightString
     * @param rightBtnListener
     */
    public static void showNegativeDialog(final Context context, final String title, final String content, final String btnLeftString, final QMUIDialogAction.ActionListener leftBtnListener, final String btnRightString, final QMUIDialogAction.ActionListener rightBtnListener){
        new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(content)
                .addAction(btnLeftString, leftBtnListener)
                .addAction(0, btnRightString, QMUIDialogAction.ACTION_PROP_NEGATIVE, rightBtnListener)
                .show();
    }

    public static QMUIDialog showMenuDialog(Context context, String title, List<String> list, DialogInterface.OnClickListener listener) {
        return showMenuDialog(context,title,list2Array(list),listener);
    }
    public static QMUIDialog showMenuDialog(Context context, List<String> list, DialogInterface.OnClickListener listener) {
       return showMenuDialog(context,"",list2Array(list),listener);
    }

    public static QMUIDialog showMenuDialog(Context context, String[] items, DialogInterface.OnClickListener listener) {
        return showMenuDialog(context,"",items,listener);
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
        return new QMUIDialog.MenuDialogBuilder(context)
                .setTitle(title)
                .addItems(items, listener).show();
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
        return showSingleChoiceDialog(context,list2Array(list),checkedIndex,listener);
    }

    private static String[] list2Array(List<String> list){
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
    public static QMUIDialog showSingleChoiceDialog(Context context, String[] items, final int checkedIndex, DialogInterface.OnClickListener listener) {
        return new QMUIDialog.CheckableDialogBuilder(context)
                .setCheckedIndex(checkedIndex)
                .addItems(items, listener)
                .show();
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
    public static void showMultiChoiceDialog(Context context, final String[] items, final int[] checkedItems, String rightBtnString, final OnMultiChoiceConfirmClickListener onMultiChoiceConfirmClickListener) {
        showMultiChoiceDialog(context, items, checkedItems, "取消", new QMUIDialogAction.ActionListener() {
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
    public static void showMultiChoiceDialog(Context context, final String[] items, final int[] checkedItems, String leftBtnString, QMUIDialogAction.ActionListener leftBtnListener, String rightBtnString, final OnMultiChoiceConfirmClickListener onMultiChoiceConfirmClickListener) {
        final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(context)
                .setCheckedItems(checkedItems)
                .addItems(items, null);
        builder.addAction(leftBtnString, leftBtnListener);
        builder.addAction(rightBtnString, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                onMultiChoiceConfirmClickListener.getCheckedItemIndexes(dialog,builder.getCheckedItemIndexes());
            }
        });
        builder.show();
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
    public static void showNumerousMultiChoiceDialog(Context context, final String[] items, int[] checkedItems, DialogInterface.OnClickListener OnItemClickListener, String rightString, final OnMultiChoiceConfirmClickListener onMultiChoiceConfirmClickListener) {
        showNumerousMultiChoiceDialog(context, items, checkedItems, OnItemClickListener, "取消", new QMUIDialogAction.ActionListener() {
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
    public static void showNumerousMultiChoiceDialog(Context context, final String[] items, int[] checkedItems, DialogInterface.OnClickListener OnItemClickListener, String leftBtnString, QMUIDialogAction.ActionListener leftClickListener, String rightString, final OnMultiChoiceConfirmClickListener onMultiChoiceConfirmClickListener) {
        final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(context)
                .setCheckedItems(checkedItems)
                .addItems(items, OnItemClickListener);
        builder.addAction(leftBtnString, leftClickListener);
        builder.addAction( 0,rightString, QMUIDialogAction.ACTION_PROP_NEGATIVE,new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                onMultiChoiceConfirmClickListener.getCheckedItemIndexes(dialog,builder.getCheckedItemIndexes());
            }
        });
        builder.show();
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

    public static void showEditTextDialog(final Context context, String title, String hint, final OnEditTextConfirmClickListener listener) {
        showEditTextDialog(context, title, hint, "取消", "确定", listener);
    }

    public static void showEditTextDialog(final Context context, String title, String hint, String leftText, String rightText, final OnEditTextConfirmClickListener listener){
        showEditTextDialog(context,InputType.TYPE_CLASS_TEXT,title,hint,leftText,rightText,listener);
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
    public static void showEditTextDialog(final Context context,int inputType, String title, String hint, String leftText, String rightText, final OnEditTextConfirmClickListener listener) {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context);
        builder.setTitle(title)
                .setPlaceholder(hint)
                .setInputType(inputType)
                .addAction(leftText, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0,rightText, QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        listener.getEditText(dialog, text.toString(), index);
                    }
                }).show();
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
    public static void showLongMessageDialog(Context context, String title, String content, String confirm, QMUIDialogAction.ActionListener listener) {
        new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(content)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0,confirm, QMUIDialogAction.ACTION_PROP_NEGATIVE, listener)
                .show();
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
                .addAction(0,rightString, QMUIDialogAction.ACTION_PROP_NEGATIVE, rightListener);
        autoTestDialogBuilder.show();
        QMUIKeyboardHelper.showKeyboard(autoTestDialogBuilder.getEditText(), true);
    }

    static class QMAutoTestDialogBuilder extends QMUIDialog.AutoResizeDialogBuilder {
        private Context mContext;
        private EditText mEditText;
        private String mTitle;
        private String mContent;

        public QMAutoTestDialogBuilder(Context context, String title, String content) {
            super(context);
            mContext = context;
            mTitle = title;
            mContent = content;
        }

        public EditText getEditText() {
            return mEditText;
        }

        @Override
        public View onBuildContent(QMUIDialog dialog, ScrollView parent) {
            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int padding = QMUIDisplayHelper.dp2px(mContext, 20);
            layout.setPadding(padding, padding, padding, padding);
            mEditText = new EditText(mContext);
            QMUIViewHelper.setBackgroundKeepingPadding(mEditText, QMUIResHelper.getAttrDrawable(mContext, R.attr.qmui_list_item_bg_with_border_bottom));
            mEditText.setHint(mTitle);
            LinearLayout.LayoutParams editTextLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIDisplayHelper.dpToPx(50));
            editTextLP.bottomMargin = QMUIDisplayHelper.dp2px(mContext, 15);
            mEditText.setLayoutParams(editTextLP);
            layout.addView(mEditText);
            TextView content = new TextView(mContext);
            content.setLineSpacing(QMUIDisplayHelper.dp2px(mContext, 4), 1.0f);
            content.setText(mContent);
            content.setTextColor(ContextCompat.getColor(mContext, R.color.qmui_config_color_gray_1));
            content.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(content);
            return layout;
        }
    }

    public static void showTipsDialog(Context context, int Type, String tips,OnTipsDialogDismissListener listener) {
        showTips(new QMUITipDialog.Builder(context)
                .setIconType(Type)
                .setTipWord(tips)
                .create(),listener);
    }

    public static void SuccessTipsDialog(Context context, String tips){
        SuccessTipsDialog(context,tips,null);
    }

    public static void SuccessTipsDialog(Context context, String tips,OnTipsDialogDismissListener listener) {
        showTips(new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(tips)
                .create(),listener);
    }
    public static void FailTipsDialog(Context context, String tips){
        FailTipsDialog(context,tips,null);
    }
    public static void FailTipsDialog(Context context, String tips,OnTipsDialogDismissListener listener) {
        showTips(new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(tips)
                .create(),listener);
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
                if(null != listener)
                    listener.onDismiss();
            }
        }, 1000);

    }

    public interface OnTipsDialogDismissListener{
        void onDismiss();
    }
}
