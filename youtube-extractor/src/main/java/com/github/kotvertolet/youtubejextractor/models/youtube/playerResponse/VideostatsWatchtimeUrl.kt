package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class VideostatsWatchtimeUrl : Serializable {
    var baseUrl: String? = null
    override fun toString(): String {
        return "VideostatsWatchtimeUrl{" +
                "baseUrl = '" + baseUrl + '\'' +
                "}"
    }
}