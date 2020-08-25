package the.one.base.widge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import the.one.base.R;


/**
 * @author Vlonjat Gashi (vlonjatg)
 */
public class StatusLayout extends RelativeLayout {

    private static final String TAG_LOADING = "StatusLayout.TAG_LOADING";
    private static final String TAG_EMPTY = "StatusLayout.TAG_EMPTY";
    private static final String TAG_ERROR = "StatusLayout.TAG_ERROR";

    final String CONTENT = "StatusLayout_type_content";
    final String LOADING = "StatusLayout_type_loading";
    final String EMPTY = "StatusLayout_type_empty";
    final String ERROR = "StatusLayout_type_error";

    LayoutInflater inflater;
    View view;
    LayoutParams layoutParams;
    Drawable currentBackground;

    List<View> contentViews = new ArrayList<>();

    RelativeLayout loadingStateRelativeLayout;
    ProgressWheel loadingStateProgressBar;
    AppCompatTextView loadingTips;


    RelativeLayout errorStateRelativeLayout;
    ImageView errorStateImageView;
    TextView errorStateTitleTextView;
    TextView errorStateContentTextView;
    QMUIRoundButton errorStateButton;

    int loadingStateProgressBarWidth;
    int loadingStateProgressBarHeight;
    int loadingStateBackgroundColor;

    int emptyStateImageWidth;
    int emptyStateImageHeight;
    int emptyStateTitleTextSize;
    int emptyStateContentTextSize;
    int emptyStateTitleTextColor;
    int emptyStateContentTextColor;
    int emptyStateBackgroundColor;

    int errorStateImageWidth;
    int errorStateImageHeight;
    int errorStateTitleTextSize;
    int errorStateContentTextSize;
    int errorStateTitleTextColor;
    int errorStateContentTextColor;
    int errorStateButtonTextColor;
    int errorStateBackgroundColor;

    Drawable errorDrawableRes;
    Drawable emptyDrawableRes;

    public static final int defaultTextColor = R.color.qmui_config_color_gray_6;
    public static final int defaultTextSize = 15;

    private String state = CONTENT;


    public StatusLayout(Context context) {
        super(context);
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        errorDrawableRes = QMUISkinHelper.getSkinDrawable(this,R.attr.app_skin_status_layout_error_drawable);
        emptyDrawableRes = QMUISkinHelper.getSkinDrawable(this,R.attr.app_skin_status_layout_empty_drawable);

        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StatusLayout);

        //Loading state attrs
        loadingStateProgressBarWidth =
                typedArray.getDimensionPixelSize(R.styleable.StatusLayout_loadingProgressBarWidth, 30);

        loadingStateProgressBarHeight =
                typedArray.getDimensionPixelSize(R.styleable.StatusLayout_loadingProgressBarHeight, 30);

        loadingStateBackgroundColor =
                typedArray.getColor(R.styleable.StatusLayout_loadingBackgroundColor, Color.TRANSPARENT);

        //Empty state attrs
        emptyStateImageWidth =
                typedArray.getDimensionPixelSize(R.styleable.StatusLayout_emptyImageWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        emptyStateImageHeight =
                typedArray.getDimensionPixelSize(R.styleable.StatusLayout_emptyImageHeight, ViewGroup.LayoutParams.WRAP_CONTENT);

        emptyStateTitleTextSize =
                typedArray.getDimensionPixelSize(R.styleable.StatusLayout_emptyTitleTextSize, defaultTextSize);

        emptyStateContentTextSize =
                typedArray.getDimensionPixelSize(R.styleable.StatusLayout_emptyContentTextSize, defaultTextSize);

        emptyStateTitleTextColor =
                typedArray.getColor(R.styleable.StatusLayout_emptyTitleTextColor, ContextCompat.getColor(getContext(), defaultTextColor));

        emptyStateContentTextColor =
                typedArray.getColor(R.styleable.StatusLayout_emptyContentTextColor, ContextCompat.getColor(getContext(), defaultTextColor));

        emptyStateBackgroundColor =
                typedArray.getColor(R.styleable.StatusLayout_emptyBackgroundColor, Color.TRANSPARENT);

        //Error state attrs
        errorStateImageWidth =
                typedArray.getDimensionPixelSize(R.styleable.StatusLayout_errorImageWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        errorStateImageHeight =
                typedArray.getDimensionPixelSize(R.styleable.StatusLayout_errorImageHeight, ViewGroup.LayoutParams.WRAP_CONTENT);

        errorStateTitleTextSize =
                typedArray.getDimensionPixelSize(R.styleable.StatusLayout_errorTitleTextSize, defaultTextSize);

        errorStateContentTextSize =
                typedArray.getDimensionPixelSize(R.styleable.StatusLayout_errorContentTextSize, defaultTextSize);

        errorStateTitleTextColor =
                typedArray.getColor(R.styleable.StatusLayout_errorTitleTextColor, ContextCompat.getColor(getContext(), defaultTextColor));

        errorStateContentTextColor =
                typedArray.getColor(R.styleable.StatusLayout_errorContentTextColor, ContextCompat.getColor(getContext(), defaultTextColor));

        errorStateButtonTextColor =
                typedArray.getColor(R.styleable.StatusLayout_errorButtonTextColor, Color.WHITE);

        errorStateBackgroundColor =
                typedArray.getColor(R.styleable.StatusLayout_errorBackgroundColor, Color.TRANSPARENT);

        typedArray.recycle();

        currentBackground = this.getBackground();
    }

    @Override
    public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        if (child.getTag() == null || (!child.getTag().equals(TAG_LOADING) &&
                !child.getTag().equals(TAG_EMPTY) && !child.getTag().equals(TAG_ERROR))) {

            contentViews.add(child);
        }
    }

