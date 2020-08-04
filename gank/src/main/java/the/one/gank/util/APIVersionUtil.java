package the.one.gank.util;

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

import the.one.base.util.SpUtil;

/**
 * @author The one
 * @date 2020/8/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class APIVersionUtil {

    private static final String VERSION = "api.version";

    public static boolean isV2(){
        return SpUtil.getInstance().getBoolean(VERSION,true);
    }

    public static void setVersion(boolean isV2){
        SpUtil.getInstance().putBoolean(VERSION,isV2);
    }

}
