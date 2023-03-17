package com.matrix.datamodelconversation.adapter.second

import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.matrix.datamodelconversation.R
import com.matrix.datamodelconversation.databinding.RowInplayChildChildBinding
import com.matrix.datamodelconversation.model.eventres.EventsData
import java.util.logging.Handler

class EventAdapter(val list: List<EventsData>):RecyclerView.Adapter<EventAdapter.MyViewHolder>() {
    class MyViewHolder(val binding:RowInplayChildChildBinding) :RecyclerView.ViewHolder(binding.root)

    var selectedItemPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowInplayChildChildBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=list[position]
        if (position == selectedItemPosition){
            holder.binding.tv1.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.greenheader))
        }else{
            holder.binding.tv1.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.buttonlightpurple))
        }
        holder.binding.apply {
            tvOneTitle.text=item.event_name
        }
    }
    fun selectItem(position: Int){
        selectedItemPosition = position
        notifyItemChanged(selectedItemPosition)
        android.os.Handler(Looper.myLooper()!!).postDelayed({
            notifyItemChanged(selectedItemPosition)
            selectedItemPosition = -1
        }, 1000)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}