package com.github.kotvertolet.youtubejextractor.models.youtube.playerResponse

import java.io.Serializable

class MessagesItem : Serializable {
    var mealbarPromoRenderer: MealbarPromoRenderer? = null
    override fun toString(): String {
        return "MessagesItem{" +
                "mealbarPromoRenderer = '" + mealbarPromoRenderer + '\'' +
                "}"
    }
}