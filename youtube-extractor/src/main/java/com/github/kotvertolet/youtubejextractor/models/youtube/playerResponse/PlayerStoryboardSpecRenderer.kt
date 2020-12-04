package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class PlayerStoryboardSpecRenderer : Serializable {
    var spec: String? = null
    override fun toString(): String {
        return "PlayerStoryboardSpecRenderer{" +
                "spec = '" + spec + '\'' +
                "}"
    }
}