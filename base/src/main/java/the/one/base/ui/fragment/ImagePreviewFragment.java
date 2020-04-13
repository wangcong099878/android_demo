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
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import the.one.base.Interface.ImageSnap;
import the.one.base.ui.presenter.BasePresenter;

import static the.one.base.constant.DataConstant.DATA;
import static the.one.base.constant.DataConstant.POSITION;

/**
 * @author The one
 * @date 2020/4/10 0010
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ImagePreviewFragment<T extends ImageSnap> extends BaseImageSnapFragment<T>{

    private ImagePreviewFragment() {}

    public static <T extends ImageSnap> ImagePreviewFragment  newInstance(List<T> datas,int position){
        ImagePreviewFragment<T> fragment = new ImagePreviewFragment<T>();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DATA, (ArrayList<? extends Parcelable>) datas);
        bundle.putInt(POSITION,position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private List<T> mImages;

    @Override
    protected void requestServer() {
        Bundle bundle = getArguments();
        assert bundle != null;
        mImages = Collections.unmodifiableList(bundle.getParcelableArrayList(DATA));
        onComplete(mImages);
        onNormal();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
