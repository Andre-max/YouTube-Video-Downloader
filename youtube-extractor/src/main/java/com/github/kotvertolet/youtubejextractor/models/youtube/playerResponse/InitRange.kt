package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class InitRange : Serializable {
    var start: String? = null
    var end: String? = null
    override fun toString(): String {
        return "InitRange{" +
                "start = '" + start + '\'' +
                ",end = '" + end + '\'' +
                "}"
    }
}