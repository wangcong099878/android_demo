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

import android.view.View;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.widget.QMUITabSegment;

import the.one.base.R;

/**
 * @author The one
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseIndexFragment extends BaseTabFragment {

    protected FrameLayout flTopLayout;

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.base_bottom_item;
    }

    @Override
    protected void initView(View rootView) {
        flTopLayout = rootView.findViewById(R.id.fl_top_layout);
        mTabSegment = rootView.findViewById(R.id.tab_segment);
        mViewPager = rootView.findViewById(R.id.qmui_view_pager);
        super.initView(rootView);
    }

    @Override
    protected void initTabAndPager() {
        super.initTabAndPager();
        mTabSegment.setHasIndicator(false);
        mTabSegment.setMode(QMUITabSegment.MODE_FIXED);
    }

}
