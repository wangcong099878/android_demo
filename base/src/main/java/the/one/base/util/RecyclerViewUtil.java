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

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.widge.OffsetLinearLayoutManager;

/**
 * @author The one
 * @date 2020/5/22 0022
 * @describe RecyclerViewUtil
 * @email 625805189@qq.com
 * @remark
 */
public class RecyclerViewUtil {

    /**
     * @TODO 获取滑动的距离
     * @param recyclerView
     * @return 滑动距离
     * @remark 如果适配器添加了有头部或者尾部，则要使用{@link OffsetLinearLayoutManager}
     */
    public static int getScrollDistance(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if(null == layoutManager) return 0;
        View firstVisibleItem = recyclerView.getChildAt(0);
        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        int recycleViewHeight = recyclerView.getHeight();
        int itemHeight = firstVisibleItem.getHeight();
        int firstItemBottom = layoutManager.getDecoratedBottom(firstVisibleItem);
        return (firstItemPosition + 1) * itemHeight - firstItemBottom;
    }

}
