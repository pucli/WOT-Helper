package com.wot.helper.ui.missions

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.wot.helper.databinding.FragmentMissionDescriptionBinding
import com.wot.helper.domain.models.missions.MissionData
import com.wot.helper.ui.core.BaseFragment
import com.wot.helper.ui.network.RetrofitClientMission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MissionDescription :
    BaseFragment<FragmentMissionDescriptionBinding>(FragmentMissionDescriptionBinding::inflate) {

    private val args: MissionDescriptionArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            val missions = loadMissions(args.campaign, args.tank, args.missionType)
            binding.recyclerView.adapter = MissionDescriptionAdapter(missions)

            val spacingPx = (16 * resources.displayMetrics.density).toInt()
            binding.recyclerView.addItemDecoration(object :
                androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect, view: View,
                    parent: androidx.recyclerview.widget.RecyclerView,
                    state: androidx.recyclerview.widget.RecyclerView.State
                ) {
                    if (parent.getChildAdapterPosition(view) == 0) outRect.top = spacingPx
                    outRect.bottom = spacingPx
                }
            })
        }
    }

    private suspend fun loadMissions(campaign: String, tank: String, missionType: String): List<MissionData> {
        return withContext(Dispatchers.IO) {
            val response = RetrofitClientMission.missionsApi.getPersonalMissions()

            val campaignData = response.data.values
                .flatMap { it.operations.values }
                .firstOrNull { it.name.equals(tank, ignoreCase = true) }
                ?: return@withContext emptyList()

            val prefix = when (missionType.lowercase()) {
                "light tank" -> "LT-"
                "medium tank" -> "MT-"
                "heavy tank" -> "HT-"
                "tank destroyer" -> "TD-"
                "spg" -> "SPG-"
                else -> ""
            }

            campaignData.missions.values.filter { it.name.startsWith(prefix) }
        }
    }
}
