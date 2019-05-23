package com.example.cashdesk.api;

import com.example.cashdesk.helper.Const;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private Retrofit retrofit = null;

    public ApiInterface getClient() {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(Const.BASE_URL)
                    .client(createClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }


    private OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60,TimeUnit.SECONDS);

        return builder.build();
    }
}
