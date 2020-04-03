package the.one.base.util;

import androidx.appcompat.app.AppCompatActivity;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import the.one.base.base.activity.UpdateApkActivity;
import the.one.base.model.IApkUpdate;
/**
 * @author The one
 * @date 2018/10/16 0016
 * @describe 更新工具
 * @email 625805189@qq.com
 * @remark
 */
public class UpdateApkUtil<T extends IApkUpdate>  {

    protected AppCompatActivity mContext;
    private QMUITipDialog loadingDialog;

    public UpdateApkUtil(AppCompatActivity mContext) {
        this.mContext = mContext;
    }

    protected boolean checkIsDownload() {
        if (ServiceUtil.isDownloadApkServiceExisted(mContext)) {
            QMUIDialogUtil.FailTipsDialog(mContext, "更新包正在下载中");
            return true;
        } else
            return false;
    }

    protected void onComplete(T response,boolean showCheck) {
        if (response.isNewVersion()) {
            showNewVersionDialog(response);
        } else if (showCheck){
            showIsNewVersionDialog();
        }
        hideCheckDialog();
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
    protected void showIsNewVersionDialog() {
        QMUIDialogUtil.SuccessTipsDialog(mContext, "已是最新版本", new QMUIDialogUtil.OnTipsDialogDismissListener() {
            @Override
            public void onDismiss() {
                mContext = null;
            }
        });
    }

    /**
     * 显示新版本提示
     */
    protected void showNewVersionDialog(final T response) {
        UpdateApkActivity.startDown(mContext,response);
    }

}
