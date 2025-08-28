package com.wot.helper.domain.models.models.profileinfo

data class ProfileInfoRequest(
    val status: Any,
    val meta: Meta,
    val data: Map<String, ProfileInfo>
)