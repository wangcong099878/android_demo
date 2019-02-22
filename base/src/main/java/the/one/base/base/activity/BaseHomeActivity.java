package the.one.base.base.activity;

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
 * @date 2019/1/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseHomeActivity extends BaseTabActivity {

    protected FrameLayout flTopLayout;

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
        flTopLayout = rootView.findViewById(R.id.fl_top_layout);
        mTabSegment = rootView.findViewById(R.id.tab_segment);
        mViewPager = rootView.findViewById(R.id.qmui_view_pager);
        super.initView(rootView);
    }

    @Override
    protected void initTabAndPager() {
        mTabSegment.setMode(QMUITabSegment.MODE_FIXED);
        //是否有 Indicator
        mTabSegment.setHasIndicator(false);
        super.initTabAndPager();
    }
}
