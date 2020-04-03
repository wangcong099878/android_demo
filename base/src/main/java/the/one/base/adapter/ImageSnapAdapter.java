package the.one.base.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import the.one.base.Interface.ImageSnap;
import the.one.base.R;
import the.one.base.util.glide.GlideEngine;

public class ImageSnapAdapter<T extends ImageSnap> extends TheBaseQuickAdapter<T> {

    private OnImageClickListener<T> onImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    private RequestOptions options;
    private GlideEngine mGlideEngine;

    public ImageSnapAdapter() {
        super(R.layout.item_image_snap);
        options = new RequestOptions()
                .placeholder(R.drawable.image_snap_loading_bg)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        mGlideEngine = GlideEngine.createGlideEngine();
    }

    @Override
    protected void convert(TheBaseViewHolder helper, final T item) {
        FrameLayout container = helper.getView(R.id.container);
        QMUIProgressBar progressBar = container.findViewById(R.id.progressbar);
        PhotoView photoView = container.findViewById(R.id.photo_view);
        SubsamplingScaleImageView longImg = container.findViewById(R.id.longImg);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onImageClickListener) {
                    onImageClickListener.onClick(item);
                }
            }
        });
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != onImageClickListener) {
                    return onImageClickListener.onLongClick(item);
                }
                return false;
            }
        });
        String refer = item.getRefer();
        Object url;
        if (TextUtils.isEmpty(refer)) {
            url = item.getImageUrl();
        } else {
            url = new GlideUrl(item.getImageUrl(), new LazyHeaders.Builder()
                    .addHeader("Referer", refer)
                    .build());
        }
        mGlideEngine.loadImageWithProgress(getContext(),url,photoView,longImg,progressBar);
    }

    public interface OnImageClickListener<T extends ImageSnap> {
        void onClick(T data);

        boolean onLongClick(T data);
    }
}
