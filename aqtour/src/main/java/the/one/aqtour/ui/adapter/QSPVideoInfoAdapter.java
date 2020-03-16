package the.one.aqtour.ui.adapter;

import the.one.aqtour.R;
import the.one.aqtour.bean.QSPVideoInfo;
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
public class QSPVideoInfoAdapter extends TheBaseQuickAdapter<QSPVideoInfo> {

    public QSPVideoInfoAdapter() {
        super(R.layout.item_video_info);
    }

    @Override
    protected void convert(TheBaseViewHolder helper, QSPVideoInfo item) {
        helper.setText(R.id.key,item.key)
                .setText(R.id.value,item.value);
    }
}
