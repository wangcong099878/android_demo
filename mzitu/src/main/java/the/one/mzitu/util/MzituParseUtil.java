package the.one.mzitu.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import the.one.mzitu.bean.Mzitu;

/**
 * 解析工具
 */
public class MzituParseUtil {

    /**
     * 解析妹子图分类数据
     * @param response
     * @param fakeRefer
     * @return
     */
    public static List<Mzitu> parseMzituCategory(String response, String fakeRefer) {
        List<Mzitu> mzitus = new ArrayList<>();
        Document doc = Jsoup.parse(response);
        Elements items = doc.select("div.postlist").select("li");
        for (Element element : items) {
            Mzitu mzitu = new Mzitu();
            mzitu.setRefer(fakeRefer);
            Element imageInfo = element.select("img").first();
            mzitu.setUrl(imageInfo.attr("data-original"));
            mzitu.setLink(element.select("a[href]").attr("href"));
            try {
                mzitu.setWidth(Integer.parseInt(imageInfo.attr("width")));
                mzitu.setHeight(Integer.parseInt(imageInfo.attr("height")));
                mzitu.setTitle(imageInfo.attr("alt"));
                mzitu.setDate(element.select("span").last().html());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mzitus.add(mzitu);
        }
        return mzitus;
    }

    /**
     * 解析妹子图详情数据
     * @param response
     * @param url
     * @return
     */
    public static List<Mzitu> parseMzituDetail(String response, String url) {
        List<Mzitu> mzitus = new ArrayList<>();
        Document doc = Jsoup.parse(response);
        String firstUrl = doc.select("div.main-image").select("img").attr("src");
        String baseUrl = firstUrl.substring(0, firstUrl.length() - 6);
        try {
            Elements pageElement = doc.select("div.pagenavi").select("span");
            Element maxPageElement = pageElement.get(pageElement.size() - 2);
            int maxPage = Integer.parseInt(maxPageElement.html());
            for (int i = 1; i < maxPage; i++) {
                String index = String.valueOf(i);
                if (i < 10)
                    index = "0" + index;
                String imageUrl = baseUrl + index + ".jpg";
                mzitus.add(new Mzitu(imageUrl, url + "/" + i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mzitus;
    }

}
