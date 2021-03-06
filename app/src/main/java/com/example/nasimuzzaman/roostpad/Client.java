package com.example.nasimuzzaman.roostpad;

/**
 * Created by nasimuzzaman on 10/12/17.
 */

import android.text.TextUtils;

import com.binjar.prefsdroid.Preference;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class Client<Service> {
    private static final String BASE_URL = "http://10.100.100.213";

    public abstract Service createService();

    protected Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(getInterceptor()).build();
    }

    private Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                String          token   = Preference.getString(PrefKeys.TOKEN, null);
                if (!TextUtils.isEmpty(token)) {
                    builder.addHeader("token", token);
                }
                Request request = builder.addHeader("Content-Type", "application/json").build();
                return chain.proceed(request);
            }
        };
    }
}