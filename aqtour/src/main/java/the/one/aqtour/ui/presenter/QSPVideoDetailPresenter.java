package the.one.aqtour.ui.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.wx.goodview.GoodView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import the.one.aqtour.bean.QSPVideo;
import the.one.aqtour.constant.QSPConstant;
import the.one.aqtour.m3u8downloader.M3U8Downloader;
import the.one.aqtour.m3u8downloader.bean.M3U8Task;
import the.one.aqtour.m3u8downloader.bean.M3U8TaskState;
import the.one.aqtour.ui.view.QSPVideoDetailView;
import the.one.aqtour.util.LitePalUtil;
import the.one.aqtour.util.QSPSoupUtil;
import the.one.aqtour.util.VideoDownloadUtil;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.widge.TheCheckBox;
import the.one.net.util.FailUtil;

public class QSPVideoDetailPresenter extends BasePresenter<QSPVideoDetailView> {


    private String getUrl(String url) {
        return QSPConstant.BASE_URL + url;
    }

    /**
     * 获取视频详情
     *
     * @param video
     */
    public void getVideoDetail(final QSPVideo video) {
        getView().showLoadingPage();
        OkHttpUtils.get()
                .url(getUrl(video.url))
                .tag(TAG)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (isViewAttached()) {
                            getView().showErrorPage(FailUtil.getFailString(e), "", "刷新试试", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getVideoDetail(video);
                                }
                            });
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        QSPSoupUtil.parseVideoDetail(response, video);
                        if (isViewAttached()) {
                            getView().onDetailComplete();
                        }
                    }
                });
    }

    /**
     * 获取视频的播放地址
     *
     * @param url
     */
    public void getSeriesPlayPath(String url) {
        OkHttpUtils.get()
                .url(getUrl(url))
                .tag(TAG)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (isViewAttached()) {
                            getView().showFailTips(FailUtil.getFailString(e));
                            getView().onSeriesError();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String path = QSPSoupUtil.getVideoPlayPath(response);
                        if (isViewAttached()) {
                            getView().onSeriesComplete(path);
                        }
                    }
                });
    }

    /**
     * 下载视频
     *
     * @param mPlayPath 视频地址
     * @param cover     封面
     * @param title     标题
     * @param series    剧集名称
     */
    public void downloadVideo(String mPlayPath, String cover, String title, String series) {
        if (TextUtils.isEmpty(mPlayPath)) return;
        // 是否在下载队列
        if (VideoDownloadUtil.isExistDownloadList(mPlayPath)) {
            getView().showToast("已在下载列表中");
            return;
        }
        M3U8Task m3U8Task = new M3U8Task(mPlayPath);
        m3U8Task.setCover(cover);
        m3U8Task.setSeries(series);
        m3U8Task.setName(title);
        m3U8Task.setState(M3U8TaskState.PENDING);
        m3U8Task.setCreateDate(System.currentTimeMillis());
        if (m3U8Task.save()) {
            M3U8Downloader.getInstance().download(mPlayPath);
            getView().showSuccessTips("已添加到下载列表");
        }
    }

    /**
     * 收藏视频
     *
     * @param context
     * @param mCbCollection
     * @param mVideo
     * @param url
     */
    public void collectionVideo(Context context, TheCheckBox mCbCollection, QSPVideo mVideo, String url) {
        boolean success;
        if (mCbCollection.isCheck()) {
            QSPVideo video = new QSPVideo();
            video.collection = url;
            video.url = mVideo.url;
            video.cover = mVideo.cover;
            video.actors = mVideo.actors;
            video.introduce = mVideo.introduce;
            video.type = mVideo.type;
            video.name = mVideo.name;
            video.remark = mVideo.remark;
            success = video.save();
        } else {
            success = LitePalUtil.deleteCollection(mVideo);
        }
        if (success) {
            GoodView goodView = new GoodView(context);
            goodView.setImage(mCbCollection.getCurrent());
            goodView.show(mCbCollection);
        } else {
            getView().showFailTips("操作失败");
            mCbCollection.setCheck(!mCbCollection.isCheck());
        }
    }

}
