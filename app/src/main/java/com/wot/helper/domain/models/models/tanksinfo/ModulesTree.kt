package com.wot.helper.domain.models.models.tanksinfo

data class ModulesTree(
    val is_default: Boolean? = null,
    val module_id: Int? = null,
    val name: String? = null,
    val next_modules: ArrayList<Int>? = arrayListOf(),
    val next_tanks: ArrayList<Int>? = arrayListOf(),
    val price_credit: Int? = null,
    val price_xp: Int? = null,
    val type: String? = null
)