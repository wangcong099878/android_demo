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
     * 热门
     */
    public static final String HOT = "hot/views/category/Article/count/"+COUNT;


}
