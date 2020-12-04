package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class PlayerConfig : Serializable {
    var mediaCommonConfig: MediaCommonConfig? = null
    var audioConfig: AudioConfig? = null
    var streamSelectionConfig: StreamSelectionConfig? = null
    override fun toString(): String {
        return "PlayerConfig{" +
                "mediaCommonConfig = '" + mediaCommonConfig + '\'' +
                ",audioConfig = '" + audioConfig + '\'' +
                ",streamSelectionConfig = '" + streamSelectionConfig + '\'' +
                "}"
    }
}