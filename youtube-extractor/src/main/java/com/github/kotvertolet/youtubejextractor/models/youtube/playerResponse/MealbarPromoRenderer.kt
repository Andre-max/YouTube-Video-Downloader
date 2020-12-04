package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class MealbarPromoRenderer : Serializable {
    var triggerCondition: String? = null
    var trackingParams: String? = null
    var impressionEndpoints: List<ImpressionEndpointsItem>? = null
    var dismissButton: DismissButton? = null
    var actionButton: ActionButton? = null
    var messageTexts: List<MessageTextsItem>? = null
    var messageTitle: MessageTitle? = null
    var style: String? = null
    var isIsVisible = false
        private set

    fun setIsVisible(isVisible: Boolean) {
        isIsVisible = isVisible
    }

    override fun toString(): String {
        return "MealbarPromoRenderer{" +
                "triggerCondition = '" + triggerCondition + '\'' +
                ",trackingParams = '" + trackingParams + '\'' +
                ",impressionEndpoints = '" + impressionEndpoints + '\'' +
                ",dismissButton = '" + dismissButton + '\'' +
                ",actionButton = '" + actionButton + '\'' +
                ",messageTexts = '" + messageTexts + '\'' +
                ",messageTitle = '" + messageTitle + '\'' +
                ",style = '" + style + '\'' +
                ",isVisible = '" + isIsVisible + '\'' +
                "}"
    }
}