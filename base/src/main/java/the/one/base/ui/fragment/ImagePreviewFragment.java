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

import android.os.Bundle;
import android.view.View;

import the.one.base.constant.DataConstant;
import the.one.base.event.ImagePreviewEvent;
import the.one.base.model.ImagePreviewBean;
import the.one.base.ui.presenter.BasePresenter;

/**
 * @author The one
 * @date 2020/4/15 0015
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ImagePreviewFragment extends BaseImageSnapFragment<ImagePreviewBean> {

    public static ImagePreviewFragment newInstance(ImagePreviewEvent previewEvent) {
        ImagePreviewFragment fragment = new ImagePreviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(DataConstant.DATA, previewEvent);
        fragment.setArguments(args);
        return fragment;
    }

    private int mImageCount;
    private ImagePreviewEvent mPreviewEvent;

    @Override
    protected boolean isStatusBarLightMode() {
        return super.isStatusBarLightMode() || mPreviewEvent.isWhiteTheme();
    }

    @Override
    protected void initView(View rootView) {
        Bundle bundle = getArguments();
        if (null == bundle) popBackStack();
        mPreviewEvent = bundle.getParcelable(DataConstant.DATA);
        if (null == mPreviewEvent) popBackStack();
        super.initView(rootView);
    }

    @Override
    protected void requestServer() {
        onComplete(mPreviewEvent.getPreviewBeans());
        mImageCount = mPreviewEvent.getPreviewBeans().size();
        onNormal();
        recycleView.scrollToPosition(mPreviewEvent.getPosition());
        onScrollChanged(null, mPreviewEvent.getPosition());
    }

    @Override
    public void onScrollChanged(ImagePreviewBean previewBean, int position) {
        if (mImageCount > 1)
            mTopLayout.setTitle(++position + "/" + mImageCount);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onVideoClick(ImagePreviewBean data) {

    }

    @Override
    public boolean onImageLongClick(ImagePreviewBean data) {
        showBottomSheetDialog(data);
        return true;
    }
}
