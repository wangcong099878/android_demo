package the.one.demo.ui.fragment.mzitu;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import the.one.base.base.fragment.BaseDataFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.constant.DataConstant;
import the.one.demo.bean.Mzitu;
import the.one.demo.constant.MzituConstant;
import the.one.demo.ui.adapter.MzitiuAdapter;
import the.one.demo.ui.presenter.MzituPresenter;


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
 * @date 2019/6/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class MzituItemFragment extends BaseDataFragment<Mzitu> {

    public static MzituItemFragment newInstance(String url){
        MzituItemFragment fragment = new MzituItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DataConstant.URL,url);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String url;
    private MzituPresenter presenter;

    @Override
    protected int setType() {
        return TYPE_STAGGERED;
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        url = getArguments().getString(DataConstant.URL);
        goneView(mTopLayout);
        return new MzitiuAdapter();
    }

    @Override
    protected void requestServer() {
        presenter.getData(url,page);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Mzitu mzitu = (Mzitu) adapter.getItem(position);
        if(MzituConstant.isNoDetail(url)){
            startFragment(MzituDetailFragment.newInstance(adapter.getData(),url,position,page));
        }else {
            startFragment(MzituDetailFragment.newInstance(mzitu));
        }
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter = new MzituPresenter();
    }
}
