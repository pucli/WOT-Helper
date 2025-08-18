package com.wot.helper.ui.network

import retrofit2.http.GET
import retrofit2.http.Query

// --- API models (Serializable so we can pass them via Bundle) ---
data class PersonalMissionsResponse(
    val status: String,
    val data: Map<String, VehicleMission>
)

data class VehicleMission(
    val name: String,
    val description: String?,
    val campaigns: Map<String, Campaign>
) : java.io.Serializable

data class Campaign(
    val name: String,
    val description: String?,
    val operations: Map<String, Operation>
) : java.io.Serializable

data class Operation(
    val name: String,
    val description: String?,
    val missions: Map<String, Mission>
) : java.io.Serializable

data class Mission(
    val mission_id: Int,
    val name: String,
    val description: String?,
    val conditions: Map<String, Any>?
) : java.io.Serializable

