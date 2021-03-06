package the.one.gank.ui.fragment;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.ui.activity.BaseWebExplorerActivity;
import the.one.base.ui.fragment.BaseGroupListFragment;
import the.one.gank.R;
import the.one.gank.constant.NetUrlConstant;
import the.one.gank.ui.activity.GankActivity;
import the.one.gank.util.APIVersionUtil;


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
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class MyFragment extends BaseGroupListFragment implements View.OnClickListener {

    QMUICommonListItemView Gank, Copy, QMUI, Adapter, NineGrid, Publish,Sample, API_VERSION;

    private boolean isV2;

    @Override
    protected boolean isNeedChangeStatusBarMode() {
        return true;
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return true;
    }

    @Override
    protected boolean showTitleBar() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    protected Object getTopLayout() {
        return R.layout.custom_mine_head;
    }

    @Override
    protected int getScrollViewParentBgColor() {
        return R.color.qmui_config_color_white;
    }

    @Override
    protected void addGroupListView() {
        Gank = CreateNormalItemView("Gank.io");
        Copy = CreateNormalItemView("KotlinGankApp");
        QMUI = CreateDetailItemView("QMUI", "强烈推荐");
        Adapter = CreateDetailItemView("BaseRecyclerViewAdapterHelper", "适配器");
        NineGrid = CreateDetailItemView("NineGridLayout", "仿朋友圈九宫格图片显示");

        Sample = CreateNormalItemView("使用示例");
        Publish = CreateNormalItemView("发布");

        isV2 = APIVersionUtil.isV2();
        API_VERSION = CreateNormalItemView("使用"+(isV2?"V1":"V2")+"版本");

        findViewByTopView(R.id.iv_icon).setOnClickListener(this);

        addToGroup("感谢", Gank, Copy);
        addToGroup("第三方", QMUI, Adapter, NineGrid);
        addToGroup(API_VERSION);

    }

    @Override
    public void onClick(View view) {
        String url = "https://gitee.com/theoneee/TheBase";
        String title = "Theoneee";
        if (view instanceof QMUICommonListItemView) {
            title = ((QMUICommonListItemView) view).getText().toString();
            if (view == Gank) {
                url = NetUrlConstant.GANK_BASE;
            } else if (view == QMUI) {
                url = "https://github.com/Tencent/QMUI_Android";
            } else if (view == Copy) {
                url = "https://github.com/JayGengi/KotlinGankApp";
            } else if (view == Adapter) {
                url = "https://github.com/CymChad/BaseRecyclerViewAdapterHelper";
            } else if (view == NineGrid) {
                url = "https://github.com/HMY314/NineGridLayout";
            } else if (view == Publish) {
                startFragment(new PublishFragment());
                return;
            }else if(view == API_VERSION){
                APIVersionUtil.setVersion(!isV2);
                startActivity(GankActivity.class,true);
                return;
            }
            BaseWebExplorerActivity.newInstance(_mActivity,title, url);
            return;
        }
        switch (view.getId()) {
            case R.id.iv_icon:
                BaseWebExplorerActivity.newInstance(_mActivity,title, url);
                break;
        }
    }


}
