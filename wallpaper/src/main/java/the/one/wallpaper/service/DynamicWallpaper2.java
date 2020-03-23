package the.one.wallpaper.service;

public  class DynamicWallpaper2 extends BaseDynamicWallpaper {

    @Override
    public Engine onCreateEngine() {
        return new VideoEngine();
    }

}  