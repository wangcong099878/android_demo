package the.one.demo.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.List;

import the.one.demo.R;
import the.one.demo.bean.Tag;


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
 * @date 2019/3/22 0022
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class TagCloudAdapter extends TagsAdapter {

    private List<Tag> mTags;
    private int color1,color2,color3;
    private OnTagItemClickListener listener;

    public TagCloudAdapter(Context context, List<Tag> tags) {
        this.mTags = tags;
        color1 = ContextCompat.getColor(context, R.color.app_color_theme_1);
        color2 = ContextCompat.getColor(context, R.color.classic_color_7);
        color3 = ContextCompat.getColor(context, R.color.app_color_theme_6);
    }

    public void setListener(OnTagItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return mTags.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        final Tag tag = mTags.get(position);
        TextView tv = new TextView(context);
        tv.setText(tag.getTagTitle());
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(position%2==0?color1:position%3==0?color2:color3);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != listener)
                    listener.onTagItemClick(tag.getTagClass());
            }
        });
        return tv;
    }


    @Override
    public Object getItem(int position) {
        return mTags.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return  position% 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
//        view.setBackgroundColor(themeColor);
    }

    public interface OnTagItemClickListener{
        void onTagItemClick(Class tagClass);
    }
}
