package the.one.base.base.fragment;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import the.one.base.Interface.OnAddressSelectorListener;
import the.one.base.R;
import the.one.base.model.Province;
import the.one.base.util.CityUtil;
import the.one.base.widge.AddressSelector;

/**
 * @author The one
 * @date 2018/10/30 0030
 * @describe 地址选择Dialog
 * @email 625805189@qq.com
 * @remark
 */
public class AddressSelectorFragment extends BaseDialogFragment implements View.OnClickListener {

    private static final String TAG = "AddressSelectorDialog";

    /**
     * 定位到的省份(设置后将定位的省份排到第一位
     */
    private String province;
    private OnAddressSelectorListener onAddressSelectorListener;

    public void setOnAddressSelectorListener(OnAddressSelectorListener onAddressSelectorListener) {
        this.onAddressSelectorListener = onAddressSelectorListener;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public static AddressSelectorFragment newInstance() {
        AddressSelectorFragment fragment = new AddressSelectorFragment();
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_address_selector_layout;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.parent_layout).setOnClickListener(this);
        addressSelector = view.findViewById(R.id.address_selector);
        addressSelector.setOnClickListener(this);
        initData();
        initSelector();
    }

    AddressSelector addressSelector;

    /**
     * 初始化选择器
     */
    private void initSelector() {
        addressSelector.setDatas(provinces);
        addressSelector.setOnAddressSelectorListener(onAddressSelectorListener);
    }

    private ArrayList<Province> provinces = new ArrayList<>();

    /**
     * 初始化数据
     * 拿assets下的json文件
     */
    private void initData() {
        if(provinces.size()==0) {
            List<Province> provinces1 = CityUtil.getProvinces(getContext());
            if (provinces1.size() > 0 && null != province) {
                // 将定位到的省份排序到第一位
                for (Province pro : provinces1) {
                    if (pro.getName().equals(province)) {
                        // 写入
                        provinces.add(pro);
                        // 并将原来的删除
                        provinces1.remove(pro);
                        break;
                    }
                }
            }
            provinces.addAll(provinces1);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.parent_layout){
            dismiss();
        }
    }
}
