package com.wot.helper.ui.missions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wot.helper.R

class MissionAdapter(private val missions: List<String>) :
    RecyclerView.Adapter<MissionAdapter.MissionViewHolder>() {

    inner class MissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val missionText: TextView = itemView.findViewById(R.id.missionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_missions, parent, false)
        return MissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: MissionViewHolder, position: Int) {
        holder.missionText.text = missions[position]
    }

    override fun getItemCount(): Int = missions.size
}
