package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class BotguardData : Serializable {
    var interpreterUrl: String? = null
    var program: String? = null
    override fun toString(): String {
        return "BotguardData{" +
                "interpreterUrl = '" + interpreterUrl + '\'' +
                ",program = '" + program + '\'' +
                "}"
    }
}