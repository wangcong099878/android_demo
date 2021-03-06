package the.one.wallpaper.util;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import the.one.base.BaseApplication;
import the.one.wallpaper.bean.Wallpaper;
import the.one.wallpaper.constant.WallpaperConstant;

public class WallpaperUtil {

    private static WallpaperUtil videoUtil;

    public static WallpaperUtil getInstance() {
        if (null == videoUtil)
            videoUtil = new WallpaperUtil();
        return videoUtil;
    }

    public static void startWallPaper(Activity context, Class c) {
        final Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(context, c));
        context.startActivityForResult(intent, WallpaperConstant.REQUEST_LIVE_PAPER);
    }

    public static void sendVoice(Context context, boolean isSilence) {
        Intent intent = new Intent(WallpaperConstant.VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(WallpaperConstant.ACTION_VOICE, isSilence);
        context.sendBroadcast(intent);
    }

    public void getData(final OnCompleteListener listener) {
        DurationUtil durationUtils = new DurationUtil();
        long MAX = WallpaperSpUtil.getMaxTime();
        long MIN = WallpaperSpUtil.getMinTime();

        ArrayList vList = new ArrayList<Wallpaper>();
        String[] mediaColumns = new String[]{MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.Video.VideoColumns.DURATION, MediaStore.Video.Thumbnails.DATA, MediaStore.MediaColumns.DATE_ADDED, MediaStore.Video.Media.HEIGHT, MediaStore.Video.Media.WIDTH};
        Cursor cursor = BaseApplication.getInstance().getContentResolver().query(
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


}
