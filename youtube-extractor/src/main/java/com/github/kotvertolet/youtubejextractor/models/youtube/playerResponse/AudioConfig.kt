package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class AudioConfig : Serializable {
    var perceptualLoudnessDb = 0.0
    var loudnessDb = 0.0
    var isEnablePerFormatLoudness = false
    override fun toString(): String {
        return "AudioConfig{" +
                "perceptualLoudnessDb = '" + perceptualLoudnessDb + '\'' +
                ",loudnessDb = '" + loudnessDb + '\'' +
                ",enablePerFormatLoudness = '" + isEnablePerFormatLoudness + '\'' +
                "}"
    }
}