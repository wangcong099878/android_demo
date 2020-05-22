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

import android.app.Activity;
import android.util.Log;

import com.luck.picture.lib.config.PictureMimeType;

import java.io.File;

import the.one.base.BaseApplication;
import the.one.base.Interface.ImageSnap;
import the.one.base.model.Download;
import the.one.base.service.DownloadService;
import the.one.base.util.imagepreview.ImageLoader;
import the.one.base.util.imagepreview.ImageUtil;

/**
 * @author The one
 * @date 2020/4/14 0014
 * @describe 下载相关工具类
 * @email 625805189@qq.com
 * @remark
 */
public class DownloadUtil {

    public static void startDownload(Activity context, ImageSnap data) {
        Download download = new Download(data.getImageUrl(), data.isVideo() ? "Video" : "Picture", getDownloadFileName(data.getImageUrl(), data.isVideo()));
        download.setImage(true);
        DownloadService.startDown(context, download);
        ToastUtil.showToast("开始下载");
    }

    public static String getDownloadFileName(String mUrl) {
        return getDownloadFileName(mUrl, false);
    }

    public static String getDownloadFileName(String mUrl, boolean isVideo) {
        StringBuffer sb = new StringBuffer();
        sb.append("download_")
                .append(System.currentTimeMillis())
                .append(getFileSuffix(mUrl, isVideo));
        return sb.toString();
    }

    /**
     * 获取下载文件的后缀
     * @param mUrl 下载地址
     * @param isVideo 是否为视频(视频这里单独出来），主要还是区分GIF和图片
     * @return
     */
    public static String getFileSuffix(String mUrl, boolean isVideo) {
        String suffix = ".jpg";
        // 一般图片已经是缓存了的。这个时候就可以用这个判断是否为GIF
        File cacheFile = ImageLoader.getGlideCacheFile(BaseApplication.getInstance(),mUrl);
        if (cacheFile != null && cacheFile.exists()) {
            boolean isCacheIsGif = ImageUtil.isGifImageWithMime(cacheFile.getAbsolutePath());
            if (isCacheIsGif) {
                return ".gif";
            }
        }
        if (isVideo) {
            suffix = " .mp4";
        } else if (PictureMimeType.isSuffixOfImage(mUrl)) {
            try {
                int position = mUrl.lastIndexOf(".");
                suffix = mUrl.substring(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return suffix;
    }

}
