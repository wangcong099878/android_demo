package the.one.wallpaper.ui.adapter;

import android.widget.ImageView;

import com.qmuiteam.qmui.layout.QMUIRelativeLayout;

import the.one.base.adapter.TheBaseQuickAdapter;
import the.one.base.adapter.TheBaseViewHolder;
import the.one.base.util.glide.GlideUtil;
import the.one.wallpaper.R;
import the.one.wallpaper.bean.Wallpaper;


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
public class WallpaperAdapter extends TheBaseQuickAdapter<Wallpaper> {

    public WallpaperAdapter() {
        super(R.layout.item_wallpaper);
    }

    @Override
    protected int getRadius() {
        return 3;
    }

    @Override
    protected void convert(TheBaseViewHolder helper, Wallpaper item) {
        GlideUtil.load(getContext(),item.path, (ImageView) helper.getView(R.id.iv_image));
        QMUIRelativeLayout relativeLayout = helper.getView(R.id.parent);
        setRadiusAndShadow(relativeLayout);
    }
}
