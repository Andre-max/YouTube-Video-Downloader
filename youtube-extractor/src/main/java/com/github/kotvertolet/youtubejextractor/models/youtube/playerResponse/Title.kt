package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class Title : Serializable {
    var simpleText: String? = null
    override fun toString(): String {
        return "Title{" +
                "simpleText = '" + simpleText + '\'' +
                "}"
    }
}