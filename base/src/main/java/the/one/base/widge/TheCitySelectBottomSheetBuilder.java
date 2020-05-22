package the.one.base.widge;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetBaseBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetListItemDecoration;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetRootLayout;
import com.qmuiteam.qmui.widget.tab.QMUIBasicTabSegment;
import com.qmuiteam.qmui.widget.tab.QMUITab;
import com.qmuiteam.qmui.widget.tab.QMUITabBuilder;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import the.one.base.Interface.OnProvinceCompleteListener;
import the.one.base.Interface.OnCitySelectListener;
import the.one.base.R;
import the.one.base.adapter.CitySelectAdapter;
import the.one.base.model.Area;
import the.one.base.model.City;
import the.one.base.model.Province;
import the.one.base.util.ProvinceUtil;
import the.one.base.util.RecyclerViewUtil;


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
    private QMUIRelativeLayout mTabParent;
    private QMUITabSegment mTabSegment;
    private RecyclerView mRecyclerView;
    private CitySelectAdapter mAdapter;

    private List<Province> mProvinces;
    private Province mSelectProvince;
    private City mSelectCity;
    private Area mSelectArea;

    private String mLBSProvince;

    private int mCurrentIndex = -1;
    private boolean isDividerShow = false;

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
        mTabParent  = mStatusLayout.findViewById(R.id.tab_parent);
        mTabSegment = mStatusLayout.findViewById(R.id.tab_segment);
        mRecyclerView = mStatusLayout.findViewById(R.id.recycle_view);
        mTabSegment.setOnTabClickListener(this);
        initRecyclerView(context);
        return mStatusLayout;
    }

    @Override
    protected void onAddCustomViewAfterContent(@NonNull QMUIBottomSheet bottomSheet, @NonNull QMUIBottomSheetRootLayout rootLayout, @NonNull Context context) {
        super.onAddCustomViewAfterContent(bottomSheet, rootLayout, context);
        initData();
    }

    private void initData() {
        mStatusLayout.showLoading();
        ProvinceUtil.getProvinceList(new OnProvinceCompleteListener() {
            @Override
            public void onComplete(List<Province> provinces) {
                if (null == provinces || provinces.size() == 0) {
                    mStatusLayout.showEmpty(null, "没有数据哦~", "", "刷新看看", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData();
                        }
                    });
                }
                List<Province> temp = new ArrayList<>();
                temp.addAll(provinces);
                mProvinces = new ArrayList<>();
                if (!TextUtils.isEmpty(mLBSProvince)) {
                    for (Province province : temp) {
                        if (province.getName().equals(mLBSProvince)) {
                            mProvinces.add(province);
                            temp.remove(province);
                            break;
                        }
                    }
                }
                mProvinces.addAll(temp);
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

    private void updateTabParentBottomDivider(boolean show) {
        if (show != isDividerShow) {
            isDividerShow = show;
            mTabParent.updateBottomDivider(0, 0, show ? 1 : 0, ContextCompat.getColor(mActivity, R.color.qmui_config_color_separator));
        }
    }

    private void initRecyclerView(Context context) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new QMUIBottomSheetListItemDecoration(context));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int y = RecyclerViewUtil.getScrollDistance(recyclerView);
                updateTabParentBottomDivider(y>10);
            }
        });
        mAdapter = new CitySelectAdapter();
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void addTab(int position) {
        QMUITabBuilder tabBuilder = mTabSegment.tabBuilder()
                .setGravity(Gravity.CENTER);
        String title = "请选择";
        if (position == 0 && null != mSelectProvince) {
            title = mSelectProvince.getName();
        } else if (position == 1 && null != mSelectCity) {
            title = mSelectCity.getName();
        }
        QMUITab tab = tabBuilder.setText(title)
                .setTextSize(QMUIDisplayHelper.sp2px(mActivity, 14), QMUIDisplayHelper.sp2px(mActivity, 16))
                .setNormalColor(ContextCompat.getColor(mActivity, R.color.qmui_config_color_gray_2))
                .setSelectColor(QMUIResHelper.getAttrColor(mActivity, R.attr.config_color))
                .build(mActivity);
        mTabSegment.addTab(tab);
    }

    @Override
    public void onTabClick(int index) {
        if (mCurrentIndex == index) return;
        mCurrentIndex = index;
        if (index == 0) {
            mSelectProvince = null;
            mSelectCity = null;
            mSelectArea = null;
        } else if (index == 1) {
            mSelectCity = null;
            mSelectArea = null;
        }
        mTabSegment.reset();
        setNetData(index);
    }


    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        Object data = adapter.getItem(position);
        if (data instanceof Province) {
            // 省份点击
            mSelectProvince = (Province) data;
            List<City> cities = mSelectProvince.getCityList();
            if (null == cities || cities.size() == 0) {
                // 只有省份的直接返回
                onSelect();
            } else {
                onTabClick(1);
            }
        } else if (data instanceof City) {
            // 选择了市
            mSelectCity = (City) data;
            List<Area> areas = mSelectCity.getAreaList();
            if (null == areas || areas.size() == 0) {
                // 只有市的直接返回
                onSelect();
            } else {
                onTabClick(2);
            }
        } else {
            // 返回数据
            mSelectArea = (Area) data;
            onSelect();
        }
    }

    /**
     * 设置新的数据
     */
    private void setNetData(int index) {
        for (int i = 0; i <= index; i++) {
            addTab(i);
        }
        mTabSegment.notifyDataChanged();
        mTabSegment.selectTab(index);
        if (index == 1) {
            // 市 的时候单独处理下, 比如北京、天津等只有两级的
            List<City> cities = mSelectProvince.getCityList();
            if (cities.size() == 1) {
                mSelectCity = cities.get(0);
                mAdapter.setNewInstance(mSelectCity.getAreaList());
            } else {
                mAdapter.setNewInstance(cities);
            }
        } else {
            mAdapter.setNewInstance(index == 0 ? mProvinces : mSelectCity.getAreaList());
        }
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    /**
     * 选择之后返回数据
     */
    private void onSelect() {
        if (null != listener) {
            listener.onCitySelect(mSelectProvince, mSelectCity, mSelectArea, getSelectAddress());
        }
        mDialog.dismiss();
    }

    /**
     * @return 获取选择的地址
     */
    private String getSelectAddress() {
        StringBuffer sb = new StringBuffer();
        if (null != mSelectProvince) {
            sb.append(mSelectProvince.getName());
        }
        // 为了防止返回的 mSelectCity 为null，这里获取全部地址时要处理只有两级的情况
        if (null != mSelectCity && !mSelectProvince.getName().equals(mSelectCity.getName())) {
            sb.append(mSelectCity.getName());
        }
        if (null != mSelectArea) {
            sb.append(mSelectArea.getName());
        }
        return sb.toString();
    }

}
