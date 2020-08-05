package the.one.demo.ui.fragment;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.ui.fragment.BaseGroupListFragment;


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
 * @date 2020/1/14 0014
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class CrashTestFragment extends BaseGroupListFragment {

    private QMUICommonListItemView CRASH;

    @Override
    protected void addGroupListView() {
        setTitleWithBackBtn("崩溃日志获取");
        CRASH = CreateNormalItemView("点击进行崩溃测试");
        addToGroup("项目崩溃后进行友好提示","需要继承BaseApplication返回启动Activity.class,详情配置见BaseApplication里的initCrashConfig()里内容,如果要对错误日志进行其他处理,仿照或者继承CrashActivity进行编写即可。",CRASH);
    }

    @Override
    public void onClick(View v) {
        // 测试崩溃
        String error = null;
        error.length();
    }
}
