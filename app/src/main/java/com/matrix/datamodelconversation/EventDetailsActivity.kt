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
import java.util.ArrayList

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

        getOddsByMarketCommon()


    }

    private fun getOddsByMarketCommon() {
        for (element in events!!.match.matchodds.indices) {
            val list: ArrayList<String> = arrayListOf()
            list.add(events!!.match.matchodds[element].market_id)
            for (element in events!!.match.matchodds.indices) {
                if (events!!.match.matchodds[element].market_id == oddsByMarket!!.data[0].market_id) {
                    val subCategory =
                        categoryList.first { it.name == events!!.match.matchodds[element].name }
                    val changedPos =
                        subCategory.eventsData.indexOfFirst { it.market_id == oddsByMarket!!.data[0].market_id }
                    val itemToChange =
                        subCategory.eventsData[changedPos]
                    itemToChange.market_odds = oddsByMarket!!.data[0]
                    adapter.notifyItemChanged(changedPos)

                    if (events!!.match.sport_id == 171 && events!!.match.all_matchbf_odds.isNotEmpty()){
                        for (i in events!!.match.all_matchbf_odds.indices){
                           if (events!!.match.all_matchbf_odds[i].market_id == childOddsByMarket!!.data[0].market_id){
                               val subCategory =
                                   categoryList.first { it.name == events!!.match.all_matchbf_odds[element].name }
                               val changedPos =
                                   subCategory.eventsData.indexOfFirst { it.market_id == childOddsByMarket!!.data[0].market_id }
                               val itemToChange =
                                   subCategory.eventsData[changedPos]
                               itemToChange.market_odds = childOddsByMarket!!.data[0]
                               adapter.notifyItemChanged(changedPos)
                           }

                        }
                    }
                }


            }
        }

    }

    private fun getEventCategories(): ArrayList<EventDetailsCategory> {
        categoryList = ArrayList()
        if (events?.match?.matchodds?.isNotEmpty()!!) {
            events!!.match.matchodds.map {
                categoryList.add(EventDetailsCategory(it.name, listOf(it)))
            }
        }
        if (events!!.match.sport_id == 30) {
            if (events!!.match.fancymarkets.isNotEmpty()) {
                events!!.match.fancymarkets.map {
                    categoryList.add(EventDetailsCategory(it.name, listOf(it)))
                }
            }
            if (events!!.match.bookmaker.isNotEmpty()) {
                events!!.match.bookmaker.map {
                    categoryList.add(EventDetailsCategory(it.name, listOf(it)))
                }
            }
        } else if (events!!.match.sport_id == 171 && events!!.match.all_matchbf_odds.isNotEmpty()) {
            events!!.match.all_matchbf_odds.map {
                categoryList.add(EventDetailsCategory(it.name, listOf(it)))
            }
        }

        return categoryList

    }

    private lateinit var adapter: SubcategoryDetailsAdapter
    var events: EventDetails? = null
    private var oddsByMarket: OddsByMarket? = null
    var childOddsByMarket: OddsByMarket? = null
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

        val fghf = applicationContext.resources.openRawResource(applicationContext
            .resources
            .getIdentifier("childoddsbymarket", "raw", applicationContext.packageName))
            .bufferedReader().use { it.readText() }
        childOddsByMarket = gson.fromJson(fghf, OddsByMarket::class.java)

    }
}