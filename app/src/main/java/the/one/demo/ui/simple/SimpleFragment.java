package the.one.demo.ui.simple;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.base.fragment.BaseGroupListFragment;
import the.one.base.base.fragment.BaseWebExplorerFragment;


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
 * @date 2019/4/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class SimpleFragment extends BaseGroupListFragment {

    private QMUICommonListItemView PICTURE_SELECTOR,WEB_VIEW,POPUP_LAYOUT,COLLAPSING_TOP_BAR,PULL_EXTEND;

    @Override
    protected void addGroupListView() {
        initFragmentBack("使用示例");
        PICTURE_SELECTOR = CreateNormalItemView("选择图片、视频、音频");
        POPUP_LAYOUT = CreateNormalItemView("PopupLayout");
        WEB_VIEW = CreateNormalItemView("WebView");
        COLLAPSING_TOP_BAR =  CreateNormalItemView("CollapsingTopBarFragment");

        PULL_EXTEND=   CreateNormalItemView("PullExtendLayout");
        addToGroup(PICTURE_SELECTOR,WEB_VIEW,PULL_EXTEND);
    }

    @Override
    public void onClick(View v) {
        if(v == PICTURE_SELECTOR){
            startFragment(new SelectPictureFragment());
        }else if(v == POPUP_LAYOUT){
            startFragment(new PopupLayoutFragment());
        }else if(v == WEB_VIEW){
            String title = "每日一笑";
            String url = "http://qt.qq.com/php_cgi/news/php/varcache_mcnarticle.php?id=&doc_type=0&docid=971312304452308411&areaid=18&version=$PROTO_VERSION$";
            startFragment(BaseWebExplorerFragment.newInstance(title, url, false));
        }else if(v == COLLAPSING_TOP_BAR){
            startFragment(new CollapsingTopBarFragment());
        }else if(v == PULL_EXTEND){
            startFragment(new PullLayoutFragment());
        }
    }
}
