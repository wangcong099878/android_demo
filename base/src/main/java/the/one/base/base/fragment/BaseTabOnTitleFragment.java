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

import android.graphics.Color;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import the.one.base.R;
import the.one.base.util.StatusBarUtil;
import the.one.base.widge.QMUITabSegment;

/**
 * @author The one
 * @date 2019/1/25 0025
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public abstract class BaseTabOnTitleFragment extends BaseTabFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.simple_viewpager_layout;
    }

    @Override
    protected void initView(View rootView) {
        mViewPager = rootView.findViewById(R.id.view_pager);
        mTabSegment = (QMUITabSegment) getView(R.layout.simple_tab_segment_layout);
        mTopLayout.setCenterView(mTabSegment);
        super.initView(rootView);
    }

    @Override
    protected void initTabAndPager() {
        // 判断TitleBar的背景颜色是否为白色，如果不是则改变TabLayout的背景颜色和TitleBar相同，字体颜色更改为白色
        if(StatusBarUtil.isTranslucent(_mActivity)){
            mTabSegment.setDefaultNormalColor(Color.WHITE);
            mTabSegment.setDefaultSelectedColor(Color.WHITE);
            BaseDataFragment.setMargins(mTabSegment,0,0,0, QMUIDisplayHelper.dp2px(getContext(), 10));
        }
        //是否有 Indicator
        mTabSegment.setHasIndicator(true);
        // Indicator的方向 ture为上方 false为下方
        mTabSegment.setIndicatorPosition(false);
        // 置 indicator的宽度是否随内容宽度变化
        mTabSegment.setIndicatorWidthAdjustContent(true);
        super.initTabAndPager();
    }
}
