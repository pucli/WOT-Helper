package com.wot.helper.domain.models.missions

data class MissionResponse(
    val status: String,
    val data: Map<String, CampaignData>
)

data class CampaignData(
    val operations: Map<String, OperationData>
)

data class OperationData(
    val name: String,
    val missions: Map<String, MissionData>
)

data class MissionData(
    val name: String,
    val hint: String?,
    val description: String?,
    val rewards: Rewards
)

data class Rewards(
    val primary: RewardConditions?,
    val secondary: RewardConditions?
)

data class RewardConditions(
    val conditions: String?
)
