package com.impulsed.moviespec.injection

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.impulsed.moviespec.BuildConfig
import com.impulsed.moviespec.remote.ApiConstants
import com.impulsed.moviespec.remote.retrofit.RetrofitAPI
import com.impulsed.moviespec.remote.retrofit.RetrofitInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

    @Provides
    fun providesGson(): Gson {
        val gsonBuilder =GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun providesRetrofitInterceptor() = RetrofitInterceptor()

    @Provides
    fun providesCache(@ApplicationContext context: Context): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() //10 MB
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    @Provides
    fun providesOkHttpClient(retrofitInterceptor: RetrofitInterceptor, cache: Cache): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addNetworkInterceptor(loggingInterceptor)
        }
        okHttpClient.addInterceptor(retrofitInterceptor)
        okHttpClient.cache(cache)
        okHttpClient.writeTimeout(ApiConstants.TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.readTimeout(ApiConstants.TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(ApiConstants.TIMEOUT, TimeUnit.SECONDS)
        return okHttpClient.build()
    }

    @Provides
    fun providesRetrofitApi(gson: Gson, okHttpClient: OkHttpClient): RetrofitAPI =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(RetrofitAPI::class.java)

}