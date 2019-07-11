package the.one.demo.ui.adapter;

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

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import the.one.base.util.GlideUtil;
import the.one.base.widge.ScaleImageView;
import the.one.demo.R;
import the.one.demo.ui.bean.GankBean;

/**
 * @author The one
 * @date 2019/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class WelfareAdapter extends BaseQuickAdapter<GankBean, BaseViewHolder> {

    public WelfareAdapter() {
        super(R.layout.item_welfare);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankBean item) {
        ScaleImageView imageView = helper.getView(R.id.girl_item_iv);
        if (item.getWidth() != 0)
            imageView.setInitSize(item.getWidth(), item.getHeight());
        String refer = item.getRefer();
        if (TextUtils.isEmpty(refer)) {
            GlideUtil.load(mContext, item.getUrl(), imageView);
        } else {
            GlideUrl glideUrl = new GlideUrl(item.getUrl(), new LazyHeaders.Builder()
                    .addHeader("Referer", refer)
                    .build());
            Glide.with(mContext).load(glideUrl).into(imageView);
        }
    }
}
