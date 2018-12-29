package the.one.net.request;

import java.util.Map;

import the.one.net.BaseHttpRequest;
import the.one.net.callback.Callback;
import the.one.net.callback.ListCallback;


/**
 * @author The one
 * @date 2018/8/30 0030
 * @describe TODO
 * @email 625805189@qq.com
 * @remark
 */
public class BaseRequest extends BaseHttpRequest {

    public void getCallback(String url, Map<String,String> params,Callback callback){
        get(url, params, callback);
    }

    public void getListCallback(String url, Map<String,String> params,ListCallback callback){
        get(url, params, callback);
    }

    public void postCallBack(String url, Map<String,String> params,Callback callback){
        post(url, params, callback);
    }

    public void postListCallback(String url, Map<String,String> params,ListCallback callback){
        post(url, params, callback);
    }
}
