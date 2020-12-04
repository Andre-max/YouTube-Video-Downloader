package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class PlayerAttestationRenderer : Serializable {
    var botguardData: BotguardData? = null
    var challenge: String? = null
    override fun toString(): String {
        return "PlayerAttestationRenderer{" +
                "botguardData = '" + botguardData + '\'' +
                ",challenge = '" + challenge + '\'' +
                "}"
    }
}