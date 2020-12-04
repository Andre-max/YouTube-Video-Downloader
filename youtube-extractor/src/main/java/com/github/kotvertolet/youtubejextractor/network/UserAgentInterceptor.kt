package com.github.kotvertolet.youtubejextractor.network

import okhttp3.*
import java.io.IOException

class UserAgentInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val originalRequest: Request = chain.request()
        val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" +
                " (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36"
        val requestWithUserAgent = originalRequest.newBuilder()
            .addHeader("User-Agent", userAgent)
            .addHeader("Accept", "*/*")
            .build()
        return chain.proceed(requestWithUserAgent)
    }
}