package the.one.demo;

/**
 * @author The one
 * @date 2018/8/10 0010
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class Constant {

    public static final String GANK_BASE = "http://gank.io/";
    public static final String GANK_API = GANK_BASE + "api/";
    public static final String GANK_CATEGORY = GANK_API + "data/";
    public static final String GANK_PUBLISH = GANK_API + "add2gank";

    public static final String WELFARE_BASE_URL = "http://www.mzitu.com/";

    public static final String HOT = "hot";
    public static final String XING_GAN = "xinggan";
    public static final String JAPAN = "japan";
    public static final String TAIWAN = "taiwan";
    public static final String MM = "mm";
    public static final String ZIPAI = "zipai";
    public static final String JIEPAI = "jiepai";
    public static final String BEST = "best";

    public static final String TODAY = GANK_API + "today";

    public static final String ANDROID = "Android";
    public static final String IOS = "iOS";
    public static final String APP = "App";
    public static final String RELAX = "休息视频";
    public static final String FRONT = "前端";
    public static final String EXTENSION = "拓展资源";
    public static final String RECOMMEND = "瞎推荐";
    public static final String WELFARE = "福利";

    public static String[] title = { Constant.ANDROID, Constant.APP,
            Constant.IOS, Constant.EXTENSION, Constant.RECOMMEND, Constant.FRONT, Constant.RELAX,Constant.WELFARE};

    public static String[] welfareTitle = { "推荐","火热","性感","清纯","日本","台湾","自拍","街拍"};

    public static String[] welfareUrl = { BEST,HOT, XING_GAN, MM, JAPAN, TAIWAN, ZIPAI,JIEPAI};

    public static final int COUNT = 20;

}
