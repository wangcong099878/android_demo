package the.one.aqtour.ui.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.rxjava.rxlife.RxLife;
import com.wx.goodview.GoodView;

import rxhttp.wrapper.param.RxHttp;
import the.one.aqtour.bean.QSPVideo;
import the.one.aqtour.constant.QSPConstant;
import the.one.aqtour.m3u8downloader.M3U8Downloader;
import the.one.aqtour.m3u8downloader.bean.M3U8Task;
import the.one.aqtour.m3u8downloader.bean.M3U8TaskState;
import the.one.aqtour.ui.view.QSPVideoDetailView;
import the.one.aqtour.util.LitePalUtil;
import the.one.aqtour.util.QSPSoupUtil;
import the.one.aqtour.util.VideoDownloadUtil;
import the.one.base.Interface.OnError;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.widge.TheCheckBox;

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
        RxHttp.get(video.url)
            .asString()
            .as(RxLife.asOnMain(this))
            .subscribe(s -> {
                //请求成功
                QSPSoupUtil.parseVideoDetail(s, video);
                if (isViewAttached()) {
                    getView().onDetailComplete();
                }
            }, (OnError) error -> {
                //请求失败
               showErrorPage(error.getErrorMsg(), "", "刷新试试", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getVideoDetail(video);
                    }
                });
            });
    }

    /**
     * 获取视频的播放地址
     *
     * @param url
     */
    public void getSeriesPlayPath(String url) {
        Log.e(TAG, "getSeriesPlayPath: "+getUrl(url) );
        RxHttp.get(url)
            .asString()
            .as(RxLife.asOnMain(this))
            .subscribe(s -> {
                //请求成功
                String path = QSPSoupUtil.getVideoPlayPath(s);
                if (isViewAttached()) {
                    getView().onSeriesComplete(path);
                }
            }, (OnError) error -> {
                //请求失败
                getView().showFailTips(error.getErrorMsg());
                getView().onSeriesError();
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
            if(!M3U8Downloader.getInstance().checkM3U8IsExist(mPlayPath)){
                //如果已经下载过文件还存在，只需要再保存一次数据即可
                m3U8Task.setState(M3U8TaskState.SUCCESS);
                getView().showSuccessTips("下载完成");
            }else{
                M3U8Downloader.getInstance().download(mPlayPath);
                getView().showSuccessTips("已添加到下载列表");
            }
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
