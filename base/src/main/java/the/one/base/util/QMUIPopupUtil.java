package the.one.base.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.AdapterView;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import java.util.List;

import the.one.base.R;
import the.one.base.adapter.ListPopupAdapter;
import the.one.base.model.PopupItem;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author The one
 * @date 2018/11/6 0006
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class QMUIPopupUtil {

    public static QMUIPopupUtil qmuiPopupUtil;

    private int mWidth = 200;
    private int mHeight = 150;

    public static QMUIPopupUtil getInstance() {
        if (null == qmuiPopupUtil)
            qmuiPopupUtil = new QMUIPopupUtil();
        return qmuiPopupUtil;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    private QMUIPopup initNormalPopup(Context context, String content) {
        QMUIPopup mNormalPopup = new QMUIPopup(context, QMUIPopup.DIRECTION_NONE);
        TextView textView = new TextView(context);
        textView.setLayoutParams(mNormalPopup.generateLayoutParam(
                QMUIDisplayHelper.dp2px(context, mWidth),
                WRAP_CONTENT
        ));
        textView.setLineSpacing(QMUIDisplayHelper.dp2px(context, 4), 1.0f);
        int padding = QMUIDisplayHelper.dp2px(context, 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(content);
        textView.setTextColor(ContextCompat.getColor(context, R.color.qmui_config_color_gray_5));
        mNormalPopup.setContentView(textView);
        mNormalPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mNormalPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
        return mNormalPopup;
    }

    public QMUIListPopup initListPopup(Context context, List<PopupItem> items, AdapterView.OnItemClickListener listener) {
        return initListPopup(context, items, QMUIListPopup.DIRECTION_NONE, listener);
    }

    public QMUIListPopup initListPopup(Context context, List<PopupItem> items, int position, AdapterView.OnItemClickListener listener) {
        ListPopupAdapter listPopupAdapter = new ListPopupAdapter(context,items);
        QMUIListPopup mListPopup = new QMUIListPopup(context, position, listPopupAdapter);
        mListPopup.create(QMUIDisplayHelper.dp2px(context, mWidth), QMUIDisplayHelper.dp2px(context, mHeight), listener);
        mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mListPopup.setPreferredDirection(position);
        return mListPopup;
    }
}
