package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class UrlEndpoint : Serializable {
    var url: String? = null
    override fun toString(): String {
        return "UrlEndpoint{" +
                "url = '" + url + '\'' +
                "}"
    }
}