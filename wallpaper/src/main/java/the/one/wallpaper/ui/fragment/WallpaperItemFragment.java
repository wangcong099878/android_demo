package the.one.wallpaper.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import the.one.base.constant.DataConstant;
import the.one.base.ui.fragment.BaseListFragment;
import the.one.base.ui.presenter.BasePresenter;
import the.one.wallpaper.bean.Wallpaper;
import the.one.wallpaper.constant.WallpaperConstant;
import the.one.wallpaper.service.DynamicWallpaper1;
import the.one.wallpaper.service.DynamicWallpaper2;
import the.one.wallpaper.ui.adapter.WallpaperAdapter;
import the.one.wallpaper.ui.presenter.WallpaperPresenter;
import the.one.wallpaper.util.WallpaperSpUtil;
import the.one.wallpaper.util.WallpaperUtil;


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
 * @date 2019/8/23 0023
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class WallpaperItemFragment extends BaseListFragment<Wallpaper> {

    public static WallpaperItemFragment newInstance(int type){
        WallpaperItemFragment fragment =  new WallpaperItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DataConstant.TYPE,type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private WallpaperPresenter presenter;
    private int mType;

    @Override
    protected int setType() {
        return TYPE_GRID;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        goneView(mTopLayout);
        mType = getArguments().getInt(DataConstant.TYPE);
        return new WallpaperAdapter();
    }

    @Override
    protected void requestServer() {
        presenter.getWallpaper(mType);
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Wallpaper wallpaper = (Wallpaper) adapter.getItem(i);
        WallpaperSpUtil.setWallpaperPath(wallpaper.path);
        String currentService = WallpaperSpUtil.getCurrentService(_mActivity);
        if (currentService.equals(WallpaperConstant.SERCIVE_2)) {
            WallpaperSpUtil.setCurrentService(WallpaperConstant.SERCIVE_1);
            WallpaperUtil.startWallPaper(_mActivity, DynamicWallpaper1.class);
        } else {
            WallpaperSpUtil.setCurrentService(WallpaperConstant.SERCIVE_2);
            WallpaperUtil.startWallPaper(_mActivity, DynamicWallpaper2.class);
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new WallpaperPresenter();
    }
}
