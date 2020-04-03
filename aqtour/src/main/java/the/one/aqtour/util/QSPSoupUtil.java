package the.one.aqtour.util;

import android.text.TextUtils;

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
        Elements elements = document.select("ul.stui-header__menu.type-slide").select("a");
        List<QSPCategory> categories = new ArrayList<>();
        for (Element element : elements) {
            String title = element.html();
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
        Elements elements = document.select("div.stui-pannel-box");
        for (Element element : elements) {
            Element head = element.select("h3.title").first();
            String title = "";
            String titleRes = "";
            String moreUrl = "";
            if (null != head) {
                Elements contents = element.select("div.stui-vodlist__box");
                titleRes = head.select("img").attr("src");
                title = head.select("a").html();
                moreUrl = head.select("a").attr("href");
                if (TextUtils.isEmpty(title)) {
                    title = head.toString();
                    title = title.substring(0, title.lastIndexOf("</h3>"));
                    title = title.substring(title.lastIndexOf(">") + 1, title.length());
                    title = title.trim();
                }
                if (!TextUtils.isEmpty(title)) {
                    if (isIndex && null != contents && contents.size() > 0)
                        videoSections.add(new QSPVideoSection( title, QSPConstant.BASE_URL + titleRes, moreUrl));
                    parseVideo(contents, videoSections, null);
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
            Element temp = content.selectFirst("a.stui-vodlist__thumb.lazyload");
            String name = temp.attr("title");
            String cover = temp.attr("data-original");
            String url = temp.attr("href");
            String actors = content.select("p.text.text-overflow.text-muted.hidden-xs").html();
            String remark = content.select("span.pic-text.text-right").html();
            QSPVideo video = new QSPVideo(name, actors, url, cover, remark);
            if (null == videoSections) {
                videos.add(video);
            } else
                videoSections.add(new QSPVideoSection(video));
        }
    }

    /**
     * 解析视频详情
     *
     * @param response
     * @param video
     */
    public static void parseVideoDetail(String response, QSPVideo video) {
        Document document = Jsoup.parse(response);
        Element detail = document.selectFirst("div.stui-content__detail");
        String score = detail.select("div.star").select("span.branch").html();
        try {
            if (!TextUtils.isEmpty(score)) {
                video.score = Float.parseFloat(score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Elements keys = detail.select("span.text-muted");
        Elements values = detail.select("a");
        video.videoInfos = new ArrayList<>();
        for (int i = 0; i < keys.size() - 1; i++) {
            try {
                String key = keys.get(i).html();
                String value = values.get(i).html();
                video.videoInfos.add(new QSPVideoInfo(key, value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Elements playList = document.select("div.stui-pannel-box.b.playlist.mb");
        video.series = parseSeriesSections(playList);
        video.introduce = parseVideoIntroduce(document);
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
            Elements elements1 = element.select("a");
            for (Element se : elements1) {
                String name = se.html();
                String url = se.attr("href");
                series.add(new QSPSeries(name, url));
            }
            break;
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
        Elements elements = document.selectFirst("div.detail.col-pd").select("p");
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
        String path = "";
        Document document = Jsoup.parse(response);
        Element element = document.selectFirst("div.stui-player__video.clearfix").selectFirst("script");
        String urlStr = element.toString();
        int start = urlStr.indexOf("http");
        String endStr = ".m3u8";
        int end = urlStr.indexOf(".m3u8");
        path = urlStr.substring(start, end + endStr.length());
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
