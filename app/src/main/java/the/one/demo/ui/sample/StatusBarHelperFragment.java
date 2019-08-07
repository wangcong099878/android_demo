package the.one.demo.ui.sample;

import android.util.Log;
import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.base.fragment.BaseGroupListFragment;
import the.one.demo.util.QMUIStatusBarHelper;


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
 * @date 2019/6/19 0019
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class StatusBarHelperFragment extends BaseGroupListFragment {

    private QMUICommonListItemView mLightMode, mDarkMode;

    @Override
    protected void addGroupListView() {
        initFragmentBack("StatusBarHelper");

        mLightMode = CreateNormalItemView("设置状态栏黑色字体与图标");
        mDarkMode = CreateNormalItemView("设置状态栏白色字体与图标");

        addToGroup(mLightMode, mDarkMode);
    }

    @Override
    public void onClick(View v) {
        if (null != getBaseFragmentActivity())
            if (v == mLightMode) {
                Log.e(TAG, "onClick: setStatusBarLightMode" +mLightMode.getText().toString());
                boolean success = QMUIStatusBarHelper.setStatusBarLightMode(getBaseFragmentActivity());
                Log.e(TAG, "onClick: " + success);
            } else {
                Log.e(TAG, "onClick: setStatusBarDarkMode" +mDarkMode.getText().toString());
                boolean success = QMUIStatusBarHelper.setStatusBarDarkMode(getBaseFragmentActivity());
                Log.e(TAG, "onClick: " + success);
            }
        else
            showFailTips("getBaseFragmentActivity is null");
    }
}
