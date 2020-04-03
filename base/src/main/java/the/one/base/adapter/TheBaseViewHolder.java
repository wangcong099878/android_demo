package the.one.base.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;


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

/**
 * @author The one
 * @date 2019/11/8 0008
 * @describe 增加TheBaseViewHolder的一些功能
 * @email 625805189@qq.com
 * @remark
 */
public class TheBaseViewHolder extends BaseViewHolder {

    public TheBaseViewHolder(View view) {
        super(view);
    }

    public ImageView getImageView(int viewId){
        return getView(viewId);
    }

}
