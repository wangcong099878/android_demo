package the.one.anastasia.util;

import android.os.Environment;

import java.io.File;

/**
 * @author The one
 * @date 2018/9/20 0020
 * @describe 文件目录
 * @email 625805189@qq.com
 * @remark
 */
public class TheFileDirectoryUtil {

    /**
     * 根目录
     */
    public static final String INDEX = "YYC";
    /**
     * 下载目录
     */
    public static final String DOWNLOAD = "Download";
    /**
     * 图片目录
     */
    public static final String PICTURE = "Picture";
    /**
     * 视频目录
     */
    public static final String VIDEO = "Video";
    /**
     * 缓存目录
     */
    public static final String CACHE = "Cache";
    /**
     * 更新包名字
     */
    public static final String UPDATE_APK_NAME = "update.apk";

    /**
     * 获取根目录
     * @return
     */
    public static String getIndexPath (){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + INDEX;
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        return path;
    }

    /**
     * 获取下载目录
     * @return
     */
    public static String getDownloadPath(){
        String path = getIndexPath() + File.separator + DOWNLOAD;
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        return path ;
    }

    /**
     * 获取更新包路径
     * @return
     */
    public static String getUpdateAPKDownloadPath(){
        return getDownloadPath() + File.separator + UPDATE_APK_NAME;
    }

    /**
     * 获取图片目录
     * @return
     */
    public static String getPicturePath(){
        return getIndexPath() + File.separator + PICTURE ;
    }

    /**
     * 获取视频目录
     * @return
     */
    public static String getVideoPath(){
        return getIndexPath() + File.separator + VIDEO ;
    }

    /**
     * 获取视频目录
     * @return
     */
    public static String getCachePath(){
        return getIndexPath() + File.separator + CACHE ;
    }

}
