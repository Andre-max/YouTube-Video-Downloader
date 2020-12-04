package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class Storyboards : Serializable {
    var playerStoryboardSpecRenderer: PlayerStoryboardSpecRenderer? = null
    override fun toString(): String {
        return "Storyboards{" +
                "playerStoryboardSpecRenderer = '" + playerStoryboardSpecRenderer + '\'' +
                "}"
    }
}