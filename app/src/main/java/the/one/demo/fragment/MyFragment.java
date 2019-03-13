package the.one.demo.fragment;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import lib.homhomlib.design.SlidingLayout;
import the.one.base.base.activity.BaseWebViewActivity;
import the.one.base.base.fragment.BaseGroupListFragment;
import the.one.demo.Constant;
import the.one.demo.R;


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

    QMUICommonListItemView  Gank,Copy,QMUI,Adapter,NineGrid,Publish;

    @Override
    protected int getTopLayout() {
        return R.layout.custom_mine_head;
    }

    @Override
    protected void addGroupListView() {
        goneView(mTopLayout);
        slidingLayout.setSlidingMode(SlidingLayout.SLIDING_MODE_TOP);
        Gank = CreateItemView(TYPE_CHEVRON, "Gank.io");
        Copy = CreateItemView(TYPE_CHEVRON, "KotlinGankApp");
        QMUI = CreateItemView(TYPE_RIGHT_DETAIL,"QMUI","强烈推荐");
        Adapter = CreateItemView(TYPE_RIGHT_DETAIL,"BaseRecyclerViewAdapterHelper","适配器大佬");
        NineGrid = CreateItemView(TYPE_RIGHT_DETAIL,"NineGridLayout","仿朋友圈九宫格图片显示");

        Publish = CreateItemView(TYPE_CHEVRON, "发布");

        flTopLayout.setOnClickListener(this);

        QMUIGroupListView.newSection(_mActivity)
                .setTitle("感谢")
                .addItemView(Gank, this)
                .addItemView(Copy, this)
                .addTo(mGroupListView);

        QMUIGroupListView.newSection(_mActivity)
                .setTitle("第三方")
                .addItemView(QMUI, this)
                .addItemView(Adapter, this)
                .addItemView(NineGrid, this)
                .addTo(mGroupListView);

        QMUIGroupListView.newSection(_mActivity)
                .setTitle("")
                .addItemView(Publish, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startFragment(new PublishFragment());
                    }
                })
                .addTo(mGroupListView);
    }

    @Override
    public void onClick(View view) {
        String url ="https://gitee.com/theoneee/TheBase" ;
        String title = "Theoneee";
        if(view instanceof QMUICommonListItemView){
            title = ((QMUICommonListItemView) view).getText().toString();
            if (view == Gank) {
                url = Constant.GANK_BASE;
            }else if (view == QMUI) {
                url ="https://github.com/Tencent/QMUI_Android";
            }else if (view == Copy) {
                url ="https://github.com/JayGengi/KotlinGankApp";
            }else if(view == Adapter){
                url ="https://github.com/CymChad/BaseRecyclerViewAdapterHelper";
            }else if(view == NineGrid){
                url ="https://github.com/HMY314/NineGridLayout";
            }
        }
        BaseWebViewActivity.startThisActivity(_mActivity,title,url);
    }

}
