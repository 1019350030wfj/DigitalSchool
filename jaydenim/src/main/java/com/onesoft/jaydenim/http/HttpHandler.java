package com.onesoft.jaydenim.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.internal.$Gson$Types;
import com.onesoft.jaydenim.R;
import com.onesoft.jaydenim.utils.LogUtil;
import com.onesoft.jaydenim.utils.NetUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OKhttp工具类
 */
public class HttpHandler {

    private static HttpHandler mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private static final MyComparator mPairComparator = new MyComparator();

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static HttpHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HttpHandler(context);
        }
        return mInstance;
    }

    private HttpHandler(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(6000, TimeUnit.SECONDS)
                .connectTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .addNetworkInterceptor(new AddCookiesInterceptor())
                .cookieJar(CookiesManager.getInstance(context));
        mOkHttpClient = builder.build();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public void cancelRequest(Context context) {
        mOkHttpClient.dispatcher().cancelAll();
    }

    private static class MyComparator implements Comparator {

        @Override
        public int compare(Object o, Object t1) {
            Param first = (Param) o;
            Param last = (Param) t1;
            return first.key.compareTo(last.key);
        }
    }

    /**
     * 获取加密的参数sign,按键名升序排序
     */
//    private static String getSign(List<Param> list) {
//        StringBuilder sb = new StringBuilder();
//        if (list != null && list.size() > 0) {
//            Collections.sort(list, mPairComparator);
//            for (Param item : list) {
//                if (TextUtils.isEmpty(item.value)) {
//                    continue;
//                }
//                if (sb.length() != 0) {
//                    sb.append("&");
//                }
//                sb.append(item.key);
//                sb.append("=");
//                sb.append(item.value);
//            }
//        }
//        sb.append(ApiRequest.KEY);
//        LogUtils.e("getSign:" + sb.toString());
//        LogUtils.e("Md5:" +SecurityUtils.encodeMD5(sb.toString()));
//        return SecurityUtils.encodeMD5(sb.toString());
//    }

    /**
     * 异步get获取数据
     */
    public void getAsync(Context context, String url, ResultCallback callback) {
        Request request = new Request.Builder().url(url).tag(context).build();
        deliveryResult(context, callback, request);
    }

    /**
     * post异步提交数据
     */
    public void postAsync(Context context, String url, List<Param> params, ResultCallback callback) {
        if (!NetUtils.isNetConnected(context)) {
            callback.onError(HttpUrl.NO_NETWORK_CODE, context.getResources().getString(R.string.loading_no_network));
            return;
        }
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            for (Param param : params) {
                LogUtil.e("param:" + param.toString());
                if (param.key != null && param.value != null) {
                    bodyBuilder.add(param.key, param.value);
                }
            }
        }

        //TODO
//        bodyBuilder.add("sign", getSign(params));
//        Headers.Builder builder = new Headers.Builder();
//        builder.add("Cookie", EMClient.getInstance().getCookie());
//        LogUtil.d("Cookie", "cookie = " + EMClient.getInstance().getCookie());
        RequestBody body = bodyBuilder.build();
        Request.Builder rb = new Request.Builder().url(url).post(body).tag(context);
        //OkHttp header 中不能传中文的坑
//        Headers header = builder.build();
//        rb.headers(header);
        Request request = rb.build();
        deliveryResult(context, callback, request);
    }

    /**
     * download异步文件下载
     */
    public void downloadAsync(final Context context, final String url, final String saveDir, final String fileName, final ResultCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(saveDir, fileName);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(context, file.getAbsolutePath(), callback);
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }
            }

        });
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }


    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void deliveryResult(final Context context, final ResultCallback callback, final Request request) {
        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("http request onFailure, msg:" + e.getMessage());
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.e("http request onResponse" + response.toString());
                if (response.code() == 500 || response.code() == 502) {
//                    sendFailedStringCallback(response.request(), new Exception(), callback);
                    callback.onError("500", "获取数据失败");
//                    ((Activity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ToastUtils.showToast(context,"获取数据失败");
//                        }
//                    });
                    return;
                }
                try {
                    final String string = response.body().string();
                    LogUtil.e("onResponse：data is " + string);
                    if (callback.mType == String.class) {
                        sendSuccessResultCallback(context, string, callback);
                    } else {
                        Object o = GsonHandler.fromJson(string, callback.mType);
                        sendSuccessResultCallback(context, o, callback);
                    }
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                } catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    sendFailedStringCallback(response.request(), e, callback);
                }
            }

        });
    }


    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Context context, final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

    public static abstract class ResultCallback<T> {
        public Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        public static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);

        public void onError(String errorCode, String errorMsg) {
        }
    }


    public static class Param {
        public String key;
        public String value;

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "[key:" + key + ",value:" + value + "]";
        }
    }

    public static class FileParam {
        public String key;
        public File file;

        public FileParam(String key, File file) {
            this.key = key;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileParam{" +
                    "key='" + key + '\'' +
                    ", file=" + file.getPath() +
                    '}';
        }
    }
}
