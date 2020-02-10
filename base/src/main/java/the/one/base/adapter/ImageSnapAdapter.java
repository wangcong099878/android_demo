package the.one.base.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.photoview.PhotoView;

import the.one.base.Interface.ImageSnap;
import the.one.base.R;

public class ImageSnapAdapter<T extends ImageSnap> extends TheBaseQuickAdapter<T> {

    private OnImageClickListener<T> onImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    private RequestOptions options;
    public ImageSnapAdapter() {
        super(R.layout.item_image_snap);
        options = new RequestOptions()
                .placeholder(R.drawable.image_snap_loading_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    protected void convert(TheBaseViewHolder helper, final T item) {
        FrameLayout container = helper.getView(R.id.container);
        PhotoView photoView;
        if(container.getChildCount()== 0 ){
            photoView = new PhotoView(mContext);
            container.addView(photoView);
        }else{
            photoView = (PhotoView) container.getChildAt(0);
        }
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onImageClickListener){
                    onImageClickListener.onClick(item);
                }
            }
        });
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(null != onImageClickListener){
                    return onImageClickListener.onLongClick(item);
                }
                return false;
            }
        });
        String refer = item.getRefer();
        if (TextUtils.isEmpty(refer)) {
            Glide.with(mContext)
                    .load(item.getImageUrl())
                    .apply(options)
                    .into(photoView);
        } else {
            GlideUrl glideUrl = new GlideUrl(item.getImageUrl(), new LazyHeaders.Builder()
                    .addHeader("Referer", refer)
                    .build());
            Glide.with(mContext).load(glideUrl).into(photoView);
        }
    }

    public interface OnImageClickListener<T extends ImageSnap>{
        void onClick(T data);
        boolean onLongClick(T data);
    }
}
