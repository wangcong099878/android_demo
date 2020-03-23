package the.one.wallpaper.service;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.text.TextUtils;
import android.view.SurfaceHolder;

import java.io.IOException;

import the.one.wallpaper.constant.WallpaperConstant;
import the.one.wallpaper.util.WallpaperSpUtil;

/**
 * thx for https://github.com/songixan/Wallpaper
 */
public abstract class BaseDynamicWallpaper extends WallpaperService {

    public final String TAG = this.getClass().getSimpleName();

    public static void setToWallPaper(Activity context, Class c) {
        final Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(context, c));
        context.startActivityForResult(intent, WallpaperConstant.REQUEST_LIVE_PAPER);
    }

    public static void setVoice(Context context, boolean isSilence) {
        Intent intent = new Intent(WallpaperConstant.VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(WallpaperConstant.ACTION_VOICE, isSilence);
        context.sendBroadcast(intent);
    }


    class VideoEngine extends Engine {

        private MediaPlayer mMediaPlayer;

        @Override
        public void onCreate(final SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            IntentFilter intentFilter = new IntentFilter(WallpaperConstant.VIDEO_PARAMS_CONTROL_ACTION);
            registerReceiver(mVideoParamsControlReceiver, intentFilter);
        }

        BroadcastReceiver mVideoParamsControlReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isSilence = intent.getBooleanExtra(WallpaperConstant.ACTION_VOICE, false);
                setVoice(isSilence);
            }
        };

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setSurface(holder.getSurface());
            try {
                if (null != mMediaPlayer) {
                    String mPath = WallpaperSpUtil.getInstance().getWallpaperPath();
                    if (!TextUtils.isEmpty(mPath)) {
                        mMediaPlayer.setDataSource(mPath);
                        mMediaPlayer.setLooping(true);
                        setVoice(WallpaperSpUtil.getInstance().getWallpaperVoiceSwitch());
                        mMediaPlayer.prepare();
                        mMediaPlayer.start();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void setVoice(boolean isSilence) {
            float v = isSilence ? 1.0f : 0;
            if (null != mMediaPlayer)
                mMediaPlayer.setVolume(v, v);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                mMediaPlayer.start();
            } else {
                mMediaPlayer.pause();
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        @Override
        public void onDestroy() {
            unregisterReceiver(mVideoParamsControlReceiver);
            super.onDestroy();
        }
    }

}