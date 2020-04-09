package the.one.gank.ui.adapter;

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
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.adapter.TheBaseViewHolder;
import the.one.base.util.glide.GlideUtil;
import the.one.gank.R;
import the.one.gank.bean.BannerBean;

/**
 * @author The one
 * @date 2020/4/9 0009
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class TheBannerAdapter extends BannerAdapter<BannerBean, TheBaseViewHolder> {

    public TheBannerAdapter(List<BannerBean> datas) {
        super(datas);
    }
    private Context mContext;

    @Override
    public TheBaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        mContext= parent.getContext();
        View container = BannerUtils.getView(parent,R.layout.item_banner);
        return new TheBaseViewHolder(container);
    }

    @Override
    public void onBindView(TheBaseViewHolder holder, BannerBean data, int position, int size) {
        AppCompatImageView imageView = holder.getView(R.id.banner_image);
        GlideUtil.load(mContext, data.getImage(),imageView);
        holder.setText(R.id.banner_title,data.getTitle());
    }

}
