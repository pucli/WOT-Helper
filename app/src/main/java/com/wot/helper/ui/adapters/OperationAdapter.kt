package com.wot.helper.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wot.helper.databinding.ItemMissionsBinding
import com.wot.helper.ui.network.Operation

class OperationAdapter(
    private val items: List<Operation>,
    private val onClick: (Operation) -> Unit
) : RecyclerView.Adapter<OperationAdapter.VH>() {

    inner class VH(val binding: ItemMissionsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemMissionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.missionName.text = item.name
        holder.binding.root.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size
}
