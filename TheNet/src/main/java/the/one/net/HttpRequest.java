package the.one.net;


import java.util.Map;

import the.one.net.callback.Callback;

/**
 * 网络请求抽象类
 */
public abstract class HttpRequest {
    /**
     * post请求
     * @param url 地址
     * @param params  键值对参数
     * @param callback  回调
     */
    abstract protected void post(String url, Map<String,String> params, Callback callback);
    /**
     * post请求 json
     * @param url 地址
     * @param json  json数据
     * @param callback  回调
     */
    abstract protected void post(String url, String mediaType, String json, Callback callback);

    /**
     * get请求
     * @param url
     * @param callback
     */
    abstract protected void get(String url, Callback callback);

    /**
     * get请求
     * @param url
     * @param params
     * @param callback
     */
    abstract protected void get(String url, Map<String,String> params, Callback callback);


}
