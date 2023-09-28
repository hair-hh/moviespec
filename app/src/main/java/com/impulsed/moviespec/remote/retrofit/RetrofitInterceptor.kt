package com.impulsed.moviespec.remote.retrofit

import com.impulsed.moviespec.BuildConfig
import com.impulsed.moviespec.remote.ApiConstants
import com.impulsed.moviespec.remote.RemoteException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RetrofitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val requestBuilder = chain.request().newBuilder()
                .addHeader(ApiConstants.AUTH, "Bearer " + BuildConfig.AUTH)
                .url(chain.request().url)
            val response = chain.proceed(requestBuilder.build())
            if (response.code in HttpURLConnection.HTTP_BAD_REQUEST..ApiConstants.STATUS_CODE_499) {
                throw RemoteException.ClientError(response.message)
            } else if (response.code in HttpURLConnection.HTTP_INTERNAL_ERROR..ApiConstants.STATUS_CODE_599) {
                throw RemoteException.ServerError(response.message)
            }
            return response
        } catch (e: Exception) {
            throw when (e) {
                is UnknownHostException -> RemoteException.NoNetworkError(e.message.toString())
                is SocketTimeoutException -> RemoteException.NoNetworkError(e.message.toString())
                is RemoteException -> e
                else -> RemoteException.GenericError(e.message.toString())
            }
        }
    }
}