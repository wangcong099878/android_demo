package the.one.base.widge;

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

import java.util.HashMap;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.util.RecyclerViewUtil;

/**
 * @author The one
 * @date 2020/4/9 0009
 * @describe 用于计算RecyclerView滑动距离,但是会影响到RecyclerView的ScrollBar的显示
 *           适用于有头部和尾部的计算，普通的滑动距离用{@link RecyclerViewUtil}来计算
 * @email 625805189@qq.com
 * @remark https://www.jianshu.com/p/b839abe49e1f
 */
public class OffsetLinearLayoutManager extends LinearLayoutManager {

    public OffsetLinearLayoutManager(Context context) {
        super(context);
    }

    private Map<Integer, Integer> heightMap = new HashMap<>();

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        int count = getChildCount();
        for (int i = 0; i < count ; i++) {
            View view = getChildAt(i);
            heightMap.put(i, view.getHeight());
        }
    }

    /**
     * 调用此方法获取滑动距离
     * @param state
     * @return
     */
    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        try {
            int firstVisiablePosition = findFirstVisibleItemPosition();
            View firstVisiableView = findViewByPosition(firstVisiablePosition);
            int offsetY = -(int) (firstVisiableView.getY());
            for (int i = 0; i < firstVisiablePosition; i++) {
                offsetY += heightMap.get(i) == null ? 0 : heightMap.get(i);
            }
            return offsetY;
        } catch (Exception e) {
            return 0;
        }
    }
}