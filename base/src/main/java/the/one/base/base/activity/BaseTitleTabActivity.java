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

import the.one.base.R;

/**
 * @author The one
 * @date 2019/1/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseTitleTabActivity extends BaseTabActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.base_title_tab_viewpager_layout;
    }

    @Override
    protected void initView(View rootView) {
        mViewPager = rootView.findViewById(R.id.view_pager);
        mTabSegment = rootView.findViewById(R.id.tab_segment);
        super.initView(rootView);
    }

    @Override
    protected void initTabAndPager() {
        //是否有 Indicator
        mTabSegment.setHasIndicator(true);
        // Indicator的方向 ture为上方 false为下方
        mTabSegment.setIndicatorPosition(false);
        // 置 indicator的宽度是否随内容宽度变化
        mTabSegment.setIndicatorWidthAdjustContent(true);
        super.initTabAndPager();
    }
}