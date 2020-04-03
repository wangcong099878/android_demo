package the.one.gank.ui.adapter;

import android.view.View;

import com.zhpan.bannerview.holder.ViewHolder;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import the.one.base.util.glide.GlideUtil;
import the.one.gank.R;
import the.one.gank.bean.Banner;

public class BannerViewHolder implements ViewHolder<Banner> {

    @Override
    public int getLayoutId() {
        return R.layout.item_banner;
    }

    @Override
    public void onBind(View itemView, Banner data, int position, int size) {
        AppCompatImageView imageView = itemView.findViewById(R.id.banner_image);
        GlideUtil.load(itemView.getContext(), data.getImage(),imageView);
        AppCompatTextView title = itemView.findViewById(R.id.banner_title);
        title.setText(data.getTitle());
    }
}