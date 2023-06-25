package com.wot.helper.domain.models.models.tanksinfo

data class DefaultProfile(
    val hp: Int? = null,
    val hull_hp: Int? = null,
    val hull_weight: Int? = null,
    val max_ammo: Int? = null,
    val max_weight: Int? = null,
    val speed_backward: Int? = null,
    val speed_forward: Int? = null,
    val weight: Int? = null,
    val ammo: List<Ammo>? = null,
    val armor: Armor? = null,
    val engine: Engine? = null,
    val gun: Gun? = null,
    val modules: Modules? = null,
    val radio: Radio? = null,
    val rapid: Rapid? = null,
    val siege: Siege? = null,
    val suspension: Suspension? = null,
    val turret: Turret? = null
)