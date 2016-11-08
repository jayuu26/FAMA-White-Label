package com.fama.app.rest;

import android.content.Context;

import com.fama.app.util.UrlConfig;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.internal.JavaNetCookieJar;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by ist on 3/9/16.
 */
public class RestClient implements Interceptor {

    Retrofit retrofit =null;
    OkHttpClient okHttp =null;
    Picasso sPicasso;

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    public enum Single {
        INSTANCE;
        RestClient s = new RestClient();

        public RestClient getInstance() {
            if (s == null)
                return new RestClient();
            else return s;
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        System.out.println("Retrofit@Response ---- "+response.body().string());
        return response;
    }

    public RestCall getRestCallsConnection(Context mContext){
        try {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Retrofit.Builder builder = new Retrofit.Builder().baseUrl(UrlConfig.BASE_URL);
            builder.addConverterFactory(ScalarsConverterFactory.create());
            builder.addConverterFactory(GsonConverterFactory.create());
            retrofit = builder.client(getOkHttpInstance(mContext)).build();

            setRetrofit(retrofit);

            return retrofit.create(RestCall.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public OkHttpClient getOkHttpInstance(Context mContext){
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
        try {
            okHttp = new OkHttpClient.Builder()
                    .addInterceptor(new AddCookiesInterceptor(mContext))
                    .addInterceptor(new ReceivedCookiesInterceptor(mContext))
                  /*.sslSocketFactory(getSSLConfig(mContext).getSocketFactory())*/
                    .cookieJar(new JavaNetCookieJar(cookieManager))
                    .build();

            setOkHttp(okHttp);
//            getSSLConfig(mContext).getSocketFactory();
            return okHttp;
//        }catch (CertificateException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//            return null;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Picasso getPicasoInstance(Context context) {
        if (sPicasso == null) {
            Picasso.Builder builder = new Picasso.Builder(context);
            try {
                OkHttp3Downloader okHttpDownloader = new OkHttp3Downloader(getOkHttpInstance(context));
                builder.downloader(okHttpDownloader);
                sPicasso = builder.build();
                Picasso.setSingletonInstance(sPicasso);
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sPicasso;
    }

    private static SSLContext getSSLConfig(Context context) throws CertificateException, IOException,
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = context.getAssets().open("siteforge.ril.com.crt");
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
        } finally {
            caInput.close();
        }

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Creating a TrustManager that trusts the CAs in our KeyStore.
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public OkHttpClient getOkHttp() {
        return okHttp;
    }

    public void setOkHttp(OkHttpClient okHttp) {
        this.okHttp = okHttp;
    }


    /**
     * concinnate url and query params
     *
     * @param url:         url
     * @param querykeys:   name of query params
     * @param queryValues: values of query params
     * @return: url with query params
     */
    private static String createUrl(String url, String[] querykeys, String[] queryValues) {
        String query2[] = arrangeQuery(querykeys, queryValues);
        return addQuery(url, query2);

    }

    /**
     * @param statement
     * @param value
     * @return
     */
    private static String[] arrangeQuery(String[] statement, String[] value) {
        if (statement != null && value != null && statement.length == value.length) {
            String[] arangement = new String[statement.length];
            for (int i = 0; i < statement.length; i++) {
                arangement[i] = statement[i] + "=" + value[i];

            }
            return arangement;
        }
        return null;
    }

    /**
     * Add query param to url
     *
     * @param url    : url
     * @param query: query params of url
     * @return : url contains query params
     */
    private static String addQuery(String url, String[] query) {
        if (query != null && query.length > 0) {
            url = url + "?" + query[0];
            for (int i = 1; i < query.length; i++) {
                url = url + "&" + query[i];
            }
        }
        return url;
    }
}
