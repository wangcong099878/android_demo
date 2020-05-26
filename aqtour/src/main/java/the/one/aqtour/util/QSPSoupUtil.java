package the.one.aqtour.util;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import the.one.aqtour.bean.QSPCategory;
import the.one.aqtour.bean.QSPContent;
import the.one.aqtour.bean.QSPSeries;
import the.one.aqtour.bean.QSPVideo;
import the.one.aqtour.bean.QSPVideoInfo;
import the.one.aqtour.bean.QSPVideoSection;
import the.one.aqtour.constant.QSPConstant;

public class QSPSoupUtil {

    private static final String TAG = "QSPSoupUtil";

    /**
     * 解析分类列表
     *
     * @param response
     * @return
     */
    public static List<QSPCategory> parseCategoryList(String response) {
        Document document = Jsoup.parse(response);
        Elements elements = document.select("ul.nav.navbar-nav.navbar-left").select("a");
        List<QSPCategory> categories = new ArrayList<>();
        for (Element element : elements) {
            String title = element.html();
            if (title.contains("更多")) continue;
            String url = element.attr("href");
            categories.add(new QSPCategory(title, url));
        }
        return categories;
    }

    /**
     * 解析视频集合
     *
     * @param response
     * @param isIndex
     * @param page
     * @return
     */
    public static QSPContent parseVideoSections(String response, boolean isIndex, int page) {
        Document document = Jsoup.parse(response);
        QSPContent qspContent = new QSPContent();
        List<QSPVideoSection> videoSections = new ArrayList<>();
        Elements elements = document.select("div.layout-box.clearfix");
        for (Element element : elements) {
            Element head = element.select("div.box-title").select("h3.m-0").first();
            String title = "";
            String titleRes = "";
            String moreTitle = "";
            String moreUrl = "";
            if (null != head) {
                title = head.html();
                if (!TextUtils.isEmpty(title)) {
                    String start = "</i>";
                    if (title.contains(start)) {
                        title = title.substring(title.lastIndexOf(start) + start.length());
                    }
//                    titleRes = head.select("img").attr("src");
                }
                Element more = head.select("div.more.pull-right").select("a").first();
                if (null != more) {
                    moreUrl = more.attr("href");
                    moreTitle = more.attr("title");
                }
                Elements contents = element.select("li.col-md-2.col-sm-3.col-xs-4");
                if (contents.size() == 0) {
                    contents = element.select("li.col-md-3.col-sm-3.col-xs-4");
                }
                if (contents.size() == 0) {
                    contents = element.select("li.swiper-slide");
                }
                if (!TextUtils.isEmpty(title) && contents.size() > 0) {
                    if (!title.contains("星")) {
                        videoSections.add(new QSPVideoSection(title, titleRes, moreUrl, moreTitle));
                        parseVideo(contents, videoSections, null);
                    }
                }
            }
        }
        qspContent.videoSections = videoSections;
        return qspContent;
    }

