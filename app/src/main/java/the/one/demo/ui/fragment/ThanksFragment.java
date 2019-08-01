package the.one.demo.ui.fragment;

import android.view.View;

import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.demo.Constant;
import the.one.demo.R;
import the.one.demo.ui.activity.BingoActivity;
import the.one.demo.ui.adapter.TagCloudAdapter;
import the.one.demo.ui.bean.Thanks;
import the.one.demo.ui.sample.SampleFragment;

public class ThanksFragment extends BaseFragment {

    @BindView(R.id.tag_cloud)
    TagCloudView mTagCloud;

    TagCloudAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_thanks;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(View rootView) {
        mTopLayout.setTitle("感谢").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BingoActivity.startThisActivity(_mActivity);
            }
        });
        mTopLayout.addRightImageButton(R.drawable.mz_titlebar_ic_list_dark,R.id.topbar_right_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new SampleFragment());
            }
        });
        List<Thanks> thanks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            thanks.add(new Thanks("Gank.io", Constant.GANK_BASE));
            thanks.add(new Thanks("GankApp", "https://github.com/JayGengi/KotlinGankApp"));
            thanks.add(new Thanks("QMUI", "https://github.com/Tencent/QMUI_Android"));
            thanks.add(new Thanks("BRVAH", "https://github.com/CymChad/BaseRecyclerViewAdapterHelper"));
            thanks.add(new Thanks("NineGrid", "https://github.com/HMY314/NineGridLayout"));
        }
        adapter = new TagCloudAdapter(this,thanks);
        mTagCloud.setAdapter(adapter);
    }

}
