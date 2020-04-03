package the.one.base.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import java.util.List;

import the.one.base.R;
import the.one.base.util.glide.GlideEngine;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class ImageWatchAdapter extends PagerAdapter {

    private static final String TAG = "ImageWatchAdapter";

    /**
     * 最大缓存图片数量
     */
    private static final int MAX_CACHE_SIZE = 20;
    /**
     * 缓存view
     */
    private SparseArray<View> mCacheView;

    private List<String> datas;
    private OnPhotoClickListener listener;
    private GlideEngine mGlideEngine;

    public void clear() {
        if (null != mCacheView) {
            mCacheView.clear();
            mCacheView = null;
        }
    }

    public void removeCacheView(int position) {
        if (mCacheView != null && position < mCacheView.size()) {
            mCacheView.removeAt(position);
        }
    }

    public ImageWatchAdapter(List<String> datas, OnPhotoClickListener listener) {
        super();
        this.datas = datas;
        this.listener = listener;
        this.mCacheView = new SparseArray<>();
        mGlideEngine = GlideEngine.createGlideEngine();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View contentView = mCacheView.get(position);
        if (contentView == null) {
            contentView = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.item_image_snap, container, false);
            mCacheView.put(position, contentView);
        }
        final String path = datas.get(position);
        final PhotoView photoView = contentView.findViewById(R.id.photo_view);
        final SubsamplingScaleImageView longImageView = contentView.findViewById(R.id.longImg);
        QMUIProgressBar progressBar = contentView.findViewById(R.id.progressbar);
        boolean isHttp = PictureMimeType.isHttp(path);
        if(isHttp){
            mGlideEngine.loadImageWithProgress(contentView.getContext(), path,
                    photoView, longImageView, progressBar);
        }else{
            mGlideEngine.loadImage(container.getContext(),path,photoView);
        }
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(path, position);
            }
        });
        contentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(path, position);
                return true;
            }
        });
        container.addView(contentView,0);
        return contentView;
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((View) object);
        if (mCacheView.size() > MAX_CACHE_SIZE) {
            mCacheView.remove(position);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface OnPhotoClickListener {

        void onClick(String path, int position);

        void onLongClick(String path, int position);
    }

}
