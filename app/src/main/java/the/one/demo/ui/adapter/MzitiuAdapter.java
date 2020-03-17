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
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import the.one.base.adapter.TheBaseQuickAdapter;
import the.one.base.adapter.TheBaseViewHolder;
import the.one.base.util.GlideUtil;
import the.one.base.widge.ScaleImageView;
import the.one.demo.Interface.IWelfare;
import the.one.demo.R;
import the.one.demo.bean.Mzitu;

/**
 * @author The one
 * @date 2019/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class MzitiuAdapter<T extends IWelfare> extends TheBaseQuickAdapter<T> {

    public MzitiuAdapter() {
        super(R.layout.item_welfare);
    }

    @Override
    protected void convert(TheBaseViewHolder helper, T item) {
        QMUILinearLayout linearLayout =helper.getView(R.id.container);
        setRadiusAndShadow(linearLayout);
        setData(helper,R.id.tv_title,item.getTitle());
        setData(helper,R.id.tv_date,item.getDate());
        ScaleImageView imageView = helper.getView(R.id.girl_item_iv);
        String refer = item.getRefer();
        if (TextUtils.isEmpty(refer)) {
            GlideUtil.load(mContext, item.getImageUrl(), imageView);
        } else {
            GlideUrl glideUrl = new GlideUrl(item.getImageUrl(), new LazyHeaders.Builder()
                    .addHeader("Referer", refer)
                    .build());
            Glide.with(mContext).load(glideUrl).into(imageView);
        }
    }

    private void setData(TheBaseViewHolder helper,int id,String content){
        TextView textView = helper.getView(id);
        if(TextUtils.isEmpty(content)){
            textView.setVisibility(View.GONE);
        }else{
            textView.setText(content);
            textView.setVisibility(View.VISIBLE);
        }
    }
}
