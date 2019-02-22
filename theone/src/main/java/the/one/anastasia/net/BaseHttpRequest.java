package the.one.anastasia.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import the.one.anastasia.bean.PageInfoBean;
import the.one.anastasia.net.callback.Callback;
import the.one.anastasia.net.callback.ListCallback;

/**
 * 网络请求基类
 * 请继承该类 否者获取不到 T 的Class类型
 * Created by tifezh on 2016/3/25.
 */
public class BaseHttpRequest extends OkHttpHttpRequestCore {
    private List<Call> mCalls = new ArrayList<>();
    static private Gson mGson;

    private Gson getGson() {
        if (mGson == null) {
            GsonBuilder builder = new GsonBuilder();
//            builder.registerTypeAdapter(float.class,FLOAT);
            mGson = builder.create();
        }
        return mGson;
    }


    /**
     * 取消所有的网络请求
     */
    public void cancelAllRequest() {
        if (mCalls == null) {
            return;
        }
        for (int i = 0; i < mCalls.size(); i++) {
            mCalls.get(i).cancel();
        }
        mCalls.clear();
    }

    @Override
    public okhttp3.Callback getCallback(Call call, Callback callback) {
        mCalls.add(call);
        return new DefaultCallback(callback);
    }

    /**
     * 重写默认的回调
     */
    private class DefaultCallback implements okhttp3.Callback {
        private Callback callback;

        public DefaultCallback(Callback callBack) {
            this.callback = callBack;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            mCalls.remove(call);
            if (e != null && e.getMessage() != null && e.getMessage().equalsIgnoreCase("Canceled")) {
                return;
            }
            String errorText = "网络错误";
            if (e != null && e instanceof SocketTimeoutException) {
                errorText = "连接服务器超时";
            }
            if (e != null && e instanceof ConnectException) {
                errorText = "连接服务器失败，请检查网络";
            }

            if (e != null && e instanceof UnknownHostException) {
                errorText = "请检查网络";
            }
            e.printStackTrace();
            if (this.callback != null) {
                sendFailure(callback, 0, errorText);
            }
        }

        @Override
        public void onResponse(Call call, Response response) {
            mCalls.remove(call);
            if (callback == null) {
                return;
            }

            Type type = callback.getType();
            int code = response.code();
            String body = null;
            String result = null;
            PageInfoBean pageInfoBean = null;
            try {
                body = response.body().string();
                Log.e("LOG_GET", "返回的数据-----" + code + "====" + body);
                if (code == 200) {
                    JSONObject object = new JSONObject(body);
                    String msg = object.getString("msg");
                    int status = object.getInt("event");
                    if (status == 0) {
                        if (type == String.class) {
                            result = body;
                            sendSuccess(callback, result, msg, pageInfoBean);
                            return;
                        }
                        if (callback instanceof ListCallback) {
                            result = object.getString("data");
                            if (object.has("pageInfo")) {
                                String pageInfo = object.getString("pageInfo");
                                if (null != pageInfo && !pageInfo.isEmpty()) {
                                    pageInfoBean = getGson().fromJson(pageInfo, new TypeToken<PageInfoBean>() {
                                    }.getType());
                                }
                            }
                            ListCallback listCallback = (ListCallback) callback;
                            Object t = getGson().fromJson(result, type);
                            sendSuccess(listCallback, t, msg, pageInfoBean);
                            return;
                        } else {
                            result = object.getString("data");
                            Object t = getGson().fromJson(result, type);
                            sendSuccess(callback, t, msg, pageInfoBean);
                        }
                    } else if (status == 511) {
                        sendFinish(callback);
                    } else if (status == 1) {
                        sendFailure(callback, status, msg);
                    } else {
                        sendFailure(callback, status, !TextUtils.isEmpty(msg) ? msg : "服务器内部错误");
                    }
                } else {
                    String msg = null;
                    try {
                        JSONObject object = new JSONObject(body);
                        msg = object.getString("msg");
                    } catch (Exception e) {

                    }
                    sendFailure(callback, code, !TextUtils.isEmpty(msg) ? msg : "服务器内部错误");
                }
            } catch (IOException e) {
                e.printStackTrace();
                sendFailure(callback, 0, "网络错误");
            } catch (JSONException e) {
                e.printStackTrace();
                sendFailure(callback, 0, "数据解析错误");
            } catch (Exception e) {
                e.printStackTrace();
                sendFailure(callback, 0, "数据解析错误");
            }
        }
    }

    private void sendSuccess(final Callback callback, final Object t, final String msg, final PageInfoBean pageInfoBean) {
        getHander().post(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSuccess(t, msg, pageInfoBean);
                    Log.e("LOG", "返回的数据-----成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sendFinish(callback);
    }

    private void sendFailure(final Callback callback, final int httpCode, final String errorText) {
        getHander().post(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onFailure(httpCode, errorText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sendFinish(callback);
    }

    private void sendFinish(final Callback callback) {
        getHander().post(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onFinish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 以json方式进行提交
     *
     * @param url
     * @param params
     * @param callback
     */
    protected void postJson(String url, Map<String, String> params, Callback callback) {
        if (callback != null && callback instanceof ListCallback) {
            if (params == null) {
                params = new HashMap<>();
            }
        }
        String json = params == null ? "{}" : getGson().toJson(params, Map.class);
        Log.e("LOG", url);
        Log.e("LOG", json);
        post(url, params, callback);
    }

    /**
     * 以json方式进行提交
     *
     * @param url
     * @param callback
     */
    protected void postJson(String url, Callback callback) {
        postJson(url, null, callback);
    }

    @Override
    protected Call doPost(String url, RequestBody body, Callback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return doExecute(request, callback);
    }

    /**
     * 以json的方式
     *
     * @param url
     * @param callback
     */
    protected void getJson(String url, Callback callback) {
        getJson(url, null, callback);
    }


    /**
     * 以json的方式提交
     *
     * @param url
     * @param callback
     */
    protected void getJson(String url, Map<String, String> params, Callback callback) {
        if (callback != null && callback instanceof ListCallback) {
            if (params == null) {
                params = new HashMap<>();
            }
        }
        if (params != null && params.size() != 0) {
            url += "?";
            for (Map.Entry<String, String> entry : params.entrySet()) {
                url += entry.getKey() + "=" + entry.getValue() + "&";
            }
            url = url.substring(0, url.length() - 1);
        }
        Log.e("LOG", "请求地址" + url);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .get()
                .build();
        doExecute(request, callback);
    }

    @Deprecated
    @Override
    protected void get(String url, Callback callback) {
        super.get(url, callback);
    }

    @Deprecated
    @Override
    protected void get(String url, Map<String, String> params, Callback data) {
        super.get(url, params, data);
    }

    @Deprecated
    @Override
    protected void post(String url, String mediaType, String text, Callback callback) {
        super.post(url, mediaType, text, callback);
        Log.e("LOG", mediaType);
    }

    @Deprecated
    @Override
    protected void post(String url, Map<String, String> params, Callback callback) {
        super.post(url, params, callback);
    }

}
