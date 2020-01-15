package the.one.demo.ui.fragment.sample;

import android.view.View;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.base.fragment.BaseGroupListFragment;


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
        initFragmentBack("崩溃日志获取");
        CRASH = CreateNormalItemView("点击进行崩溃测试");
        addToGroup("项目崩溃后进行友好提示","详情见BaseApplication里的initSpiderMan()里内容,如果要对错误日志进行其他处理,仿照CrashActivity进行编写即可。",CRASH);
    }

    @Override
    public void onClick(View v) {
        // 测试崩溃
        String error = null;
        error.length();
    }
}
