package the.one.base.util.glide;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.qmuiteam.qmui.widget.QMUIProgressBar;

import the.one.base.Interface.GlideProgressListener;

public class SampleGlideProgressListener implements GlideProgressListener {

    private static final String TAG = "Progress";
    
    private ImageView imageView;
    private QMUIProgressBar progressBar;
    private int mMax;

    public SampleGlideProgressListener(ImageView imageView,QMUIProgressBar progressBar) {
        this(imageView,progressBar,100);
    }

    public SampleGlideProgressListener(ImageView imageView, QMUIProgressBar progressBar,int max) {
        this.imageView = imageView;
        this.progressBar = progressBar;
        this.mMax = max;
        progressBar.setMaxValue(max);
    }

    @Override
    public void onProgress(int progress,boolean success) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if(progress == mMax || success){
                    progressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }else{
                    progressBar.setProgress(progress);
                    progressBar.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                }
            }
        });

    }
}
