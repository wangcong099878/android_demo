package the.one.base.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
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
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.glide.FileTarget;
import cc.shinichi.library.glide.ImageLoader;
import cc.shinichi.library.tool.image.ImageUtil;
import cc.shinichi.library.tool.ui.ToastUtil;
import cc.shinichi.library.view.helper.SubsamplingScaleImageViewDragClose;
import the.one.base.Interface.GlideProgressListener;
import the.one.base.Interface.ImageSnap;
import the.one.base.R;
import the.one.base.util.glide.GlideProgressInterceptor;

/**
 * 图片显示参考  https://github.com/SherlockGougou/BigImageViewPager
 * @param <T>
 */
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
        QMUIProgressBar progressBar = helper.getView(R.id.progressbar);
        PhotoView photoView = helper.getView(R.id.photo_view);
        SubsamplingScaleImageViewDragClose longImg = helper.getView(R.id.longImg);
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
        File cacheFile = ImageLoader.getGlideCacheFile(getContext(), url.toString());
        if (cacheFile != null && cacheFile.exists()) {
            boolean isCacheIsGif = ImageUtil.isGifImageWithMime(cacheFile.getAbsolutePath());
            if (isCacheIsGif) {
                String imagePath = cacheFile.getAbsolutePath();
                loadGifImageSpec(imagePath, photoView, longImg, progressBar);
            } else {
                String imagePath = cacheFile.getAbsolutePath();
                loadImageSpec(imagePath, photoView, longImg, progressBar);
            }
        } else
            loadImage(url, photoView, longImg, progressBar, 1);
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

    private void loadImage(Object url, ImageView imageGif, SubsamplingScaleImageViewDragClose imageView, QMUIProgressBar progressBar, final int count) {
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
                    loadFailed(imageView, imageGif, progressBar, e);
                } else
                    loadImage(url, imageGif, imageView, progressBar, count + 1);
                return true;
            }

            @Override
            public boolean onResourceReady(File resource, Object model, Target<File> target,
                                           DataSource dataSource, boolean isFirstResource) {
                GlideProgressInterceptor.removeListener(url);
                loadSuccess(resource, imageView, imageGif, progressBar);
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


    private void loadSuccess(File resource, SubsamplingScaleImageViewDragClose imageView, ImageView imageGif,
                             QMUIProgressBar progressBar) {
        String imagePath = resource.getAbsolutePath();
        boolean isCacheIsGif = ImageUtil.isGifImageWithMime(imagePath);
        if (isCacheIsGif) {
            loadGifImageSpec(imagePath, imageGif, imageView, progressBar);
        } else {
            loadImageSpec(imagePath, imageGif, imageView, progressBar);
        }
    }

    private void loadFailed(SubsamplingScaleImageViewDragClose imageView, ImageView imageGif, QMUIProgressBar progressBar,
                            GlideException e) {
        progressBar.setVisibility(View.GONE);
        imageGif.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);

        imageView.setZoomEnabled(false);
        imageView.setImage(cc.shinichi.library.view.helper.ImageSource.resource(ImagePreview.getInstance().getErrorPlaceHolder()));

        if (ImagePreview.getInstance().isShowErrorToast()) {
            String errorMsg = "加载失败";
            if (e != null) {
                errorMsg = errorMsg.concat(":\n").concat(e.getMessage());
            }
            if (errorMsg.length() > 200) {
                errorMsg = errorMsg.substring(0, 199);
            }
            ToastUtil.getInstance()._short(getContext().getApplicationContext(), errorMsg);
        }
    }

    private void loadImageWithProgress(@NonNull Context context, @NonNull final Object url,
                                       @NonNull final ImageView imageView,
                                       final SubsamplingScaleImageView longImageView, final QMUIProgressBar progressBar, final View ivPlay, final int visible) {
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
                        Log.e(TAG, "setResource: " + url.toString());
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        if (resource != null) {
                            Log.e(TAG, "setResource: resource != null  " + url.toString());
                            GlideProgressInterceptor.removeListener(url);
                            boolean eqLongImage = MediaUtils.isLongImg(resource.getWidth(),
                                    resource.getHeight());
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
                                Log.e(TAG, "setResource:  eqLongImage " + url.toString() + "  visible  " + longImageView.getVisibility());
                            } else {
                                // 普通图片
                                imageView.setImageBitmap(resource);
                            }
                        }
                    }
                });


    }

    private void loadImageSpec(final String imagePath, final ImageView imageGif,
                               final SubsamplingScaleImageViewDragClose imageView, final QMUIProgressBar progressBar) {

        imageGif.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);

        setImageSpec(imagePath, imageView);

        imageView.setOrientation(SubsamplingScaleImageViewDragClose.ORIENTATION_USE_EXIF);
        cc.shinichi.library.view.helper.ImageSource imageSource = cc.shinichi.library.view.helper.ImageSource.uri(Uri.fromFile(new File(imagePath)));
        if (ImageUtil.isBmpImageWithMime(imagePath)) {
            imageSource.tilingDisabled();
        }
        imageView.setImage(imageSource);

        imageView.setOnImageEventListener(new SubsamplingScaleImageViewDragClose.OnImageEventListener() {
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

    private void setImageSpec(final String imagePath, final SubsamplingScaleImageViewDragClose imageView) {
        boolean isLongImage = ImageUtil.isLongImage(getContext(), imagePath);
        if (isLongImage) {
            imageView.setMinimumScaleType(SubsamplingScaleImageViewDragClose.SCALE_TYPE_START);
            imageView.setMinScale(ImageUtil.getLongImageMinScale(getContext(), imagePath));
            imageView.setMaxScale(ImageUtil.getLongImageMaxScale(getContext(), imagePath));
            imageView.setDoubleTapZoomScale(ImageUtil.getLongImageMaxScale(getContext(), imagePath));
        } else {
            boolean isWideImage = ImageUtil.isWideImage(getContext(), imagePath);
            boolean isSmallImage = ImageUtil.isSmallImage(getContext(), imagePath);
            if (isWideImage) {
                imageView.setMinimumScaleType(SubsamplingScaleImageViewDragClose.SCALE_TYPE_CENTER_INSIDE);
                imageView.setMinScale(ImagePreview.getInstance().getMinScale());
                imageView.setMaxScale(ImagePreview.getInstance().getMaxScale());
                imageView.setDoubleTapZoomScale(ImageUtil.getWideImageDoubleScale(getContext(), imagePath));
            } else if (isSmallImage) {
                imageView.setMinimumScaleType(SubsamplingScaleImageViewDragClose.SCALE_TYPE_CUSTOM);
                imageView.setMinScale(ImageUtil.getSmallImageMinScale(getContext(), imagePath));
                imageView.setMaxScale(ImageUtil.getSmallImageMaxScale(getContext(), imagePath));
                imageView.setDoubleTapZoomScale(ImageUtil.getSmallImageMaxScale(getContext(), imagePath));
            } else {
                imageView.setMinimumScaleType(SubsamplingScaleImageViewDragClose.SCALE_TYPE_CENTER_INSIDE);
                imageView.setMinScale(ImagePreview.getInstance().getMinScale());
                imageView.setMaxScale(ImagePreview.getInstance().getMaxScale());
                imageView.setDoubleTapZoomScale(ImagePreview.getInstance().getMediumScale());
            }
        }
    }

    private void loadGifImageSpec(final String imagePath, final ImageView imageGif, final SubsamplingScaleImageViewDragClose imageView, final QMUIProgressBar progressBar) {

        imageGif.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
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
                        .error(ImagePreview.getInstance().getErrorPlaceHolder()))
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
                        progressBar.setVisibility(View.GONE);
                        GlideProgressInterceptor.removeListener(imagePath);
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
