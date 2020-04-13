package the.one.base.ui.fragment;

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

import net.lucode.hackware.magicindicator.MagicIndicator;

import the.one.base.R;

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
        mMagicIndicator = new MagicIndicator(_mActivity);
        mTopLayout.setCenterView(mMagicIndicator);
        super.initView(rootView);
    }


}
