package the.one.base.util;

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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author The one
 * @date 2019/4/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SpUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static String ShareName = "SpUtil";

    @SuppressLint("StaticFieldLeak")
    private static SpUtil spUtil;

    public static SpUtil getInstance(){
        if(null == spUtil)
            spUtil = new SpUtil();
        return spUtil;
    }

    public void init(Context context){
        SpUtil.context = context;
    }

    public void init(Context context,String shareName){
        SpUtil.context = context;
        SpUtil.ShareName = shareName;
    }

    public  SharedPreferences getSharedPreferences(){
        if(null == context){
            throw new IllegalStateException("SpUtil's context is null,init this in Application or extend BaseApplication.");
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(ShareName, Context.MODE_MULTI_PROCESS);
        return sharedPreferences;
    }

}
