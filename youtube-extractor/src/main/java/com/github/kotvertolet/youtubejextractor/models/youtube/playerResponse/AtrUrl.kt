package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class AtrUrl : Serializable {
    var baseUrl: String? = null
    var elapsedMediaTimeSeconds = 0
    override fun toString(): String {
        return "AtrUrl{" +
                "baseUrl = '" + baseUrl + '\'' +
                ",elapsedMediaTimeSeconds = '" + elapsedMediaTimeSeconds + '\'' +
                "}"
    }
}