package com.matrix.datamodelconversation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.matrix.datamodelconversation.model.oddsbymarket.OddsByMarket
import com.matrix.datamodelconversation.adapter.SubcategoryDetailsAdapter
import com.matrix.datamodelconversation.databinding.ActivityEventDetailsBinding
import com.matrix.datamodelconversation.model.categorydetail.EventDetailsCategory
import com.matrix.datamodelconversation.model.eventdetailsres.EventDetails

class EventDetailsActivity : AppCompatActivity() {
    private val binding: ActivityEventDetailsBinding by lazy {
        ActivityEventDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var categoryList: java.util.ArrayList<EventDetailsCategory>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initData()

        val eventCategories = getEventCategories()
        binding.rvBothGames.adapter = SubcategoryDetailsAdapter(eventCategories).also {
            adapter = it
        }

        if (events!!.match.matchodds[0].market_id == oddsByMarket!!.data[0].market_id) {
            val subCategory =
                categoryList.first { it.name == "Match Odds" }
            val changedPos =
                subCategory.eventsData.indexOfFirst { it.market_id == oddsByMarket!!.data[0].market_id }
            val itemToChange =
                subCategory.eventsData[changedPos]
            itemToChange.market_odds = oddsByMarket!!.data[0]
            adapter.notifyItemChanged(changedPos)
        }
        if (events!!.match.bookmaker[0].market_id == oddsByMarket!!.data[0].market_id) {
            val subCategory =
                categoryList.first { it.name == "Bookmaker" }
            val changedPos =
                subCategory.eventsData.indexOfFirst { it.market_id == oddsByMarket!!.data[0].market_id }
            val itemToChange =
                subCategory.eventsData[changedPos]
            itemToChange.market_odds = oddsByMarket!!.data[0]
            adapter.notifyItemChanged(changedPos)
        }

    }

    private fun getEventCategories(): ArrayList<EventDetailsCategory> {
        categoryList = ArrayList()

        if (events!!.match.matchodds.isNotEmpty()) {
            categoryList.add(EventDetailsCategory("Match Odds",
                events!!.match.matchodds))
        }
        if (events!!.match.fancymarkets.isNotEmpty()) {
            if (events!!.match!!.fancymarkets[0].selection_lists.isNotEmpty()){
                categoryList.add(EventDetailsCategory("Fancy",
                    events!!.match.fancymarkets))
            }

        }
        if (events!!.match.bookmaker.isNotEmpty()) {
            categoryList.add(EventDetailsCategory("Bookmaker",
                events!!.match.bookmaker))
        }

        return categoryList

    }

    private lateinit var adapter: SubcategoryDetailsAdapter
    var events: EventDetails? = null
    var oddsByMarket: OddsByMarket? = null
    private fun initData() {
        val jsonData = applicationContext.resources.openRawResource(applicationContext
            .resources
            .getIdentifier("events", "raw", applicationContext.packageName))
            .bufferedReader().use { it.readText() }
        val gson = Gson()
        events = gson.fromJson(jsonData, EventDetails::class.java)
        val fff = applicationContext.resources.openRawResource(applicationContext
            .resources
            .getIdentifier("oddsbymarket", "raw", applicationContext.packageName))
            .bufferedReader().use { it.readText() }
        oddsByMarket = gson.fromJson(fff, OddsByMarket::class.java)

    }
}