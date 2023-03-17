package com.matrix.datamodelconversation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.matrix.datamodelconversation.adapter.second.EventCategoryAdapter
import com.matrix.datamodelconversation.databinding.ActivityMainBinding
import com.matrix.datamodelconversation.model.category.EventCategory
import com.matrix.datamodelconversation.model.category.EventSubCategory
import com.matrix.datamodelconversation.model.eventres.*
import com.matrix.datamodelconversation.model.socketres.SocketRes
import org.greenrobot.eventbus.EventBus

class MainActivitySecond : AppCompatActivity() {
    private lateinit var categoryList: java.util.ArrayList<EventCategory>
    private lateinit var adapter: EventCategoryAdapter
    private lateinit var inPlayCricket: List<EventsData>
    private lateinit var inPlayFootball: List<EventsData>
    private lateinit var inPlayTennis: List<EventsData>
    private lateinit var mpCricket: List<EventsData>
    private lateinit var mpFootball: List<EventsData>
    private lateinit var mpTennis: List<EventsData>
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initData()
        val eventCategories=getEventCategories()
        binding.rvBothGames.adapter=EventCategoryAdapter(eventCategories).also {
            adapter=it
        }

        binding.connectBtn.setOnClickListener {
            val id=85061 //sport id received from socket to update
            val socketString=loadJSONFromAsset("socket.json")
            val gson = Gson()
            val socketRes: SocketRes = gson.fromJson(socketString, SocketRes::class.java)
            val subCategory=categoryList[1].list.first { it.name=="Football" } //we will receive sports_id so we can easily find subcategory of item to update
            val changedPos=subCategory.eventsData.indexOfFirst { it.id==id }
            val itemToChange=subCategory.eventsData[changedPos]
            val  selectionMainList = ArrayList<Selection?>()
            socketRes.message?.BF?.SL?.map {
                    it ->
                val bo =  it!!.BO.map {
                    BackOdd(it?.O, it?.S, it?.I)
                }
                val lo =  it!!.LO.map {
                    LayOdd(it?.O, it?.S, it?.I)
                }
                selectionMainList.add(Selection(bo, lo, it.I, "ACTIVE"))
            }
            itemToChange.event_name= socketRes.event
            subCategory.eventAdapter?.selectItem(changedPos)
            subCategory.eventAdapter?.notifyItemChanged(changedPos)

        }
    }

    private fun initData() {
        val responseString=loadJSONFromAsset("indicator.json")
        val gson = Gson()
        val events: EventRes = gson.fromJson(responseString, EventRes::class.java)
        inPlayCricket=events.events_data.filter { it.sport_id == AppConstants.SportType.CRICKET && it.currently_live == 1 }
        inPlayFootball=events.events_data.filter { it.sport_id == AppConstants.SportType.FOOTBALL && it.currently_live == 1 }
        inPlayTennis=events.events_data.filter { it.sport_id == AppConstants.SportType.TENNIS && it.currently_live == 1 }

        mpCricket=events.events_data.filter { it.sport_id == AppConstants.SportType.CRICKET && it.currently_live == 0 }
        mpFootball=events.events_data.filter { it.sport_id == AppConstants.SportType.FOOTBALL && it.currently_live == 0 }
        mpTennis=events.events_data.filter { it.sport_id == AppConstants.SportType.TENNIS && it.currently_live == 0 }
    }

    private fun getEventCategories(): ArrayList<EventCategory> {
        val inPlays=ArrayList<EventSubCategory>()
        val mp=ArrayList<EventSubCategory>()
        if (inPlayCricket.isNotEmpty()){
            inPlays.add(EventSubCategory("Cricket",inPlayCricket))
        }
        if (inPlayFootball.isNotEmpty()){
            inPlays.add(EventSubCategory("Football",inPlayFootball))
        }
        if (inPlayTennis.isNotEmpty()){
            inPlays.add(EventSubCategory("Tennis",inPlayTennis))
        }
        if (mpCricket.isNotEmpty()){
            mp.add(EventSubCategory("Cricket",mpCricket))
        }
        if (mpFootball.isNotEmpty()){
            mp.add(EventSubCategory("Football",mpFootball))
        }
        if (mpTennis.isNotEmpty()){
            mp.add(EventSubCategory("Tennis",mpTennis))
        }
        categoryList= arrayListOf(
            EventCategory("In Play",inPlays),
            EventCategory("Most Popular",mp),
        )

        return categoryList
    }
}