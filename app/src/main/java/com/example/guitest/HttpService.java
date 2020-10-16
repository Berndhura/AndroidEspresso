package com.example.guitest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class HttpService {

    private WebService mWebService;
    private Retrofit.Builder builder;
    private Retrofit restAdapter;

    private static final String WEB_SERVICE_BASE_URL = "http://dummy.restapiexample.com/api/v1/";

    public HttpService() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //api/V3
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(logging);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        this.builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(WEB_SERVICE_BASE_URL)
                .client(builder.build());

        restAdapter = this.builder.build();

        mWebService = restAdapter.create(WebService.class);
    }

    private interface WebService {

        @GET("employees")
        Observable<Object> getEmployees();
    }

    public Observable<Object> getEmployeesObserv() {
        return mWebService.getEmployees();
    }
}