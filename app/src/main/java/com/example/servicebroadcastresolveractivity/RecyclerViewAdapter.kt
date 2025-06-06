package com.example.servicebroadcastresolveractivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.servicebroadcastresolveractivity.RCAdapter.RCHolder

class RCAdapter: RecyclerView.Adapter<RCHolder>() {

    val nameList = mutableListOf<String>()

    class RCHolder(item: View): RecyclerView.ViewHolder(item) {
        val textView = item.findViewById<TextView>(R.id.textView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RCHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return RCHolder(view)
    }

    override fun onBindViewHolder(
        holder: RCHolder,
        position: Int
    ) {
        holder.textView.text = nameList[position]
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    fun addAllNames(newList: List<String>){
        nameList.clear()
        nameList.addAll(newList)
        notifyDataSetChanged()
    }
}