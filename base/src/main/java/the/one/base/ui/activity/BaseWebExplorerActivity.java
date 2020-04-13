package the.one.base.ui.activity;

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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ZoomButtonsController;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.util.QMUILangHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIProgressBar;
import com.qmuiteam.qmui.widget.webview.QMUIWebView;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewContainer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import the.one.base.R;
import the.one.base.ui.presenter.BasePresenter;
import the.one.base.widge.BridgeWebView;
import the.one.base.widge.BridgeWebViewClient;
import the.one.base.widge.MyTopBarLayout;

/**
 * @author The one
 * @date 2019/4/25 0025
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BaseWebExplorerActivity extends BaseActivity  {

    public static final String EXTRA_URL = "EXTRA_URL";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_NEED_DECODE = "EXTRA_NEED_DECODE";
    public static final String EXTRA_CHANGE_TITLE = "EXTRA_CHANGE_TITLE";
    public static final String EXTRA_TITLE_COLOR = "EXTRA_TITLE_COLOR";

    private static final String IMAGE_LISTENER = "imagelistener";

    protected MyTopBarLayout mTopBarLayout;
    protected QMUIWebViewContainer mWebViewContainer;
    protected QMUIProgressBar mProgressBar;
    protected BridgeWebView mWebView;

    protected String mUrl;
    protected String mTitle;

    public final static int PROGRESS_PROCESS = 0;
    public final static int PROGRESS_GONE = 1;

    private ProgressHandler mProgressHandler;
    private boolean isFirstInit = true;
    private boolean isSeparatorShow = false;
    private boolean mIsPageFinished = false;
    private boolean mNeedDecodeUrl = false;
    private boolean mIsChangeTitle = true;
    private boolean mIsWhiteBg = false;

    public static void newInstance(Activity activity, String title, String url) {
        newInstance(activity, title, url, true, true, true);
    }

    public static void newInstance(Activity activity, String title, String url, boolean changeTitle) {
        newInstance(activity, title, url, true, changeTitle, true);
    }

    public static void newInstance(Activity activity, String title, String url, boolean needDecodeUrl, boolean changeTitle, boolean whiteBg) {
        newInstance(activity, BaseWebExplorerActivity.class, title, url, needDecodeUrl, changeTitle, whiteBg);
    }

    public static void newInstance(Activity activity, Class targetActivity, String title, String url, boolean needDecodeUrl, boolean changeTitle, boolean whiteBg) {
        Intent intent = new Intent(activity, targetActivity);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_NEED_DECODE, needDecodeUrl);
        intent.putExtra(EXTRA_CHANGE_TITLE, changeTitle);
        intent.putExtra(EXTRA_TITLE_COLOR, whiteBg);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_base_webview_exploerer;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(View rootView) {
        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra(EXTRA_URL);
            mTitle = intent.getStringExtra(EXTRA_TITLE);
            mNeedDecodeUrl = intent.getBooleanExtra(EXTRA_NEED_DECODE, false);
            mIsChangeTitle = intent.getBooleanExtra(EXTRA_CHANGE_TITLE, true);
            mIsWhiteBg = intent.getBooleanExtra(EXTRA_TITLE_COLOR, false);
            if (url != null && url.length() > 0) {
                handleUrl(url);
            }
        }
        mTopBarLayout = rootView.findViewById(R.id.topbar);
        mWebViewContainer = rootView.findViewById(R.id.webview_container);
        mProgressBar = rootView.findViewById(R.id.progressbar);
        mProgressHandler = new ProgressHandler();
        initTopBar();
        initWebView();
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        if (isFirstInit) {
            isFirstInit = false;
            // 将耗时的放在动画结束后再进行
            loadUrl();
        }
    }

    protected void initTopBar() {
        mTopBarLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doOnBackPressed();
            }
        });
        mTopBarLayout.setTitle(mTitle);
    }

    private void updateTitle(String title) {
        if (mIsChangeTitle)
            if (title != null && !title.equals("")) {
                mTitle = title;
                mTopBarLayout.setTitle(mTitle);
            }
    }

    protected boolean needDispatchSafeAreaInset() {
        return false;
    }

    @SuppressLint("JavascriptInterface")
    protected void initWebView() {
        mWebView = new BridgeWebView(this);
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        boolean needDispatchSafeAreaInset = needDispatchSafeAreaInset();
        mWebViewContainer.addWebView(mWebView, needDispatchSafeAreaInset);
        mWebViewContainer.setCustomOnScrollChangeListener(new QMUIWebView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(WebView webView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                onScrollWebContent(scrollX, scrollY, oldScrollX, oldScrollY);
            }
        });
        FrameLayout.LayoutParams containerLp = (FrameLayout.LayoutParams) mWebViewContainer.getLayoutParams();
        mWebViewContainer.setFitsSystemWindows(!needDispatchSafeAreaInset);
        containerLp.topMargin = needDispatchSafeAreaInset ? 0 : QMUIResHelper.getAttrDimen(this, R.attr.qmui_topbar_height);
        mWebViewContainer.setLayoutParams(containerLp);
//        mWebView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//                boolean needConfirm = true;
//                if (needConfirm) {
//                    final String finalURL = url;
//                    new QMUIDialog.MessageDialogBuilder(BaseWebExplorerActivity.this)
//                            .setMessage("确认下载此文件？")
//                            .addAction(R.string.cancel, new QMUIDialogAction.ActionListener() {
//                                @Override
//                                public void onClick(QMUIDialog dialog, int index) {
//                                    dialog.dismiss();
//                                }
//                            })
//                            .addAction(R.string.sure, new QMUIDialogAction.ActionListener() {
//                                @Override
//                                public void onClick(QMUIDialog dialog, int index) {
//                                    dialog.dismiss();
//                                    doDownload(finalURL);
//                                }
//                            })
//                            .show();
//                } else {
//                    doDownload(url);
//                }
//            }
//
//            private void doDownload(String url) {
//                ToastUtil.showToast(url);
//            }
//        });
        mWebView.setWebChromeClient(getWebViewChromeClient());
        mWebView.setWebViewClient(getWebViewClient());
        mWebView.requestFocus(View.FOCUS_DOWN);

        setZoomControlGone(mWebView);
        configWebView(mWebViewContainer, mWebView);
        registerHandler();
    }

    protected void loadUrl() {
        mWebView.loadUrl(mUrl);
    }

    protected void registerHandler() {
        mWebView.registerHandler("imagelistener", imageHandler);
    }

    protected void configWebView(QMUIWebViewContainer webViewContainer, QMUIWebView webView) {

    }

    protected void onScrollWebContent(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        // 滑动后添加分割线
        if (!isSeparatorShow && scrollY > 10) {
            isSeparatorShow = true;
            mTopBarLayout.updateBottomSeparatorColor(getColorr(R.color.qmui_config_color_separator));
        } else if (isSeparatorShow) {
            isSeparatorShow = false;
            mTopBarLayout.updateBottomSeparatorColor(getColorr(R.color.qmui_config_color_white));
        }
    }

    private void handleUrl(String url) {
        if (mNeedDecodeUrl) {
            String decodeURL;
            try {
                decodeURL = URLDecoder.decode(url, "utf-8");
            } catch (UnsupportedEncodingException ignored) {
                decodeURL = url;
            }
            mUrl = decodeURL;
        } else {
            mUrl = url;
        }
    }

    protected WebChromeClient getWebViewChromeClient() {
        return new ExplorerWebViewChromeClient(this);
    }

    protected QMUIWebViewClient getWebViewClient() {
        return new ExplorerWebViewClient(needDispatchSafeAreaInset());
    }

    private void sendProgressMessage(int progressType, int newProgress, int duration) {
        Message msg = new Message();
        msg.what = progressType;
        msg.arg1 = newProgress;
        msg.arg2 = duration;
        mProgressHandler.sendMessage(msg);
    }


    public static void setZoomControlGone(WebView webView) {
        webView.getSettings().setDisplayZoomControls(false);
        @SuppressWarnings("rawtypes")
        Class classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController zoomButtonsController = new ZoomButtonsController(
                    webView);
            zoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(webView, zoomButtonsController);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    // 注入js函数监听
    protected void addImageClickListener(WebView webView) {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，
        //函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                " var array=new Array(); " +
                " for(var j=0;j<objs.length;j++){ array[j]=objs[j].src; }" +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.WebViewJavascriptBridge.callHandler('imagelistener',{'position':this.src,'data':array});  " +
                "    }  " +
                "}" +
                "})()");
    }


    protected   BridgeHandler imageHandler = new BridgeHandler() {
        @Override
        public void handler(String data, CallBackFunction function) {
            Log.e(TAG, "handler: " );
            JSONObject jsonObject = null;
            ArrayList<String> itemData;
            int position = 0;
            try {
                jsonObject = new JSONObject(data);
                String src = jsonObject.getString("position");
                String personObject = jsonObject.getString("data");
                itemData = new Gson().fromJson(personObject,
                        new TypeToken<List<String>>() {
                        }.getType());
                for (int i = 0; i < itemData.size(); i++) {
                    String item = itemData.get(i);
                    if (item.contains("apps.game.qq.com") || item.contains("gpcd.gtimg.cn")) {
                        itemData.remove(i);
                    }
                }
                for (int i = 0; i < itemData.size(); i++) {
                    String item = itemData.get(i);
                    if (item.equals(src)) {
                        position = i;
                    }
                }
                startPhotoWatchActivity(itemData, position);
            } catch (JSONException e) {
                Log.e(TAG, "JSONException: ");
                e.printStackTrace();
            }
        }
    };

//    protected BridgeHandler getBridgeHandler(){
//        return new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                Log.e(TAG, "handler: "+data );
//                JSONObject jsonObject = null;
//                ArrayList<String> itemData;
//                int position = 0;
//                try {
//                    jsonObject = new JSONObject(data);
//                    String src = jsonObject.getString("position");
//                    String personObject = jsonObject.getString("data");
//                    itemData = new Gson().fromJson(personObject,
//                            new TypeToken<List<String>>() {
//                            }.getType());
//                    for (int i = 0; i < itemData.size(); i++) {
//                        String item = itemData.get(i);
//                        if (item.equals(src)) {
//                            position = i;
//                        }
//                    }
//                    startPhotoWatchActivity(itemData, position);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//    }

    public class ExplorerWebViewChromeClient extends WebChromeClient {
        private BaseWebExplorerActivity mFragment;

        public ExplorerWebViewChromeClient(BaseWebExplorerActivity fragment) {
            mFragment = fragment;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // 修改进度条
            if (newProgress > mFragment.mProgressHandler.mDstProgressIndex) {
                mFragment.sendProgressMessage(PROGRESS_PROCESS, newProgress, 100);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mFragment.updateTitle(view.getTitle());
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            callback.onCustomViewHidden();
            mTopBarLayout.setBackgroundAlpha(0);
        }

        @Override
        public void onHideCustomView() {

        }
    }

    protected class ExplorerWebViewClient extends BridgeWebViewClient {

        public ExplorerWebViewClient(boolean needDispatchSafeAreaInset) {
            super(mWebView, needDispatchSafeAreaInset, true);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (QMUILangHelper.isNullOrEmpty(mTitle)) {
                updateTitle(view.getTitle());
            }
            if (mProgressHandler.mDstProgressIndex == 0) {
                sendProgressMessage(PROGRESS_PROCESS, 30, 500);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            sendProgressMessage(PROGRESS_GONE, 100, 0);
            if (QMUILangHelper.isNullOrEmpty(mTitle)) {
                updateTitle(view.getTitle());
            }
            addImageClickListener(mWebView);
        }
    }

    private class ProgressHandler extends Handler {

        private int mDstProgressIndex;
        private int mDuration;
        private ObjectAnimator mAnimator;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROGRESS_PROCESS:
                    mIsPageFinished = false;
                    mDstProgressIndex = msg.arg1;
                    mDuration = msg.arg2;
                    mProgressBar.setVisibility(View.VISIBLE);
                    if (mAnimator != null && mAnimator.isRunning()) {
                        mAnimator.cancel();
                    }
                    mAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", mDstProgressIndex);
                    mAnimator.setDuration(mDuration);
                    mAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (mProgressBar.getProgress() == 100) {
                                sendEmptyMessageDelayed(PROGRESS_GONE, 500);
                            }
                        }
                    });
                    mAnimator.start();
                    break;
                case PROGRESS_GONE:
                    mDstProgressIndex = 0;
                    mDuration = 0;
                    mProgressBar.setProgress(0);
                    mProgressBar.setVisibility(View.GONE);
                    if (mAnimator != null && mAnimator.isRunning()) {
                        mAnimator.cancel();
                    }
                    mAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", 0);
                    mAnimator.setDuration(0);
                    mAnimator.removeAllListeners();
                    mIsPageFinished = true;
                    break;
                default:
                    break;
            }
        }
    }

    protected void startPhotoWatchActivity(ArrayList<String> itemData, int position) {
//        ImagePreviewActivity.startThisActivity(BaseWebExplorerActivity.this, null, itemData, position);
//        ImagePreviewUtil.show(BaseWebExplorerActivity.this,itemData,position);
    }

    @Override
    protected void doOnBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else
            super.doOnBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebViewContainer.destroy();
        mWebView = null;
    }

}
