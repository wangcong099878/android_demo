package the.one.base.ui.activity;

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

import the.one.base.R;
import the.one.base.constant.DataConstant;
import the.one.base.ui.fragment.BaseFragment;
import the.one.base.ui.fragment.ImagePreviewFragment;
import the.one.base.util.ImagePreviewUtil;

/**
 * @author The one
 * @date 2020/4/15 0015
 * @describe 图片预览 请使用{@link ImagePreviewUtil}
 * @email 625805189@qq.com
 * @remark
 */
public class ImagePreviewActivity extends BaseFragmentActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        Intent intent = getIntent();
        if(null == intent) finish();
        return ImagePreviewFragment.newInstance(intent.getParcelableExtra(DataConstant.DATA));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_still, R.anim.scale_exit);
    }
}
