package the.one.demo.service.wallpaper;

public  class DynamicWallpaper1 extends BaseDynamicWallpaper {

    @Override
    public Engine onCreateEngine() {
        return new VideoEngine();
    }

}  