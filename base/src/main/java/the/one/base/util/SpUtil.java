package the.one.base.util;

import android.content.Context;

import com.tencent.mmkv.MMKV;

/**
 * @author The one
 * @date 2018/5/28 0028
 * @describe SharedPreferences 工具类
 * @email 625805189@qq.com
 * @remark
 */
public class SpUtil {

    private static SpUtil spUtil = null;
    private static MMKV kv = null;

    public static SpUtil getInstance() {
        if (spUtil == null) {
            spUtil = new SpUtil();
        }
//        if(null == kv){
            kv = MMKV.defaultMMKV();
//        }
        return spUtil;
    }

    public static void init(Context context) {
        MMKV.initialize(context.getApplicationContext());
    }

    public String getString(String key) {
        return getString(key,null);
    }

    public String getString(String key, String value) {
        return kv.decodeString(key,value);
    }

    public void putString(String key, String value) {
        kv.encode(key,value);
    }

    public int getInt(String key, int value) {
        return kv.decodeInt(key,value);
    }

    public void putInt(String key, int value) {
        kv.encode(key,value);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key,false);
    }

    public boolean getBoolean(String key, boolean value) {
        return kv.decodeBool(key,value);
    }

    public void putBoolean(String key, boolean value) {
        kv.encode(key,value);
    }

    public long getLong(String key, long value) {
        return kv.decodeLong(key,value);
    }

    public void putLong(String key, long value) {
        kv.encode(key,value);
    }

    public float getFloat(String key, float value) {
        return kv.decodeFloat(key,value);
    }

    public void putFloat(String key, float value) {
        kv.encode(key,value);
    }

    public void clear() {
        kv.clearAll();
    }
}
