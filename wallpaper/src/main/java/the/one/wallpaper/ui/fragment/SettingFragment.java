package the.one.wallpaper.ui.fragment;

import android.view.View;
import android.widget.CompoundButton;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;

import the.one.base.ui.fragment.BaseGroupListFragment;
import the.one.wallpaper.constant.WallpaperConstant;
import the.one.wallpaper.service.DynamicWallpaper1;
import the.one.wallpaper.service.DynamicWallpaper2;
import the.one.wallpaper.util.WallpaperSpUtil;

public class SettingFragment extends BaseGroupListFragment implements CompoundButton.OnCheckedChangeListener {

    private QMUICommonListItemView VOICE_SWITCH, VOLUME, DURATION;

    @Override
    protected void addGroupListView() {
        initFragmentBack("设置");
        VOLUME = CreateDetailItemView("声音大小", "");

        VOICE_SWITCH = CreateSwitchItemView("视频声音", this);

        boolean volume = WallpaperSpUtil.getWallpaperVoiceSwitch();
        VOICE_SWITCH.getSwitch().setChecked(volume);
        VOLUME.setEnabled(volume);

        addToGroup("", VOICE_SWITCH, VOLUME);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        WallpaperSpUtil.setWallpaperVoiceSwitch(b);
        VOLUME.setEnabled(b);
        // 当前有运行且当前服务是此APP的才进行更改
        if(WallpaperSpUtil.isCurrentAppWallpaper(_mActivity)){
            String service = WallpaperSpUtil.getCurrentService();
            if (service.equals(WallpaperConstant.SERCIVE_1)) {
                DynamicWallpaper1.sendVoice(_mActivity, b);
            } else if (service.equals(WallpaperConstant.SERCIVE_2)) {
                DynamicWallpaper2.sendVoice(_mActivity, b);
            } else {
                return;
            }
        }
    }

}
