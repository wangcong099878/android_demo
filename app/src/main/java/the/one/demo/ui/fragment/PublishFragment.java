package the.one.demo.ui.fragment;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.layout.QMUIRelativeLayout;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;
import the.one.base.base.fragment.BaseFragment;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.util.QMUIDialogUtil;
import the.one.demo.Constant;
import the.one.demo.R;
import the.one.net.FailUtil;


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
 * @date 2019/3/12 0012
 * @describe 发布( 勿滥用 )
 * @email 625805189@qq.com
 * @remark
 */
public class PublishFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.btn_type)
    QMUIRoundButton btnType;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.btn_url)
    QMUIRoundButton btnUrl;
    @BindView(R.id.et_url)
    EditText etUrl;
    @BindView(R.id.btn_des)
    QMUIRoundButton btnDes;
    @BindView(R.id.rl_des)
    QMUIRelativeLayout rlDes;
    @BindView(R.id.et_des)
    EditText etDes;

    private QMUIDialog typeDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_publish;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(View rootView) {
        initFragmentBack("发布");
        mTopLayout.addRightTextButton("提交", R.id.topbar_right_1).setOnClickListener(this);
        tvType.setText(Constant.title[0]);
        btnType.setOnClickListener(this);
        tvType.setOnClickListener(this);
        rlDes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topbar_right_1:
                check();
                break;
            case R.id.btn_type:
            case R.id.tv_type:
                showTypeDialog();
                break;
            case R.id.rl_des:
                if(!QMUIKeyboardHelper.isKeyboardVisible(_mActivity)){
                    QMUIKeyboardHelper.showKeyboard(etDes,true);
                }else{
                    QMUIKeyboardHelper.hideKeyboard(view);
                }
                break;
        }
    }

    private void showTypeDialog() {
        if (null == typeDialog) {
            typeDialog = QMUIDialogUtil.showSingleChoiceDialog(_mActivity, Constant.title, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    tvType.setText(Constant.title[i]);
                    dialogInterface.dismiss();
                }
            });
        } else
            typeDialog.show();
    }

    private void check() {
        String type = getTextViewString(tvType);
        if (isEmpty(type, "类型")) {
            String url = getEditTextString(etUrl);
            if (isEmpty(url, "地址")) {
                String des = getEditTextString(etDes);
                if (isEmpty(des, "描述")) {
                    submit(type, url, des);
                }
            }
        }
    }

    private void submit(String type, String url, String des) {
        showLoadingDialog("提交中");
        OkHttpUtils
                .post()
                .url(Constant.GANK_PUBLISH)
                .addParams("type", type)
                .addParams("url", url)
                .addParams("desc", des)
                .addParams("who", "Theoneee")
                .addParams("debug", "false")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoadingDialog();
                        showFailTips(FailUtil.getFailString(e));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoadingDialog();
                        try {
                            JSONObject object = new JSONObject(response);
                            boolean error = object.getBoolean("error");
                            String msg = object.getString("msg");
                            if(!error){
                                showSuccessExit(msg);
                            }else{
                                showFailTips(msg);
                            }
                        } catch (JSONException e) {
                            showFailTips("解析错误");
                            e.printStackTrace();
                        }
                    }
                });
    }

    private boolean isEmpty(String content, String tips) {
        if (content.isEmpty()) {
            showFailTips(tips + "不能为空");
            return false;
        }
        return true;
    }

}
