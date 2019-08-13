package the.one.base.util;

import android.support.v4.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

/**
 * @author The one
 * @date 2018/10/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SelectPictureUtil {

    private static SelectPictureUtil selectPictureUtil;

    public static SelectPictureUtil getInstance() {
        if (null == selectPictureUtil)
            selectPictureUtil = new SelectPictureUtil();
        return selectPictureUtil;
    }

    public void initIcon(Fragment fragment) {
        initSelectPicture(fragment, null, true, false, 3, PictureConfig.SINGLE, 1);
    }

    public void initIconCrop(Fragment fragment) {
        initSelectPicture(fragment, null, true, true, 3, PictureConfig.SINGLE, 1);
    }

    public void initSelectPicture(Fragment fragment, List<LocalMedia> selectList) {
        initSelectPicture(fragment, selectList, true, false, 3, PictureConfig.MULTIPLE, 9);
    }

    public void initSelectPicture(Fragment fragment, List<LocalMedia> selectList, boolean showCamera, boolean crop, int spanCount, int mode, int maxSelect) {
        PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .isCamera(showCamera)
                .imageSpanCount(spanCount)// 每行显示个数 int
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .selectionMode(mode)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .maxSelectNum(maxSelect)// 最大图片选择数量
                .selectionMedia(selectList)
                .enableCrop(crop)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(1, 1)
                .hideBottomControls(true)
                .isGif(false)
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .cropCompressQuality(40)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .compressSavePath(FileDirectoryUtil.getCachePath())//压缩图片保存地址
                .setOutputCameraPath(File.separator + FileDirectoryUtil.getIndexPath() + File.separator + FileDirectoryUtil.getPicturePath())
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    public void initSelectVideo(Fragment fragment) {
        initSelectVideo(fragment, true, 3, false, 100, 60, 5,
                60);
    }

    public void initSelectVideo(Fragment fragment, boolean showCamera, int spanCount, boolean isCompress, int compressRate,
                                int maxSecond, int minSecond, int recordSecond) {
        PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofVideo())
                .isCamera(showCamera)
                .imageSpanCount(spanCount)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .compress(isCompress)// 是否压缩 true or false
                .cropCompressQuality(compressRate)
                .withAspectRatio(1, 1)
                .hideBottomControls(true)
                .openClickSound(false)// 是否开启点击声音 true or false
                .compressSavePath(FileDirectoryUtil.getCachePath())//压缩图片保存地址
                .setOutputCameraPath(File.separator + FileDirectoryUtil.getIndexPath() + File.separator + FileDirectoryUtil.getPicturePath())
                .videoMaxSecond(maxSecond)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(minSecond)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(recordSecond)//视频秒数录制 默认60s int
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
}
