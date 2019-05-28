package the.one.base.base.fragment;

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

import android.annotation.SuppressLint;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITabSegment;

import the.one.base.R;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseHomeFragment extends BaseTabFragment {

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.base_index;
    }

    @Override
    protected void initView(View rootView) {
        mTabSegment = rootView.findViewById(R.id.tab_segment);
        mViewPager = rootView.findViewById(R.id.qmui_view_pager);
        super.initView(rootView);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initTabAndPager() {
        mTabSegment.setMode(QMUITabSegment.MODE_FIXED);
        //是否有 Indicator
        mTabSegment.setHasIndicator(false);
        mTabSegment.setShowAnimation(true);
        super.initTabAndPager();
    }

}
