package com.matrix.datamodelconversation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.matrix.datamodelconversation.adapter.AdapterBothMatch
import com.matrix.datamodelconversation.databinding.ActivityMainBinding
import com.matrix.datamodelconversation.model.EventNewModel
import com.matrix.datamodelconversation.model.eventres.*
import com.matrix.datamodelconversation.model.socketres.SocketRes
import org.greenrobot.eventbus.EventBus

class MainActivity : AppCompatActivity(), AdapterBothMatch.OnItemClickListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var itemsArrayList: MutableList<EventNewModel>? = ArrayList()
    private var mostPopularList: MutableList<EventNewModel>? = ArrayList()
    private var itemList: MutableList<Int>? = ArrayList()

    private val inPlayCricket: MutableList<EventsData> = mutableListOf()
    private val inPlayTennis: MutableList<EventsData> = mutableListOf()
    private val inPlayFootball: MutableList<EventsData> = mutableListOf()

    private val cricketData: MutableList<EventsData> = mutableListOf()
    private val tennisData: MutableList<EventsData> = mutableListOf()
    private val footballData: MutableList<EventsData> = mutableListOf()

    private val selectionMainList: MutableList<Selection?> = mutableListOf()


    private val adapterBothMatch: AdapterBothMatch = AdapterBothMatch(true, ArrayList(),
        ArrayList(), ArrayList(), this)

    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val jsonData = applicationContext.resources.openRawResource(applicationContext
            .resources
            .getIdentifier("indicator", "raw", applicationContext.packageName))
            .bufferedReader().use { it.readText() }

        val gson = Gson()
        val market: EventRes = gson.fromJson(jsonData, EventRes::class.java)

        setData(market)
        val socketJsonData = applicationContext.resources.openRawResource(applicationContext
            .resources
            .getIdentifier("socket", "raw", applicationContext.packageName))
            .bufferedReader().use { it.readText() }

        val socketRes: SocketRes = gson.fromJson(socketJsonData, SocketRes::class.java)

        binding.connectBtn.setOnClickListener {
            val itemToUpdate=inPlayFootball.get(1)
            when{
                itemToUpdate.sport_id==AppConstants.SportType.FOOTBALL->{
                    val indexOfItem=inPlayFootball.indexOfFirst {
                        it.id==itemToUpdate.id
                    }
                    inPlayFootball[indexOfItem].event_name="updated"
                    adapterBothMatch.notifyItemChanged(2)
                }
            }

            /*selectionMainList.clear()
            for (i in market.events_data.indices) {
                if (market.events_data[i].id == socketRes.MI) {
                    Log.d("Tag", "response ${market.events_data[i].market_odds?.selections}")
                    if (market.events_data[i].id == socketRes.MI) {
                        for (j in market.events_data[i].market_odds?.selections?.indices!!) {
                            val resId =
                                market!!.events_data[i].market_odds?.selections?.get(j)?.selection_id
                            val socketId = socketRes.message?.BF?.SL?.get(j)?.I
                            if (resId == socketId) {
                                val bo: MutableList<BackOdd> = mutableListOf()
                                val lo: MutableList<LayOdd> = mutableListOf()
                                if (socketRes.message?.BF?.SL?.get(j)?.BO?.size!! > 0) {
                                    if (socketRes.message.BF.SL[j]?.BO?.get(0) != null)
                                        bo.add(BackOdd(socketRes.message.BF.SL[j]?.BO?.get(0)?.O,
                                            socketRes.message.BF.SL[j]?.BO?.get(0)?.S,
                                            socketRes.message.BF.SL[j]?.BO?.get(0)?.I))
                                }

                                if (socketRes.message.BF.SL[j]?.LO?.size!! > 0) {
                                    if (socketRes.message.BF.SL[j]?.LO?.get(0) != null)
                                        lo.add(LayOdd(socketRes.message.BF.SL[j]?.LO?.get(0)?.O,
                                            socketRes.message.BF.SL[j]?.LO?.get(0)?.S,
                                            socketRes.message.BF.SL[j]?.LO?.get(0)?.I))
                                }

                                selectionMainList.add(Selection(bo, lo, socketId, "ACTIVE"))
                            }
                        }
                    }

                }
            }*/
        }

        binding.rvBothGames.adapter = adapterBothMatch
        adapterBothMatch.notifyDataSetChanged()
    }

    private fun setData(market: EventRes) {
        inPlayFootball.clear()
        inPlayCricket.clear()
        inPlayFootball.clear()
        inPlayTennis.clear()
        cricketData.clear()
        tennisData.clear()
        footballData.clear()
        itemList?.clear()
        for (i in market.events_data.indices) {
            if (market.events_data[i].sport_id == AppConstants.SportType.CRICKET && market.events_data[i].currently_live == 1) {
                inPlayCricket.add(market.events_data[i])
            } else if (market.events_data[i].sport_id == AppConstants.SportType.FOOTBALL && market.events_data[i].currently_live == 1) {
                inPlayFootball.add(market.events_data[i])
            } else if (market.events_data[i].sport_id == AppConstants.SportType.TENNIS && market.events_data[i].currently_live == 1) {
                inPlayTennis.add(market.events_data[i])
            } else if (market.events_data[i].sport_id == AppConstants.SportType.CRICKET && market.events_data[i].currently_live == 0) {
                cricketData.add(market.events_data[i])
            } else if (market.events_data[i].sport_id == AppConstants.SportType.FOOTBALL && market.events_data[i].currently_live == 0) {
                footballData.add(market.events_data[i])
            } else if (market.events_data[i].sport_id == AppConstants.SportType.TENNIS && market.events_data[i].currently_live == 0) {
                tennisData.add(market.events_data[i])
            }
        }

        Log.e("LoginActivity",
            "from tennis ${inPlayTennis} \n from cricket ${inPlayCricket} \nfrom fotball ${inPlayFootball}")
        if (inPlayCricket.size > 0) {
            itemsArrayList!!.add(EventNewModel("Cricket", inPlayCricket.toMutableList()))
        }
        if (inPlayTennis.size > 0) {
            itemsArrayList!!.add(EventNewModel("Tennis", inPlayTennis.toMutableList()))
        }
        if (inPlayFootball.size > 0) {
            itemsArrayList!!.add(EventNewModel("Football", inPlayFootball!!))
        }
        if (cricketData.size > 0) {
            mostPopularList!!.add(EventNewModel("Cricket", cricketData.toMutableList()))
        }
        if (tennisData.size > 0) {
            mostPopularList!!.add(EventNewModel("Tennis", tennisData.toMutableList()))
        }
        if (footballData.size > 0) {
            mostPopularList!!.add(EventNewModel("Football", footballData.toMutableList()))
        }



        itemList?.add(0)
        itemList?.add(2)

        adapterBothMatch.updateAdapter(itemList!!, itemsArrayList!!, mostPopularList!!)


    }

    override fun onItemClick() {

    }
}