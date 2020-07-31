package the.one.base.util;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;
import java.util.List;

import the.one.base.ui.fragment.BaseFragment;
import the.one.base.util.glide.GlideEngine;

/**
 * @author The one
 * @date 2018/10/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SelectPictureUtil {

    private static final int DEFAULT_SPAN_COUNT = 4;
    private static final int DEFAULT_MAX_COUNT = 9;

    private static SelectPictureUtil selectPictureUtil;

    public static SelectPictureUtil getInstance() {
        if (null == selectPictureUtil)
            selectPictureUtil = new SelectPictureUtil();
        return selectPictureUtil;
    }

    public void initIcon(Fragment fragment,OnResultCallbackListener<LocalMedia> listener) {
        initImageSelector(fragment, true, DEFAULT_SPAN_COUNT, PictureConfig.SINGLE,1, null,false,false,90,200,false,listener);
    }

    public void initIconCrop(Fragment fragment,OnResultCallbackListener<LocalMedia> listener) {
        initImageSelector(fragment, true, DEFAULT_SPAN_COUNT, PictureConfig.SINGLE,1, null,true,true,90,200,false,listener);
    }

    public void initImageSelector(Fragment fragment,int maxSelect,List<LocalMedia> selectList, OnResultCallbackListener<LocalMedia> listener){
        initSelector(fragment,PictureMimeType.ofImage(),true,DEFAULT_SPAN_COUNT, PictureConfig.MULTIPLE,true, maxSelect,selectList,false,false,100,100,false,0,0,0,listener);
    }

    public void initImageSelector(Fragment fragment, boolean showCamera, int spanCount, int mode,int maxSelectNum,List<LocalMedia> selectList,boolean isCut, boolean isCompress, int cutOutQuality,int minCompressSize, boolean openClickSound, OnResultCallbackListener<LocalMedia> listener){
        initSelector(fragment,PictureMimeType.ofImage(),showCamera,spanCount,mode,true, maxSelectNum,selectList,isCut,isCompress,cutOutQuality,minCompressSize,openClickSound,0,0,0,listener);
    }

    public void initSingleVideoSelector(Fragment fragment, OnResultCallbackListener<LocalMedia> listener){
        initSelector(fragment,PictureMimeType.ofVideo(),false,DEFAULT_SPAN_COUNT,PictureConfig.SINGLE,true,1,null,false,false,0,0,false,10000,5,60,listener);
    }

    public void initVideoSelector(Fragment fragment, boolean showCamera, int spanCount, int mode,int maxSelectNum,List<LocalMedia> selectList, boolean openClickSound,
                                  int videoMaxSecond, int videoMinSecond, int recordSecond, OnResultCallbackListener<LocalMedia> listener){
        initSelector(fragment,PictureMimeType.ofVideo(),showCamera,spanCount,mode,true,maxSelectNum,selectList,false,false,0,0,openClickSound,videoMaxSecond,videoMinSecond,recordSecond,listener);
    }


    /**
     *
     * @param fragment
     * @param type   PictureMimeType.ofVideo()  PictureMimeType.ofImage()
     * @param showCamera 是否显示拍照按钮
     * @param spanCount 列数
     * @param mode   选择模式 PictureConfig.MULTIPLE or PictureConfig.SINGLE
     * @param singleReturn 单选时是否直接返回
     * @param maxSelectNum 最大选择数量
     * @param selectList 已选择
     * @param isCut 是否剪裁
     * @param isCompress 是否压缩
     * @param compressQuality 压缩质量 0 -100 default 90
     * @param minCompressSize 小于此大小不压缩
     * @param openClickSound 是否打开点击声音
     * @param videoMaxSecond 视频最大秒数
     * @param videoMinSecond 视频最小秒数
     * @param recordSecond 录屏最大秒数
     * @param listener 回调
     */
    public void initSelector(Fragment fragment, int type, boolean showCamera, int spanCount, int mode, boolean singleReturn,int maxSelectNum,List<LocalMedia> selectList,boolean isCut, boolean isCompress, int compressQuality,int minCompressSize, boolean openClickSound,
                             int videoMaxSecond, int videoMinSecond, int recordSecond, OnResultCallbackListener<LocalMedia> listener){
        PictureSelector.create(fragment)
                .openGallery(type)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .isCamera(showCamera)
                .imageSpanCount(spanCount)// 每行显示个数 int
                .selectionMode(mode)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .enableCrop(isCut)
                .isSingleDirectReturn(singleReturn)
                .selectionMedia(selectList)
                .maxSelectNum(maxSelectNum)
                .compress(isCompress)// 是否压缩 true or false
                .cutOutQuality(compressQuality)
                .withAspectRatio(1, 1)
                .hideBottomControls(true)
                .openClickSound(openClickSound)// 是否开启点击声音 true or false
                .compressSavePath(FileDirectoryUtil.getCachePath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .minimumCompressSize(minCompressSize)// 小于100kb的图片不压缩
                .setOutputCameraPath(File.separator + FileDirectoryUtil.getIndexPath() + File.separator + FileDirectoryUtil.getPicturePath())
                .videoMaxSecond(videoMaxSecond)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(videoMinSecond)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(recordSecond)//视频秒数录制 默认60s int
                .forResult(listener);
    }

}
