package com.github.kotvertolet.youtubejextractor.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IGoogleVideoApi {
    @GET("timedtext")
    fun getSubtitles(
        @Query("type") type: String?,
        @Query("v") videoId: String?,
        @Query("lang") langCode: String?
    ): Call<ResponseBody?>
}