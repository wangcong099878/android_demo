package the.one.demo.service.wallpaper;

import android.service.wallpaper.WallpaperService;

public  class DynamicWallpaper2 extends BaseDynamicWallpaper {

    @Override
    public WallpaperService.Engine onCreateEngine() {
        return new VideoEngine();
    }

}  