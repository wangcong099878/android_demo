package the.one.base.util;

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

/**
 * @author The one
 * @date 2020/4/28 0028
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CNADIdHelper {

    private String TAG = "CNAdidHelper";

    private CNADIdHelper() {
    }

    public static CNADIdHelper getInstance() {
        return Inner.instance;
    }

    private static class Inner {
        private static final CNADIdHelper instance = new CNADIdHelper();
    }

    public String readCNAdId(Context ctx) {
        String adid = getCNAdID1(ctx);
        Log.e(TAG, "getCNAdID1: "+adid );
        if (adid != null) {
            return adid;
        }
        adid = getCNAdID2(ctx);
        Log.e(TAG, "getCNAdID2: "+adid );
        if (adid != null) {
            return adid;
        }
        adid = getCNAdID3(ctx);
        Log.e(TAG, "getCNAdID3: "+adid );
        if (adid != null) {
            return adid;
        }
        return null;
    }

    private String getCNAdID1(Context ctx) {
        String result = null;
        result = Settings.System.getString(ctx.getContentResolver(), "ZHVzY2Lk");
        return result;
    }
    private String getCNAdID2(Context ctx) {
        String result = null;
        String pkgName = ctx.getPackageName();
        SharedPreferences sp = ctx.getSharedPreferences(pkgName + "_dna", 0);
        result = sp.getString("ZHVzY2Lk", "NA");
        if (result.equals("NA")) {
            return null;
        }
        return result;
    }
    private String getCNAdID3(Context ctx) {
        String result = null;
        String path = "/sdcard/Android/ZHVzY2Lk";
        try {
            File file = new File(path);
            if (file.isDirectory() || !file.isFile()) {
                Log.e(TAG, "The File doesn't not exist.");
                return null;
            }
            InputStream instream = new FileInputStream(file);
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line = buffreader.readLine();
            if (line != null) {
                result = line;
            }
            instream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
