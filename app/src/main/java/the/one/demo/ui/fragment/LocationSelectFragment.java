package the.one.demo.ui.fragment;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import androidx.fragment.app.FragmentManager;
import the.one.base.Interface.OnAddressSelectorListener;
import the.one.base.base.fragment.AddressSelectorFragment;
import the.one.base.base.fragment.BaseGroupListFragment;
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
 * @date 2019/8/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class LocationSelectFragment extends BaseGroupListFragment {

    private QMUICommonListItemView mLetterSearch,mDialog;

    @Override
    protected void addGroupListView() {
        initFragmentBack("地理位置选择");
        mLetterSearch = CreateDetailItemView("侧边快速查询样式","适用于选择城市",true);
        mDialog = CreateDetailItemView("Dialog样式","适用于选择地址",true);
        addToGroup("",getStringg(R.string.location_select_tips),mDialog,mLetterSearch);
    }

    @Override
    public void onClick(View v) {
        if( v== mLetterSearch){
            startFragment(new the.one.base.base.fragment.CitySelectFragment());
        }else if(v == mDialog){
            showSearchDialogFragment();
        }
    }

    private long clickTime = 0;
    private FragmentManager manager;
    private AddressSelectorFragment selectorDialog;

    private void showSearchDialogFragment() {
        // 防止连续点击
        if (clickTime != 0 && System.currentTimeMillis() - clickTime < 1000)
            return;
        clickTime = System.currentTimeMillis();
        if (null == manager) {
            manager = getChildFragmentManager();
        }
        if (null == selectorDialog) {
            selectorDialog = AddressSelectorFragment.newInstance();
            // 如果要把定位到的省份放在首位
            selectorDialog.setProvince("贵州省");
            selectorDialog.setOnAddressSelectorListener(new OnAddressSelectorListener() {
                @Override
                public void onSelect(String address) {
                    mDialog.setDetailText(address);
                    selectorDialog.dismiss();
                }
            });
        }
        selectorDialog.show(manager, TAG);
    }
}
