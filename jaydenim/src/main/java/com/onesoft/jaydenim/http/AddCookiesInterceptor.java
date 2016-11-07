package com.onesoft.jaydenim.http;

import com.onesoft.jaydenim.EMClient;
import com.onesoft.jaydenim.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jayden on 2016/10/21.
 */

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        //添加cookie
        builder.addHeader("Cookie", EMClient.getInstance().getCookie());
        LogUtil.d("Cookie", "Interceptor cookie = " + EMClient.getInstance().getCookie());
        return chain.proceed(builder.build());
    }
}
