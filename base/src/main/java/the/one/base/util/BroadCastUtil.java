package the.one.base.util;

import android.content.Context;
import android.content.Intent;

/**
 * @author The one
 * @date 2018/9/21 0021
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BroadCastUtil {

    public static void send(Context context,String action){
        send(context,action,null,null);
    }

    public static void send(Context context,String action, String msgName, String msgContent){
        Intent intent = new Intent();
        if(null != msgName) intent.putExtra(msgName,msgContent);
        intent.setAction(action);
        context.sendBroadcast(intent);
    }
}
