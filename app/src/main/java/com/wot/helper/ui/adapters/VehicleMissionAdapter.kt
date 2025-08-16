package com.wot.helper.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wot.helper.databinding.ItemMissionsBinding
import com.wot.helper.ui.network.VehicleMission

class VehicleMissionAdapter(
    private var items: List<VehicleMission>,
    private val onClick: (VehicleMission) -> Unit
) : RecyclerView.Adapter<VehicleMissionAdapter.VH>() {

    inner class VH(val binding: ItemMissionsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemMissionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.missionName.text = item.name
        holder.binding.root.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size

    // Update adapter list dynamically
    fun updateItems(newItems: List<VehicleMission>) {
        items = newItems
        notifyDataSetChanged()
    }
}


