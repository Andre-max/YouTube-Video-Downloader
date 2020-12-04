package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class MessageTextsItem : Serializable {
    var runs: List<RunsItem>? = null
    override fun toString(): String {
        return "MessageTextsItem{" +
                "runs = '" + runs + '\'' +
                "}"
    }
}