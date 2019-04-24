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
    private static  String INDEX = "The one";
    /**
     * 下载目录
     */
    private static  String DOWNLOAD = "Download";
    /**
     * 图片目录
     */
    private static  String PICTURE = "Picture";
    /**
     * 视频目录
     */
    private static  String VIDEO = "Video";
    /**
     * 缓存目录
     */
    private static  String CACHE = "Cache";
    /**
     * 更新包名字
     */
    private static  String UPDATE_APK_NAME = "update.apk";

    private static Builder mBuilder;

    public static Builder getBuilder(){
        if(null == mBuilder){
            mBuilder = new Builder();
        }
        return mBuilder;
    }

    /**
     * 获取根目录
     * @return
     */
    public static String getIndexPath (){
        return checkFileExists(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +INDEX);
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

    public static class Builder{

        /**
         * 根目录
         */
        public String mIndex = "The one";
        /**
         * 下载目录
         */
        public String mDownload = "Download";
        /**
         * 图片目录
         */
        public String mPicture = "Picture";
        /**
         * 视频目录
         */
        public String mVideo = "Video";
        /**
         * 缓存目录
         */
        public String mCache = "Cache";
        /**
         * 更新包名字
         */
        public String mUpdateApkName = "update.apk";

        public String getIndex() {
            return mIndex;
        }

        public Builder setIndex(String mIndex) {
            this.mIndex = mIndex;
            return this;
        }

        public String getDownload() {
            return mDownload;
        }

        public Builder setDownload(String mDownload) {
            this.mDownload = mDownload;
            return this;
        }

        public String getPicture() {
            return mPicture;
        }

        public Builder setPicture(String mPicture) {
            this.mPicture = mPicture;
            return this;
        }

        public String getVideo() {
            return mVideo;
        }

        public Builder setVideo(String mVideo) {
            this.mVideo = mVideo;
            return this;
        }

        public String getCache() {
            return mCache;
        }

        public Builder setCache(String mCache) {
            this.mCache = mCache;
            return this;
        }

        public String getUpdateApkName() {
            return mUpdateApkName;
        }

        public Builder setUpdateApkName(String mUpdateApkName) {
            this.mUpdateApkName = mUpdateApkName;
            return this;
        }

        public Builder build(){
            FileDirectoryUtil.INDEX = getIndex();
            FileDirectoryUtil.DOWNLOAD = getDownload();
            FileDirectoryUtil.PICTURE = getPicture();
            FileDirectoryUtil.VIDEO = getVideo();
            FileDirectoryUtil.CACHE = getCache();
            FileDirectoryUtil.UPDATE_APK_NAME = getUpdateApkName();
            return this;
        }
    }

}
