package com.wot.helper.ui.achievements.model

data class AchievementsResponse(
    val status: String,
    val data: Map<String, AchievementData>
)

data class AchievementData(
    val name_i18n: String?,    // ✅ localized display name
    val description: String?,
    val section: String?,
    val hero_info: String?,
    val image: String?,
    val image_big: String?
)
