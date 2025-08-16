package com.wot.helper.domain.models.models

data class BasicCard(
    var title: String? = null,
    var background: String? = null,
    var type : String? = null,
    var tier : Int? = null,
    val tierString: String? = null
    // val backgroundUrl: String
)
