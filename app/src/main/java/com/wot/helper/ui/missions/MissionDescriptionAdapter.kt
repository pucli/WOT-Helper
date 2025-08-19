package com.wot.helper.ui.missions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wot.helper.databinding.ItemMissionsBinding
import com.wot.helper.domain.models.missions.MissionData

class MissionDescriptionAdapter(
    private val missions: List<MissionData>
) : RecyclerView.Adapter<MissionDescriptionAdapter.MissionViewHolder>() {

    inner class MissionViewHolder(private val binding: ItemMissionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mission: MissionData) {
            binding.missionName.text = mission.name

            // Combine description + hint
            val descriptionWithHint = buildString {
                mission.description?.let { append(it) }
                mission.hint?.let { append("\n$it") }
            }
            binding.missionDescription.text = descriptionWithHint

            binding.missionPrimary.text = mission.rewards.primary?.conditions ?: ""
            binding.missionSecondary.text = mission.rewards.secondary?.conditions ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionViewHolder {
        val binding = ItemMissionsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MissionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MissionViewHolder, position: Int) {
        holder.bind(missions[position])
    }

    override fun getItemCount(): Int = missions.size
}
