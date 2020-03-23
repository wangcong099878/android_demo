package the.one.wallpaper.ui.fragment;

import android.view.View;
import android.widget.CompoundButton;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.base.fragment.BaseGroupListFragment;
import the.one.wallpaper.constant.WallpaperConstant;
import the.one.wallpaper.service.DynamicWallpaper1;
import the.one.wallpaper.service.DynamicWallpaper2;
import the.one.wallpaper.util.WallpaperSpUtil;
import the.one.wallpaper.util.WallpaperUtil;

public class SettingFragment extends BaseGroupListFragment implements CompoundButton.OnCheckedChangeListener {

    private QMUICommonListItemView VOICE_SWITCH, VOLUME, DURATION;
    private WallpaperSpUtil wallpaperSpUtil;

    @Override
    protected void addGroupListView() {
        initFragmentBack("设置");
        wallpaperSpUtil = WallpaperSpUtil.getInstance();
        VOLUME = CreateDetailItemView("声音大小", "");

        VOICE_SWITCH = CreateSwitchItemView("视频声音", this);

        boolean volume = wallpaperSpUtil.getWallpaperVoiceSwitch();
        VOICE_SWITCH.getSwitch().setChecked(volume);
        VOLUME.setEnabled(volume);

        addToGroup("", VOICE_SWITCH, VOLUME);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        wallpaperSpUtil.setWallpaperVoiceSwitch(b);
        VOLUME.setEnabled(b);
        // 当前有运行且当前服务是此APP的才进行更改
        if(WallpaperUtil.isCurrentAppWallpaper(_mActivity)){
            String service = wallpaperSpUtil.getCurrentService();
            if (service.equals(WallpaperConstant.SERCIVE_1)) {
                DynamicWallpaper1.setVoice(_mActivity, b);
            } else if (service.equals(WallpaperConstant.SERCIVE_2)) {
                DynamicWallpaper2.setVoice(_mActivity, b);
            } else {
                return;
            }
        }
    }

}
