package com.wot.helper.domain.models.models.tanksinfo

data class Gun(
    val aim_time: Float? = null,
    val caliber: Int? = null,
    val dispersion: Float? = null,
    val fire_rate: Float? = null,
    val move_down_arc: Int? = null,
    val move_up_arc: Int? = null,
    val name: String? = null,
    val reload_time: Float? = null,
    val tag: String? = null,
    val tier: Int? = null,
    val traverse_speed: Int? = null,
    val weight: Int? = null
)