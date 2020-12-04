package com.github.kotvertolet.youtubejextractor.network

import com.google.gson.Gson
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class GoogleVideoNetwork(gson: Gson?) {
    private val googleVideoApi: IGoogleVideoApi
    @Throws(IOException::class)
    fun getSubtitlesList(videoId: String?): Response<ResponseBody?> {
        return googleVideoApi.getSubtitles("list", videoId, null).execute()
    }

    @Throws(IOException::class)
    fun getSubtitles(videoId: String?, langCode: String?): Response<ResponseBody?> {
        return googleVideoApi.getSubtitles("track", videoId, langCode).execute()
    }

    companion object {
        private const val BASE_URL = "https://video.google.com/"
    }

    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val httpClient = Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(UserAgentInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        googleVideoApi = retrofit.create(IGoogleVideoApi::class.java)
    }
}