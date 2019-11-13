package the.one.demo.ui.sample.fragment;

import android.support.v7.widget.OrientationHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import the.one.base.base.fragment.BasePullExtendFragment;
import the.one.demo.R;
import the.one.demo.adapter.OverFlyingLayoutManager;
import the.one.demo.adapter.PullExtendAdapterThe;
import the.one.demo.bean.SimpleBean;


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
 * @date 2019/6/11 0011
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class PullLayoutFragment extends BasePullExtendFragment {

    private PullExtendAdapterThe adapter;

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_pull_layout;
    }

    @Override
    protected void initView(View rootView) {
        initFragmentBack("PullExtendLayout");
        super.initView(rootView);

        mHeaderRc.setLayoutManager(new OverFlyingLayoutManager(OrientationHelper.HORIZONTAL));
        mFooterRc.setLayoutManager(new OverFlyingLayoutManager(OrientationHelper.HORIZONTAL));

        adapter = new PullExtendAdapterThe();
        mHeaderRc.setAdapter(adapter);
        mFooterRc.setAdapter(adapter);

        List<SimpleBean> datas = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            datas.add(new SimpleBean("QQ",R.drawable.ic_qq));
            datas.add(new SimpleBean("微信",R.drawable.ic_wechat));
            datas.add(new SimpleBean("微博",R.drawable.ic_weibo));
            datas.add(new SimpleBean("抖音",R.drawable.ic_douyin));
        }

        adapter.setNewData(datas);
    }
}
