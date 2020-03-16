package the.one.aqtour.widge;

import android.content.Context;
import android.util.AttributeSet;

import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class StandardTheVideoPlayer extends StandardGSYVideoPlayer {

    public StandardTheVideoPlayer(Context context) {
        super(context);
    }

    public StandardTheVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onLoading(){
        setStateAndUi(CURRENT_STATE_PREPAREING);
    }

    public void onNormal(){
        setStateAndUi(CURRENT_STATE_NORMAL);
    }

    public void onError(){
        setStateAndUi(CURRENT_STATE_ERROR);
    }

    @Override
    protected void setStateAndUi(int state) {
        if(mCurrentState == state) return;
        super.setStateAndUi(state);
    }

    protected void changeUiToPlayingClear() {
        Debuger.printfLog("changeUiToPlayingClear");
        changeUiToClear();
        setViewShowState(mBottomProgressBar, GONE);
    }

    public void startStandar(){
        startWindowFullscreen(getContext(),false,true);
    }
}
