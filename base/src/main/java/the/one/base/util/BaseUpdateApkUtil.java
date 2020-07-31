package the.one.base.util;

import android.content.Context;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.appcompat.app.AppCompatActivity;
import the.one.base.Interface.IApkUpdate;
import the.one.base.ui.activity.UpdateApkActivity;

/**
 * @author The one
 * @date 2018/10/16 0016
 * @describe 更新工具
 * @email 625805189@qq.com
 * @remark
 */
public class BaseUpdateApkUtil<T extends IApkUpdate> {

    private QMUITipDialog loadingDialog;
    private AppCompatActivity mContext;

    public BaseUpdateApkUtil() {
    }

    public void checkUpdate(AppCompatActivity context, final boolean showCheck) {
        mContext = context;
        if (checkIsDownload()) return;
        if (showCheck) {
            showCheckDialog();
        }
    }

    protected boolean checkIsDownload( ) {
        if (ServiceUtil.isDownloadApkServiceExisted(mContext)) {
            QMUIDialogUtil.FailTipsDialog(mContext, "更新包正在下载中");
            return true;
        } else
            return false;
    }

    public void onComplete(Context context,T response, boolean showCheck) {
        if (response.isNewVersion()) {
            showNewVersionDialog(context,response);
        } else if (showCheck) {
            showIsNewVersionDialog(context);
        }
        hideCheckDialog();
    }

    protected void onFail(String errorMsg){
        hideCheckDialog();
        QMUIDialogUtil.FailTipsDialog(mContext, errorMsg);
    }

    /**
     * 显示检查进度Dialog
     */
    protected void showCheckDialog() {
        loadingDialog = QMUIDialogUtil.LoadingTipsDialog(mContext, "检查中");
        loadingDialog.show();
    }

    /**
     * 去掉检查进度Dialog
     */
    protected void hideCheckDialog() {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 显示已是最新提示语
     */
    protected void showIsNewVersionDialog(Context context) {
        QMUIDialogUtil.SuccessTipsDialog(context, "已是最新版本");
    }

    /**
     * 显示新版本提示
     */
    protected void showNewVersionDialog(Context context,final T response) {
        UpdateApkActivity.startDown( context, response);
    }

}
