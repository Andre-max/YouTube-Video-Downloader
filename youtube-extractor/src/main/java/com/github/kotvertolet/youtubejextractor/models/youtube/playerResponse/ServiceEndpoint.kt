package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class ServiceEndpoint : Serializable {
    var feedbackEndpoint: FeedbackEndpoint? = null
    var clickTrackingParams: String? = null
    override fun toString(): String {
        return "ServiceEndpoint{" +
                "feedbackEndpoint = '" + feedbackEndpoint + '\'' +
                ",clickTrackingParams = '" + clickTrackingParams + '\'' +
                "}"
    }
}