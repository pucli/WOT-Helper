package com.wot.helper.domain.models.models.tanksinfo

data class Ammo(
    val damage: List<Int>? = null,
    val penetration: List<Int>? = null,
    val type: String? = null,
    val stun: Stun? = null
)