package com.wot.helper.domain.models.models.profileinfo

data class Statistics(
    val all: All? = null,
    val clan: Clan? = null,
    val company: Company? = null,
    val frags: Any? = null,
    val historical: Historical? = null,
    val regular_team: RegularTeam? = null,
    val stronghold_defense: StrongholdDefense? = null,
    val stronghold_skirmish: StrongholdSkirmish? = null,
    val team: Team? = null,
    val trees_cut: Int
)