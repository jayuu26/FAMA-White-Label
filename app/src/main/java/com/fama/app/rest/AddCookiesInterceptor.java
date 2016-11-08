package com.fama.app.rest;

import android.content.Context;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This interceptor put all the Cookies in Preferences in the Request.
 * Your implementation on how to get the Preferences MAY VARY.
 * <p>
 * Created by tsuharesu on 4/1/15.
 */
public class AddCookiesInterceptor implements Interceptor {

    Context mContext;

    public  AddCookiesInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet) mContext.getSharedPreferences("COOKIES",Context.MODE_PRIVATE).getStringSet("PREF_COOKIES", new HashSet<String>());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            System.out.println("Request-Response  Request : "+chain.request());
        }
        return chain.proceed(builder.build());
    }
}
