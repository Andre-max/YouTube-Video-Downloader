package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class NavigationEndpoint : Serializable {
    var clickTrackingParams: String? = null
    var urlEndpoint: UrlEndpoint? = null
    override fun toString(): String {
        return "NavigationEndpoint{" +
                "clickTrackingParams = '" + clickTrackingParams + '\'' +
                ",urlEndpoint = '" + urlEndpoint + '\'' +
                "}"
    }
}