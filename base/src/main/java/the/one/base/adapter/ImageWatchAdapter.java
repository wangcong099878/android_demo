package the.one.base.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.photoview.PhotoView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class ImageWatchAdapter extends PagerAdapter {

    private static final String TAG = "ImageWatchAdapter";

    private List<String> datas;
    private OnPhotoClickListener listener;

    public ImageWatchAdapter(List<String> datas,OnPhotoClickListener listener) {
        this.datas = datas;
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final String path = datas.get(position);
        final PhotoView photoView = new PhotoView(container.getContext());
        Glide.with(container.getContext()).load(path).into(photoView);
        container.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(path,position);
            }
        });
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(path,position);
                return true;
            }
        });

        return photoView;
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
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface OnPhotoClickListener{

        void onClick(String path,int position);

        void onLongClick(String path,int position);
    }

}
