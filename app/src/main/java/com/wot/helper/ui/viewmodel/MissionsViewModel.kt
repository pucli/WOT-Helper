package com.wot.helper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wot.helper.ui.network.RetrofitClient
import com.wot.helper.ui.network.VehicleMission
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MissionsViewModel : ViewModel() {

    private val _missions = MutableStateFlow<Map<String, VehicleMission>>(emptyMap())
    val missions: StateFlow<Map<String, VehicleMission>> = _missions

    fun loadMissions() {
        if (_missions.value.isNotEmpty()) return
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getPersonalMissions()
                if (response.status == "ok") {
                    _missions.value = response.data
                } else {
                    // Handle API error
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle network error
            }
        }
    }
}
