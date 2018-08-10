package com.example.potikorn.movielists.di

import com.example.potikorn.movielists.BuildConfig
import com.example.potikorn.movielists.data.AppSettings
import com.example.potikorn.movielists.data.User
import com.example.potikorn.movielists.httpmanager.FirebaseApi
import com.example.potikorn.movielists.httpmanager.MovieApi
import com.example.potikorn.movielists.httpmanager.UserApi
import com.example.potikorn.movielists.remote.RemoteContract
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RemoteModule {

    companion object {
        private const val TIME_OUT = 60L
    }

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(settingData: AppSettings): OkHttpClient =
        OkHttpClient.Builder()
            .certificatePinner(CertificatePinner.DEFAULT)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()
                val originalHttpBuilder = originalHttpUrl.newBuilder()
                if (settingData.getLang().isNotEmpty())
                    originalHttpBuilder.addQueryParameter("language", settingData.getLang())
                val requestBuilder = original.newBuilder()
                requestBuilder.url(originalHttpBuilder.build())
                requestBuilder.method(original.method(), original.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BODY)
                    .log(Platform.INFO)
                    .request("Request")
                    .response("Response")
                    .addHeader("version", BuildConfig.VERSION_NAME)
                    .addQueryParam("api_key", RemoteContract.ACCESS_KEY_API_LAYER)
                    .build()
            )
            .build()

    @Provides
    @Singleton
    @Named("FirebaseOkHttp")
    fun provideFirebaseOkHttpClient(userData: User): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder
            .certificatePinner(CertificatePinner.DEFAULT)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                if (userData.getUserId().isNotEmpty())
                    requestBuilder.addHeader("uid", userData.getUserId())
                requestBuilder.method(original.method(), original.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(
                LoggingInterceptor.Builder().apply {
                    loggable(BuildConfig.DEBUG)
                    setLevel(Level.BASIC)
                    log(Platform.INFO)
                    request("Firebase-Request")
                    response("Firebase-Response")
                }.build()
            )
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(RemoteContract.BASE_API_LAYER)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    @Named("Firebase")
    fun provideFirebaseRetrofit(gson: Gson, @Named("FirebaseOkHttp") okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(RemoteContract.BASE_FIREBASE_API_LAYER)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideRemoteMovieService(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteUserService(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteFirebaseService(@Named("Firebase") retrofit: Retrofit): FirebaseApi =
        retrofit.create(FirebaseApi::class.java)
}
