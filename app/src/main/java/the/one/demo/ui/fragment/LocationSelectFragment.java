package the.one.demo.ui.fragment;

import android.Manifest;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import the.one.base.Interface.OnProvinceCompleteListener;
import the.one.base.Interface.OnCitySelectListener;
import the.one.base.model.Area;
import the.one.base.model.City;
import the.one.base.model.Province;
import the.one.base.ui.fragment.BaseGroupListFragment;
import the.one.base.util.ProvinceUtil;
import the.one.base.util.QMUIDialogUtil;
import the.one.base.widge.TheCitySelectBottomSheetBuilder;
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
public class LocationSelectFragment extends BaseGroupListFragment implements OnCitySelectListener, Observer<Boolean> {

    private QMUICommonListItemView mLetterSearch, mDialog,mUpdate;
    private View mClickView;

    @Override
    protected void addGroupListView() {
        setTitleWithBackBtn("地址选择");

        mDialog = CreateDetailItemView("Dialog样式", "适用于选择地址",false,true);
        mLetterSearch = CreateDetailItemView("侧边快速查询样式", "适用于选择城市",false, true);

        mUpdate = CreateNormalItemView("更新本地JSON数据");

        addToGroup(getStringg(R.string.location_select_tips), getStringg(R.string.location_select_dialog_tips), mDialog);
        addToGroup(mLetterSearch);
        addToGroup("",getStringg(R.string.location_select_update),mUpdate);
    }

    @Override
    public void onClick(View v) {
        mClickView = v;
        requestPermission();
    }

    protected void requestPermission() {
        new RxPermissions(this)
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET)
                .subscribe(this);
    }

    @Override
    public void onCitySelect(Province province, City city, Area area, String address) {
        mDialog.setDetailText(address);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Boolean aBoolean) {
        if(aBoolean){
            if (mClickView == mLetterSearch) {
                startFragment(new CitySelectFragment());
            } else if (mClickView == mDialog) {
                new TheCitySelectBottomSheetBuilder(_mActivity)
                        .setOnCitySelectListener(this)
                        .setLBSProvince("贵州省")
                        .build().show();
            }else if(mClickView == mUpdate){
                showLoadingDialog("更新中");
                ProvinceUtil.getProvinceJsonData(new OnProvinceCompleteListener() {
                    @Override
                    public void onComplete(List<Province> provinces) {
                        hideLoadingDialog();
                        showSuccessTips("更新成功");
                    }

                    @Override
                    public void onError() {
                        hideLoadingDialog();
                        showSuccessTips("更新失败");
                    }
                });
            }
        }else{
            QMUIDialogUtil.showNegativeDialog(_mActivity, "提示", "需要用到存储权限,请打开。", "取消", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    dialog.dismiss();
                }
            }, "获取", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    dialog.dismiss();
                    requestPermission();
                }
            });
        }
    }

    @Override
    public void onError(Throwable e) {
        showFailTips("获取权限失败");
    }

    @Override
    public void onComplete() {

    }

}
