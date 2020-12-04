package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class FeedbackEndpoint : Serializable {
    var uiActions: UiActions? = null
    var feedbackToken: String? = null
    override fun toString(): String {
        return "FeedbackEndpoint{" +
                "uiActions = '" + uiActions + '\'' +
                ",feedbackToken = '" + feedbackToken + '\'' +
                "}"
    }
}