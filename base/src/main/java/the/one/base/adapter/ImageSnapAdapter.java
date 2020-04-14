package the.one.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import the.one.base.Interface.GlideProgressListener;
import the.one.base.Interface.ImageSnap;
import the.one.base.R;
import the.one.base.util.glide.GlideProgressInterceptor;

public class ImageSnapAdapter<T extends ImageSnap> extends TheBaseQuickAdapter<T> {

    private OnImageClickListener<T> onImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public ImageSnapAdapter() {
        super(R.layout.item_image_snap);
    }

    @Override
    protected void convert(TheBaseViewHolder helper, final T item) {
        FrameLayout container = helper.getView(R.id.container);
        QMUIProgressBar progressBar = container.findViewById(R.id.progressbar);
        PhotoView photoView = container.findViewById(R.id.photo_view);
        SubsamplingScaleImageView longImg = container.findViewById(R.id.longImg);
        AppCompatImageView ivPlay = container.findViewById(R.id.iv_play);
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
        loadImageWithProgress(getContext(),url,photoView,longImg,progressBar,ivPlay,item.isVideo()?View.VISIBLE:View.GONE);
    }

    private void loadImageWithProgress(@NonNull Context context, @NonNull final Object url,
                                      @NonNull ImageView imageView,
                                      SubsamplingScaleImageView longImageView, QMUIProgressBar progressBar, View ivPlay, int visible) {
        GlideProgressInterceptor.addListener(url, new GlideProgressListener() {
            @Override
            public void onProgress(int progress, boolean success) {
                progressBar.setProgress(progress);
            }
        });
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new ImageViewTarget<Bitmap>(imageView) {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        if (progressBar != null) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        imageView.setImageDrawable(QMUIResHelper.getAttrDrawable(context, R.attr.glide_fail_drawable));
                        GlideProgressInterceptor.removeListener(url);
                    }

                    @Override
                    protected void setResource(@Nullable Bitmap resource) {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        if (resource != null) {
                            GlideProgressInterceptor.removeListener(url);
                            boolean eqLongImage = MediaUtils.isLongImg(resource.getWidth(),
                                    resource.getHeight());
                            Log.e(TAG, "setResource: "+eqLongImage );
                            longImageView.setVisibility(eqLongImage ? View.VISIBLE : View.GONE);
                            imageView.setVisibility(eqLongImage ? View.GONE : View.VISIBLE);
                            if (null != ivPlay)
                                ivPlay.setVisibility(visible);
                            if (eqLongImage) {
                                // 加载长图
                                longImageView.setQuickScaleEnabled(true);
                                longImageView.setZoomEnabled(true);
                                longImageView.setPanEnabled(true);
                                longImageView.setDoubleTapZoomDuration(100);
                                longImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
                                longImageView.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
                                longImageView.setImage(ImageSource.bitmap(resource),
                                        new ImageViewState(0, new PointF(0, 0), 0));
                            } else {
                                // 普通图片
                                imageView.setImageBitmap(resource);
                            }
                        }
                    }
                });
    }

    public interface OnImageClickListener<T extends ImageSnap> {
        void onClick(T data);

        boolean onLongClick(T data);
    }
}
