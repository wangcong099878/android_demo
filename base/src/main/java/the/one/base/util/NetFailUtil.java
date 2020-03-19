package the.one.base.util;

import android.text.TextUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * @author The one
 * @date 2018/8/23 0023
 * @describe 网络请求错误原因
 * @email 625805189@qq.com
 * @remark
 */
public class NetFailUtil {

    public static String getFailString(Throwable e){
        if(null == e) return "";
        if (!TextUtils.isEmpty(e.getMessage()) && e.getMessage().equalsIgnoreCase("Canceled")) {
            return "";
        }

        String errorText = "网络错误";
        if ( e instanceof SocketTimeoutException) {
            errorText = "连接服务器超时";
        }
        if ( e instanceof ConnectException) {
            errorText = "连接服务器失败，请检查网络";
        }

        if ( e instanceof UnknownHostException) {
            errorText = "找不到服务器，请检查网络";
        }
        e.printStackTrace();
        return errorText;
    }
}
