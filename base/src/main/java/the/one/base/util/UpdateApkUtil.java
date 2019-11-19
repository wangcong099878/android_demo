package the.one.base.util;

import android.app.Activity;
import android.content.Context;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.Map;

import the.one.base.base.activity.UpdateApkActivity;
import the.one.base.model.IApkUpdate;
import the.one.net.BaseHttpRequest;
import the.one.net.callback.Callback;

/**
 * @author The one
 * @date 2018/10/16 0016
 * @describe 更新工具
 * @email 625805189@qq.com
 * @remark
 */
public class UpdateApkUtil<T extends IApkUpdate> extends BaseHttpRequest {

    protected Activity mContext;
    protected QMUITipDialog loadingDialog;
    protected boolean isShowProgress;

    public void check(Activity activity, Map<String, Object> map, String url, final boolean isShowProgress, Callback callback) {
        mContext = activity;
        this.isShowProgress = isShowProgress;
        if (isShowProgress) {
            showCheckDialog();
        }
        if (!checkIsDownload(activity)) {
            get(url, map, callback);
        }
    }

    protected boolean checkIsDownload(Context context) {
        if (ServiceUtil.isDownloadApkServiceExisted(context)) {
            QMUIDialogUtil.FailTipsDialog(context, "更新包正在下载中");
            return true;
        } else
            return false;
    }

    protected void onComplete(T response) {
        if (response.isNewVersion()) {
            showNewVersionDialog(response);
        } else if (isShowProgress) {
            showIsNewVersionDialog();
            mContext = null;
        }
    }

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
    public void hideCheckDialog() {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 显示已是最新提示语
     */
    private void showIsNewVersionDialog() {
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
    private void showNewVersionDialog(final T response) {
        UpdateApkActivity.startDown(mContext,response);
    }

}
