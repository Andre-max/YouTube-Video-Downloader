package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class ActionButton : Serializable {
    var buttonRenderer: ButtonRenderer? = null
    override fun toString(): String {
        return "ActionButton{" +
                "buttonRenderer = '" + buttonRenderer + '\'' +
                "}"
    }
}