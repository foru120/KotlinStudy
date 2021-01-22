package com.hongbog.viewpagerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomPagerAdapter(val data: List<String>) : RecyclerView.Adapter<PagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_pager, parent, false))
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textView: TextView = itemView.findViewById(R.id.textView)

    fun bind(data: String) {
        textView.text = data
    }
}