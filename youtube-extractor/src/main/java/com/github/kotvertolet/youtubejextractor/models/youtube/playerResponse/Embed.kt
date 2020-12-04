package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class Embed : Serializable {
    var width = 0
    var flashUrl: String? = null
    var flashSecureUrl: String? = null
    var iframeUrl: String? = null
    var height = 0
    override fun toString(): String {
        return "Embed{" +
                "width = '" + width + '\'' +
                ",flashUrl = '" + flashUrl + '\'' +
                ",flashSecureUrl = '" + flashSecureUrl + '\'' +
                ",iframeUrl = '" + iframeUrl + '\'' +
                ",height = '" + height + '\'' +
                "}"
    }
}