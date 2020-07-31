package the.one.demo.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;

import the.one.base.ui.fragment.BaseCollapsingTopBarFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.util.QMUIStatusBarHelper;
import the.one.demo.R;
import the.one.demo.bean.GankBean;
import the.one.demo.constant.NetUrlConstant;
import the.one.demo.ui.adapter.GankAdapter;
import the.one.demo.ui.presenter.GankPresenter;


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
 * @date 2019/6/10 0010
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class CollapsingTopBarFragment extends BaseCollapsingTopBarFragment<GankBean> {

    ImageView ivPicture;
    private QMUIAlphaImageButton mBackBtn;

    private GankPresenter presenter;
    private boolean isChanged = false;


    @Override
    protected Object getCollapsingContentLayout() {
        ivPicture = new ImageView(_mActivity);
        ivPicture.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return ivPicture;
    }

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected void onLazyInit() {
        super.onLazyInit();
        loadImage("https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg", ivPicture);
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mCollapsingTopBarLayout.setTitle("时光机");
        mCollapsingTopBarLayout.setCollapsedTitleTextColor(getColorr(R.color.qmui_config_color_gray_1));
        mBackBtn = mTopBar.addLeftImageButton(R.drawable.mz_titlebar_ic_back_dark,false, R.id.topbar_left_button);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean isFirst = true;

    @Override
    public void showContentPage() {
        super.showContentPage();
        // 虽然这个方法只会被触发一次，但是为了谨慎还是做一次判断
        if (isFirst) {
            isFirst = false;
            mBackBtn.setImageResource(R.drawable.mz_titlebar_ic_back_light);
            // 刚进来时白色的背景,是LightMode,显示内容层时，顶部的图片已经显示了，这个时候要改变，之后再根据折叠状态去改变
            QMUIStatusBarHelper.setStatusBarDarkMode(_mActivity);
            mCollapsingTopBarLayout.addOnOffsetUpdateListener(new QMUICollapsingTopBarLayout.OnOffsetUpdateListener() {
                @Override
                public void onOffsetChanged(QMUICollapsingTopBarLayout layout, int offset, float expandFraction) {
                    if(offset == 0) return;
                    if (!isChanged && expandFraction < 0.5f) {
                        isChanged = true;
                        mBackBtn.setImageResource(R.drawable.mz_titlebar_ic_back_light);
                        QMUIStatusBarHelper.setStatusBarDarkMode(_mActivity);
                    } else if (isChanged && expandFraction > 0.5f) {
                        isChanged = false;
                        mBackBtn.setImageResource(R.drawable.mz_titlebar_ic_back_dark);
                        QMUIStatusBarHelper.setStatusBarLightMode(_mActivity);
                    }
                }
            });
        }
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new GankAdapter();
    }

    @Override
    protected void requestServer() {
        presenter.getData(_mActivity, NetUrlConstant.ANDROID, page);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new GankPresenter();
    }

}
