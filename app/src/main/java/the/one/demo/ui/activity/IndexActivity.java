package the.one.demo.ui.activity;

import the.one.base.base.activity.BaseFragmentActivity;
import the.one.base.base.fragment.BaseFragment;
import the.one.demo.ui.fragment.IndexFragment;


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
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class IndexActivity extends BaseFragmentActivity {

    @Override
    protected boolean LightMode() {
        return false;
    }

    @Override
    protected boolean isTranslucent() {
        return false;
    }

    @Override
    protected BaseFragment getBaseFragment() {
//        FileDirectoryUtil.getCachePath();  测试文件夹创建
        return new IndexFragment();
    }
}
