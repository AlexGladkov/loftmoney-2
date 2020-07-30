package com.agladkov.loftmoney;

import android.app.Application;

import androidx.recyclerview.widget.RecyclerView;

import com.agladkov.loftmoney.remote.AuthApi;
import com.agladkov.loftmoney.remote.MoneyApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoftApp extends Application {

    private MoneyApi moneyApi;
    private AuthApi authApi;

    @Override
    public void onCreate() {
        super.onCreate();

        configureRetrofit();
    }

    private void configureRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://loftschool.com/android-api/basic/v1/")
                .build();

        moneyApi = retrofit.create(MoneyApi.class);
        authApi = retrofit.create(AuthApi.class);
    }

    public MoneyApi getMoneyApi() {
        return moneyApi;
    }
    public AuthApi getAuthApi() {
        return authApi;
    }
}
