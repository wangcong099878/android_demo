package the.one.base.ui.fragment;

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

import android.view.View;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnItemClickListener;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import the.one.base.R;
import the.one.base.adapter.FullyGridLayoutManager;
import the.one.base.adapter.GridImageAdapter;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.ImagePreviewUtil;
import the.one.base.util.SelectPictureUtil;

/**
 * @author The one
 * @date 2019/4/24 0024
 * @describe 选择图片
 * @email 625805189@qq.com
 * @remark
 */
public class BasePictureSelectorFragment extends BaseFragment implements GridImageAdapter.onAddPicClickListener, OnItemClickListener, OnResultCallbackListener<LocalMedia> {

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
        mImageAdapter = new GridImageAdapter(_mActivity,this);
        mImageAdapter.setSelectMax(getMaxSelectNum());
        mImageAdapter.setList(mSelectList);
        mRecyclerView.setAdapter(mImageAdapter);
        mImageAdapter.setOnItemClickListener(this);
    }

    protected void initLayoutManager() {
        mRecyclerView.setLayoutManager(new FullyGridLayoutManager(_mActivity, 4));
    }

    @Override
    public void onAddPicClick() {
        SelectPictureUtil.getInstance().initImageSelector(this,getMaxSelectNum(),mSelectList,this);
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
                    for (LocalMedia media:mSelectList){
                        images.add(media.getPath());
                    }
                    ImagePreviewUtil.start(_mActivity,images,position);
                    break;
            }
        }
    }

    @Override
    public void onResult(List<LocalMedia> result) {
        mSelectList = result;
        mImageAdapter.setList(result);
    }

    @Override
    public void onCancel() {

    }
}
