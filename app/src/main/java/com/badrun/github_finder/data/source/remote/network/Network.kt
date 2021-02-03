package com.badrun.github_finder.data.source.remote.network

import com.badrun.github_finder.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class Network(
    private val headerInterceptor: HeaderInterceptor
) {

    fun builder(): Retrofit {
        val logger = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .dispatcher(dispatcher)
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.MINUTES)
            .writeTimeout(REQUEST_TIME_OUT, TimeUnit.MINUTES)
            .readTimeout(REQUEST_TIME_OUT, TimeUnit.MINUTES)
            .addNetworkInterceptor(logger)
            .addInterceptor(headerInterceptor)
            .build()

        val gsonBuilder = GsonBuilder()
            /**
             * setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
             * convert a conventional json entity names into java object
             */
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .build()
    }

    companion object {
        const val REQUEST_TIME_OUT: Long = 5
    }
}