package the.one.demo.ui.presenter;

import java.util.List;

import the.one.base.base.presenter.BaseDataPresenter;
import the.one.base.model.SamplePageInfoBean;
import the.one.demo.bean.Wallpaper;
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
 * @date 2019/4/16 0016
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class WallpaperPresenter extends BaseDataPresenter<Wallpaper> {

    public void getLocationWallpaper() {
        WallpaperUtil.getInstance().getData(new WallpaperUtil.OnCompleteListener() {
            @Override
            public void onSearchComplete(List<Wallpaper> wallpapers) {
                onComplete(wallpapers, new SamplePageInfoBean(1, 1));
            }
        });
    }

}
