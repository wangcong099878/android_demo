package the.one.demo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.ArrayList;
import java.util.List;

import the.one.base.base.activity.BaseWebViewActivity;
import the.one.base.base.fragment.BaseFragment;
import the.one.demo.R;
import the.one.demo.ui.bean.Thanks;


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


    private List<Thanks> thanks;
    private Activity activity;
    private int color1,color2,color3;

    public TagCloudAdapter(Activity activity, List<Thanks> thanks) {
        this.activity = activity;
        this.thanks = thanks;
        color1 = ContextCompat.getColor(activity, R.color.app_color_theme_1);
        color2 = ContextCompat.getColor(activity, R.color.classic_color_7);
        color3 = ContextCompat.getColor(activity, R.color.app_color_theme_6);
    }

    @Override
    public int getCount() {
        return thanks.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        final Thanks thank = thanks.get(position);
        TextView tv = new TextView(context);
        tv.setText(thank.name);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(position%2==0?color1:position%3==0?color2:color3);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseWebViewActivity.startThisActivity(activity,thank.name,thank.url);
            }
        });
        return tv;
    }


    @Override
    public Object getItem(int position) {
        return thanks.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return  position% 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
//        view.setBackgroundColor(themeColor);
    }
}
