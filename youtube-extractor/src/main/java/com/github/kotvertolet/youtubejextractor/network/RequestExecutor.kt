package com.github.kotvertolet.youtubejextractor.network

import android.util.Log
import com.github.kotvertolet.youtubejextractor.exception.YoutubeRequestException
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class RequestExecutor {
    private val TAG = RequestExecutor::class.java.simpleName
    private var attemptsCounter = 0
    private var response: Response<ResponseBody?>? = null
    @Throws(YoutubeRequestException::class)
    fun executeWithRetry(httpCall: Call<ResponseBody?>): Response<ResponseBody?>? {
        try {
            response = httpCall.execute()
        } catch (e: IOException) {
            if (attemptsCounter < 2) {
                Log.i(TAG, "Attempting to receive successful response, attempt #$attemptsCounter")
                attemptsCounter++
                executeWithRetry(httpCall.clone())
            } else throw YoutubeRequestException(
                String.format(
                    "Could not receive successful" +
                            "response after 3 attempts, check the internet connection, http code was: '%s'",
                    response!!.code()
                ), e
            )
        }
        return response
    }
}