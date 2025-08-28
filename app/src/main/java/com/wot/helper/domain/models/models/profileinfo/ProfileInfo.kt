package com.wot.helper.domain.models.models.profileinfo

data class ProfileInfo(
    val account_id: Int? = null,
    val clan_id: Int? = null,
    val client_language: String? = null,
    val created_at: Long? = null,
    val global_rating: Int? = null,
    val last_battle_time: Long? = null,
    val logout_at: Long? = null,
    val nickname: String? = null,
    val private: Any? = null,
    val statistics: Statistics? = null,
    val updated_at: Long? = null
)