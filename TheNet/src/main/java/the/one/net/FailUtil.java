package the.one.net;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * @author The one
 * @date 2018/8/23 0023
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class FailUtil {

    public static String getFailString(Exception e){
        if (e != null && e.getMessage() != null && e.getMessage().equalsIgnoreCase("Canceled")) {
            return "";
        }
        String errorText = "网络错误";
        if (e != null && e instanceof SocketTimeoutException) {
            errorText = "连接服务器超时";
        }
        if (e != null && e instanceof ConnectException) {
            errorText = "连接服务器失败，请检查网络";
        }

        if (e != null && e instanceof UnknownHostException) {
            errorText = "找不到服务器，请检查网络";
        }
        e.printStackTrace();
        return errorText;
    }
}
