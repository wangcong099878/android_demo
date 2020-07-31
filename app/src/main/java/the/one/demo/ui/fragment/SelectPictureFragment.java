package the.one.demo.ui.fragment;

import android.view.View;

import the.one.base.ui.fragment.BasePictureSelectorFragment;
import the.one.base.util.SelectPictureUtil;
import the.one.demo.R;


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

/**
 * @author The one
 * @date 2019/4/24 0024
 * @describe 选择图片示例
 * @email 625805189@qq.com
 * @remark
 */
public class SelectPictureFragment extends BasePictureSelectorFragment {

    @Override
    protected Object getTopLayout() {
        return R.layout.fragment_select_picture;
    }

    @Override
    protected void initView(View rootView) {
        initFragmentBack("选择图片");
        super.initView(rootView);
    }

    @Override
    public void onAddPicClick() {
        SelectPictureUtil.getInstance().initImageSelector(this,getMaxSelectNum(),mSelectList,this);
    }

}
