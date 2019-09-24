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

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import the.one.base.adapter.BaseRadiusShadowAdapter;
import the.one.demo.R;
import the.one.demo.bean.SimpleBean;

/**
 * @author The one
 * @date 2019/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class PullExtendAdapter extends BaseRadiusShadowAdapter<SimpleBean> {

    public PullExtendAdapter() {
        super(R.layout.item_pull_extend);
    }

    @Override
    protected void convert(BaseViewHolder helper, SimpleBean item) {
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        ivIcon.setImageResource(item.getRes());
        helper.setText(R.id.tv_title, item.getTitle());
        QMUILinearLayout linearLayout = helper.getView(R.id.ll_parent);
        setRadiusAndShadow(linearLayout);
    }
}