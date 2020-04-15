package the.one.base.adapter;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import the.one.base.Interface.GlideProgressListener;
import the.one.base.Interface.ImageSnap;
import the.one.base.R;
import the.one.base.util.ViewUtil;
import the.one.base.util.glide.GlideProgressInterceptor;
import the.one.base.util.imagepreview.FileTarget;
import the.one.base.util.imagepreview.ImageLoader;
import the.one.base.util.imagepreview.ImageUtil;
/**
 * 图片显示参考  https://github.com/SherlockGougou/BigImageViewPager
 * @param <T>
 */
public class ImageSnapAdapter<T extends ImageSnap> extends TheBaseQuickAdapter<T> {

    private float minScale = 1.0f;// 最小缩放倍数
    private float mediumScale = 3.0f;// 中等缩放倍数
    private float maxScale = 5.0f;// 最大缩放倍数

    private OnImageClickListener<T> onImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public ImageSnapAdapter() {
        super(R.layout.item_image_snap);
    }

    @Override
    protected void convert(TheBaseViewHolder helper, final T item) {
        QMUIProgressBar progressBar = helper.getView(R.id.progressbar);
        PhotoView photoView = helper.getView(R.id.photo_view);
        SubsamplingScaleImageView longImg = helper.getView(R.id.longImg);
        AppCompatImageView ivPlay = helper.getView(R.id.iv_play);
        String refer = item.getRefer();
        Object url;
        if (TextUtils.isEmpty(refer)) {
            url = item.getImageUrl();
        } else {
            url = new GlideUrl(item.getImageUrl(), new LazyHeaders.Builder()
                    .addHeader("Referer", refer)
                    .build());
        }
        int playVisible = item.isVideo()?View.VISIBLE:View.GONE;
        File cacheFile = ImageLoader.getGlideCacheFile(getContext(), url.toString());
        if (cacheFile != null && cacheFile.exists()) {
            boolean isCacheIsGif = ImageUtil.isGifImageWithMime(cacheFile.getAbsolutePath());
            if (isCacheIsGif) {
                String imagePath = cacheFile.getAbsolutePath();
                loadGifImageSpec(imagePath, photoView, longImg, progressBar);
            } else {
                String imagePath = cacheFile.getAbsolutePath();
                loadImageSpec(imagePath, photoView, longImg, progressBar,ivPlay,playVisible);
            }
        } else
            loadImage(url, photoView, longImg, progressBar, 1,ivPlay,playVisible);
        setListener(item, photoView, longImg);
    }

    private void setListener(T item, View... views) {
        if (null != onImageClickListener) {
            for (View view : views) {
                view.setOnClickListener(v -> onImageClickListener.onClick(item));
                view.setOnLongClickListener(v -> onImageClickListener.onLongClick(item));
            }
        }
    }

