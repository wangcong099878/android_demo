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

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import the.one.base.util.StringUtils;
import the.one.base.widge.NineGridView;
import the.one.demo.R;
import the.one.demo.ui.bean.GankBean;

/**
 * @author The one
 * @date 2019/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class GankAdapter extends BaseQuickAdapter<GankBean, BaseViewHolder> {

    public GankAdapter() {
        super(R.layout.item_home_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankBean item) {
        String author = "#" + item.getWho() + "  ";
        helper.setText(R.id.tv_content, StringUtils.SpannableString(StringUtils.Type.ForegroundColorSpan,author + item.getDesc(), author, ContextCompat.getColor(mContext, R.color.qmui_config_color_blue)));
        NineGridView nineImageLayout = helper.getView(R.id.nine_grid);
        if (null == item.getImages() || item.getImages().size() < 1) {
            if(item.getUrl().endsWith(".jpg")){
                ArrayList<String> strings = new ArrayList<>();
                strings.add(item.getUrl());
                nineImageLayout.setUrlList(strings);
            }else{
                nineImageLayout.setUrlList(null);
            }
        } else
            nineImageLayout.setUrlList(item.getImages());
    }
}
