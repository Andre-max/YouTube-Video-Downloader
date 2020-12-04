package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class Microformat : Serializable {
    var playerMicroformatRenderer: PlayerMicroformatRenderer? = null
    override fun toString(): String {
        return "Microformat{" +
                "playerMicroformatRenderer = '" + playerMicroformatRenderer + '\'' +
                "}"
    }
}