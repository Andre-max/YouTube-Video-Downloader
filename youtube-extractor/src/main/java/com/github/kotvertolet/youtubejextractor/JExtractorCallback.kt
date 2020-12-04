package com.github.kotvertolet.youtubejextractor

import com.github.kotvertolet.youtubejextractor.exception.YoutubeRequestException
import com.github.kotvertolet.youtubejextractor.models.youtube.videoData.YoutubeVideoData

interface JExtractorCallback {
    fun onSuccess(videoData: YoutubeVideoData?)
    fun onNetworkException(e: YoutubeRequestException?)
    fun onError(exception: Exception?)
}