    private void loadImage(Object url, ImageView imageGif, SubsamplingScaleImageView imageView, QMUIProgressBar progressBar, final int count,AppCompatImageView ivPlay,int visible) {
        GlideProgressInterceptor.addListener(url, new GlideProgressListener() {
            @Override
            public void onProgress(int progress, boolean success) {
                progressBar.setProgress(progress);
            }
        });
        Glide.with(getContext()).downloadOnly().load(url).addListener(new RequestListener<File>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                        Target<File> target, boolean isFirstResource) {
                if (count > 3) {
                    GlideProgressInterceptor.removeListener(url);
                    loadFailed(imageView, imageGif,ivPlay, progressBar, e);
                } else
                    loadImage(url, imageGif, imageView, progressBar, count + 1,ivPlay,visible);
                return true;
            }

            @Override
            public boolean onResourceReady(File resource, Object model, Target<File> target,
                                           DataSource dataSource, boolean isFirstResource) {
                GlideProgressInterceptor.removeListener(url);
                loadSuccess(resource, imageView, imageGif, progressBar,ivPlay,visible);
                return true;
            }
        }).into(new FileTarget() {
            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
                super.onLoadStarted(placeholder);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }


    private void loadSuccess(File resource, SubsamplingScaleImageView imageView, ImageView imageGif,
                             QMUIProgressBar progressBar,AppCompatImageView ivPlay,int visible) {
        String imagePath = resource.getAbsolutePath();
        boolean isCacheIsGif = ImageUtil.isGifImageWithMime(imagePath);
        if (isCacheIsGif) {
            loadGifImageSpec(imagePath, imageGif, imageView, progressBar);
        } else {
            loadImageSpec(imagePath, imageGif, imageView, progressBar,ivPlay,visible);
        }
    }

    private void loadFailed(SubsamplingScaleImageView imageView, ImageView imageGif,AppCompatImageView ivPlay, QMUIProgressBar progressBar,
                            GlideException e) {
        ViewUtil.goneViews(progressBar,imageView,ivPlay);
        imageGif.setVisibility(View.VISIBLE);
        imageGif.setImageDrawable(QMUIResHelper.getAttrDrawable(getContext(),R.attr.glide_fail_drawable));
    }

    private void loadImageSpec(final String imagePath, final ImageView imageGif,
                               final SubsamplingScaleImageView imageView, final QMUIProgressBar progressBar,AppCompatImageView ivPlay,int visible) {

        imageGif.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        ivPlay.setVisibility(visible);

        setImageSpec(imagePath, imageView);

        imageView.setOrientation(SubsamplingScaleImageView.ORIENTATION_USE_EXIF);
        ImageSource imageSource = ImageSource.uri(Uri.fromFile(new File(imagePath)));
        if (ImageUtil.isBmpImageWithMime(imagePath)) {
            imageSource.tilingDisabled();
        }
        imageView.setImage(imageSource);
        imageView.setOnImageEventListener(new SubsamplingScaleImageView.OnImageEventListener() {
            @Override
            public void onReady() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onImageLoaded() {

            }

            @Override
            public void onPreviewLoadError(Exception e) {

            }

            @Override
            public void onImageLoadError(Exception e) {

            }

            @Override
            public void onTileLoadError(Exception e) {

            }

            @Override
            public void onPreviewReleased() {

            }
        });
    }

    private void setImageSpec(final String imagePath, final SubsamplingScaleImageView imageView) {
        boolean isLongImage = ImageUtil.isLongImage(getContext(), imagePath);
        if (isLongImage) {
            imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
            imageView.setMinScale(ImageUtil.getLongImageMinScale(getContext(), imagePath));
            imageView.setMaxScale(ImageUtil.getLongImageMaxScale(getContext(), imagePath));
            imageView.setDoubleTapZoomScale(ImageUtil.getLongImageMaxScale(getContext(), imagePath));
        } else {
            boolean isWideImage = ImageUtil.isWideImage(getContext(), imagePath);
            boolean isSmallImage = ImageUtil.isSmallImage(getContext(), imagePath);
            if (isWideImage) {
                imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);
                imageView.setMinScale(minScale);
                imageView.setMaxScale(maxScale);
                imageView.setDoubleTapZoomScale(ImageUtil.getWideImageDoubleScale(getContext(), imagePath));
            } else if (isSmallImage) {
                imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
                imageView.setMinScale(ImageUtil.getSmallImageMinScale(getContext(), imagePath));
                imageView.setMaxScale(ImageUtil.getSmallImageMaxScale(getContext(), imagePath));
                imageView.setDoubleTapZoomScale(ImageUtil.getSmallImageMaxScale(getContext(), imagePath));
            } else {
                imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);
                imageView.setMinScale(minScale);
                imageView.setMaxScale(maxScale);
                imageView.setDoubleTapZoomScale(mediumScale);
            }
        }
    }

    private void loadGifImageSpec(final String imagePath, final ImageView imageGif, final SubsamplingScaleImageView imageView, final QMUIProgressBar progressBar) {

        ViewUtil.showViews(progressBar,imageGif);
        ViewUtil.goneViews(imageView);
        GlideProgressInterceptor.addListener(imagePath, new GlideProgressListener() {
            @Override
            public void onProgress(int progress, boolean success) {
                progressBar.setProgress(progress);
            }
        });
        Glide.with(getContext())
                .asGif()
                .load(imagePath)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .error(QMUIResHelper.getAttrDrawable(getContext(),R.attr.glide_fail_drawable)))
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target,
                                                boolean isFirstResource) {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        imageGif.setImageDrawable(QMUIResHelper.getAttrDrawable(getContext(), R.attr.glide_fail_drawable));
                        GlideProgressInterceptor.removeListener(imagePath);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        if(resource != null){
                            progressBar.setVisibility(View.GONE);
                            GlideProgressInterceptor.removeListener(imagePath);
                        }
                        return false;
                    }
                })
                .into(imageGif);
    }



    public interface OnImageClickListener<T extends ImageSnap> {

        void onClick(T data);

        boolean onLongClick(T data);
    }
}
