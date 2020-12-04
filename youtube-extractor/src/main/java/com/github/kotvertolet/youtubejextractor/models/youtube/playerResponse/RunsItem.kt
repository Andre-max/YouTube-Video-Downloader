package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class RunsItem : Serializable {
    var text: String? = null
    override fun toString(): String {
        return "RunsItem{" +
                "text = '" + text + '\'' +
                "}"
    }
}