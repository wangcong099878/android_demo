package the.one.demo.fragment.titleTabViewPager;

import android.os.Bundle;

import the.one.base.constant.DataConstant;
import the.one.demo.fragment.SimpleDataFragment;


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
 * @date 2018/12/29 0029
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class TabDataFragment extends SimpleDataFragment {

    public static TabDataFragment newInstance(int type) {
        TabDataFragment fragment = new TabDataFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DataConstant.DATA, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected boolean showTitleBar() {
        return false;
    }
}
