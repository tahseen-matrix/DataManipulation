package com.matrix.datamodelconversation.model.eventdetailsres

data class EventDetails(
    val betRadarMatchId: String,
    val bet_fair_percentage: Double,
    val booker_id: String,
    val global_bookmaker_bet_settings: GlobalBookmakerBetSettings,
    val global_fancy_bet_settings: GlobalFancyBetSettings,
    val live_video_url: String,
    val market_ids: String,
    val markets_grouped_by_match: List<Any>,
    val markets_list_for_sidebar: List<Any>,
    val match: Match,
    val oddsData: String,
    val score_frame: String,
    val session: String,
    val start_date: String,
    val time_before: Int,
    val user_stake_option_detail: List<String>
)