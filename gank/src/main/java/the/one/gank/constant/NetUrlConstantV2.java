package the.one.gank.constant;

import rxhttp.wrapper.annotation.Domain;

/**
 * @author The one
 * @date 2018/8/10 0010
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class NetUrlConstantV2 {

    public static final int COUNT = 20;

    @Domain(name = "v2Url")
    public static final String GANK_BASE = "https://gank.io/api/v2/";

    /**
     * 轮播图
     */
    public static final String BANNER = "banners";

    /**
     *
     * @param hot_type 可接受参数 views（浏览数） | likes（点赞数） | comments（评论数）
     * @param category category 可接受参数 Article | GanHuo | Girl
     * @return
     */
    public static  String getHotUrl(String hot_type,String category){
       return  "hot/"+hot_type+"/category/"+category+"/count/"+COUNT;
    }


    public static String getCategory(String category){
        return  "categories/"+category;
    }

    public static String getCategoryData(String category,String type,int page){
       return  "data/category/"+category+"/type/"+type+"/page/"+page+"/count/"+COUNT;
    }


    /**
     * 热门类型
     */
    public static final String HOT_TYPE_VIEWS = "views";
    public static final String HOT_TYPE_LIKES = "likes";
    public static final String HOT_TYPE_COMMENTS = "comments";

    /**
     * 热门分类
     */
    public static final String CATEGORY_ARTICLE = "Article";
    public static final String CATEGORY_GANHUO = "GanHuo";
    public static final String CATEGORY_GIRL = "Girl";


}
