package the.one.aqtour.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import the.one.aqtour.R;
import the.one.aqtour.bean.QSPSeries;
import the.one.base.adapter.TheBaseQuickAdapter;
import the.one.base.adapter.TheBaseViewHolder;


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
 * @date 2020/1/7 0007
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class QSPSeriesAdapter extends TheBaseQuickAdapter<QSPSeries> {

    private RecyclerView recyclerView;
    private int mSelect = -1;

    public void setSelect(int position) {
        this.mSelect = position;
        notifyDataSetChanged();
        recyclerView.scrollToPosition(position);
    }

    public int getSelect() {
        return mSelect;
    }

    public QSPSeries getSelectSeries(){
        if(mSelect == -1) return null;
        return getItem(mSelect);
    }

    public QSPSeriesAdapter(int layout, RecyclerView recyclerView) {
        super(layout, null);
        this.recyclerView = recyclerView;
    }

    @Override
    protected void convert(TheBaseViewHolder helper, QSPSeries series) {
        TextView btnSeries = helper.getView(R.id.btn_series);
        btnSeries.setText(series.name);
        if (-1 != mSelect && mSelect == helper.getAdapterPosition()) {
            btnSeries.setSelected(true);
            btnSeries.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            btnSeries.setSelected(false);
            btnSeries.setTextColor(ContextCompat.getColor(mContext, R.color.qmui_config_color_gray_4));
            helper.addOnClickListener(R.id.btn_series);
        }
    }
}
