package the.one.base.base.fragment;

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃                  神兽保佑
//    ┃　　　┃                  永无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import the.one.base.R;
import the.one.base.adapter.FullyGridLayoutManager;
import the.one.base.adapter.GridImageAdapter;
import the.one.base.base.activity.PhotoWatchActivity;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.SelectPictureUtil;

/**
 * @author The one
 * @date 2019/4/24 0024
 * @describe 选择图片
 * @email 625805189@qq.com
 * @remark
 */
public class BasePictureSelectorFragment extends BaseFragment implements GridImageAdapter.onAddPicClickListener, OnItemClickListener {

    protected RecyclerView mRecyclerView;
    protected List<LocalMedia> mSelectList = new ArrayList<>();

//    protected GridImageAdapter mImageAdapter;
    protected GridImageAdapter mImageAdapter2;

    protected int getMaxSelectNum() {
        return 9;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.base_picture_selector_layout;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.recycle_view);
        flTopLayout = rootView.findViewById(R.id.fl_top_layout);
        flBottomLayout = rootView.findViewById(R.id.fl_bottom_layout);
        initAroundView();

        initLayoutManager();
//        mImageAdapter = new GridImageAdapter(this);
        mImageAdapter2 = new GridImageAdapter(_mActivity,this);
        mImageAdapter2.setSelectMax(getMaxSelectNum());
        mImageAdapter2.setList(mSelectList);
        mRecyclerView.setAdapter(mImageAdapter2);
        mImageAdapter2.setOnItemClickListener(this);
    }

    protected void initLayoutManager() {
        mRecyclerView.setLayoutManager(new FullyGridLayoutManager(_mActivity, 4));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    onResult();
                    break;
            }
        }
    }

    protected void onResult() {
        mImageAdapter2.setList(mSelectList);
    }

//    @Override
//    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        if (mSelectList.size() > 0) {
//            LocalMedia localMedia = (LocalMedia) adapter.getItem(position);
//            String pictureType = localMedia.getMimeType();
//            int mediaType = PictureMimeType.getMimeType(pictureType);
//            switch (mediaType) {
//                case 1:
//                    // 预览图片 可自定长按保存路径
//                    PictureSelector.create(_mActivity).externalPicturePreview(position, mSelectList, 500);
//                    break;
//                case 2:
//                    // 预览视频
//                    PictureSelector.create(_mActivity).externalPictureVideo(localMedia.getPath());
//                    break;
//                case 3:
//                    // 预览音频
//                    PictureSelector.create(_mActivity).externalPictureAudio(localMedia.getPath());
//                    break;
//            }
//        }
//    }

    @Override
    public void onAddPicClick() {
        SelectPictureUtil.getInstance().initSelectPicture(this, mSelectList);
    }

    @Override
    public void onItemClick(View v, int position) {
        if (mSelectList.size() > 0) {
            LocalMedia localMedia = (LocalMedia) mSelectList.get(position);
            String pictureType = localMedia.getMimeType();
            int mediaType = PictureMimeType.getMimeType(pictureType);
            switch (mediaType) {
                case PictureConfig.TYPE_VIDEO:
                    // 预览视频
                    PictureSelector.create(_mActivity).externalPictureVideo(localMedia.getPath());
                    break;
                case PictureConfig.TYPE_AUDIO:
                    // 预览音频
                    PictureSelector.create(_mActivity).externalPictureAudio(localMedia.getPath());
                    break;
                default:
                    ArrayList<String> images = new ArrayList<>();
                    images.add("http://abc.2008php.com/2013_Website_appreciate/2013-03-13/20130313000352.jpg");
                    for (LocalMedia media:mSelectList){
                        images.add(media.getPath());
                    }
                    PhotoWatchActivity.startThisActivity(_mActivity,v,images,position);
                    break;
            }
        }
    }
}
