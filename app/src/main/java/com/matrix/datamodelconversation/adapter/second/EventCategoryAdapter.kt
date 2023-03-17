package com.matrix.datamodelconversation.adapter.second

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.matrix.datamodelconversation.R
import com.matrix.datamodelconversation.databinding.RowInplayOpenbetsBinding
import com.matrix.datamodelconversation.model.category.EventCategory

class EventCategoryAdapter(val list: List<EventCategory>):RecyclerView.Adapter<EventCategoryAdapter.MyViewHolder>() {
    class MyViewHolder(val binding:RowInplayOpenbetsBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowInplayOpenbetsBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=list[position]
        holder.binding.apply {
            tvHeader.text=item.name
            if (position==0){
                header.setBackgroundResource(R.color.greenheader)
                openbets.isVisible=true
            }else{
                header.setBackgroundResource(R.color.mostpopularview)
                openbets.isVisible=false
            }
            rvChildInplay.adapter=SubcategoryAdapter(item.list)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}