package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class VideostatsPlaybackUrl : Serializable {
    var baseUrl: String? = null
    override fun toString(): String {
        return "VideostatsPlaybackUrl{" +
                "baseUrl = '" + baseUrl + '\'' +
                "}"
    }
}