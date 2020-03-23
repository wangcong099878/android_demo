package the.one.wallpaper.ui.presenter;

import java.util.List;

import the.one.base.base.presenter.BaseDataPresenter;
import the.one.base.model.SamplePageInfoBean;
import the.one.wallpaper.bean.Wallpaper;
import the.one.wallpaper.constant.WallpaperConstant;
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
 * @date 2019/4/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class WallpaperPresenter extends BaseDataPresenter<Wallpaper> {

    public void getWallpaper(int type){
        if(type == WallpaperConstant.TYPE_VIDEO){
            getVideoWallpaper();
        }
//        else if(type == WallpaperConstant.TYPE_IMAGE){
//
//        }
        else{
            onComplete(null);
        }
    }

    public void getVideoWallpaper() {
        WallpaperUtil.getInstance().getData(new WallpaperUtil.OnCompleteListener() {
            @Override
            public void onSearchComplete(List<Wallpaper> wallpapers) {
                onComplete(wallpapers, new SamplePageInfoBean(1, 1));
            }
        });
    }

}
