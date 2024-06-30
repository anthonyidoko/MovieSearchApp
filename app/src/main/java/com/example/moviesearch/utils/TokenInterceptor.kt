package com.example.moviesearch.utils

import com.example.moviesearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
//
//class TokenInterceptor: Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val requestBuilder = request.newBuilder()
//        requestBuilder.addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
//        return chain.proceed(requestBuilder.build())
//    }
//}