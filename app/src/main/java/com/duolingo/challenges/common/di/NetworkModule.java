package com.duolingo.challenges.common.di;

import com.duolingo.challenges.data.remote.RemoteDataSource;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/* Module that contains network dependencies. */
@Module
public class NetworkModule {

    @Provides
    @ApplicationScope
    public Converter.Factory provideConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @ApplicationScope
    public CallAdapter.Factory provideCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @ApplicationScope
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @ApplicationScope
    public OkHttpClient provideOkHttpClient(
            HttpLoggingInterceptor httpLoggingInterceptor
    ) {
        return new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
    }

    @Provides
    @ApplicationScope
    public Retrofit provideRetrofit(
            Converter.Factory converterFactory,
            CallAdapter.Factory callAdapterFactory,
            OkHttpClient okHttpClient
    ) {
        return new Retrofit.Builder()
                .baseUrl("https://s3.amazonaws.com/")
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @ApplicationScope
    public RemoteDataSource provideRemoteDataSource(
            Retrofit retrofit
    ) {
        return retrofit.create(RemoteDataSource.class);
    }

}