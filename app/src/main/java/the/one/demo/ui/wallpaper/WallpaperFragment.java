package the.one.demo.ui.wallpaper;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import the.one.base.base.fragment.BaseDataFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.adapter.WallpaperAdapter;
import the.one.demo.bean.Wallpaper;
import the.one.demo.constant.WallpaperConstant;
import the.one.demo.service.wallpaper.DynamicWallpaper1;
import the.one.demo.service.wallpaper.DynamicWallpaper2;
import the.one.demo.ui.presenter.WallpaperPresenter;
import the.one.demo.util.WallpaperSpUtil;
import the.one.demo.util.WallpaperUtil;


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
public class WallpaperFragment extends BaseDataFragment<Wallpaper> {

    private WallpaperPresenter presenter;
    private WallpaperSpUtil wallpaperSpUtil;

    @Override
    protected boolean onAnimationEndInit() {
        return false;
    }

    @Override
    protected int setType() {
        return TYPE_GRID;
    }

    @Override
    protected int setColumn() {
        return 3;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        initFragmentBack("动态壁纸");
        wallpaperSpUtil = WallpaperSpUtil.getInstance();
        return new WallpaperAdapter(_mActivity);
    }

    @Override
    protected void requestServer() {
        presenter.getLocationWallpaper();
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Wallpaper wallpaper = (Wallpaper) adapter.getItem(i);
        WallpaperSpUtil.getInstance().setWallpaperPath(wallpaper.path);
        String currentService = WallpaperUtil.getCurrentService(_mActivity);
        if (currentService.equals(WallpaperConstant.SERCIVE_2)) {
            wallpaperSpUtil.setCurrentService(WallpaperConstant.SERCIVE_1);
            DynamicWallpaper1.setToWallPaper(_mActivity, DynamicWallpaper1.class);
        } else {
            wallpaperSpUtil.setCurrentService(WallpaperConstant.SERCIVE_2);
            DynamicWallpaper2.setToWallPaper(_mActivity, DynamicWallpaper2.class);
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
