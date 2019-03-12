package the.one.demo.adapter;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import the.one.base.util.GlideUtil;
import the.one.base.widge.ScaleImageView;
import the.one.demo.R;
import the.one.demo.model.GankBean;

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
        imageView.setInitSize(item.getWidth(), item.getHeight());
        GlideUtil.load(mContext, item.getUrl(), imageView);
    }
}