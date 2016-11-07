package com.onesoft.jaydenim.http;

import android.content.Context;

import com.onesoft.jaydenim.utils.LogUtil;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 */
public class CookiesManager implements CookieJar {

    private final PersistentCookieStore cookieStore;

    private CookiesManager(Context context) {
        cookieStore = new PersistentCookieStore(context.getApplicationContext());
    }

    private static CookiesManager sInstance;

    public static CookiesManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (CookiesManager.class) {
                if (sInstance == null) {
                    sInstance = new CookiesManager(context);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                LogUtil.d("saveFromResponse url host = " + url.host());
                LogUtil.d("saveFromResponse url = " + url + " ||cookie value= " + item.value());
                LogUtil.d("saveFromResponse url = " + url + " ||cookie domain = " + item.domain());
                LogUtil.d("saveFromResponse url = " + url + " ||cookie path = " + item.path());
                LogUtil.d("saveFromResponse url = " + url + " ||cookie toString= " + item.toString());
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        if (cookies != null && cookies.size() > 0) {
            LogUtil.d("loadForRequest url = " + url + " ||cookies size = " + cookies.size());

            LogUtil.d("loadForRequest url = " + url + " ||cookies  = " + cookies.get(0).toString());
        }
        return cookies;
    }
}