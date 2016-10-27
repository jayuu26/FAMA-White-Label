package com.thunder.pay.rest;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

import static com.thunder.pay.constant.CollectionObject.requestResponse;
import static com.thunder.pay.constant.CollectionObject.requestResponseMap;

/**
 * This Interceptor add all received Cookies to the app DefaultPreferences.
 * Your implementation on how to save the Cookies on the Preferences MAY VARY.
 * <p>
 * Created by tsuharesu on 4/1/15.
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    Context mContext;

    public ReceivedCookiesInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());


            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                HashSet<String> cookies = new HashSet<>();

                for (String header : originalResponse.headers("Set-Cookie")) {
                    cookies.add(header);
                }

                mContext.getSharedPreferences("COOKIES", Context.MODE_PRIVATE).edit()
                        .putStringSet("PREF_COOKIES", cookies)
                        .apply();
            }

        return originalResponse;
    }
}
