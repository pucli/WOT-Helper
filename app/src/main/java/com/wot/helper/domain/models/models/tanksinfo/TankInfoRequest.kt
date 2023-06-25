package com.wot.helper.domain.models.models.tanksinfo

data class TankInfoRequest(
    val meta: Meta? = Meta(),
    val status: String? = null,
    val data: HashMap<Int, TankInfo>? = hashMapOf()
)