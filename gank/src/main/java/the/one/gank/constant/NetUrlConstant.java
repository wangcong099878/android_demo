package the.one.gank.constant;

/**
 * @author The one
 * @date 2018/8/10 0010
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class NetUrlConstant {

    public static final String GANK_BASE = "http://gank.io/";
    public static final String GANK_API = GANK_BASE + "api/";
    public static final String GANK_CATEGORY = GANK_API + "data/";
    public static final String GANK_PUBLISH = GANK_API + "add2gank";


    public static final String TODAY = GANK_API + "today";

    public static final String ANDROID = "Android";
    public static final String IOS = "iOS";
    public static final String APP = "App";
    public static final String RELAX = "休息视频";
    public static final String FRONT = "前端";
    public static final String EXTENSION = "拓展资源";
    public static final String RECOMMEND = "瞎推荐";
    public static final String WELFARE = "福利";

    public static String[] title = { NetUrlConstant.ANDROID, NetUrlConstant.APP,
            NetUrlConstant.IOS, NetUrlConstant.EXTENSION, NetUrlConstant.RECOMMEND, NetUrlConstant.FRONT, NetUrlConstant.RELAX, NetUrlConstant.WELFARE};

    public static final int COUNT = 20;

}
