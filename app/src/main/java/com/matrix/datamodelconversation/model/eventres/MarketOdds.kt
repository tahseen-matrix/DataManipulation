package com.matrix.datamodelconversation.model.eventres

data class MarketOdds(
    val betDelay: Int?,
    val inplay: Boolean?,
    var market_id: String?,
    var selections: MutableList<Selection?>?,
    val status: String?
)