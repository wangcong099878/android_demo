package the.one.base.util;

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

import android.content.Context;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.List;

import androidx.core.content.ContextCompat;
import the.one.base.model.PopupItem;

/**
 * @author The one
 * @date 2020/4/14 0014
 * @describe 对 {@link QMUIBottomSheet} 的使用做了一些简单封装
 * @email 625805189@qq.com
 * @remark
 */
public class QMUIBottomSheetUtil {

    /**
     * @param context
     * @param items
     * @param title
     * @param markIndex
     * @param listener
     * @return
     */
    public QMUIBottomSheet showSimpleBottomSheetList(Context context, List<PopupItem> items,
                                                     CharSequence title,
                                                     int markIndex, QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener listener) {
        return showSimpleBottomSheetList(context, items, title, true, markIndex, true, true, listener);
    }

    /**
     * @param context
     * @param items
     * @param title
     * @param allowDragDismiss
     * @param markIndex
     * @param gravityCenter
     * @param addCancelBtn
     * @param listener
     * @return
     */
    public QMUIBottomSheet showSimpleBottomSheetList(Context context, List<PopupItem> items,
                                                     CharSequence title,
                                                     boolean allowDragDismiss,
                                                     int markIndex, boolean gravityCenter,
                                                     boolean addCancelBtn, QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener listener) {
        boolean withMark = markIndex != -1;
        QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(context);
        builder.setGravityCenter(gravityCenter)
                .setTitle(title)
                .setAddCancelBtn(addCancelBtn)
                .setAllowDrag(allowDragDismiss)
                .setNeedRightMark(withMark)
                .setOnSheetItemClickListener(listener);
        if (withMark) {
            builder.setCheckedIndex(markIndex);
        }
        for (PopupItem item : items) {
            if (item.isHaveIcon()) {
                builder.addItem(ContextCompat.getDrawable(context, item.getImage()), item.getTitle());
            } else {
                builder.addItem(item.getTitle());
            }
        }
        return builder.build();
    }

    public static QMUIBottomSheet showGridBottomSheet(Context context, List<PopupItem> items, OnSheetItemClickListener listener) {
      return   showGridBottomSheet(context,items,4,true,listener);
    }

    /**
     *
     * @param context
     * @param items
     * @param column
     * @param cancelBtn
     * @param listener
     * @return
     */
    public static QMUIBottomSheet showGridBottomSheet(Context context, List<PopupItem> items, int column,
                                                      boolean cancelBtn, OnSheetItemClickListener listener) {
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(context);
        builder.setAddCancelBtn(cancelBtn);
        for (int i = 0; i < items.size(); i++) {
            PopupItem item = items.get(i);
            builder.addItem(item.getImage(), item.getTitle(), item.getTitle(), column > i ? QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE : QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE);
        }
        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
            @Override
            public void onClick(QMUIBottomSheet dialog, View itemView) {
                String tag = (String) itemView.getTag();
                int position = 0;
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getTitle().equals(tag)) {
                        position = i;
                        break;
                    }
                }
                if (null != listener) {
                    listener.onSheetItemClick(position, tag, dialog, itemView);
                }
            }
        });
        return builder.build();
    }

    public interface OnSheetItemClickListener {
        void onSheetItemClick(int position, String title, QMUIBottomSheet dialog, View itemView);
    }

}
