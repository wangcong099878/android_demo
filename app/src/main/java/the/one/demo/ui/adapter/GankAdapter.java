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

import java.util.ArrayList;

import the.one.base.adapter.TheBaseQuickAdapter;
import the.one.base.adapter.TheBaseViewHolder;
import the.one.base.util.StringUtils;
import the.one.base.widge.NineGridView;
import the.one.demo.R;
import the.one.demo.bean.GankBean;

/**
 * @author The one
 * @date 2019/3/5 0005
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class GankAdapter extends TheBaseQuickAdapter<GankBean> {

    public GankAdapter() {
        super(R.layout.item_home_item);
    }

    @Override
    protected void convert(TheBaseViewHolder helper, GankBean item) {
        String author = "#" + item.getWho() + "  ";
        helper.setText(R.id.tv_content, StringUtils.SpannableString(author + item.getDesc(), author, getColor(R.color.qmui_config_color_blue),StringUtils.Type.ForegroundColorSpan));
        NineGridView nineImageLayout = helper.getView(R.id.nine_grid);
        showView(nineImageLayout);
        if (null == item.getImages() || item.getImages().size() < 1) {
            if(item.getUrl().endsWith(".jpg")){
                ArrayList<String> strings = new ArrayList<>();
                strings.add(item.getUrl());
                nineImageLayout.setUrlList(strings);
            }else{
                goneView(nineImageLayout);
                nineImageLayout.setUrlList(null);
            }
        } else
            nineImageLayout.setUrlList(item.getImages());
    }
}
