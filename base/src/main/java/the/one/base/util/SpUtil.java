package the.one.base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author The one
 * @date 2018/5/28 0028
 * @describe SharedPreferences 工具类
 * @email 625805189@qq.com
 * @remark
 */
public class SpUtil {

    private static SpUtil spUtil;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static SpUtil getInstance() {
        if (spUtil == null) {
            spUtil = new SpUtil();
        }
        return spUtil;
    }

    public void init(Context context) {
        init(context, context.getPackageName());
    }

    @SuppressLint("CommitPrefEdits")
    public void init(Context context, String sharedName) {
        preferences = context.getSharedPreferences(sharedName, Context.MODE_MULTI_PROCESS);
        editor = preferences.edit();
    }

    public String getString(String key) {
        String s = preferences.getString(key, null);
        return s;
    }

    public String getString(String key, String value) {
        String s = preferences.getString(key, value);
        return s;
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public int getInt(String key, int value) {
        int s = preferences.getInt(key, value);
        return s;
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean value) {
        boolean s = preferences.getBoolean(key, value);
        return s;
    }

    public boolean getBoolean(String key) {
        boolean s = preferences.getBoolean(key, false);
        return s;
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public long getLong(String key, long value) {
        long s = preferences.getLong(key, value);
        return s;
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public float getFloat(String key, float value) {
        float s = preferences.getFloat(key, value);
        return s;
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public void clear() {
        editor.clear();
    }
}
