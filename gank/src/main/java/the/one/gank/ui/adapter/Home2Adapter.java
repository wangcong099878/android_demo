package the.one.gank.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.qmuiteam.qmui.util.QMUIResHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import the.one.base.adapter.TheBaseViewHolder;
import the.one.base.util.StringUtils;
import the.one.base.widge.NineGridView;
import the.one.gank.R;
import the.one.gank.bean.GankBean;
import the.one.gank.bean.HomeSection;


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
public class Home2Adapter extends BaseSectionQuickAdapter<HomeSection, TheBaseViewHolder> implements LoadMoreModule {

    public Home2Adapter() {
        super(R.layout.item_home_head, R.layout.item_home_item, null);
    }

    @Override
    protected void convertHeader(@NotNull TheBaseViewHolder holder, @NotNull HomeSection section) {
        TextView tvTitle = holder.itemView.findViewById(R.id.tv_title);
        tvTitle.setText(section.header);
    }

    @Override
    protected void convert(@NotNull TheBaseViewHolder holder, HomeSection homeSection) {
        TextView tvContent = holder.getView(R.id.tv_content);
        NineGridView nineImageLayout = holder.getView(R.id.nine_grid);
        GankBean itemSection = homeSection.t;
        String author = "#" + itemSection.getWho()+ "  ";
        tvContent.setText(StringUtils.SpannableString(author + itemSection.getDesc(), author, QMUIResHelper.getAttrColor(getContext(), R.attr.config_color), StringUtils.Type.ForegroundColorSpan));
        if (null == itemSection.getImages() || itemSection.getImages().size() < 1) {
            if (itemSection.getUrl().endsWith(".jpg")) {
                ArrayList<String> strings = new ArrayList<>();
                strings.add(itemSection.getUrl());
                nineImageLayout.setUrlList(strings);
            } else {
                nineImageLayout.setUrlList(null);
            }
        } else
            nineImageLayout.setUrlList(itemSection.getImages());
    }
}
