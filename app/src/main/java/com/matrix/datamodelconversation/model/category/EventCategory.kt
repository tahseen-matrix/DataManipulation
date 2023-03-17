package com.matrix.datamodelconversation.model.category

data class EventCategory(
    val name:String,
    val list:List<EventSubCategory>
)