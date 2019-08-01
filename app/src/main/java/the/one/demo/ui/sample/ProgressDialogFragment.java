package the.one.demo.ui.sample;

import android.os.Handler;
import android.os.Message;
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
 * @date 2019/7/11 0011
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class ProgressDialogFragment extends BaseGroupListFragment {

    private QMUICommonListItemView mProgressDialog;

    @Override
    protected void addGroupListView() {
        initFragmentBack("ProgressDialogFragment");
        mProgressDialog  = CreateNormalItemView("显示进度弹窗");
        addToGroup(mProgressDialog);
    }

    protected static final int STOP = 0x10000;
    protected static final int NEXT = 0x10001;

    int count;

    private ProgressHandler myHandler = new ProgressHandler();
    @Override
    public void onClick(View v) {
        showProgressDialog("上传中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    try {
                        count = i + 1;
                        if (i == 5) {
                            Message msg = new Message();
                            msg.what = STOP;
                            myHandler.sendMessage(msg);
                        } else {
                            Message msg = new Message();
                            msg.what = NEXT;
                            msg.arg1 = count;
                            myHandler.sendMessage(msg);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private  class ProgressHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case STOP:
                    break;
                case NEXT:
                    if (!Thread.currentThread().isInterrupted()) {
                        showProgressDialog(msg.arg1);
                    }
            }

        }
    }

}
