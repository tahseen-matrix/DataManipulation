package com.matrix.datamodelconversation.model.categorydetail

import com.matrix.datamodelconversation.adapter.EventDetailsAdapter
import com.matrix.datamodelconversation.model.eventdetailsres.Matchodd
import com.matrix.datamodelconversation.model.eventdetailsres.SelectionListsX

/**
 * Created By Matrix Marketers on 17/03/23.
 */
data class EventDetailsCategory (val name:String,
                                 val eventsData: List<Matchodd>,
                                 var eventAdapter: EventDetailsAdapter?=null)