package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class Text : Serializable {
    var runs: List<RunsItem>? = null
    override fun toString(): String {
        return "Text{" +
                "runs = '" + runs + '\'' +
                "}"
    }
}