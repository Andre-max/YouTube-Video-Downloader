package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class PlayabilityStatus : Serializable {
    var isPlayableInEmbed = false
    var contextParams: String? = null
    var status: String? = null
    override fun toString(): String {
        return "PlayabilityStatus{" +
                "playableInEmbed = '" + isPlayableInEmbed + '\'' +
                ",contextParams = '" + contextParams + '\'' +
                ",status = '" + status + '\'' +
                "}"
    }
}