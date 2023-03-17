package com.matrix.datamodelconversation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matrix.datamodelconversation.databinding.RowInplayChildBinding
import com.matrix.datamodelconversation.databinding.RowMatchOddsBinding
import com.matrix.datamodelconversation.databinding.RowMostPopularHeaderBinding
import com.matrix.datamodelconversation.model.category.EventSubCategory
import com.matrix.datamodelconversation.model.categorydetail.EventDetailsCategory

class SubcategoryDetailsAdapter(val list: List<EventDetailsCategory>):RecyclerView.Adapter<SubcategoryDetailsAdapter.MyViewHolder>() {
    class MyViewHolder(val binding:RowMatchOddsBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowMatchOddsBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=list[position]
        holder.binding.apply {
            matchOdds.text=item.name
            rvMatchOddsChild.adapter=EventDetailsAdapter(item.eventsData).also {
                item.eventAdapter=it
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}