package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class IndexRange : Serializable {
    var start: String? = null
    var end: String? = null
    override fun toString(): String {
        return "IndexRange{" +
                "start = '" + start + '\'' +
                ",end = '" + end + '\'' +
                "}"
    }
}