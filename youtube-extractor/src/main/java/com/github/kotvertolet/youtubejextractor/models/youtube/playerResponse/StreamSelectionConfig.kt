package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class StreamSelectionConfig : Serializable {
    var maxBitrate: String? = null
    override fun toString(): String {
        return "StreamSelectionConfig{" +
                "maxBitrate = '" + maxBitrate + '\'' +
                "}"
    }
}