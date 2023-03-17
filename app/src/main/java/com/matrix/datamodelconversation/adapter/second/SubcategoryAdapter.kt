package com.matrix.datamodelconversation.adapter.second

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matrix.datamodelconversation.databinding.RowInplayChildBinding
import com.matrix.datamodelconversation.model.category.EventSubCategory

class SubcategoryAdapter(val list: List<EventSubCategory>):RecyclerView.Adapter<SubcategoryAdapter.MyViewHolder>() {
    class MyViewHolder(val binding:RowInplayChildBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowInplayChildBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=list[position]
        holder.binding.apply {
            tvTitle.text=item.name
            rvChildChild.adapter=EventAdapter(item.eventsData).also {
                item.eventAdapter=it
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}