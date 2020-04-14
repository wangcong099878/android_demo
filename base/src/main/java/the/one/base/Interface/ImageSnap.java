package the.one.base.Interface;


public interface ImageSnap {

    /**
     * @return 图片地址
     */
    String getImageUrl();

    /**
     * @return 有些图片需要加refer，没有就不用管
     */
    String getRefer();

    /**
     * @return 是否为视频，如果为视频，视频播放图标会显示
     */
    boolean isVideo();

}
