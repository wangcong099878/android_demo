package the.one.demo.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import the.one.base.base.fragment.BaseDataFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.base.util.GlideUtil;
import the.one.base.widge.ScaleImageView;
import the.one.demo.R;
import the.one.demo.model.GirlItemData;
import the.one.demo.presenter.GirlPresenter;


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
 * @date 2018/12/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SimpleDataFragment extends BaseDataFragment<GirlItemData> {

    public static SimpleDataFragment newInstance(int type) {
        SimpleDataFragment fragment = new SimpleDataFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DataConstant.DATA, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int type;

    @Override
    protected int setType() {
        return TYPE_STAGGERED;
    }

    @Override
    protected void initView(View rootView) {
        initFragmentBack("Girls");
        assert getArguments() != null;
        type = getArguments().getInt(DataConstant.DATA);
        super.initView(rootView);
    }

    @Override
    protected BaseQuickAdapter<GirlItemData, BaseViewHolder> getAdapter() {
        return new BaseQuickAdapter<GirlItemData, BaseViewHolder>(R.layout.item_girls) {
            @Override
            protected void convert(BaseViewHolder helper, GirlItemData item) {
                ScaleImageView imageView = helper.getView(R.id.girl_item_iv);
                imageView.setInitSize(item.getWidth(), item.getHeight());
                GlideUtil.load(mContext, item.getUrl(), imageView);
            }
        };
    }

    @Override
    protected void requestServer() {
        girlPresenter.getData(getContext(),type, page);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return girlPresenter = new GirlPresenter();
    }

    private GirlPresenter girlPresenter;
}
