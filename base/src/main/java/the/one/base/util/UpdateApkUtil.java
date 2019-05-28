package the.one.base.util;

import android.app.Activity;
import android.content.Context;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import the.one.base.BaseApplication;
import the.one.base.service.UpdateApkService;

/**
 * @author The one
 * @date 2018/10/16 0016
 * @describe 更新工具
 * @email 625805189@qq.com
 * @remark 由于网络和请求结果并不在此范围内，所以这里只封装了这些
 */
public class UpdateApkUtil {

    private  Context mContext = BaseApplication.context;
    private QMUITipDialog loadingDialog;

    /**
     * 显示检查进度Dialog
     */
    private void showCheckDialog() {
        loadingDialog = QMUIDialogUtil.LoadingTipsDialog(mContext, "检查中");
        loadingDialog.show();
    }

    /**
     * 去掉检查进度Dialog
     */
    private void hideCheckDialog() {
        if (null != loadingDialog)
            loadingDialog.dismiss();
    }

    /**
     * 显示已是最新提示语
     */
    private void showIsNewVersionDialog() {
        QMUIDialogUtil.SuccessTipsDialog(mContext, "已是最新版本",null);
    }

    /**
     * 显示新版本提示
     *
     * @param updateContent
     * @param url
     */
    public void showNewVersionDialog(final String updateContent, final String url) {
        QMUIDialogUtil.showLongMessageDialog(mContext, "发现新版本", updateContent, "立即更新", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
                // 传入下载地址开始下载
                UpdateApkService.startDown((Activity) mContext, url);
            }
        });
    }
}
