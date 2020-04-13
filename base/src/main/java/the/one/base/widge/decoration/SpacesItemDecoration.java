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
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author The one
 * @date 2019/10/9 0009
 * @describe 分割线
 * @email 625805189@qq.com
 * @remark
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int halfSpace;
    private int column;
    private int headerNum;

    public SpacesItemDecoration(int space, int column) {
        this.space = space;
        this.column = column;
        halfSpace = space / 2;
    }

    public SpacesItemDecoration(int headerNum,int space, int column) {
        this.headerNum = headerNum;
        this.space = space;
        this.halfSpace = space / 2;
        this.column = column;
    }

    public void setHeaderNum(int headerNum) {
        this.headerNum = headerNum;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        // 当前View行位置
        int index = 0;
        // 当前View为第几个
        int position = 0;
        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams params =
                    (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            index = params.getSpanIndex();
            position = params.getViewAdapterPosition();
        } else if(parent.getLayoutManager() instanceof GridLayoutManager){
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            index = params.getSpanIndex();
            position = params.getViewAdapterPosition();
        }else{
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            position = params.getViewAdapterPosition();
        }
        // 设置到头部的不加间距
        if (position >= headerNum || noHeader()) {
            outRect.left = space;
            outRect.right = space;
            // 头部下面的第一个给一整个间距
            outRect.top = noHeader()&& position == headerNum?space:halfSpace;
            outRect.bottom = halfSpace;
            if (column != 1) {
                int X = column - index;
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
        }
    }

    private boolean noHeader(){
        return headerNum == 0;
    }
}