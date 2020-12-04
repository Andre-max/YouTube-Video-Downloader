package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class QoeUrl : Serializable {
    var baseUrl: String? = null
    override fun toString(): String {
        return "QoeUrl{" +
                "baseUrl = '" + baseUrl + '\'' +
                "}"
    }
}