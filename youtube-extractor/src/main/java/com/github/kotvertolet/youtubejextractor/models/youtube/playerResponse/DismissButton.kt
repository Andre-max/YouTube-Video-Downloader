package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class DismissButton : Serializable {
    var buttonRenderer: ButtonRenderer? = null
    override fun toString(): String {
        return "DismissButton{" +
                "buttonRenderer = '" + buttonRenderer + '\'' +
                "}"
    }
}