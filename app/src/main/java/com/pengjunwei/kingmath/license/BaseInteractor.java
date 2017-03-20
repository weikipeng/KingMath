package com.pengjunwei.kingmath.license;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.pengjunwei.kingmath.BuildConfig;
import com.pengjunwei.kingmath.tool.FOpenLog;
import com.pengjunwei.kingmath.tool.PackageTool;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WikiPeng on 2017/3/20 11:37.
 */
public class BaseInteractor {
    public static Gson sGson;


    public static       String       sUserAgent          = null;
    public static       OkHttpClient sOkHttpClient       = null;
    public static final int          REQUEST_TIMEOUT     = 30 * 1000;
    public final static String       VERSION_NAME        = "1.0";
    public static final String       PARAM_V             = "v";
    public final static String       PARAM_AUTHORIZATION = "Authorization";
    public static final String       PARAM_USER_AGENT    = "User-Agent";

    public static void init(Context context) {
        initUserAgent(context);
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        if (sGson == null) {
            sGson = new Gson();
        }
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }

        Retrofit.Builder builder = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(sGson))
                .baseUrl(baseUrl);

        Retrofit adapter = builder.build();

        return adapter.create(serviceClass);
    }

    public static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient != null) {
            return sOkHttpClient;
        }

        sOkHttpClient = getOkHttpClientBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        long    t1      = System.nanoTime();
                        Request request = chain.request();

                        request = buildRequest(request);

                        FOpenLog.e(String.format("Sending request type %s %s on %s%n%s%n",
                                request.method(), request.url(), chain.connection(), request.headers()));


                        Response response = chain.proceed(request);
//                        FOpenLog.e("jzbfocus BaseInteractor======> password ===  "+response.code());
//                        isLogOut(response);
//                        if (!response.isSuccessful()) {
//                            ACRA.getErrorReporter().handleException(new JZBHttpException(chain.connection(), request, response));
//                        }

                        long t2 = System.nanoTime();
//                        FOpenLog.e(String.format("Received response for %s in %.1fms%n%s",
//                                request.url(), (t2 - t1) / 1e6d, response.headers()));
                        return response;
                    }
                })
                .build();

        return sOkHttpClient;
    }

    public static OkHttpClient.Builder getOkHttpClientBuilder() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
//        AddCookiesInterceptor      addCookiesInterceptor      = new AddCookiesInterceptor(HttpManager.getCookiePreferences(), BaseHttpClient.getCookieStore());
//        ReceivedCookiesInterceptor receivedCookiesInterceptor = new ReceivedCookiesInterceptor(HttpManager.getCookiePreferences());

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .cookieJar(new JavaNetCookieJar(cookieManager))
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.MILLISECONDS);
//                .addInterceptor(addCookiesInterceptor)
//                .addInterceptor(receivedCookiesInterceptor);
//                .authenticator(new Authenticator() {
//                    @Override
//                    public Request authenticate(Route route, Response response) throws IOException {
//                        FOpenLog.e("okHttpClient set authenticate");
//                        return response.request().newBuilder().addHeader("Authorization", RestfulClient.getOAuthKey()).build();
//                    }
//                });

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        return builder;

////        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
////        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
////                .addInterceptor(logging)
    }

    public static Request buildRequest(Request request) {
        //先拼Url，如果是Get请求，需要把签名加上，并且每个请求加上版本号信息
        HttpUrl         httpUrl        = request.url();
        HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
        if ("GET".equalsIgnoreCase(request.method())) {
            httpUrlBuilder.addQueryParameter(PARAM_V, VERSION_NAME);
//            httpUrlBuilder.addQueryParameter(HttpManager.PARAM_API_KEY, HttpManager.getOAuthKey());
        }

        httpUrl = httpUrlBuilder.build();


        //添加头部信息
        request = request.newBuilder()
                .url(httpUrl)
//                .header(PARAM_AUTHORIZATION, RestfulClient.getOAuthKey())
                .header(PARAM_USER_AGENT, sUserAgent)
                .build();

        return request;
    }

    private static String initUserAgent(Context context) {
        if (sUserAgent == null) {
            // patriarch/2.2 (android;4.1.1;Scale/2.0,720*1280)

            String[] packageInfo = PackageTool.getVersionInfo(context);
            //packageInfo[0]是versionname
            sUserAgent = "patriarch/" + packageInfo[0] + " (android)";
            try {
                String         release = Build.VERSION.RELEASE;
                DisplayMetrics dm      = context.getResources().getDisplayMetrics();
                int            width   = dm.widthPixels; // 宽
                int            height  = dm.heightPixels; // 高
                float          density = dm.density; // 屏幕密度（0.75 / 1.0 / 1.5）

                sUserAgent = "patriarch/" + packageInfo[0] + " (android;" + release + ";" + "Scale/"
                        + density + "," + width + "*" + height + ")";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sUserAgent;
    }
}
