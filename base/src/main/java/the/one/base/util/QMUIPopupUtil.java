package the.one.base.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import java.util.List;

import the.one.base.R;

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

    public static QMUIPopupUtil getInstance() {
        if (null == qmuiPopupUtil)
            qmuiPopupUtil = new QMUIPopupUtil();
        return qmuiPopupUtil;
    }


    private QMUIPopup initNormalPopup(Context context, String content) {
        QMUIPopup mNormalPopup = new QMUIPopup(context, QMUIPopup.DIRECTION_NONE);
        TextView textView = new TextView(context);
        textView.setLayoutParams(mNormalPopup.generateLayoutParam(
                QMUIDisplayHelper.dp2px(context, 250),
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


    public QMUIListPopup initListPopup(Context context, List<String> items, AdapterView.OnItemClickListener listener) {
        return initListPopup(context, items, QMUIListPopup.DIRECTION_TOP, listener);
    }

    public QMUIListPopup initListPopup(Context context, List<String> items, int position, AdapterView.OnItemClickListener listener) {
        ArrayAdapter adapter = new ArrayAdapter<>(context, R.layout.simple_list_item, items);
        QMUIListPopup mListPopup = new QMUIListPopup(context, position, adapter);
        mListPopup.create(QMUIDisplayHelper.dp2px(context, 180), QMUIDisplayHelper.dp2px(context, 200), listener);
        mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mListPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
        return mListPopup;
    }
}
