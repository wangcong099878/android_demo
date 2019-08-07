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

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import the.one.base.R;
import the.one.base.adapter.FullyGridLayoutManager;
import the.one.base.adapter.GridImageAdapter;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.base.util.SelectPictureUtil;

/**
 * @author The one
 * @date 2019/4/24 0024
 * @describe 选择图片
 * @email 625805189@qq.com
 * @remark
 */
public class BasePictureSelectorFragment extends BaseFragment implements GridImageAdapter.onAddPicClickListener, BaseQuickAdapter.OnItemClickListener {

    protected RecyclerView mRecyclerView;
    protected List<LocalMedia> mSelectList = new ArrayList<>();

    protected GridImageAdapter mImageAdapter;

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
        mImageAdapter = new GridImageAdapter(this);
        mImageAdapter.setSelectMax(getMaxSelectNum());
        mImageAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mImageAdapter);
        mImageAdapter.setNewData(mSelectList);
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
        mImageAdapter.setNewData(mSelectList);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LocalMedia localMedia = (LocalMedia) adapter.getItem(position);
        String pictureType = localMedia.getPictureType();
        int mediaType = PictureMimeType.pictureToVideo(pictureType);
        switch (mediaType) {
            case 1:
                // 预览图片 可自定长按保存路径
                PictureSelector.create(_mActivity).externalPicturePreview(position, mSelectList);
                break;
            case 2:
                // 预览视频
                PictureSelector.create(_mActivity).externalPictureVideo(localMedia.getPath());
                break;
            case 3:
                // 预览音频
                PictureSelector.create(_mActivity).externalPictureAudio(localMedia.getPath());
                break;
        }
    }

    @Override
    public void onAddPicClick() {
        SelectPictureUtil.getInstance().initSelectPicture(this, mSelectList);
    }

}
