package com.matrix.datamodelconversation.model.oddsbymarket

import com.matrix.datamodelconversation.model.eventres.Selection


data class Data(
    val betDelay: Int,
    val inplay: Boolean,
    val market_id: String,
    val selections: List<Selection>,
    val status: String
)