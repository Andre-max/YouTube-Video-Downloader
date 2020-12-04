package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class SetAwesomeUrl : Serializable {
    var baseUrl: String? = null
    var elapsedMediaTimeSeconds = 0
    override fun toString(): String {
        return "SetAwesomeUrl{" +
                "baseUrl = '" + baseUrl + '\'' +
                ",elapsedMediaTimeSeconds = '" + elapsedMediaTimeSeconds + '\'' +
                "}"
    }
}