package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class Description : Serializable {
    var simpleText: String? = null
    override fun toString(): String {
        return "Description{" +
                "simpleText = '" + simpleText + '\'' +
                "}"
    }
}