    /**
     * Hide all other states and show content
     */
    public void showContent() {
        switchState(CONTENT, null, null, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Hide all other states and show content
     *
     * @param skipIds Ids of views not to show
     */
    public void showContent(List<Integer> skipIds) {
        switchState(CONTENT, null, null, null, null, null, skipIds);
    }

    /**
     * Hide content and show the progress bar
     */
    public void showLoading() {
        switchState(LOADING, null, null, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Hide content and show the progress bar
     *
     * @param skipIds Ids of views to not hide
     */
    public void showLoading(List<Integer> skipIds) {
        switchState(LOADING, null, null, null, null, null, skipIds);
    }

    public void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle) {
        showEmpty(emptyImageDrawable, emptyTextTitle, "", "刷新试试", null);
    }

    public void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent) {
        showEmpty(emptyImageDrawable, emptyTextTitle, emptyTextContent, "刷新试试", null);
    }

    /**
     * Show empty view when there are not data to show
     *
     * @param emptyImageDrawable Drawable to show
     * @param emptyTextTitle     Title of the empty view to show
     * @param emptyTextContent   Content of the empty view to show
     */
    public void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent, OnClickListener onClickListener) {
        showEmpty(emptyImageDrawable, emptyTextTitle, emptyTextContent, "刷新试试", onClickListener);
    }

    /**
     * Show empty view when there are not data to show
     *
     * @param emptyImageDrawable Drawable to show
     * @param emptyTextTitle     Title of the empty view to show
     * @param emptyTextContent   Content of the empty view to show
     */
    public void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent, String btnText, OnClickListener onClickListener) {
        switchState(EMPTY, emptyImageDrawable, emptyTextTitle, emptyTextContent, btnText, onClickListener, Collections.<Integer>emptyList());
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     *
     * @param errorImageDrawable Drawable to show
     * @param errorTextTitle     Title of the error view to show
     * @param errorTextContent   Content of the error view to show
     * @param errorButtonText    Text on the error view button to show
     * @param onClickListener    Listener of the error view button
     */
    public void showError(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent, String errorButtonText, OnClickListener onClickListener) {
        switchState(ERROR, errorImageDrawable, errorTextTitle, errorTextContent, errorButtonText, onClickListener, Collections.<Integer>emptyList());
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     *
     * @param errorImageDrawable Drawable to show
     * @param errorTextTitle     Title of the error view to show
     * @param errorTextContent   Content of the error view to show
     * @param errorButtonText    Text on the error view button to show
     * @param onClickListener    Listener of the error view button
     * @param skipIds            Ids of views to not hide
     */
    public void showError(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent, String errorButtonText, OnClickListener onClickListener, List<Integer> skipIds) {
        switchState(ERROR, errorImageDrawable, errorTextTitle, errorTextContent, errorButtonText, onClickListener, skipIds);
    }

    /**
     * Get which state is set
     *
     * @return State
     */
    public String getState() {
        return state;
    }

    /**
     * Check if content is shown
     *
     * @return boolean
     */
    public boolean isContent() {
        return state.equals(CONTENT);
    }

    /**
     * Check if loading state is shown
     *
     * @return boolean
     */
    public boolean isLoading() {
        return state.equals(LOADING);
    }

    /**
     * Check if empty state is shown
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return state.equals(EMPTY);
    }

    /**
     * Check if error state is shown
     *
     * @return boolean
     */
    public boolean isError() {
        return state.equals(ERROR);
    }

    private void switchState(String state, Drawable drawable, String errorText, String errorTextContent,
                             String errorButtonText, OnClickListener onClickListener, List<Integer> skipIds) {
        this.state = state;

        switch (state) {
            case CONTENT:
                //Hide all state views to display content
                hideLoadingView();
                hideErrorView();
                setContentVisibility(true, skipIds);
                break;
            case LOADING:
                hideErrorView();
                setLoadingView();
                setContentVisibility(false, skipIds);
                break;
            case EMPTY:
            case ERROR:
                hideLoadingView();
                setErrorView();
                if(null != drawable){
                    errorStateImageView.setImageDrawable(drawable);
                } else {
                    errorStateImageView.setImageDrawable(state.equals(EMPTY)?emptyDrawableRes:errorDrawableRes);
                }
                errorStateTitleTextView.setVisibility(VISIBLE);
                errorStateTitleTextView.setText(errorText);
                errorStateContentTextView.setText(errorTextContent);
                if (null == errorButtonText || errorButtonText.isEmpty())
                    errorStateButton.setVisibility(GONE);
                errorStateButton.setText(errorButtonText);
                errorStateButton.setOnClickListener(onClickListener);
                setContentVisibility(false, skipIds);
                break;
        }
    }

    private void setLoadingView() {
        if (loadingStateRelativeLayout == null) {
            view = inflater.inflate(R.layout.status_loading_view, null);
            loadingStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.loadingStateRelativeLayout);
            loadingStateRelativeLayout.setTag(TAG_LOADING);

            loadingStateProgressBar = view.findViewById(R.id.loading_view);
            loadingStateProgressBar.setBarColor(QMUISkinHelper.getSkinColor(this,R.attr.app_skin_primary_color));
            loadingTips = view.findViewById(R.id.loading_tips);
            //Set background type_color_selector if not TRANSPARENT
            if (loadingStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(loadingStateBackgroundColor);
            }

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            addView(loadingStateRelativeLayout, layoutParams);
        } else {
            loadingStateRelativeLayout.setVisibility(VISIBLE);
        }
    }

    private void setErrorView() {
        if (errorStateRelativeLayout == null) {
            view = inflater.inflate(R.layout.status_error_view, null);
            errorStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.errorStateRelativeLayout);
            errorStateRelativeLayout.setTag(TAG_ERROR);

            errorStateImageView = (ImageView) view.findViewById(R.id.errorStateImageView);
            errorStateTitleTextView = (TextView) view.findViewById(R.id.errorStateTitleTextView);
            errorStateContentTextView = (TextView) view.findViewById(R.id.errorStateContentTextView);
            errorStateButton = view.findViewById(R.id.errorStateButton);

            //Set error state image width and height
            errorStateImageView.getLayoutParams().width = errorStateImageWidth;
            errorStateImageView.getLayoutParams().height = errorStateImageHeight;
            errorStateImageView.requestLayout();

            errorStateTitleTextView.setTextColor(errorStateTitleTextColor);
            errorStateContentTextView.setTextColor(errorStateContentTextColor);
            errorStateButton.setTextColor(errorStateButtonTextColor);

            //Set background type_color_selector if not TRANSPARENT
            if (errorStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(errorStateBackgroundColor);
            }

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            addView(errorStateRelativeLayout, layoutParams);
        } else {
            errorStateRelativeLayout.setVisibility(VISIBLE);
        }
    }

    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : contentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void hideLoadingView() {
        if (loadingStateRelativeLayout != null) {
            loadingStateRelativeLayout.setVisibility(GONE);
            //Restore the background type_color_selector if not TRANSPARENT
            if (loadingStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundDrawable(currentBackground);
            }
        }
    }

    private void hideErrorView() {
        if (errorStateRelativeLayout != null) {
            errorStateRelativeLayout.setVisibility(GONE);
            //Restore the background type_color_selector if not TRANSPARENT
            if (errorStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundDrawable(currentBackground);
            }
        }
    }

    public ProgressWheel getLoadingStateProgressBar() {
        return loadingStateProgressBar;
    }

    public RelativeLayout getLoadingStateRelativeLayout() {
        return loadingStateRelativeLayout;
    }

    public AppCompatTextView getLoadingTipsView(){
        return loadingTips;
    }

}