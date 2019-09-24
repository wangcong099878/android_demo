package the.one.demo.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import the.one.base.adapter.BaseRadiusShadowAdapter;
import the.one.base.util.GlideUtil;
import the.one.demo.R;
import the.one.demo.bean.Wallpaper;


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
public class WallpaperAdapter extends BaseRadiusShadowAdapter<Wallpaper> {

    public WallpaperAdapter() {
        super(R.layout.item_wallpaper);
    }

    @Override
    protected void convert(BaseViewHolder helper, Wallpaper item) {
        GlideUtil.load(mContext,item.path, (ImageView) helper.getView(R.id.iv_image));
        QMUIRelativeLayout relativeLayout = helper.getView(R.id.parent);
        mRadius = QMUIDisplayHelper.dp2px(mContext,3);
        setRadiusAndShadow(relativeLayout);
    }
}
