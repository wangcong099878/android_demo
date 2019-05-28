package the.one.base.base.fragment;

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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ZoomButtonsController;

import com.qmuiteam.qmui.util.QMUILangHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIProgressBar;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.webview.QMUIWebView;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewContainer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.ArrayList;

import the.one.base.R;
import the.one.base.base.activity.PhotoWatchActivity;
import the.one.base.base.presenter.BasePresenter;
import the.one.base.widge.BaseWebView;

/**
 * @author The one
 * @date 2019/4/25 0025
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BaseWebExplorerFragment extends BaseFragment {

    public static final String EXTRA_URL = "EXTRA_URL";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_NEED_DECODE = "EXTRA_NEED_DECODE";
    public static final String EXTRA_CHANGE_TITLE = "EXTRA_CHANGE_TITLE";

    protected QMUITopBarLayout mTopBarLayout;
    protected QMUIWebViewContainer mWebViewContainer;
    protected QMUIProgressBar mProgressBar;
    private BaseWebView mWebView;

    private String mUrl;
    private String mTitle;

    private final static int PROGRESS_PROCESS = 0;
    private final static int PROGRESS_GONE = 1;

    private ProgressHandler mProgressHandler;
    private boolean mIsPageFinished = false;
    private boolean mNeedDecodeUrl = false;
    private boolean mIsChangeTitle = true;

    public static BaseWebExplorerFragment newInstance(String title, String url) {
        return newInstance(title, url, true, true);
    }

    public static BaseWebExplorerFragment newInstance(String title, String url, boolean changeTitle) {
        return newInstance(title, url, true, changeTitle);
    }

    public static BaseWebExplorerFragment newInstance(String title, String url, boolean needDecodeUrl, boolean changeTitle) {
        BaseWebExplorerFragment fragment = new BaseWebExplorerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        bundle.putString(EXTRA_URL, url);
        bundle.putBoolean(EXTRA_NEED_DECODE, needDecodeUrl);
        bundle.putBoolean(EXTRA_CHANGE_TITLE, changeTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected boolean showTitleBar() {
        return false;
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            String url = bundle.getString(EXTRA_URL);
            mTitle = bundle.getString(EXTRA_TITLE);
            mNeedDecodeUrl = bundle.getBoolean(EXTRA_NEED_DECODE, false);
            mIsChangeTitle = bundle.getBoolean(EXTRA_CHANGE_TITLE, true);
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

    protected void initTopBar() {
        mTopBarLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popBackStack();
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
        mWebView = new BaseWebView(getContext());
        boolean needDispatchSafeAreaInset = needDispatchSafeAreaInset();
        mWebViewContainer.addWebView(mWebView, needDispatchSafeAreaInset);
        mWebViewContainer.setCustomOnScrollChangeListener(new QMUIWebView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                onScrollWebContent(scrollX, scrollY, oldScrollX, oldScrollY);

            }
        });
        FrameLayout.LayoutParams containerLp = (FrameLayout.LayoutParams) mWebViewContainer.getLayoutParams();
        mWebViewContainer.setFitsSystemWindows(!needDispatchSafeAreaInset);
        containerLp.topMargin = needDispatchSafeAreaInset ? 0 : QMUIResHelper.getAttrDimen(getContext(), R.attr.qmui_topbar_height);
        mWebViewContainer.setLayoutParams(containerLp);

        mWebView.addJavascriptInterface(new MJavascriptInterface(_mActivity), "imagelistener");
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                boolean needConfirm = !url.startsWith("http://qmuiteam.com") && !url.startsWith("https://qmuiteam.com");
                if (needConfirm) {
                    final String finalURL = url;
                    new QMUIDialog.MessageDialogBuilder(getContext())
                            .setMessage("确认下载此文件？")
                            .addAction(R.string.cancel, new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                    popBackStack();
                                }
                            })
                            .addAction(R.string.ok, new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                    doDownload(finalURL);
                                    popBackStack();
                                }
                            })
                            .show();
                } else {
                    doDownload(url);
                }
            }

            private void doDownload(String url) {

            }
        });

        mWebView.setWebChromeClient(getWebViewChromeClient());
        mWebView.setWebViewClient(getWebViewClient());
        mWebView.requestFocus(View.FOCUS_DOWN);
        setZoomControlGone(mWebView);
        configWebView(mWebViewContainer, mWebView);
        mWebView.loadUrl(mUrl);

    }

    protected void configWebView(QMUIWebViewContainer webViewContainer, QMUIWebView webView) {

    }

    protected void onScrollWebContent(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebViewContainer.destroy();
        mWebView = null;
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

    public class ExplorerWebViewChromeClient extends WebChromeClient {
        private BaseWebExplorerFragment mFragment;

        public ExplorerWebViewChromeClient(BaseWebExplorerFragment fragment) {
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

    protected class ExplorerWebViewClient extends QMUIWebViewClient {

        public ExplorerWebViewClient(boolean needDispatchSafeAreaInset) {
            super(needDispatchSafeAreaInset, true);
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
            addImageClickListener();
        }
    }

    // 注入js函数监听
    private void addImageClickListener() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，
        //函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                " var array=new Array(); " +
                " for(var j=0;j<objs.length;j++){ array[j]=objs[j].src; }" +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistener.openImage(this.src,array);  " +
                "    }  " +
                "}" +
                "})()");
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

    public class MJavascriptInterface {

        private Activity activity;
        private ArrayList<String> list_imgs = new ArrayList<>();
        private int index = 0;

        public MJavascriptInterface(Activity activity) {
            this.activity = activity;
        }

        @JavascriptInterface
        public void openImage(String img, String[] imgs) {
            list_imgs.clear();
            for (int i = 0; i < imgs.length; i++) {
                Log.e(TAG, "openImage: " + imgs[i]);
                if (imgs[i].equals(img)) {
                    index = i;
                }
                list_imgs.add(imgs[i]);
            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PhotoWatchActivity.startThisActivity(activity, mWebView, list_imgs, index);
                }
            });
        }
    }
}
