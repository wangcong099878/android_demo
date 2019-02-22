package the.one.base.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import the.one.base.R;
import the.one.base.model.PopupItem;

/**
 * @author The one
 * @date 2018/7/31 0031
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ListPopupAdapter extends EntityAdapter<PopupItem, ListPopupAdapter.ViewHolder> {

    public ListPopupAdapter(Context context, List<PopupItem> data) {
        super(context,data);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.simple_list_item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position, View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position, final PopupItem data) {
        if (data != null) {
          if(0 != data.getImage()){
              holder.ivIcon.setImageResource(data.getImage());
          }
          holder.tvTitle.setText(data.getTitle());
        } else {
            Log.e("LOG", "null");
        }
    }


    public class ViewHolder extends EntityAdapter.BaseHolder {

        ImageView ivIcon;
        TextView tvTitle;
        public ViewHolder(View convertView) {
            super(convertView);
            ivIcon = convertView.findViewById(R.id.icon);
            tvTitle = convertView.findViewById(R.id.text);
        }
    }
}
