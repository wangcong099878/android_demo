package the.one.demo.ui.fragment.sample;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import the.one.base.base.fragment.BaseDataFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.QMUIStatusBarHelper;
import the.one.base.widge.TheCollapsingTopBarLayout;
import the.one.demo.constant.NetUrlConstant;
import the.one.demo.R;
import the.one.demo.ui.adapter.GankAdapter;
import the.one.demo.bean.GankBean;
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
public class CollapsingTopBarFragment extends BaseDataFragment<GankBean> {

    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.topbar)
    QMUITopBar topbar;
    @BindView(R.id.collapsing_topbar_layout)
    TheCollapsingTopBarLayout collapsingTopbarLayout;

    private QMUIAlphaImageButton mBackBtn;

    private GankPresenter presenter;
    private boolean isChanged = false;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_collapsing_recyleview;
    }

    @Override
    protected boolean isNeedSpace() {
        return false;
    }

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected void onLazyInit() {
        super.onLazyInit();
        loadImage("http://theone.0851zy.com/2019/11/25/6e81f85c50e649cab8e0aca7493239bf.jpeg",ivPicture);
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        collapsingTopbarLayout.setTitle("时光机");
        collapsingTopbarLayout.setCollapsedTitleTextColor(getColorr(R.color.qmui_config_color_gray_1));
        mBackBtn = topbar.addLeftImageButton(R.drawable.mz_titlebar_ic_back_light,R.id.topbar_left_button);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        collapsingTopbarLayout.setStateChangeListener(new TheCollapsingTopBarLayout.AppBarStateChangeListener() {
            @Override
            public void onStateChanged(TheCollapsingTopBarLayout.State state, int offset) {
                if (state == TheCollapsingTopBarLayout.State.COLLAPSED) {
                    isChanged = true;
                    mBackBtn.setImageResource(R.drawable.mz_titlebar_ic_back_dark);
                    QMUIStatusBarHelper.setStatusBarLightMode(_mActivity);
                } else if (isChanged) {
                    isChanged = false;
                    mBackBtn.setImageResource(R.drawable.mz_titlebar_ic_back_light);
                    QMUIStatusBarHelper.setStatusBarDarkMode(_mActivity);
                }
            }
        });
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
