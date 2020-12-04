package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class MessageTitle : Serializable {
    var runs: List<RunsItem>? = null
    override fun toString(): String {
        return "MessageTitle{" +
                "runs = '" + runs + '\'' +
                "}"
    }
}