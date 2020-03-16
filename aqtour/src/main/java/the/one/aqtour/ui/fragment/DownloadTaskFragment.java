package the.one.aqtour.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import the.one.aqtour.m3u8downloader.M3U8Downloader;
import the.one.aqtour.m3u8downloader.OnDeleteTaskListener;
import the.one.aqtour.m3u8downloader.OnM3U8DownloadListener;
import the.one.aqtour.m3u8downloader.bean.M3U8Task;
import the.one.aqtour.m3u8downloader.bean.M3U8TaskState;
import the.one.aqtour.ui.activity.DownloadVideoPlayActivity;
import the.one.aqtour.ui.adapter.DownloadTaskAdapter;
import the.one.aqtour.util.VideoDownloadUtil;
import the.one.base.base.fragment.BaseDataFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.base.event.SuccessEvent;
import the.one.base.util.EventBusUtil;
import the.one.net.entity.PageInfoBean;


public class DownloadTaskFragment extends BaseDataFragment<M3U8Task> {

    public static DownloadTaskFragment newInstance( boolean isDownload) {
        DownloadTaskFragment fragment = new DownloadTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(DataConstant.TYPE, isDownload);
        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean isDownload;

    private DownloadTaskAdapter mAdapter;
    private List<M3U8Task> mTasks;

    @Override
    protected boolean isRegisterEventBus() {
        Bundle bundle = getArguments();
        assert bundle != null;
        isDownload = bundle.getBoolean(DataConstant.TYPE);
        return isDownload;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        goneView(mTopLayout);
        empty_str = "无" + (isDownload ? "已下载视频" : "下载任务");
        if (!isDownload)
            M3U8Downloader.getInstance().setOnM3U8DownloadListener(onM3U8DownloadListener);
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return mAdapter = new DownloadTaskAdapter(isDownload);
    }

    @Override
    protected void requestServer() {
        mTasks = VideoDownloadUtil.getVideoDownloadList(isDownload);
        onComplete(mTasks, new PageInfoBean(1, 1));
        setPullLayoutEnabled(false);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        M3U8Task task = (M3U8Task) adapter.getItem(position);
        if (isDownload) {
            // 播放
            DownloadVideoPlayActivity.startThisActivity(_mActivity, task);
        } else {
            M3U8Downloader.getInstance().download(task.getUrl());
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        M3U8Task task = (M3U8Task) adapter.getItem(position);
        showDeleteTaskDialog(task, position);
        return true;
    }

    private void showDeleteTaskDialog(final M3U8Task task, final int position) {
        final QMUIDialog.CheckBoxMessageDialogBuilder builder = new QMUIDialog.CheckBoxMessageDialogBuilder(_mActivity)
                .setTitle("是否删除任务")
                .setMessage("删除已下载文件")
                .setChecked(true);
        builder.addAction("取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        showLoadingDialog("删除中");
                        if (builder.isChecked())
                            M3U8Downloader.getInstance().cancelAndDelete(task.getUrl(), new OnDeleteTaskListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess() {
                                    _mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            hideLoadingDialog();
                                            task.delete();
                                            mAdapter.getData().remove(position);
                                            refresh();
                                        }
                                    });
                                }

                                @Override
                                public void onFail() {
                                    hideLoadingDialog();
                                    showFailTips("删除失败");
                                }

                                @Override
                                public void onError(Throwable errorMsg) {
                                    showFailTips("错误");
                                    hideLoadingDialog();
                                }
                            });
                        else {
                            hideLoadingDialog();
                            M3U8Downloader.getInstance().cancel(task.getUrl());
                            task.delete();
                            mAdapter.notifyItemRemoved(position);
                        }
                    }
                }).show();
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    private OnM3U8DownloadListener onM3U8DownloadListener = new OnM3U8DownloadListener() {

        @Override
        public void onDownloadItem(M3U8Task task, long itemFileSize, int totalTs, int curTs) {
            super.onDownloadItem(task, itemFileSize, totalTs, curTs);
        }

        @Override
        public void onDownloadSuccess(M3U8Task task) {
            super.onDownloadSuccess(task);
            notifyChanged(task);
        }

        @Override
        public void onDownloadPending(M3U8Task task) {
            super.onDownloadPending(task);
            notifyChanged(task);
        }

        @Override
        public void onDownloadPause(M3U8Task task) {
            super.onDownloadPause(task);
            notifyChanged(task);
        }

        @Override
        public void onDownloadProgress(final M3U8Task task) {
            super.onDownloadProgress(task);
            notifyChanged(task);
        }

        @Override
        public void onDownloadPrepare(final M3U8Task task) {
            notifyChanged(task);
        }

        @Override
        public void onDownloadError(final M3U8Task task, Throwable errorMsg) {
            notifyChanged(task);
        }

    };

    private void notifyChanged(final M3U8Task task) {
        _mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyData(task);
            }
        });
    }

    public void notifyData(M3U8Task m3U8Task) {
        if (null == mTasks) return;
        int size = mTasks.size();
        for (int i = 0; i < size; i++) {
            M3U8Task task = mTasks.get(i);
            if (task.equals(m3U8Task)) {
                task.setProgress(m3U8Task.getProgress());
                task.setSpeed(m3U8Task.getSpeed());
                task.setState(m3U8Task.getState());
                if (task.getState() == M3U8TaskState.SUCCESS) {
                    mTasks.remove(task);
                    mAdapter.notifyDataSetChanged();
                    if (mTasks.size() == 0) {
                        showEmptyPage(empty_str);
                    }
                    EventBusUtil.sendSuccessEvent();
                    break;
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SuccessEvent event) {
        if (!mIsFirstLayInit) {
            isFirst = true;
            requestServer();
        }
        EventBusUtil.removeStickyEvent(event);
    }

}
