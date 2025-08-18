package com.wot.helper.ui.skills

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wot.helper.R
import com.wot.helper.ui.skills.model.SkillData

class SkillsAdapter :
    ListAdapter<SkillData, SkillsAdapter.SkillViewHolder>(SkillDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_skills, parent, false)
        return SkillViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.skillName)
        private val desc: TextView = itemView.findViewById(R.id.skillDesc)
        private val image: ImageView = itemView.findViewById(R.id.skillImage)

        fun bind(skillData: SkillData) {
            name.text = skillData.name ?: "Unknown Skill"
            desc.text = skillData.description ?: "No description"
            desc.visibility = if (skillData.description.isNullOrEmpty()) View.GONE else View.VISIBLE

            // Use big_icon instead of small_icon
            val imageUrl = skillData.image_url?.big_icon
            Log.d("SkillsAdapter", "Loading image: $imageUrl")

            image.load(imageUrl) {
                crossfade(true)
                placeholder(R.drawable.black_gradient)
                error(R.drawable.black_gradient)
            }
        }
    }

    class SkillDiffCallback : DiffUtil.ItemCallback<SkillData>() {
        override fun areItemsTheSame(oldItem: SkillData, newItem: SkillData) =
            oldItem.skill == newItem.skill

        override fun areContentsTheSame(oldItem: SkillData, newItem: SkillData) =
            oldItem == newItem
    }
}
