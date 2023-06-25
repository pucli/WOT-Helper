package com.wot.helper.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wot.helper.databinding.LayoutHomeCardBinding
import com.wot.helper.domain.models.models.BasicCard

class HomePageAdapter(private val onCardClickListener: OnCardClickListener) :
    ListAdapter<BasicCard, HomePageAdapter.MapViewHolder>(DiffCallback()) {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomePageAdapter.MapViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutHomeCardBinding.inflate(layoutInflater, parent, false)
        return MapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        val map = currentList[position]
        holder.bind(map)
    }

    override fun getItemCount() = currentList.size

    private class DiffCallback : DiffUtil.ItemCallback<BasicCard>() {
        override fun areItemsTheSame(oldItem: BasicCard, newItem: BasicCard) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: BasicCard, newItem: BasicCard) =
            oldItem == newItem
    }

    inner class MapViewHolder(private val binding: LayoutHomeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                CardView.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val selectedMap = currentList[position]
                        onCardClickListener.onCardClick(selectedMap)
                    }
                }
            }
        }

        fun bind(basicCard: BasicCard) {
            binding.apply {
                Cardname.text = basicCard.title
                ivBackground.load(basicCard.background) {
                    crossfade(enable = true)
                }
            }
        }
    }

    interface OnCardClickListener {
        fun onCardClick(basicCard: BasicCard)
    }



}