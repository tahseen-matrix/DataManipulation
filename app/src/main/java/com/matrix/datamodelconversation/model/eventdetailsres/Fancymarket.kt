package com.matrix.datamodelconversation.model.eventdetailsres

import com.matrix.datamodelconversation.model.eventres.MarketOdds

data class Fancymarket(
    val bf_market_type: Any,
    val created_at: String,
    val deleted_at: Any,
    val description: Any,
    val id: Int,
    val is_active: Int,
    val is_freezed: Int,
    val is_self_opened: Int,
    val is_self_settled: Int,
    val market_betfair_percentage: Any,
    val market_id: String,
    val market_time: Any,
    val market_type: String,
    val match_id: Int,
    val max_bet: Any,
    val max_bet_percentage: Int,
    val max_market: Any,
    val max_market_percentage: Int,
    val min_bet: Any,
    val name: String,
    val result_runs: Any,
    val runners_metadata: Any,
    val selection_list: String,
    val market_odds: MarketOdds?,
    val selection_lists: List<SelectionListsX>,
    val status: String,
    val time_unfreeze_odds: Any,
    val type: String,
    val updated_at: String,
    val withdrawal_percentage: Any
)