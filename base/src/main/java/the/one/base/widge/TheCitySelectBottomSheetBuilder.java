package the.one.base.widge;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetBaseBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetListItemDecoration;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetRootLayout;
import com.qmuiteam.qmui.widget.tab.QMUIBasicTabSegment;
import com.qmuiteam.qmui.widget.tab.QMUITab;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.Interface.OnCitySelectListener;
import the.one.base.Interface.IProvinceListener;
import the.one.base.R;
import the.one.base.adapter.CitySelectAdapter;
import the.one.base.model.Area;
import the.one.base.model.City;
import the.one.base.model.Province;
import the.one.base.util.ProvinceUtil;


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
 * @date 2020/5/21 0021
 * @describe 地址选择
 * @email 625805189@qq.com
 * @remark
 */
public class TheCitySelectBottomSheetBuilder extends QMUIBottomSheetBaseBuilder<TheCitySelectBottomSheetBuilder>
        implements QMUIBasicTabSegment.OnTabClickListener, OnItemClickListener {

    private OnCitySelectListener listener;
    private Activity mActivity;
    private StatusLayout mStatusLayout;
    private QMUITabSegment mTabSegment;
    private RecyclerView mRecyclerView;
    private CitySelectAdapter mAdapter;

    private List<Province> mProvinces;
    private Province mSelectProvince;
    private City mSelectCity;
    private Area mSelectArea;

    private String mLBSProvince;

    private int mCurrentIndex = -1;

    public TheCitySelectBottomSheetBuilder(Activity context, OnCitySelectListener listener) {
        super(context);
        this.listener = listener;
        mActivity = context;
    }

    public TheCitySelectBottomSheetBuilder setLBSProvince(String lbsProvince) {
        this.mLBSProvince = lbsProvince;
        return this;
    }

    @Nullable
    @Override
    protected View onCreateContentView(@NonNull QMUIBottomSheet bottomSheet, @NonNull QMUIBottomSheetRootLayout rootLayout, @NonNull Context context) {
        mStatusLayout = (StatusLayout) LayoutInflater.from(context).inflate(R.layout.custom_city_select_dialog, null);
        mTabSegment = mStatusLayout.findViewById(R.id.tab_segment);
        mRecyclerView = mStatusLayout.findViewById(R.id.recycle_view);
        initTabSegment(context);
        initRecyclerView(context);
        return mStatusLayout;
    }

    @Override
    protected void onAddCustomViewAfterContent(@NonNull QMUIBottomSheet bottomSheet, @NonNull QMUIBottomSheetRootLayout rootLayout, @NonNull Context context) {
        super.onAddCustomViewAfterContent(bottomSheet, rootLayout, context);
        initData();
    }

    private void initData(){
        mStatusLayout.showLoading();
        ProvinceUtil.getProvinceList(new IProvinceListener() {
            @Override
            public void onComplete(List<Province> provinces) {
                if(null == provinces || provinces.size() == 0){
                    mStatusLayout.showEmpty(null, "没有数据哦~", "", "刷新看看", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData();
                        }
                    });
                }
                mProvinces = new ArrayList<>();
                if(!TextUtils.isEmpty(mLBSProvince)){
                    for (Province province:provinces){
                        if(province.getName().equals(mLBSProvince)){
                            mProvinces.add(province);
                            provinces.remove(province);
                            break;
                        }
                    }
                }
                mProvinces.addAll(provinces);
                onTabClick(0);
                mStatusLayout.showContent();
            }

            @Override
            public void onError() {
                mStatusLayout.showError(null, "获取数据失败",
                        "", "重新获取", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initData();
                            }
                        });
            }
        });
    }

    private void initTabSegment(Context context) {
        mTabSegment.setOnTabClickListener(this);
        mTabSegment.setIndicator(new QMUITabIndicator(QMUIDisplayHelper.dp2px(context, 2), false, true));
    }

    private void initRecyclerView(Context context) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new QMUIBottomSheetListItemDecoration(context));
        mAdapter = new CitySelectAdapter();
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void addTab(int position){
        QMUITabBuilder tabBuilder = mTabSegment.tabBuilder()
                .setGravity(Gravity.CENTER);
        String title = "请选择";
        if(position == 0 && null != mSelectProvince){
            title =mSelectProvince.getName();
        }else if(position == 1 && null != mSelectCity){
            title = mSelectCity.getName();
        }
        QMUITab tab = tabBuilder.setText(title)
                .setTextSize(QMUIDisplayHelper.sp2px(mActivity, 14),QMUIDisplayHelper.sp2px(mActivity, 14))
                .setNormalColor(ContextCompat.getColor(mActivity,R.color.qmui_config_color_gray_2))
                .setSelectColor(QMUIResHelper.getAttrColor(mActivity,R.attr.config_color))
                .build(mActivity);
        mTabSegment.addTab(tab);
    }

    @Override
    public void onTabClick(int index) {
        if(mCurrentIndex == index) return;
        mCurrentIndex = index;
        if(index == 0){
            mSelectProvince = null;
            mSelectCity = null;
            mSelectArea = null;
        }else if(index == 1){
            mSelectCity = null;
            mSelectArea = null;
        }
        mTabSegment.reset();
        setNetData(index);
    }


    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        Object data = adapter.getItem(position);
        if(data instanceof Province){
            // 省份点击
            mSelectProvince = (Province) data;
            List<City> cities = mSelectProvince.getCityList();
            if(null == cities || cities.size() == 0){
                // 只有省份的直接返回
                onSelect();
            }else{
                onTabClick(1);
            }
        }else if(data instanceof City){
            // 选择了市
            mSelectCity = (City) data;
            List<Area> areas = mSelectCity.getAreaList();
            if(null == areas || areas.size() == 0){
                // 只有市的直接返回
                onSelect();
            }else{
                onTabClick(2);
            }
        }else{
            // 返回数据
            mSelectArea = (Area) data;
            onSelect();
        }
    }

    /**
     * 设置新的数据
     */
    private void setNetData(int index){
        for (int i = 0;i <= index;i++){
            addTab(i);
        }
        mTabSegment.selectTab(index);
        mTabSegment.notifyDataChanged();
        mAdapter.setNewInstance(index== 0?mProvinces:index == 1?mSelectProvince.getCityList():mSelectCity.getAreaList());
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    private void onSelect(){
        if(null != listener){
            listener.onCitySelect(mSelectProvince,mSelectCity,mSelectArea,getSelectAddress());
        }
        mDialog.dismiss();
    }

    private String getSelectAddress(){
        StringBuffer sb = new StringBuffer();
        if(null != mSelectProvince){
            sb.append(mSelectProvince.getName());
        }
        if(null != mSelectCity){
            sb.append(mSelectCity.getName());
        }
        if(null != mSelectArea){
            sb.append(mSelectArea.getName());
        }
        return sb.toString();
    }
}
