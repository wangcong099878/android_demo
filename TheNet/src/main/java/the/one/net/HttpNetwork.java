package the.one.net;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.view.View;

/**网络请求权限
 * Created by Administrator on 2017/3/11.
 */

public class HttpNetwork {

    /**
     * android 6.0之后需要动态的获取权限
     * 获取上网的权限模式
     */
    public static void network(){

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    /**
     * 判断网络是否可用。
     * @param context
     * @return   是：true，否：false
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager manger = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manger.getActiveNetworkInfo();
            //return (info!=null && info.isConnected());
            if(info != null){
                return info.isConnected();
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    public static boolean is_netWork(View view, Context context){
        if (!HttpNetwork.isNetworkAvailable(context)){
            view.setVisibility(View.VISIBLE);
            return true;
        }else {
            view.setVisibility(View.GONE);
        }
        return false;
    }

}
