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


import the.one.base.BaseApplication;
import the.one.base.util.SpUtil;
import the.one.wallpaper.constant.WallpaperConstant;

/**
 * @author The one
 * @date 2019/4/17 0017
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class WallpaperSpUtil {

    private static WallpaperSpUtil wallpaperSpUtil;
    private static SpUtil preferencesUtil;

    private final String CURRENT_SERVICE = "current_service";
    private final String WALLPAPER_PATH = "wallpaper_path";
    private final String VOICE_SWITCH = "wallpaper_voice";

    private final String MAX_TIME = "max_time";
    private final String MIN_TIME = "min_time";

    public static WallpaperSpUtil getInstance() {
        if (null == wallpaperSpUtil)
            wallpaperSpUtil = new WallpaperSpUtil();
        if (null == preferencesUtil)
            preferencesUtil = SpUtil.getInstance();
        // 这里每次又要初始化一次 不然设置无效  视频的地址保存不了，服务里获取的时候还是上一个的
        preferencesUtil.init(BaseApplication.getInstance());
        return wallpaperSpUtil;
    }

    public void setCurrentService(String service) {
        preferencesUtil.putString(CURRENT_SERVICE, service);
    }

    public String getCurrentService() {
        return preferencesUtil.getString(CURRENT_SERVICE, WallpaperConstant.SERCIVE_1);
    }

    public void setWallpaperPath(String path) {
        preferencesUtil.putString(WALLPAPER_PATH, path);
    }

    public String getWallpaperPath() {
        return preferencesUtil.getString(WALLPAPER_PATH);
    }

    public void setWallpaperVoiceSwitch(boolean isSilence) {
        preferencesUtil.putBoolean(VOICE_SWITCH, isSilence);
    }

    public boolean getWallpaperVoiceSwitch() {
        return preferencesUtil.getBoolean(VOICE_SWITCH);
    }

    public void setMinTime(String min) {
        preferencesUtil.putString(MIN_TIME, min);
    }

    public long getMinTime() {
        return new DurationUtil().String2Long(preferencesUtil.getString(MIN_TIME, "5s"));
    }

    public void setMaxTime(String min) {
        preferencesUtil.putString(MAX_TIME, min);
    }

    public long getMaxTime() {
        return new DurationUtil().String2Long(preferencesUtil.getString(MAX_TIME, "1min"));
    }
}
