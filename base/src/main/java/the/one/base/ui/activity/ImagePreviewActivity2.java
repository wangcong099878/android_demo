package the.one.base.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.Transition;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;
import cc.shinichi.library.glide.FileTarget;
import cc.shinichi.library.glide.ImageLoader;
import cc.shinichi.library.glide.progress.OnProgressListener;
import cc.shinichi.library.glide.progress.ProgressManager;
import cc.shinichi.library.tool.common.HandlerUtils;
import cc.shinichi.library.tool.image.DownloadPictureUtil;
import cc.shinichi.library.tool.ui.ToastUtil;
import cc.shinichi.library.view.HackyViewPager;
import cc.shinichi.library.view.ImagePreviewAdapter;
import the.one.base.R;
import the.one.base.ui.presenter.BasePresenter;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ImagePreviewActivity2 extends BaseActivity implements Handler.Callback, View.OnClickListener {

    public static final String TAG = "ImagePreview";

    private Context context;

    private List<ImageInfo> imageInfoList;
    private int currentItem;// 当前显示的图片索引
    private boolean isShowDownButton;
    private boolean isShowCloseButton;
    private boolean isShowOriginButton;
    private boolean isShowIndicator;

    private ImagePreviewAdapter imagePreviewAdapter;
    private HackyViewPager viewPager;
    private TextView tv_indicator;
    private FrameLayout fm_image_show_origin_container;
    private QMUIProgressBar mProgressBar;
    private Button btn_show_origin;
    private ImageView img_download;
    private ImageView imgCloseButton;
    private View rootView;
    private View progressParentLayout;

    private boolean isUserCustomProgressView = false;

    // 指示器显示状态
    private boolean indicatorStatus = false;
    // 原图按钮显示状态
    private boolean originalStatus = false;
    // 下载按钮显示状态
    private boolean downloadButtonStatus = false;
    // 关闭按钮显示状态
    private boolean closeButtonStatus = false;

    private String currentItemOriginPathUrl = "";// 当前显示的原图链接
    private HandlerUtils.HandlerHolder handlerHolder;
    private int lastProgress = 0;

    public static void activityStart(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(context, ImagePreviewActivity2.class);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected boolean isStatusBarLightMode() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_image_preview;
    }

    @Override
    protected void initView(View mRootView) {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        context = this;
        handlerHolder = new HandlerUtils.HandlerHolder(this);
        imageInfoList = ImagePreview.getInstance().getImageInfoList();
        if (null == imageInfoList || imageInfoList.size() == 0) {
            onBackPressed();
            return;
        }
        currentItem = ImagePreview.getInstance().getIndex();
        isShowDownButton = ImagePreview.getInstance().isShowDownButton();
        isShowCloseButton = ImagePreview.getInstance().isShowCloseButton();
        isShowIndicator = ImagePreview.getInstance().isShowIndicator();

        currentItemOriginPathUrl = imageInfoList.get(currentItem).getOriginUrl();

        isShowOriginButton = ImagePreview.getInstance().isShowOriginButton(currentItem);
        if (isShowOriginButton) {
            // 检查缓存是否存在
            checkCache(currentItemOriginPathUrl);
        }

        rootView = findViewById(R.id.rootView);
        viewPager = findViewById(R.id.viewPager);
        tv_indicator = findViewById(R.id.tv_indicator);

        fm_image_show_origin_container = findViewById(R.id.fm_image_show_origin_container);
        mProgressBar = findViewById(R.id.progressbar);

        fm_image_show_origin_container.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        btn_show_origin = findViewById(cc.shinichi.library.R.id.btn_show_origin);
        img_download = findViewById(cc.shinichi.library.R.id.img_download);
        imgCloseButton = findViewById(cc.shinichi.library.R.id.imgCloseButton);

        img_download.setImageResource(ImagePreview.getInstance().getDownIconResId());
        imgCloseButton.setImageResource(ImagePreview.getInstance().getCloseIconResId());

        // 关闭页面按钮
        imgCloseButton.setOnClickListener(this);
        // 查看与原图按钮
        btn_show_origin.setOnClickListener(this);
        // 下载图片按钮
        img_download.setOnClickListener(this);

        if (!isShowIndicator) {
            tv_indicator.setVisibility(View.GONE);
            indicatorStatus = false;
        } else {
            if (imageInfoList.size() > 1) {
                tv_indicator.setVisibility(View.VISIBLE);
                indicatorStatus = true;
            } else {
                tv_indicator.setVisibility(View.GONE);
                indicatorStatus = false;
            }
        }
        // 设置顶部指示器背景shape
        if (ImagePreview.getInstance().getIndicatorShapeResId() > 0) {
            tv_indicator.setBackgroundResource(ImagePreview.getInstance().getIndicatorShapeResId());
        }

        if (isShowDownButton) {
            img_download.setVisibility(View.VISIBLE);
            downloadButtonStatus = true;
        } else {
            img_download.setVisibility(View.GONE);
            downloadButtonStatus = false;
        }

        if (isShowCloseButton) {
            imgCloseButton.setVisibility(View.VISIBLE);
            closeButtonStatus = true;
        } else {
            imgCloseButton.setVisibility(View.GONE);
            closeButtonStatus = false;
        }

        // 更新进度指示器
        tv_indicator.setText(
                String.format(getString(cc.shinichi.library.R.string.indicator), currentItem + 1 + "", "" + imageInfoList.size()));

        imagePreviewAdapter = new ImagePreviewAdapter(this, imageInfoList);
        viewPager.setAdapter(imagePreviewAdapter);
        viewPager.setCurrentItem(currentItem);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (ImagePreview.getInstance().getBigImagePageChangeListener() != null) {
                    ImagePreview.getInstance().getBigImagePageChangeListener().onPageSelected(position);
                }
                currentItem = position;
                currentItemOriginPathUrl = imageInfoList.get(position).getOriginUrl();

                isShowOriginButton = ImagePreview.getInstance().isShowOriginButton(currentItem);
                if (isShowOriginButton) {
                    // 检查缓存是否存在
                    checkCache(currentItemOriginPathUrl);
                } else {
                    gone();
                }
                // 更新进度指示器
                tv_indicator.setText(
                        String.format(getString(cc.shinichi.library.R.string.indicator), currentItem + 1 + "", "" + imageInfoList.size()));
                // 如果是自定义百分比进度view，每次切换都先隐藏，并重置百分比
                if (isUserCustomProgressView) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                if (ImagePreview.getInstance().getBigImagePageChangeListener() != null) {
                    ImagePreview.getInstance()
                            .getBigImagePageChangeListener()
                            .onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

                if (ImagePreview.getInstance().getBigImagePageChangeListener() != null) {
                    ImagePreview.getInstance().getBigImagePageChangeListener().onPageScrollStateChanged(state);
                }
            }
        });
    }

    /**
     * 下载当前图片到SD卡
     */
    private void downloadCurrentImg() {
        DownloadPictureUtil.downloadPicture(context.getApplicationContext(), currentItemOriginPathUrl);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(cc.shinichi.library.R.anim.fade_in, cc.shinichi.library.R.anim.fade_out);
    }

    public int convertPercentToBlackAlphaColor(float percent) {
        percent = Math.min(1, Math.max(0, percent));
        int intAlpha = (int) (percent * 255);
        String stringAlpha = Integer.toHexString(intAlpha).toLowerCase();
        String color = "#" + (stringAlpha.length() < 2 ? "0" : "") + stringAlpha + "000000";
        return Color.parseColor(color);
    }

    public void setAlpha(float alpha) {
        int colorId = convertPercentToBlackAlphaColor(alpha);
        rootView.setBackgroundColor(colorId);
        if (alpha >= 1) {
            if (indicatorStatus) {
                tv_indicator.setVisibility(View.VISIBLE);
            }
            if (originalStatus) {
                fm_image_show_origin_container.setVisibility(View.VISIBLE);
            }
            if (downloadButtonStatus) {
                img_download.setVisibility(View.VISIBLE);
            }
            if (closeButtonStatus) {
                imgCloseButton.setVisibility(View.VISIBLE);
            }
        } else {
            tv_indicator.setVisibility(View.GONE);
            fm_image_show_origin_container.setVisibility(View.GONE);
            img_download.setVisibility(View.GONE);
            imgCloseButton.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == 0) {// 点击查看原图按钮，开始加载原图
            final String path = imageInfoList.get(currentItem).getOriginUrl();
            visible();
            if (isUserCustomProgressView) {
                gone();
            } else {
                btn_show_origin.setText("0 %");
            }

            if (checkCache(path)) {
                Message message = handlerHolder.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("url", path);
                message.what = 1;
                message.obj = bundle;
                handlerHolder.sendMessage(message);
                return true;
            }
            loadOriginImage(path);
        } else if (msg.what == 1) {// 加载完成
            Bundle bundle = (Bundle) msg.obj;
            String url = bundle.getString("url");
            gone();
            if (currentItem == getRealIndexWithPath(url)) {
                if (isUserCustomProgressView) {
                    mProgressBar.setVisibility(View.GONE);
                    if (ImagePreview.getInstance().getOnOriginProgressListener() != null) {
                        progressParentLayout.setVisibility(View.GONE);
                        ImagePreview.getInstance().getOnOriginProgressListener().finish(progressParentLayout);
                    }
                    imagePreviewAdapter.loadOrigin(imageInfoList.get(currentItem));
                } else {
                    imagePreviewAdapter.loadOrigin(imageInfoList.get(currentItem));
                }
            }
        } else if (msg.what == 2) {// 加载中
            Bundle bundle = (Bundle) msg.obj;
            String url = bundle.getString("url");
            int progress = bundle.getInt("progress");
            if (currentItem == getRealIndexWithPath(url)) {
                if (isUserCustomProgressView) {
                    gone();
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(progress);
                } else {
                    visible();
                    btn_show_origin.setText(String.format("%s %%", String.valueOf(progress)));
                }
            }
        } else if (msg.what == 3) {// 隐藏查看原图按钮
            btn_show_origin.setText("查看原图");
            fm_image_show_origin_container.setVisibility(View.GONE);
            originalStatus = false;
        } else if (msg.what == 4) {// 显示查看原图按钮
            fm_image_show_origin_container.setVisibility(View.VISIBLE);
            originalStatus = true;
        }
        return true;
    }

    private int getRealIndexWithPath(String path) {
        for (int i = 0; i < imageInfoList.size(); i++) {
            if (path.equalsIgnoreCase(imageInfoList.get(i).getOriginUrl())) {
                return i;
            }
        }
        return 0;
    }

    private boolean checkCache(String url) {
        File cacheFile = ImageLoader.getGlideCacheFile(context, url);
        if (cacheFile != null && cacheFile.exists()) {
            gone();
            return true;
        } else {
            visible();
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == cc.shinichi.library.R.id.img_download) {// 检查权限
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ImagePreviewActivity2.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // 拒绝权限
                    ToastUtil.getInstance()._short(context, "没有权限很难办事啊~");
                } else {
                    //申请权限
                    ActivityCompat.requestPermissions(ImagePreviewActivity2.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
                }
            } else {
                // 下载当前图片
                downloadCurrentImg();
            }
        } else if (i == R.id.btn_show_origin) {
            handlerHolder.sendEmptyMessage(0);
        } else if (i == R.id.imgCloseButton) {
            onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    downloadCurrentImg();
                } else {
                    ToastUtil.getInstance()._short(context, "您拒绝了存储权限，下载失败！");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImagePreview.getInstance().reset();
        if (imagePreviewAdapter != null) {
            imagePreviewAdapter.closePage();
        }
    }

    private void gone() {
        handlerHolder.sendEmptyMessage(3);
    }

    private void visible() {
        handlerHolder.sendEmptyMessage(4);
    }

    private void loadOriginImage(final String path) {
        Glide.with(context).downloadOnly().load(path).into(new FileTarget() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                super.onResourceReady(resource, transition);
            }
        });

        ProgressManager.addListener(path, new OnProgressListener() {
            @Override
            public void onProgress(String url, boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                if (isComplete) {// 加载完成
                    Message message = handlerHolder.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    message.what = 1;
                    message.obj = bundle;
                    handlerHolder.sendMessage(message);
                } else {// 加载中，为减少回调次数，此处做判断，如果和上次的百分比一致就跳过
                    if (percentage == lastProgress) {
                        return;
                    }
                    lastProgress = percentage;
                    Message message = handlerHolder.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    bundle.putInt("progress", percentage);
                    message.what = 2;
                    message.obj = bundle;
                    handlerHolder.sendMessage(message);
                }
            }
        });
    }
}