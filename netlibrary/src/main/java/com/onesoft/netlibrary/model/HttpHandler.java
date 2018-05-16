package com.onesoft.netlibrary.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.internal.$Gson$Types;
import com.onesoft.netlibrary.R;
import com.onesoft.netlibrary.utils.LogUtil;
import com.onesoft.netlibrary.utils.NetUtils;

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
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OKhttp工具类
 */
public class HttpHandler {

    /**
     * 没有网络错误码 222.47.48.38
     */
    public static final String NO_NETWORK_CODE = "1234";

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
     * 异步get获取数据
     */
    public void getAsync(Context context, String url, ResultCallback callback) {
        Request request = new Request.Builder().url(url).tag(context).build();
        deliveryResult(context, callback, request);
    }

    /**
     * 异步get获取数据
     */
    public void getAsync(Context context, String url, ResponseCallback callback) {
        Request request = new Request.Builder().url(url).tag(context).build();
        deliveryResult(context, callback, request);
    }

    /**
     * post异步提交数据
     */
    public void postAsync(Context context, String url, List<Param> params, ResultCallback callback) {
        if (!NetUtils.isNetConnected(context)) {
            callback.onError(NO_NETWORK_CODE, context.getResources().getString(R.string.loading_no_network));
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
                sendFailedCallback(request, e, callback);
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
                    sendFailedCallback(response.request(), e, callback);
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

    /**
     * 返回字符串
     *
     * @param context
     * @param callback
     * @param request
     */
    private void deliveryResult(final Context context, final ResponseCallback callback, final Request request) {
        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("http request onFailure, msg:" + e.getMessage());
                if (callback != null)
                    callback.onError(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.e("http request onResponse" + response.toString());
                if (response.code() == 500 || response.code() == 502) {
                    callback.onError("500", "获取数据失败");
                    return;
                }
                try {
                    final String string = response.body().string();
                    LogUtil.e("onResponse：data is " + string);
                    sendSuccessResponseCallback(response.code(), string, callback);
                } catch (IOException e) {
                    if (callback != null)
                        callback.onError(request, e);
                }
            }
        });
    }

    /**
     * json格式
     *
     * @param context
     * @param callback
     * @param request
     */
    private void deliveryResult(final Context context, final ResultCallback callback, final Request request) {
        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("http request onFailure, msg:" + e.getMessage());
                sendFailedCallback(request, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.e("http request onResponse" + response.toString());
                if (response.code() == 500 || response.code() == 502) {
                    callback.onError("500", "获取数据失败");
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
                    sendFailedCallback(response.request(), e, callback);
                } catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    sendFailedCallback(response.request(), e, callback);
                }
            }

        });
    }

    private void sendFailedCallback(final Request request, final Exception e, final ResultCallback callback) {
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

    private void sendSuccessResponseCallback(final int code, final String object, final ResponseCallback callback) {
        if (callback != null) {
            callback.onResponse(code, object);
        }
    }

    /**
     * post异步文件上传
     */
    public void postAsync(Context context, String url, List<Param> params, List<FileParam> fileParams, final ResultCallback callback) {
        if (!NetUtils.isNetConnected(context)) {
            callback.onError("", context.getResources().getString(R.string.loading_no_network));
            return;
        }
        Request request = buildMultipartFormRequest(context, url, params, fileParams);
        deliveryResult(context, callback, request);
    }

    private Request buildMultipartFormRequest(Context context, String url, List<Param> params, List<FileParam> fileParams) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        Headers header = new Headers.Builder().build();
        if (params != null && params.size() > 0) {
            for (Param param : params) {
                if (param.value != null) {
                    LogUtil.e("param:" + param.toString());
                    bodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                            RequestBody.create(null, param.value));
                }
            }
        }
        if (fileParams != null && fileParams.size() > 0) {
            for (FileParam param : fileParams) {
                LogUtil.e("file:" + param.toString());
                bodyBuilder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + param.key + "\"; filename=\"" + param.file.getName() + "\""),
//                        RequestBody.create(MediaType.parse("image/*"), param.file));
                        RequestBody.create(MediaType.parse(guessMimeType(param.file.getAbsolutePath())), param.file));
            }
        }
        RequestBody body = bodyBuilder.build();
        return new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
                .headers(header)
                .build();
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


    public static abstract class ResponseCallback {//回调是异步的，如果有UI操作，记得切换到主线程

        public ResponseCallback() {
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(int code, String response);

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
