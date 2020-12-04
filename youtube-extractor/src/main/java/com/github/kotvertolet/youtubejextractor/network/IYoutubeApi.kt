package com.github.kotvertolet.youtubejextractor.network

import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface IYoutubeApi {
    @GET("get_video_info")
    fun getVideoInfoAsync(
        @Query("video_id") videoId: String?,
        @Query("eurl") eurl: String?
    ): Deferred<String?>?

    @GET("embed/{videoId}")
    fun getEmbeddedVideoPageAsync(@Path("videoId") videoId: String?): Deferred<String?>?

    @GET("watch")
    fun getVideoPageAsync(
        @Query("v") videoId: String?, @Query("gl") locale: String?,
        @Query("has_verified") hasVerified: Int, @Query("bpctr") bpctr: String?
    ): Deferred<String?>?

    @GET
    fun getWebPageAsync(@Url url: String?): Deferred<String?>?

    @Streaming
    @GET
    fun getStreamAsync(@Url url: String?): Deferred<String?>?
}