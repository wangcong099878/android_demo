package the.one.anastasia.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import the.one.anastasia.R;


/**
 * 加载中Dialog
 */
public class LoadingDialog extends AlertDialog {

	private TextView tips_loading_msg;
	private int layoutResId;
	private String message;

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            上下文
	 */
	public LoadingDialog(Context context) {
		super(context, R.style.dialog);
		this.layoutResId = R.layout.dialog_loading;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(layoutResId);
		tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
		tips_loading_msg.setText(this.message);
	}

	/**
	 * 设置提示语
	 * @param message
     */
	public void setMessage(String message)
	{
		this.message=message;
		if(tips_loading_msg!=null) {
			tips_loading_msg.setText(message);
		}
	}
}
