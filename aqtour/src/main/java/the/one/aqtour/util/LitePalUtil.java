package the.one.aqtour.util;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import the.one.aqtour.bean.QSPVideo;
import the.one.aqtour.bean.QSPVideoSection;

public class LitePalUtil {

    public static List<QSPVideoSection> getCollectionVideoList(String collectionUrl) {
        List<QSPVideo> qspVideos = LitePal.where("collection = ?", collectionUrl).find(QSPVideo.class);
        List<QSPVideoSection> videoSections = new ArrayList<>();
        if(null != qspVideos && qspVideos.size()>0){
            for (QSPVideo video:qspVideos){
                videoSections.add(new QSPVideoSection(video));
            }
        }
        return videoSections;
    }

    public static boolean isExist(QSPVideo video) {
        List<QSPVideo> selects = LitePal.where("url = ?", video.url).find(QSPVideo.class);
        return null != selects && selects.size() > 0;
    }

    public static boolean deleteCollection(QSPVideo video){
       return LitePal.deleteAll(QSPVideo.class,"url = ?", video.url) == 1;
    }

}
