package the.one.gank.ui.fragment;

import the.one.base.ui.presenter.BasePresenter;
import the.one.gank.constant.NetUrlConstant;
import the.one.gank.ui.presenter.GankPresenter;


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
 * @date 2019/3/4 0004
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class GankFragment extends BaseGankFragment {

    private GankPresenter gankPresenter;

    @Override
    protected boolean isWelfare() {
        return mType.equals(NetUrlConstant.WELFARE);
    }

    @Override
    public BasePresenter getPresenter() {
        return gankPresenter = new GankPresenter();
    }

    @Override
    protected void requestServer() {
        gankPresenter.getData(_mActivity, mType, page);
    }

}
