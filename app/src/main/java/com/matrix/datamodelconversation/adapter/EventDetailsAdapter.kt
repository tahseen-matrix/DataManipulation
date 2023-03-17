package com.matrix.datamodelconversation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matrix.datamodelconversation.databinding.RowTwoBetsViewBinding
import com.matrix.datamodelconversation.model.eventdetailsres.Matchodd
import com.matrix.datamodelconversation.model.eventdetailsres.SelectionListsX

class EventDetailsAdapter(val list: List<Matchodd>):RecyclerView.Adapter<EventDetailsAdapter.MyViewHolder>() {
    class MyViewHolder(val binding:RowTwoBetsViewBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowTwoBetsViewBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=list[0]
        holder.binding.tvFirst.text = item.selection_lists[position].selectionName
        try {

            holder.binding.tv1.text = item.market_odds?.selections?.get(position)?.back_odds?.get(0)?.price.toString()
            holder.binding.tv2.text = item.market_odds?.selections?.get(position)?.lay_odds?.get(0)?.price.toString()
        }catch (ex:java.lang.Exception){}
    }


    override fun getItemCount(): Int {
        return list[0].selection_lists.size
    }
}