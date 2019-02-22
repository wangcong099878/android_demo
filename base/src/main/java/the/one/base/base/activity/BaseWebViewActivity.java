package the.one.base.base.activity;

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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qmuiteam.qmui.widget.QMUIProgressBar;

import the.one.base.R;
import the.one.base.constant.DataConstant;
import the.one.base.util.DeviceUtil;
import the.one.base.util.FileDirectoryUtil;

/**
 * @author The one
 * @date 2019/1/24 0024
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BaseWebViewActivity extends BaseActivity {

    QMUIProgressBar progressBar;
    WebView mWebView;
    private String title;
    private String url;

    public static void startThisActivity(Activity activity,String title,String url){
        Intent intent = new Intent(activity,BaseWebViewActivity.class);
        intent.putExtra(DataConstant.TITLE,title);
        intent.putExtra(DataConstant.URL,url);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.base_web_view;
    }

    @Override
    protected void initView(View rootView) {
        progressBar = rootView.findViewById(R.id.progressbar);
        mWebView = rootView.findViewById(R.id.web_view);
        title = getIntent().getStringExtra(DataConstant.TITLE);
        url = getIntent().getStringExtra(DataConstant.URL);
        initFragmentBack(title);
        initWebView();
    }


    @SuppressLint("SetJavaScriptEnabled")
    protected  void initWebView() {
        WebSettings mSettings = mWebView.getSettings();

        //设置自适应屏幕，两者合用
        mSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        mSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        mSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        mSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        mSettings.setJavaScriptEnabled(true);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        if (DeviceUtil.isNetworkConnected(this)) {
            mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }

        mSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        mSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        mSettings.setAppCacheEnabled(false);//开启 Application Caches 功能

        mSettings.setAppCachePath(FileDirectoryUtil.getCachePath()); //设置  Application Caches 缓存目录
        mWebView.loadUrl(url);
        //设置不用系统浏览器打开,直接显示在当前Webview

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  //接受所有证书
            }
        });

            mWebView.setWebChromeClient(new WebChromeClient() {
                //获取网站标题
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    if (null != mTopLayout) {
                        mTopLayout.setTitle(title);
                    }
                }

                //获取加载进度
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    Log.e(TAG, "onProgressChanged: " + newProgress);
                    if (null != progressBar) {
                        progressBar.setProgress(newProgress);
                        if (newProgress == 100) {
                            progressBar.setVisibility(View.GONE);
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        mWebView.setLayerType(View.LAYER_TYPE_NONE, null);
    }

}
