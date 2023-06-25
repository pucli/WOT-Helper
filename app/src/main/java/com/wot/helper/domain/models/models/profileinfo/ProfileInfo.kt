package com.wot.helper.domain.models.models.profileinfo

import java.sql.Timestamp

data class ProfileInfo(
    val account_id: Int,
    val clan_id: Int,
    val client_language: String,
    val created_at: Long,
    val global_rating: Int? = null,
    val last_battle_time: Long,
    val logout_at: Long,
    val nickname: String,
    val private: Any? = null,
    val statistics: Statistics? = null,
    val updated_at: Long
)