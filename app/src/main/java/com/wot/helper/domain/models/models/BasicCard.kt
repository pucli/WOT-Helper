package com.wot.helper.domain.models.models

data class BasicCard(
    var title: String? = null,
    var background: Int? = null,
    var type : String? = null,
    var tier : Int? = null,
    val tierString: String? = null
)
