package the.one.demo.ui.sample;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.base.fragment.BaseGroupListFragment;
import the.one.base.base.activity.BaseWebExplorerActivity;
import the.one.demo.ui.sample.activity.CameraSamplePermissionActivity;


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
public class SampleFragment extends BaseGroupListFragment {

    private QMUICommonListItemView PICTURE_SELECTOR,WEB_VIEW,POPUP_LAYOUT,COLLAPSING_TOP_BAR,
            PULL_EXTEND,STATUS_BAR_HELP,STRING_UTIL,PROGRESS_DIALOG,CAMERA,DATE_PICKER,LETTER_SEARCH,
            ROUND_CHECK_BOX;

    @Override
    protected void addGroupListView() {
        initFragmentBack("使用示例");
        PICTURE_SELECTOR = CreateDetailItemView("BasePictureSelectorFragment","选择图片、视频、音频",false,true);
        WEB_VIEW = CreateDetailItemView("BaseWebExplorerActivity","网页",true);
        PULL_EXTEND=   CreateDetailItemView("BasePullExtendFragment","下拉菜单",true);
        LETTER_SEARCH = CreateDetailItemView("BaseLetterSearchFragment","侧边快速选择",true);
        CAMERA = CreateDetailItemView("BaseCameraPermissionActivity","相机权限相关的界面可以继承此Activity",false,true);
        ROUND_CHECK_BOX =  CreateDetailItemView("TheCheckBox","普通的CheckBox设置leftPadding无效",false,true);

        STATUS_BAR_HELP = CreateDetailItemView("StatusBarHelper","状态栏工具 QMUI提供",true);
        STRING_UTIL= CreateDetailItemView("StringUtils","String的工具类",true);
        DATE_PICKER = CreateDetailItemView("DatePickerUtil","日期选择的工具类",true);

//        COLLAPSING_TOP_BAR =  CreateDetailItemView("CollapsingTopBarFragment","");
//        PROGRESS_DIALOG = CreateDetailItemView("ProgressDialog","");
//        POPUP_LAYOUT = CreateDetailItemView("PopupLayout","");


        addToGroup("UI",PICTURE_SELECTOR,WEB_VIEW,PULL_EXTEND,LETTER_SEARCH,CAMERA,ROUND_CHECK_BOX);
        addToGroup("工具",STATUS_BAR_HELP,STRING_UTIL,DATE_PICKER);
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
            BaseWebExplorerActivity.newInstance(_mActivity,title, url, false);
        }else if(v == COLLAPSING_TOP_BAR){
            startFragment(new CollapsingTopBarFragment());
        }else if(v == PULL_EXTEND){
            startFragment(new PullLayoutFragment());
        }else if(v ==STATUS_BAR_HELP){
            startFragment(new StatusBarHelperFragment());
        }else if(v == STRING_UTIL){
            startFragment(new StringUtilFragment());
        }else if(v == PROGRESS_DIALOG){
            startFragment(new ProgressDialogFragment());
        }else if(v == CAMERA){
            startActivity(CameraSamplePermissionActivity.class);
        }else if(v == DATE_PICKER){
            startFragment(new DatePickerFragment());
        }else if(v == LETTER_SEARCH){
            startFragment(new LetterSearchFragment());
        }else if(v == ROUND_CHECK_BOX){
            startFragment(new TheCheckBoxFragment());
        }
    }
}
