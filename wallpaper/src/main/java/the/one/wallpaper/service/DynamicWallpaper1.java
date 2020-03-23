package the.one.wallpaper.service;

import android.service.wallpaper.WallpaperService;

public  class DynamicWallpaper1 extends BaseDynamicWallpaper {

    @Override
    public WallpaperService.Engine onCreateEngine() {
        return new VideoEngine();
    }

}  