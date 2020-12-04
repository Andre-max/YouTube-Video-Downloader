package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class ColorInfo : Serializable {
    var primaries: String? = null
    var matrixCoefficients: String? = null
    var transferCharacteristics: String? = null
    override fun toString(): String {
        return "ColorInfo{" +
                "primaries = '" + primaries + '\'' +
                ",matrixCoefficients = '" + matrixCoefficients + '\'' +
                ",transferCharacteristics = '" + transferCharacteristics + '\'' +
                "}"
    }
}