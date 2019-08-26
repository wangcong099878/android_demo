package the.one.base.util;

import android.app.Activity;
import android.content.Context;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.Map;

import the.one.base.Interface.IUpdateApk;
import the.one.base.service.UpdateApkService;
import the.one.net.BaseHttpRequest;
import the.one.net.callback.Callback;
import the.one.net.entity.PageInfoBean;

/**
 * @author The one
 * @date 2018/10/16 0016
 * @describe 更新工具
 * @email 625805189@qq.com
 * @remark
 */
public class UpdateApkUtil<T extends IUpdateApk>  extends BaseHttpRequest{

    private  Context mContext;
    private QMUITipDialog loadingDialog;
    private boolean isShowProgress =false;

    public void check(Activity activity,Map<String,String> map,String url,boolean isShowProgress){
        mContext = activity;
        this.isShowProgress = isShowProgress;
        if(isShowProgress){
            showCheckDialog();
        }
        get(url,map,callback);
    }

    protected Callback<T> callback = new Callback<T>() {
        @Override
        public void onSuccess(T response, String msg, PageInfoBean pageInfoBean) {
            if(response.isUpdate()){
                showNewVersionDialog(response.getUpdateDes(),response.getApkPath());
            }else if(isShowProgress){
                showIsNewVersionDialog();
                mContext= null;
            }
        }

        @Override
        public void onFailure(int resultCode, String errorMsg) {
            ToastUtil.showToast(errorMsg);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            hideCheckDialog();
        }
    };

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
    private void hideCheckDialog() {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
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
    private void showNewVersionDialog(final String updateContent, final String url) {
        QMUIDialogUtil.showLongMessageDialog(mContext, "发现新版本", updateContent, "立即更新", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
                // 传入下载地址开始下载
                UpdateApkService.startDown((Activity) mContext, url);
                mContext= null;
            }
        });
    }

}
