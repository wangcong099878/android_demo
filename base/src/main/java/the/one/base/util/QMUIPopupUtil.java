package the.one.base.util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.adapter.ListPopupAdapter;
import the.one.base.model.PopupItem;
import the.one.base.widge.WrapContentRecyclerView;

/**
 * @author The one
 * @date 2018/11/6 0006
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class QMUIPopupUtil {

    public static  QMUIPopup initNormalPopup(Context context, String content) {
        TextView textView = new TextView(context);
        textView.setLineSpacing(QMUIDisplayHelper.dp2px(context, 4), 1.0f);
        int padding = QMUIDisplayHelper.dp2px(context, 20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(content);
        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
        QMUISkinHelper.setSkinValue(textView, builder);
        builder.release();
        return QMUIPopups.popup(context, QMUIDisplayHelper.dp2px(context, 250))
                .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                .view(textView)
                .shadowElevation(QMUIDisplayHelper.dp2px(context, 5),0.55f)
                .edgeProtection(QMUIDisplayHelper.dp2px(context, 10))
                .offsetX(QMUIDisplayHelper.dp2px(context, 20))
                .offsetYIfBottom(QMUIDisplayHelper.dp2px(context, 5))
                .shadow(true)
                .arrow(true)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
    }

    public static QMUIPopup createListPop(Context context, String[] mMenus, OnItemClickListener listener){
        List<PopupItem> items = new ArrayList<>();
        for (String menu:mMenus){
            items.add(new PopupItem(menu));
        }
        return createListPop(context,items,listener,200,300);
    }

    public static QMUIPopup createListPop(Context context, List<PopupItem> mMenus, OnItemClickListener listener){
        return createListPop(context,mMenus,listener,200,300);
    }

    public static QMUIPopup createListPop(Context context, List<PopupItem> mMenus, OnItemClickListener listener,int maxWidth, int maxHeight){
        RecyclerView recyclerView = new WrapContentRecyclerView(context,QMUIDisplayHelper.dp2px(context, maxHeight));
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ListPopupAdapter adapter = new ListPopupAdapter();
        adapter.setOnItemClickListener(listener);
        adapter.setNewInstance(mMenus);
        recyclerView.setAdapter(adapter);
        return QMUIPopups.popup(context,
                QMUIDisplayHelper.dp2px(context, maxWidth)).view(recyclerView)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .preferredDirection(QMUIPopup.DIRECTION_TOP)
                .shadow(true)
                .edgeProtection(QMUIDisplayHelper.dp2px(context, 10))
                .shadowElevation(QMUIDisplayHelper.dp2px(context, 5),0.55f)
                .dismissIfOutsideTouch(true);
    }


}
