package the.one.demo.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.section.QMUISection;

import java.util.ArrayList;

import the.one.base.adapter.BaseSectionAdapter;
import the.one.base.util.StringUtils;
import the.one.base.widge.NineGridView;
import the.one.demo.R;
import the.one.demo.ui.bean.HomeHeadSection;
import the.one.demo.ui.bean.HomeItemSection;


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
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class HomeAdapter extends BaseSectionAdapter<HomeHeadSection, HomeItemSection> {

    public HomeAdapter() {
        super(R.layout.item_home_head,R.layout.item_home_item);
    }

    @Override
    protected void onBindSectionHeader(ViewHolder holder, int position, QMUISection<HomeHeadSection, HomeItemSection> section) {
        TextView tvTitle = holder.itemView.findViewById(R.id.tv_title);
        tvTitle.setText(section.getHeader().title);
    }

    @Override
    protected void onBindSectionItem(ViewHolder holder, int position, QMUISection<HomeHeadSection, HomeItemSection> section, int itemIndex) {
        TextView tvContent = holder.itemView.findViewById(R.id.tv_content);
        NineGridView nineImageLayout = holder.itemView.findViewById(R.id.nine_grid);
        HomeItemSection itemSection = section.getItemAt(itemIndex);
        String author = "#" + itemSection.remark + "  ";
        tvContent.setText(StringUtils.SpannableString(StringUtils.Type.ForegroundColorSpan,author + itemSection.content, author, ContextCompat.getColor(mContext, R.color.qmui_config_color_blue)));
        if (null == itemSection.images || itemSection.images.size() < 1) {
            if(itemSection.url.endsWith(".jpg")){
                ArrayList<String> strings = new ArrayList<>();
                strings.add(itemSection.url);
                nineImageLayout.setUrlList(strings);
            }else{
                nineImageLayout.setUrlList(null);
            }
        } else
            nineImageLayout.setUrlList(itemSection.images);
    }

}
