/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package the.one.demo.skin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.util.Log;

import com.qmuiteam.qmui.skin.QMUISkinManager;

import the.one.base.util.QMUIDialogUtil;
import the.one.base.util.SpUtil;
import the.one.demo.App;
import the.one.demo.R;

public class SkinManager {

    private static final String TAG = "SkinManager";

    public static final int SKIN_WHITE = 1;
    public static final int SKIN_DARK = 2;
    public static final int SKIN_BLUE = 3;

    public static void install(Context context) {
        QMUISkinManager skinManager = QMUISkinManager.defaultInstance(context);
        skinManager.addSkin(SKIN_WHITE, R.style.AppTheme);
        skinManager.addSkin(SKIN_DARK, R.style.app_skin_dark);
        skinManager.addSkin(SKIN_BLUE, R.style.app_skin_blue);
        boolean isDarkMode = (context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        int storeSkinIndex = getSkinIndex();
        if (isDarkMode && storeSkinIndex != SKIN_DARK) {
            skinManager.changeSkin(SKIN_DARK);
        } else if (!isDarkMode && storeSkinIndex == SKIN_DARK) {
            skinManager.changeSkin(SKIN_BLUE);
        }else{
            skinManager.changeSkin(storeSkinIndex);
        }
    }

    public static void changeSkin(int index) {
        index++;
        Log.e(TAG, "changeSkin: "+index );
        QMUISkinManager.defaultInstance(App.getInstance()).changeSkin(index);
        SpUtil.getInstance().putInt(SKIN_INDEX,index);
    }

    public static int getCurrentSkin() {
        return QMUISkinManager.defaultInstance(App.getInstance()).getCurrentSkin();
    }

    private static final String SKIN_INDEX = "skin_index";

    public static int getSkinIndex(){
        return SpUtil.getInstance().getInt(SKIN_INDEX,SKIN_WHITE);
    }

    private static String[] themes = new String[]{"白色","黑色","渐变"};

    public static void showThemeDialog(Context context){
        QMUIDialogUtil.showSingleChoiceDialog(context, themes, SkinManager.getSkinIndex() -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SkinManager.changeSkin(which);
            }
        });
    }
}
