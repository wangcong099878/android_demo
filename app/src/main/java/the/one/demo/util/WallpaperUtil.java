package the.one.demo.util;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import the.one.demo.App;
import the.one.demo.bean.Wallpaper;

public class WallpaperUtil {

    private static WallpaperUtil videoUtil;

    public static WallpaperUtil getInstance() {
        if (null == videoUtil)
            videoUtil = new WallpaperUtil();
        return videoUtil;
    }

    public void getData(final OnCompleteListener listener) {
        DurationUtil durationUtils = new DurationUtil();
        WallpaperSpUtil sp = WallpaperSpUtil.getInstance();
        long MAX = sp.getMaxTime();
        long MIN = sp.getMinTime();

        ArrayList vList = new ArrayList<Wallpaper>();
        String[] mediaColumns = new String[]{MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.Video.VideoColumns.DURATION, MediaStore.Video.Thumbnails.DATA, MediaStore.MediaColumns.DATE_ADDED, MediaStore.Video.Media.HEIGHT, MediaStore.Video.Media.WIDTH};
        Cursor cursor = App.getInstance().getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns,
                null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            do {
                int width = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH));
                int height = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT));
                if (width < height) {
                    // 竖屏的视频才显示
                    long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    if (duration >= MIN && duration <= MAX) {
                        Wallpaper info = new Wallpaper();
                        info.path = cursor.getString(cursor
                                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                        info.size = duration;
                        info.duration = durationUtils.stringForTime((int) duration);
                        String videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
                        info.thumbPath = videoPath;
                        String addtime = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED));
                        info.addTime = Long.parseLong(addtime);
                        vList.add(info);
                    }
                }
            } while (cursor.moveToNext());
        }
        // 时间排序
        Collections.sort(vList, comp);
        listener.onSearchComplete(vList);
    }

    Comparator comp = new Comparator() {
        public int compare(Object o1, Object o2) {
            Wallpaper p1 = (Wallpaper) o1;
            Wallpaper p2 = (Wallpaper) o2;
            long time1 = p1.addTime;
            long time2 = p2.addTime;
            if (time1 < time2)
                return 1;
            else if (time1 == time2)
                return 0;
            else if (time1 > time2)
                return -1;
            return 0;
        }
    };

    /**
     * 将本地扫描的视频进行拆分（全部、历史、喜欢）
     */
//    public void SplitVideos(ArrayList<Wallpaper> wallpapers){
//        List<Wallpaper> all = new ArrayList<>();
//        List<Wallpaper> history = new ArrayList<>();
//        List<Wallpaper> like = new ArrayList<>();
//        // 循环判断视频类型，并加入到对应的数据里面
//        for(Wallpaper wallpaper : wallpapers){
//            // 判断数据库里是否存在此条视频信息（ 只有经过操作过后才加入到数据库 )
//            List<Wallpaper> list = LitePal.where("path = ?", wallpaper.getPath()).find(Wallpaper.class);
//            if(list.size()>0){
//                // 存在，则判断用户是否标记移除显示列表
//                Wallpaper wallpaper1 = list.get(0);
//                if(wallpaper1.isShow()){
//                    // 首先加入到全部里
//                    all.add(wallpaper1);
//                    // 判断是否是历史
//                    if(wallpaper1.isHistory()){
//                        history.add(wallpaper1);
//                    }
//                    //判断是否是喜欢
//                    if(wallpaper1.isLike()){
//                        like.add(wallpaper1);
//                    }
//                }
//            }else{
//                // 数据库里没有当前视频信息则直接加入到全部里
//                all.add(wallpaper);
//            }
//        }
//        // 将处理过后的数据保存到缓存用户页面数据显示（ 用户操作时，直接更改缓存里的数据和更改数据库，显示时直接用缓存的)
//        DataCache dataCache = DataCache.getInstance();
//        dataCache.setWallpapers(all);
//        dataCache.setHistory(history);
//        dataCache.setLike(like);
//    }

    public interface OnCompleteListener {
        void onSearchComplete(List<Wallpaper> datas);
    }


    /**
     * 判断是否点击了更换了动态壁纸
     *
     * @return ture 关闭应用 false 只是返回到了选择界面
     */
    public static boolean isLiveWallpaperChanged(Context context) {
       return getCurrentService(context).equals(WallpaperSpUtil.getInstance().getCurrentService());
    }

    public static String getCurrentService(Context context){
        String current  = "";
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);// 得到壁纸管理器
        WallpaperInfo wallpaperInfo = wallpaperManager.getWallpaperInfo();// 如果系统使用的壁纸是动态壁纸话则返回该动态壁纸的信息,否则会返回null
        if (wallpaperInfo != null) { // 如果是动态壁纸,则得到该动态壁纸的包名,并与想知道的动态壁纸包名做比较
            current = wallpaperInfo.getServiceName();
        }
       return current;
    }
}
