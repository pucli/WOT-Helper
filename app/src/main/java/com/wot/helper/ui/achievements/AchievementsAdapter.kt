package com.wot.helper.ui.achievements

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wot.helper.R
import com.wot.helper.ui.achievements.model.AchievementData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class AchievementsAdapter :
    ListAdapter<AchievementData, AchievementsAdapter.AchievementViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_achievement, parent, false)
        return AchievementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.achievementName)
        private val desc: TextView = itemView.findViewById(R.id.achievementDesc)
        private val info: TextView = itemView.findViewById(R.id.achievementInfo)
        private val image: ImageView = itemView.findViewById(R.id.achievementImage)

        fun bind(achievement: AchievementData) {
            // Show texts
            name.text = achievement.name_i18n ?: "Mark of Excellence"
            desc.text = achievement.description ?: "No description"

            if (!achievement.hero_info.isNullOrEmpty()) {
                info.visibility = View.VISIBLE
                info.text = "Historical Info: ${achievement.hero_info}"
            } else {
                info.visibility = View.GONE
            }

            // Prefer image_big
            val imageUrl = achievement.image_big ?: achievement.image
            Log.d("AchievementsAdapter", "Loading image for ${achievement.name_i18n}: $imageUrl")

            if (!imageUrl.isNullOrEmpty()) {
                // Load image manually in background
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val input = URL(imageUrl).openStream()
                        val bitmap = BitmapFactory.decodeStream(input)
                        withContext(Dispatchers.Main) {
                            image.setImageBitmap(bitmap)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            image.setImageResource(R.drawable.marks)
                        }
                    }
                }
            } else {
                image.setImageResource(R.drawable.marks)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<AchievementData>() {
        override fun areItemsTheSame(oldItem: AchievementData, newItem: AchievementData) =
            oldItem.name_i18n == newItem.name_i18n

        override fun areContentsTheSame(oldItem: AchievementData, newItem: AchievementData) =
            oldItem == newItem
    }
}
