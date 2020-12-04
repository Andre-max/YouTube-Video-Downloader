package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class PtrackingUrl : Serializable {
    var baseUrl: String? = null
    override fun toString(): String {
        return "PtrackingUrl{" +
                "baseUrl = '" + baseUrl + '\'' +
                "}"
    }
}