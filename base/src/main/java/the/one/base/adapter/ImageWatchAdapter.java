package the.one.base.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.viewpager.widget.PagerAdapter;
import the.one.base.R;
import the.one.base.util.glide.GlideEngine;

//import the.one.base.widge.preview.ImageSource;
//import the.one.base.widge.preview.PhotoView;
//import the.one.base.widge.preview.SubsamplingScaleImageView;
//

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class ImageWatchAdapter extends PagerAdapter {

    private static final String TAG = "ImageWatchAdapter";

    private Activity mActivity;
    private List<String> mImageList;
    private HashMap<String, SubsamplingScaleImageView> imageHashMap = new HashMap<>();
    private HashMap<String, PhotoView> imageGifHashMap = new HashMap<>();
    private OnPhotoClickListener listener;
    private GlideEngine mGlideEngine;

    public ImageWatchAdapter(Activity activity, List<String> datas, OnPhotoClickListener listener) {
        super();
        mActivity = activity;
        this.mImageList = datas;
        this.listener = listener;
        mGlideEngine= GlideEngine.createGlideEngine();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if(null == mActivity) return container;
        View contentView = View.inflate(mActivity, R.layout.item_image_snap, null);
        final String path = mImageList.get(position);
        final PhotoView photoView = contentView.findViewById(R.id.photo_view);
        final SubsamplingScaleImageView longImageView = contentView.findViewById(R.id.longImg);
        final QMUIProgressBar progressBar = contentView.findViewById(R.id.progressbar);

//        setClickListener(path,position,photoView,longImageView);
//        Glide.with(container.getContext())
//                .load(path)
//                .into(photoView);
        imageGifHashMap.remove(path);
        imageGifHashMap.put(path, photoView);

        imageHashMap.remove(path);
        imageHashMap.put(path, longImageView);

        progressBar.setProgress(0);

        mGlideEngine.loadImageWithProgress(container.getContext(), path,
                photoView, longImageView, progressBar);

        container.addView(contentView);
        return contentView;
    }

    private void setClickListener(final String path,final int position,View... views){
        for (View view:views){
            view.setOnClickListener(v -> listener.onClick(path,position));
            view.setOnLongClickListener(v -> listener.onLongClick(path,position));
        }
    }

    @Override
    public int getCount() {
        return mImageList != null ? mImageList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        String originUrl = mImageList.get(position);
        try {
            if (imageHashMap != null) {
                SubsamplingScaleImageView imageViewDragClose = imageHashMap.get(originUrl);
                if (imageViewDragClose != null) {
                    imageViewDragClose.resetScaleAndCenter();
                    imageViewDragClose.destroyDrawingCache();
                    imageViewDragClose.recycle();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (imageGifHashMap != null) {
                PhotoView photoView = imageGifHashMap.get(originUrl);
                if (photoView != null) {
                    photoView.destroyDrawingCache();
                    photoView.setImageBitmap(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glide.get(mActivity.getApplicationContext()).clearMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface OnPhotoClickListener {

        void onClick(String path, int position);

        boolean onLongClick(String path, int position);
    }

    public void closePage() {
        try {
            if (imageHashMap != null && imageHashMap.size() > 0) {
                for (Object o : imageHashMap.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    if (entry != null && entry.getValue() != null) {
                        ((SubsamplingScaleImageView) entry.getValue()).destroyDrawingCache();
                        ((SubsamplingScaleImageView) entry.getValue()).recycle();
                    }
                }
                imageHashMap.clear();
                imageHashMap = null;
            }
            if (imageGifHashMap != null && imageGifHashMap.size() > 0) {
                for (Object o : imageGifHashMap.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    if (entry != null && entry.getValue() != null) {
                        ((PhotoView) entry.getValue()).destroyDrawingCache();
                        ((PhotoView) entry.getValue()).setImageBitmap(null);
                    }
                }
                imageGifHashMap.clear();
                imageGifHashMap = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
