package the.one.aqtour.ui.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUIProgressBar;

import the.one.aqtour.R;
import the.one.aqtour.m3u8downloader.bean.M3U8Task;
import the.one.aqtour.m3u8downloader.bean.M3U8TaskState;
import the.one.base.adapter.TheBaseQuickAdapter;
import the.one.base.adapter.TheBaseViewHolder;
import the.one.base.util.glide.GlideUtil;

public class DownloadTaskAdapter extends TheBaseQuickAdapter<M3U8Task> {

    private static final String TAG = "DownloadTaskAdapter";
    private boolean isDownload;

    public DownloadTaskAdapter(boolean isDownload) {
        super(R.layout.item_download_task);
        this.isDownload = isDownload;
    }

    @Override
    protected void convert(TheBaseViewHolder helper, final M3U8Task item) {
        helper.setText(R.id.tv_name,item.getFullName());
        GlideUtil.load(mContext,item.getCover(),helper.getImageView(R.id.cover));
        TextView tvStatus = helper.getView(R.id.tv_status);
        QMUIProgressBar progressBar = helper.getView(R.id.progressbar);
        setStateText(tvStatus,progressBar, item);
        setViewVisible(progressBar,tvStatus);
    }

    @SuppressLint("SetTextI18n")
    private void setStateText(TextView stateTv, QMUIProgressBar progressBar, M3U8Task task){
        int progress = (int) (task.getProgress()*100);
        progressBar.setProgress(progress);

        switch (task.getState()){
            case M3U8TaskState.PENDING:
                stateTv.setText("等待中");
                break;
            case M3U8TaskState.DOWNLOADING:
                @SuppressLint("DefaultLocale")
                String progressStr=String.format("%.1f ",task.getProgress() * 100);
                stateTv.setText(task.getFormatSpeed()+"  "+ progressStr + "%  正在下载" );
                break;
            case M3U8TaskState.ERROR:
                stateTv.setText("下载异常，点击重试");
                break;
            //关于存储空间不足测试方案，参考 http://blog.csdn.net/google_acmer/article/details/78649720
            case M3U8TaskState.ENOSPC:
                stateTv.setText("存储空间不足");
                break;
            case M3U8TaskState.PREPARE:
                stateTv.setText("准备中");
                break;
            case M3U8TaskState.SUCCESS:
                stateTv.setText("下载完成");
                break;
            case M3U8TaskState.PAUSE:
                stateTv.setText("暂停中");
                break;
            case M3U8TaskState.DELETE:
                stateTv.setText("文件被删除，点击重新下载");
                break;
            default:stateTv.setText("未下载");
                break;
        }
    }

    private void setViewVisible(View... views){
        int visible = isDownload?View.GONE:View.VISIBLE;
        for (View view:views){
            view.setVisibility(visible);
        }
    }

}
