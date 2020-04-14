package the.one.base.adapter;

import org.jetbrains.annotations.NotNull;

import the.one.base.R;
import the.one.base.model.PopupItem;

/**
 * @author The one
 * @date 2018/7/31 0031
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ListPopupAdapter extends TheBaseQuickAdapter<PopupItem> {


    public ListPopupAdapter() {
        super(R.layout.simple_list_item);
    }

    @Override
    protected void convert(@NotNull TheBaseViewHolder holder, PopupItem data) {
        if(data.isHaveIcon()){
            holder.getImageView(R.id.icon).setImageResource(data.getImage());
        }
        holder.setText(R.id.text,data.getTitle());
    }

}
