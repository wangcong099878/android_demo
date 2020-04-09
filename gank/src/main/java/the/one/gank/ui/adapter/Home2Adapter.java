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

//    @Override
//    public void onViewDetachedFromWindow(@NonNull TheBaseViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//        /**
//         * 下面的代码需要根据你的实际情况调整哦！
//         */
//        //定位你的header位置
//        if (holder.getAdapterPosition()==0) {
//            if (getHeaderLayoutCount() > 0) {
//                //这里是获取你banner放的位置，这个根据你自己实际位置来获取，我这里header只有一个所以这么获取
//                Banner banner = (Banner) getHeaderLayout().getChildAt(0);
//                banner.stop();
//            }
//        }
//    }
//    //当banner可见时继续
//    @Override
//    public void onViewAttachedToWindow(TheBaseViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        if (holder.getAdapterPosition()==0) {
//            if (getHeaderLayoutCount() > 0) {
//                Banner banner = (Banner) getHeaderLayout().getChildAt(0);
//                banner.start();
//            }
//        }
//    }

}
