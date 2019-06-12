package the.one.base.adapter;

import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class ImageWatchAdapter extends PagerAdapter {

    private static final String TAG = "ImageWatchAdapter";

    private List<String> datas;
    private AppCompatActivity activity;
    private OnPhotoLongClickListener listener;

    public ImageWatchAdapter(List<String> datas, AppCompatActivity activity,OnPhotoLongClickListener listener) {
        this.datas = datas;
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final String path = datas.get(position);
        final PhotoView photoView = new PhotoView(activity);
        Glide.with(activity).load(path).into(photoView);
        container.addView(photoView);
        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView imageView, float v, float v1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    activity.finishAfterTransition();
                }else{
                    activity.finish();
                }
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

    public interface OnPhotoLongClickListener{
        void onLongClick(String path,int position);
    }

}
