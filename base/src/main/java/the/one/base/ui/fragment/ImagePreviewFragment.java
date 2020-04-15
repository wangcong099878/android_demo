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

    @Override
    protected void requestServer() {
        Bundle bundle = getArguments();
        if(null == bundle) popBackStack();
        ImagePreviewEvent previewEvent = bundle.getParcelable(DataConstant.DATA);
        if(null == previewEvent) popBackStack();
        onComplete(previewEvent.getPreviewBeans());
        onNormal();
        recycleView.scrollToPosition(previewEvent.getPosition());
    }

    @Override
    public void onClick(ImagePreviewBean data) {

    }

    @Override
    public boolean onLongClick(ImagePreviewBean data) {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

}
