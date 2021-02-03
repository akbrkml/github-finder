package com.badrun.github_finder.data.source.remote.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.badrun.github_finder.BuildConfig
import com.badrun.github_finder.utils.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    context: Context
) : Interceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnectionOn()) {
            throw NoConnectivityException()
        } else {
            var request = chain.request()
            if (request.header(NO_AUTHENTICATION) == null) {
                request = chain.request().newBuilder()
                    .addHeader(AUTHORIZATION, "token ${BuildConfig.TOKEN}")
                    .build()
            }

            return chain.proceed(request)
        }
    }

    private fun preAndroidMInternetCheck(
        connectivityManager: ConnectivityManager
    ): Boolean {
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null) {
            return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun postAndroidMInternetCheck(
        connectivityManager: ConnectivityManager
    ): Boolean {
        val network = connectivityManager.activeNetwork
        val connection =
            connectivityManager.getNetworkCapabilities(network)

        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    private fun isConnectionOn(): Boolean {
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            postAndroidMInternetCheck(connectivityManager)
        } else {
            preAndroidMInternetCheck(connectivityManager)
        }
    }

}