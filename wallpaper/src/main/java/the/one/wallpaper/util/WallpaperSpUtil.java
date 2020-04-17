package the.one.wallpaper.util;

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


import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;

import the.one.base.BaseApplication;
import the.one.base.util.SpUtil;
import the.one.wallpaper.App;
import the.one.wallpaper.constant.WallpaperConstant;

/**
 * @author The one
 * @date 2019/4/17 0017
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class WallpaperSpUtil {

    private static final String CURRENT_SERVICE = "current_service";
    private static final String WALLPAPER_PATH = "wallpaper_path";
    private static final String VOICE_SWITCH = "wallpaper_voice";

    private static final String MAX_TIME = "max_time";
    private static final String MIN_TIME = "min_time";


    public static void setCurrentService(String service) {
        SpUtil.getInstance().putString(CURRENT_SERVICE, service);
    }

    public static String getCurrentService() {
        return SpUtil.getInstance().getString(CURRENT_SERVICE, WallpaperConstant.SERCIVE_1);
    }

    public static void setWallpaperPath(String path) {
        putString(WALLPAPER_PATH,path);
    }

    public static String getWallpaperPath() {
        return getString(WALLPAPER_PATH);
    }

    public static void setWallpaperVoiceSwitch(boolean isSilence) {
        putBoolean(VOICE_SWITCH, isSilence);
    }

    public static boolean getWallpaperVoiceSwitch() {
      return   getBoolean(VOICE_SWITCH);
    }

    /**
     * 这里必须得用这个MODE_MULTI_PROCESS，而且每次都要获取，
     * 如果用一个静态的SharedPreferences，点击后在服务里是获取不到最新的
     * @return
     */
    private static SharedPreferences getSharedPreferences(){
        return BaseApplication.getInstance().getSharedPreferences("wallpaper", App.MODE_MULTI_PROCESS);
    }

    public static void putString(String key,String value){
        SharedPreferences.Editor editor =  getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key){
        return getSharedPreferences().getString(key,"");
    }

    public static void putBoolean(String key,boolean value){
        SharedPreferences.Editor editor =  getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key){
        return getSharedPreferences().getBoolean(key,false);
    }

    public static void setMinTime(String min) {
        SpUtil.getInstance().putString(MIN_TIME, min);
    }

    public static long getMinTime() {
        return new DurationUtil().String2Long(SpUtil.getInstance().getString(MIN_TIME, "5s"));
    }

    public static void setMaxTime(String min) {
        SpUtil.getInstance().putString(MAX_TIME, min);
    }

    public static long getMaxTime() {
        return new DurationUtil().String2Long(SpUtil.getInstance().getString(MAX_TIME, "1min"));
    }


    /**
     * 获取当前运行的壁纸服务
     * @param context
     * @return
     */
    public static String getCurrentService(Context context){
        String current  = "";
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);// 得到壁纸管理器
        WallpaperInfo wallpaperInfo = wallpaperManager.getWallpaperInfo();// 如果系统使用的壁纸是动态壁纸话则返回该动态壁纸的信息,否则会返回null
        if (wallpaperInfo != null) { // 如果是动态壁纸,则得到该动态壁纸的包名,并与想知道的动态壁纸包名做比较
            current = wallpaperInfo.getServiceName();
        }
        return current;
    }


    /**
     * 判断是否点击了更换了动态壁纸
     *
     * @return ture 关闭应用 false 只是返回到了选择界面
     */
    public static boolean isLiveWallpaperChanged(Context context) {
        return getCurrentService(context).equals(WallpaperSpUtil.getCurrentService());
    }


    /**
     * 检查当前运行的壁纸服务是不是此APP的
     * @param context
     * @return
     */
    public static boolean isCurrentAppWallpaper(Context context){
        String current = getCurrentService(context);
        return current.equals(WallpaperConstant.SERCIVE_1) || current.equals(WallpaperConstant.SERCIVE_2);
    }
}
