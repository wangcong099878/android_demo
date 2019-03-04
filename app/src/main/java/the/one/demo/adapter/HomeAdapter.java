package the.one.demo.adapter;

import android.widget.TextView;

import com.qmuiteam.qmui.widget.section.QMUISection;

import the.one.base.adapter.BaseSectionAdapter;
import the.one.demo.R;
import the.one.demo.model.HomeHeadSection;
import the.one.demo.model.HomeItemSection;


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
public class HomeAdapter extends BaseSectionAdapter<HomeHeadSection,HomeItemSection> {

    public HomeAdapter() {
        super(R.layout.item_home_item, R.layout.item_home_head);
    }

    @Override
    protected void onBindSectionHeader(ViewHolder holder, int position, QMUISection<HomeHeadSection, HomeItemSection> section) {
        TextView tvTitle = holder.itemView.findViewById(R.id.tv_title);
        tvTitle.setText(section.getHeader().title);
    }

    @Override
    protected void onBindSectionItem(ViewHolder holder, int position, QMUISection<HomeHeadSection, HomeItemSection> section, int itemIndex) {
        TextView tvContent = holder.itemView.findViewById(R.id.tv_content);
        HomeItemSection itemSection = section.getItemAt(itemIndex);
        tvContent.setText(itemSection.content);
    }
}
