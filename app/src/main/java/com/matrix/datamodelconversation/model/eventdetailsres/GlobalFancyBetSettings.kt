package com.matrix.datamodelconversation.model.eventdetailsres

data class GlobalFancyBetSettings(
    val created_at: String,
    val id: Int,
    val market_type: String,
    val max_bet: Int,
    val max_market: Int,
    val min_bet: Int,
    val sport_id: Int,
    val updated_at: String
)