package the.one.aqtour.ui.adapter;

import android.widget.TextView;

import androidx.core.content.ContextCompat;
import the.one.aqtour.R;
import the.one.aqtour.bean.QSPCategory;
import the.one.base.adapter.TheBaseQuickAdapter;
import the.one.base.adapter.TheBaseViewHolder;

public class VideoCategoryAdapter extends TheBaseQuickAdapter<QSPCategory> {

    private int mSelect;

    public int getSelect() {
        return mSelect;
    }

    public void setSelect(int mSelect) {
        this.mSelect = mSelect;
        notifyDataSetChanged();
    }

    public VideoCategoryAdapter() {
        super(R.layout.item_video_catogary);
    }

    @Override
    protected void convert(TheBaseViewHolder helper, QSPCategory item) {
        addChildClickViewIds(R.id.catogary);
        TextView catogary = helper.getView(R.id.catogary);
        catogary.setText(item.title);
        boolean isSelect = helper.getLayoutPosition() == mSelect;
        catogary.setSelected(isSelect);
        catogary.setTextColor(ContextCompat.getColor(getContext(),isSelect ? R.color.white : R.color.qmui_config_color_gray_1));
    }
}
