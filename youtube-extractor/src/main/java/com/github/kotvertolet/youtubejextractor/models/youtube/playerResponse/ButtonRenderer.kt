package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class ButtonRenderer : Serializable {
    var trackingParams: String? = null
    var size: String? = null
    var style: String? = null
    var text: Text? = null
    var navigationEndpoint: NavigationEndpoint? = null
    var serviceEndpoint: ServiceEndpoint? = null
    override fun toString(): String {
        return "ButtonRenderer{" +
                "trackingParams = '" + trackingParams + '\'' +
                ",size = '" + size + '\'' +
                ",style = '" + style + '\'' +
                ",text = '" + text + '\'' +
                ",navigationEndpoint = '" + navigationEndpoint + '\'' +
                ",serviceEndpoint = '" + serviceEndpoint + '\'' +
                "}"
    }
}