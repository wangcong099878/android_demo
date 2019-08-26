package the.one.demo.ui.presenter;

import java.util.List;

import the.one.base.base.presenter.BasePresenter;
import the.one.base.base.view.BaseDataView;
import the.one.demo.bean.Wallpaper;
import the.one.demo.util.WallpaperUtil;
import the.one.net.entity.PageInfoBean;


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
public class WallpaperPresenter extends BasePresenter<BaseDataView<Wallpaper>> {

    public void getLocationWallpaper() {
        WallpaperUtil.getInstance().getData(new WallpaperUtil.OnCompleteListener() {
            @Override
            public void onComplete(List<Wallpaper> wallpapers) {
                if (isViewAttached())
                    getView().onComplete(wallpapers,new PageInfoBean(1,1));
            }
        });
    }

}
