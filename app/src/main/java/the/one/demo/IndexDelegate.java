package the.one.demo;

import android.os.Bundle;
import android.view.View;

import the.one.base.base.BaseDelegate;
import the.one.base.base.BasePresenter;


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
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class IndexDelegate extends BaseDelegate {

    @Override
    protected int getContentViewId() {
        return R.layout.delegate_index;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View view) {
        mTopLayout.setTitle("Theone");
    }
}
