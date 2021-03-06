package the.one.base.widge.decoration;

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

import android.graphics.Rect;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * @author The one
 * @date 2019/10/9 0009
 * @describe 网格分割线
 * @email 625805189@qq.com
 * @remark
 */
public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int halfSpace;
    private int column;

    public GridSpacesItemDecoration(int space, int column) {
        this.space = space;
        this.column = column;
        halfSpace = space / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        if (column != 1) {
            // 当前位置
            int position;
            if(parent.getLayoutManager() instanceof StaggeredGridLayoutManager){
                StaggeredGridLayoutManager.LayoutParams params =
                        (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                 position = params.getSpanIndex();
            }else{
                GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                position = params.getSpanIndex();
            }
            int X = column - position;
            if (X == column) {
                // 最左边
                outRect.right = halfSpace;
            } else if (X == 1) {
                // 最右边
                outRect.left = halfSpace;
            } else {
                outRect.left = halfSpace;
                outRect.right = halfSpace;
            }
        }
        outRect.bottom = space;
        if (parent.getChildAdapterPosition(view) < column)
            outRect.top = space;
    }
}