package com.github.kotvertolet.youtubejextractor.network

import com.github.kotvertolet.youtubejextractor.exception.YoutubeRequestException
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YoutubeNetwork {
    private lateinit var youtubeApi: IYoutubeApi

    constructor(gson: Gson?) {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(UserAgentInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(YOUTUBE_SITE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson ?: return))
            .build()
        youtubeApi = retrofit.create(IYoutubeApi::class.java)
    }

    constructor(gson: Gson?, client: OkHttpClient?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(YOUTUBE_SITE_URL)
            .client(client ?: return)
            .addConverterFactory(GsonConverterFactory.create(gson ?: return))
            .build()
        youtubeApi = retrofit.create(IYoutubeApi::class.java)
    }

    @Throws(YoutubeRequestException::class)
    suspend fun getYoutubeVideoInfo(videoId: String?, eUrl: String?): String? {
        return youtubeApi.getVideoInfoAsync(videoId, eUrl)?.await()
    }

    @Throws(YoutubeRequestException::class)
    suspend fun getYoutubeEmbeddedVideoPage(videoId: String?): String? {
        return youtubeApi.getEmbeddedVideoPageAsync(videoId)?.await()
    }

    @Throws(YoutubeRequestException::class)
    suspend fun getYoutubeVideoPage(videoId: String?): String? {
        return youtubeApi.getVideoPageAsync(
                videoId,
                "US",
                1,
                "9999999999"
            )?.await()
    }

    @Throws(YoutubeRequestException::class)
    suspend fun downloadWebpage(url: String?): String? {
        return youtubeApi.getWebPageAsync(url)?.await()
    }

    @Throws(YoutubeRequestException::class)
    suspend fun getStream(url: String?): String? {
        return youtubeApi.getStreamAsync(url)?.await()
    }

    companion object {
        private const val YOUTUBE_SITE_URL = "https://www.youtube.com/"
    }
}