package com.wot.helper.ui.skills.model


data class SkillsResponse(
    val status: String,
    val data: Map<String, SkillData>
)

data class SkillData(
    val name: String?,
    val description: String?,
    val image_url: SkillImage?,  // match JSON key
    val skill: String?,          // JSON has this
    val is_perk: Boolean?        // JSON has this too
) {
    // derive role from skill prefix
    val role: String?
        get() = when {
            skill?.startsWith("commander_", true) == true -> "commander"
            skill?.startsWith("driver_", true) == true -> "driver"
            skill?.startsWith("gunner_", true) == true -> "gunner"
            skill?.startsWith("loader_", true) == true -> "loader"
            skill?.startsWith("radioman_", true) == true ||
                    skill?.startsWith("radio_operator_", true) == true -> "radio_operator"
            else -> null
        }
}

data class SkillImage(
    val big_icon: String?,
    val small_icon: String?
)
