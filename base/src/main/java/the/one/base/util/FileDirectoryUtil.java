package the.one.base.util;

import android.os.Environment;

import java.io.File;

/**
 * @author The one
 * @date 2018/9/20 0020
 * @describe 文件目录
 * @email 625805189@qq.com
 * @remark
 */
public class FileDirectoryUtil {

    /**
     * 根目录
     */
    public static  String INDEX = "The one";
    /**
     * 下载目录
     */
    public static  String DOWNLOAD = "Download";
    /**
     * 图片目录
     */
    public static  String PICTURE = "Picture";
    /**
     * 视频目录
     */
    public static  String VIDEO = "Video";
    /**
     * 缓存目录
     */
    public static  String CACHE = "Cache";
    /**
     * 更新包名字
     */
    public static  String UPDATE_APK_NAME = "update.apk";

    /**
     * 获取根目录
     * @return
     */
    public static String getIndexPath (){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + INDEX;
    }

    /**
     * 获取下载目录
     * @return
     */
    public static String getDownloadPath(){
        return checkFileExists(getIndexPath() + File.separator + DOWNLOAD );
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
        return checkFileExists(getIndexPath() + File.separator + PICTURE) ;
    }

    /**
     * 获取视频目录
     * @return
     */
    public static String getVideoPath(){
        return checkFileExists(getIndexPath() + File.separator + VIDEO) ;
    }

    /**
     * 获取视频目录
     * @return
     */
    public static String getCachePath(){
        return checkFileExists(getIndexPath() + File.separator + CACHE );
    }

    private static String checkFileExists(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        return path;
    }

}
