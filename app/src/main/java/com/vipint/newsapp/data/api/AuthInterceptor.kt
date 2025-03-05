package com.vipint.newsapp.data.api

import com.vipint.newsapp.di.NetworkAPIKey
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(@NetworkAPIKey val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("X-Api-Key", apiKey)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}