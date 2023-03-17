package com.matrix.datamodelconversation.model.category

import com.matrix.datamodelconversation.adapter.second.EventAdapter
import com.matrix.datamodelconversation.model.eventres.EventsData

data class EventSubCategory(
    val name:String,
    val eventsData: List<EventsData>,
    var eventAdapter:EventAdapter?=null
)
