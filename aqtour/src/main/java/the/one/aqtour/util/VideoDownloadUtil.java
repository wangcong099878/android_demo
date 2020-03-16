package the.one.aqtour.util;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import the.one.aqtour.m3u8downloader.M3U8Downloader;
import the.one.aqtour.m3u8downloader.bean.M3U8;
import the.one.aqtour.m3u8downloader.bean.M3U8Task;
import the.one.aqtour.m3u8downloader.bean.M3U8TaskState;

public class VideoDownloadUtil {

    public static List<M3U8Task> getVideoDownloadList(boolean isDownload){
        String order = isDownload?"desc":"asc";
        List<M3U8Task> m3U8Tasks = LitePal.order("createDate "+order).find(M3U8Task.class);
        List<M3U8Task> result = new ArrayList<>();
        for (M3U8Task task:m3U8Tasks){
            boolean isExit = M3U8Downloader.getInstance().checkM3U8IsExist(task.getUrl());
            if(isDownload && isExit){
                result.add(task);
            }else if(!isDownload && !isExit){
                // 下载成功了，但是文件不存在，说明文件被删除了
                if(task.getState() == M3U8TaskState.SUCCESS){
                    task.setState(M3U8TaskState.DELETE);
                }
                task.setProgress(0);
                result.add(task);
            }
        }
        return result;
    }

    /**
     * 检查地址是否存添加过
     * @param url
     * @return
     */
    public static boolean isExistDownloadList(String url){
        List<M3U8Task> m3U8Tasks = LitePal.where("url = ?",url).find(M3U8Task.class);
        return m3U8Tasks.size()>0;
    }

}
