package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class VideostatsDelayplayUrl : Serializable {
    var baseUrl: String? = null
    override fun toString(): String {
        return "VideostatsDelayplayUrl{" +
                "baseUrl = '" + baseUrl + '\'' +
                "}"
    }
}