    /**
     * 解析视频数据
     *
     * @param contents
     * @param videoSections
     * @param videos
     */
    private static void parseVideo(Elements contents, List<QSPVideoSection> videoSections, List<QSPVideo> videos) {
        for (Element content : contents) {
            try {
                Element temp = content.selectFirst("a");
                String name = temp.attr("title");
                if (TextUtils.isEmpty(name)) {
                    name = temp.select("span").html();
                }
                String cover = temp.attr("data-original");
                if (TextUtils.isEmpty(cover)) {
                    cover = temp.toString();
                    String start = "data-background=\"";
                    String end = "\"><span";
                    cover = cover.substring(cover.indexOf(start) + start.length(), cover.indexOf(end));
                }
                String url = temp.attr("href");
                String actors = content.select("div.subtitle.text-muted.text-overflow.hidden-xs").html();
                String remark = content.select("span.note.text-bg-r").html();
                QSPVideo video = new QSPVideo(name, actors, url, cover, remark);
                if (null == videoSections) {
                    videos.add(video);
                } else
                    videoSections.add(new QSPVideoSection(video));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 解析视频详情
     *
     * @param response
     * @param video
     */
    public static void parseVideoDetail(String response, QSPVideo video) {
        Log.e(TAG, "parseVideoDetail: ");
        Document document = Jsoup.parse(response);
        Element detail = document.selectFirst("div.col-md-9.col-sm-12.col-xs-12").selectFirst("ul.info.clearfix");
        Log.e(TAG, "parseVideoDetail: " + detail.toString());
//        String score = detail.select("div.star").select("span.branch").html();
//        try {
//            if (!TextUtils.isEmpty(score)) {
//                video.score = Float.parseFloat(score);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Elements infos = detail.select("li");
//
//        for (Element info: infos){
//            String key = info.select("span").html();
//            Log.e(TAG, "parseVideoDetail: "+key);
//            String value = info.html();
//
//        }
//        Elements keys = detail.select("span.text-muted");
//        Elements values = detail.select("a");
//        video.videoInfos = new ArrayList<>();
//        for (int i = 0; i < keys.size() - 1; i++) {
//            try {
//                String key = keys.get(i).html();
//                String value = values.get(i).html();
//                video.videoInfos.add(new QSPVideoInfo(key, value));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        Elements playList = document.select("div.playlist").select("ul.clearfix").first().select("a");
        video.series = parseSeriesSections(playList);
        video.introduce = document.selectFirst("div.details-content").html();
        video.recommends = new ArrayList<>();
        video.recommends.addAll(parseVideoSections(response, true, 1).videoSections);
    }

    /**
     * 解析剧集
     *
     * @param elements
     * @return
     */
    private static List<QSPSeries> parseSeriesSections(Elements elements) {
        List<QSPSeries> series = new ArrayList<>();
        for (Element element : elements) {
            String name = element.html();
            if(name.contains("span")){
                name = name.substring(0,name.indexOf("<span"));
            }
            String url = element.attr("href");
            series.add(new QSPSeries(name, url));
        }
        return series;
    }


    private static final String[] fixs = {"<br>", "</span>"};

    /**
     * 解析影片介绍
     *
     * @param document
     * @return
     */
    private static String parseVideoIntroduce(Document document) {
        Elements elements = document.selectFirst("div.details-content").select("p");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < elements.size(); i++) {
            String content = elements.get(i).html();
            for (String fix : fixs) {
                if (content.contains(fix)) {
                    content = content.replaceAll(fix, "");
                }
            }
            String startStr = "<span";
            String endStr = ">";
            if (content.contains(startStr)) {
                int startIndex = content.indexOf(startStr);
                int endIndex = content.indexOf(endStr);
                if (endIndex > startIndex) {
                    content = content.substring(endIndex + endStr.length());
                }
            }
            stringBuffer.append(content);
            stringBuffer.append("\n");
            if (!content.contains("：")) {
                stringBuffer.append("\n");
            }
        }

        return stringBuffer.toString();
    }

    /**
     * 获取播放地址
     *
     * @param response
     * @return
     */
    public static String getVideoPlayPath(String response) {
        Document document = Jsoup.parse(response);
        Element element = document.selectFirst("div.embed-responsive.embed-responsive-16by9").selectFirst("script");
        String path = element.html();
        int start = path.indexOf("http");
        String endStr = ".m3u8";
        int end = path.indexOf(".m3u8");
        path = path.substring(start, end + endStr.length());
        path = path.replaceAll("\\\\", "");
        return path;
    }

    public static QSPContent parseSearchList(String response) {
        QSPContent qspContent = new QSPContent();
        qspContent.videoSections = new ArrayList<>();
        Document document = Jsoup.parse(response);
        Elements elements = document.select("ul.stui-vodlist__media.col-pd.clearfix").select("li.active");
        for (Element element : elements) {
            String cover = element.select("a.v-thumb.stui-vodlist__thumb.lazyload").attr("data-original");
            String title = element.select("a.v-thumb.stui-vodlist__thumb.lazyload").attr("title");
            String url = element.select("a.v-thumb.stui-vodlist__thumb.lazyload").attr("href");
            String remark = element.select("a.v-thumb.stui-vodlist__thumb.lazyload").select("span.pic-text.text-right").html();
            qspContent.videoSections.add(new QSPVideoSection(new QSPVideo(title, "", url, cover, remark)));
        }
        return qspContent;
    }


}
