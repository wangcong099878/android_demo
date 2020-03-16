package the.one.aqtour.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;

import the.one.aqtour.R;
import the.one.aqtour.bean.QSPSeries;
import the.one.aqtour.bean.QSPSeriesSection;
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
public class QSPSeriesSectionAdapter extends BaseSectionQuickAdapter<QSPSeriesSection, TheBaseViewHolder> {

    private QSPSeries mSelect;

    public void setSelect(QSPSeries mSelect) {
        this.mSelect = mSelect;
    }

    public QSPSeries getSelect() {
        return mSelect;
    }

    public QSPSeriesSectionAdapter() {
        super(R.layout.item_video_series, R.layout.item_video_series_head,null);
    }

    @Override
    protected void convertHead(TheBaseViewHolder helper, QSPSeriesSection item) {
        helper.setText(R.id.tv_line,item.header);
    }

    @Override
    protected void convert(TheBaseViewHolder helper, QSPSeriesSection item) {
        QSPSeries series = item.t;
        TextView btnSeries = helper.getView(R.id.btn_series);
        btnSeries.setText(series.name);
        if(null != mSelect && mSelect.equals(series)){
            btnSeries.setSelected(true);
            btnSeries.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        }else{
            btnSeries.setSelected(false);
            btnSeries.setTextColor(ContextCompat.getColor(mContext,R.color.qmui_config_color_gray_4));
            helper.addOnClickListener(R.id.btn_series);
        }
    }
}
