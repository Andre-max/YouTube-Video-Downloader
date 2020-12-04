package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class Attestation : Serializable {
    var playerAttestationRenderer: PlayerAttestationRenderer? = null
    override fun toString(): String {
        return "Attestation{" +
                "playerAttestationRenderer = '" + playerAttestationRenderer + '\'' +
                "}"
    }
}