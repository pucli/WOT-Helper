package com.wot.helper.ui.missions

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.wot.helper.databinding.FragmentMissionDescriptionBinding
import com.wot.helper.ui.core.BaseFragment

class MissionDescription :
    BaseFragment<FragmentMissionDescriptionBinding>(FragmentMissionDescriptionBinding::inflate) {

    private val args: MissionDescriptionArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val missions = getMissions(args.campaign, args.tank, args.missionType)

        val adapter = MissionAdapter(missions)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Spațiu între item-uri (în dp)
        val spacingDp = 16
        val spacingPx = (spacingDp * resources.displayMetrics.density).toInt()

        binding.recyclerView.addItemDecoration(object : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: android.graphics.Rect,
                view: android.view.View,
                parent: androidx.recyclerview.widget.RecyclerView,
                state: androidx.recyclerview.widget.RecyclerView.State
            ) {
                // Spațiu de sus pentru primul item
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = spacingPx
                }
                // Spațiu între toate item-urile
                outRect.bottom = spacingPx
            }
        })
    }

    private fun getMissions(campaign: String, tank: String, missionType: String): List<String> {
        // Aici pui lista ta de misiuni exact cum ai în exemplul anterior
        return when {
            campaign.equals("The Long-Awaited Backup", ignoreCase = true) &&
                    tank.equals("Stug IV", ignoreCase = true) &&
                    missionType.equals("Light Tank", ignoreCase = true) -> listOf(
                "LT-1: Top 10, Win battle. Honors: Survive.",
                "LT-2: Spot 2 enemy vehicles. Honors: Survive.",
                "LT-3: Cause damage 2 times by shooting. Honors: Destroy 1 vehicle.",
                "LT-4: Destroy a track. Honors: Allies must cause damage.",
                "LT-5: Enable allies to destroy 1 spotted vehicle. Honors: 2 vehicles."

            )

            campaign.equals("The Long-Awaited Backup", ignoreCase = true) &&
            tank.equals("T28 HTC", ignoreCase = true) &&
            missionType.equals("Light Tank", ignoreCase = true) -> listOf(
                "LT-1: Top 10, Win battle. Honors: Survive.",
                "LT-2: Spot 2 enemy vehicles. Honors: Survive.",
                "LT-3: Cause damage 2 times by shooting. Honors: Destroy 1 vehicle.",
                "LT-4: Destroy a track. Honors: Allies must cause damage.",
                "LT-5: Enable allies to destroy 1 spotted vehicle. Honors: 2 vehicles."
                // ... restul
            )

            else -> emptyList()
        }
    }
}
