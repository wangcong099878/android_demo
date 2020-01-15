package the.one.demo.ui.fragment.sample;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.base.fragment.BaseGroupListFragment;
import the.one.base.widge.ThePopupWindow;
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
 * @date 2020/1/14 0014
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ThePopupWindowFragment extends BaseGroupListFragment {

    private QMUICommonListItemView TOP_BOTTOM,BOTTOM_TOP,LEFT_RIGHT,RIGHT_LEFT;
    private ThePopupWindow mPopupWindow;

    @Override
    protected void addGroupListView() {
        initFragmentBack("ThePopupWindow");

        TOP_BOTTOM = CreateNormalItemView("从上到下");
        BOTTOM_TOP = CreateNormalItemView("从下到上");
        LEFT_RIGHT = CreateNormalItemView("从左到右");
        RIGHT_LEFT = CreateNormalItemView("从右到左");

        addToGroup("各种方向动画效果","解决了PopupWindow位置不准确问题",TOP_BOTTOM,BOTTOM_TOP,LEFT_RIGHT,RIGHT_LEFT);

    }

    private void initPopupWindow(){
        if(null == mPopupWindow){
            View contentView = getView(R.layout.sample_popupwindow_layout);
            contentView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.hide();
                }
            });
            mPopupWindow = new ThePopupWindow(_mActivity,mRootView,contentView,mTopLayout);
            mPopupWindow.setOutsideTouchable(false);
        }
    }

    @Override
    protected void onBackPressed() {
        if(null != mPopupWindow && mPopupWindow.isShowing()){
            mPopupWindow.hide();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        initPopupWindow();
        if(v == TOP_BOTTOM){
            mPopupWindow.setAnimStyle(ThePopupWindow.AnimStyle.TOP_2_BOTTOM);
        }else if(v == BOTTOM_TOP){
            mPopupWindow.setAnimStyle(ThePopupWindow.AnimStyle.BOTTOM_2_TOP);
        }else if(v == LEFT_RIGHT){
            mPopupWindow.setAnimStyle(ThePopupWindow.AnimStyle.LEFT_2_RIGHT);
        }else if(v == RIGHT_LEFT){
            mPopupWindow.setAnimStyle(ThePopupWindow.AnimStyle.RIGHT_2_LEFT);
        }
        mPopupWindow.show();
    }

}
