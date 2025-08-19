package com.wot.helper.ui.missions

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.wot.helper.databinding.FragmentMissionDescriptionBinding
import com.wot.helper.domain.models.missions.MissionData
import com.wot.helper.domain.models.missions.RewardConditions
import com.wot.helper.domain.models.missions.Rewards
import com.wot.helper.ui.core.BaseFragment
import com.wot.helper.ui.network.RetrofitClientMission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

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
            // Check if it's The Second Front → Excalibur/Chimera/Obj279e → Union/Bloc/Alliance/Coalition
            if (campaign.equals("The Second Front", ignoreCase = true)) {
                val hardcoded = getHardcodedMissions(tank, missionType)
                if (hardcoded.isNotEmpty()) {
                    return@withContext hardcoded
                }
            }

            // Otherwise, fallback to API
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
    private val missionDescription = "These missions must be completed in one battle. " +
            "To receive rewards for the secondary condition (together with the primary condition or to improve the result), " +
            "a player must complete the primary condition. Otherwise, the mission will not be considered completed and no rewards will be provided for the completion of the secondary condition."

    private fun getHardcodedMissions(tank: String, missionType: String): List<MissionData> {
        return when (tank.lowercase(Locale.getDefault())) {

            "excalibur" -> when (missionType.lowercase(Locale.getDefault())) {
                "union" -> listOf(
                    MissionData(
                        name = "Union-1",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Enable your allies to cause 15000HP of damage to enemy vehicles that do not belong to Union by spotting them or destroying their tracks."),
                            secondary = RewardConditions("Honors: Survive (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-2",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Cause 6000HP damage to light tanks."),
                            secondary = RewardConditions("Honors: Cause 2000HP damage to enemy vehicles (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-3",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Destroy 15 vehicles that caused damage or hit your vehicle causing no damage."),
                            secondary = RewardConditions("Honors: Survive (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-4",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Block 40 hits with your vehicle."),
                            secondary = RewardConditions("Honors: Survive (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-5",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Cause 2500HP damage to SPGs."),
                            secondary = RewardConditions("Honors: Cause 2000HP damage to enemy vehicles (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-6",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Enable your allies to destroy a total of 12 enemy light tanks, medium tanks, or tank destroyers by spotting them or destroying their tracks."),
                            secondary = RewardConditions("Honors: Destroy an immobilized vehicle. (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-7",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Destroy 10 medium tanks."),
                            secondary = RewardConditions("Honors: Cause 2000HP damage to enemy vehicles. (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-8",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Enable your allies to cause 10000HP damage to enemy vehicles by spotting, stunning, or destroying their tracks."),
                            secondary = RewardConditions("Honors: Stun enemy vehicles for 100 seconds total. Potential is counted. (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-9",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Cause 2x more damage than the HP of your vehicle 10x."),
                            secondary = RewardConditions("Honors: Destroy 2 enemy vehicles. (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-10",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Participate in the successful capture of a base 2x."),
                            secondary = RewardConditions("Honors: Be among the top 5 players on your team. (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-11",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Destroy 2 enemy vehicles."),
                            secondary = RewardConditions("Honors: Cause 2000HP damage. (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-12",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Block 2x the HP of your vehicle."),
                            secondary = RewardConditions("Honors: Survive (4X)")
                        )
                    ),
                    MissionData(
                        name = "Union-13",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Cause damage to an enemy by ramming with your vehicle (7x)."),
                            secondary = RewardConditions("Honors: Survive (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-14",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Destroy 7 enemy vehicles that have lost more than 50% of their hit points as a result of damage caused by you (7x)."),
                            secondary = RewardConditions("Honors: Cause 3000HP of damage (5X)")
                        )
                    ),
                    MissionData(
                        name = "Union-15",
                        description = missionDescription,
                        hint = null,
                        rewards = Rewards(
                            primary = RewardConditions("Receive the mastery badge class I or higher (5x)."),
                            secondary = RewardConditions("Honors: Receive the Ace Tanker mastery badge (3X)")
                        )
                    )
                )



                "bloc" -> listOf(
                    MissionData("Bloc-1", missionDescription, null,
                        Rewards(RewardConditions("Cause 12000HP damage to enemy medium tanks."),
                            RewardConditions("Honors: Cause 3000HP damage. (5X)"))),
                    MissionData("Bloc-2", missionDescription, null,
                        Rewards(RewardConditions("Block damage from 2 enemy vehicles."),
                            RewardConditions("Honors: Survive (5X)"))),
                    MissionData("Bloc-3", missionDescription, null,
                        Rewards(RewardConditions("Destroy 7 enemy light tanks."),
                            RewardConditions("Honors: Cause 2000HP damage to enemy vehicles (5X)"))),
                    MissionData("Bloc-4", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to cause a total of 8000HP damage to enemy light tanks, medium tanks, or tank destroyers."),
                            RewardConditions("Honors: Survive (5X)"))),
                    MissionData("Bloc-5", missionDescription, null,
                        Rewards(RewardConditions("Score an armor-penetrating hit on an enemy vehicle 60 times."),
                            RewardConditions("Honors: Destroy 2 enemy vehicles (5X)"))),
                    MissionData("Bloc-6", missionDescription, null,
                        Rewards(RewardConditions("Destroy 30 enemy vehicles."),
                            RewardConditions("Honors: Stun an enemy vehicle 8x. (5X)"))),
                    MissionData("Bloc-7", missionDescription, null,
                        Rewards(RewardConditions("Be the top player on your team by total damage blocked. Must block a minimum of 1000HP damage."),
                            RewardConditions("Honors: Be the top player on your team by damage caused. (5X)"))),
                    MissionData("Bloc-8", missionDescription, null,
                        Rewards(RewardConditions("Destroy 3 enemy vehicles of 2 different types."),
                            RewardConditions("Honors: Be the top player on your team by damage caused. (5X)"))),
                    MissionData("Bloc-9", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to destroy a total of 7 enemy medium or heavy tanks by spotting them or destroying their tracks."),
                            RewardConditions("Honors: Destroy an immobilized enemy vehicle. (5X)"))),
                    MissionData("Bloc-10", missionDescription, null,
                        Rewards(RewardConditions("Cause 12000HP damage to enemy vehicles that belong to the bloc."),
                            RewardConditions("Honors: Destroy 2 enemy vehicles. (5X)"))),
                    MissionData("Bloc-11", missionDescription, null,
                        Rewards(RewardConditions("Cause 35000 damage to enemy vehicles."),
                            RewardConditions("Honors: Stun 3 different vehicles. (5X)"))),
                    MissionData("Bloc-12", missionDescription, null,
                        Rewards(RewardConditions("Destroy 2 enemy vehicles during the first 4 minutes of battle."),
                            RewardConditions("Honors: Win (4X)"))),
                    MissionData("Bloc-13", missionDescription, null,
                        Rewards(RewardConditions("Be among the top 3 players in the battle by damage caused (7x)."),
                            RewardConditions("Honors: Survive (5X)"))),
                    MissionData("Bloc-14", missionDescription, null,
                        Rewards(RewardConditions("Block 2x the hitpoints of your vehicle (5x)."),
                            RewardConditions("Honors: Finish the battle as the top player on your team by damage blocked (5X)"))),
                    MissionData("Bloc-15", missionDescription, null,
                        Rewards(RewardConditions("Earn a total of 8 rewards in the Battle Heroes and/or epic medals (8x)."),
                            RewardConditions("Honors: Earn a total of 2 rewards in the battle heroes or epic medals. (3X)")))
                )

                "alliance" -> listOf(
                    MissionData("Alliance-1", missionDescription, null,
                        Rewards(RewardConditions("Destroy 20 enemy vehicles."),
                            RewardConditions("Honors: Cause 3000HP damage. (5X)"))),
                    MissionData("Alliance-2", missionDescription, null,
                        Rewards(RewardConditions("Stun 30 different enemy vehicles."),
                            RewardConditions("Honors: Top 3 on your team by XP (5X)"))),
                    MissionData("Alliance-3", missionDescription, null,
                        Rewards(RewardConditions("Cause 10000HP damage to enemy TD's."),
                            RewardConditions("Honors: Destroy 2 enemy vehicles (5X)"))),
                    MissionData("Alliance-4", missionDescription, null,
                        Rewards(RewardConditions("Spot 25 enemy vehicles while remaining unspotted."),
                            RewardConditions("Honors: Win (5X)"))),
                    MissionData("Alliance-5", missionDescription, null,
                        Rewards(RewardConditions("Destroy 10 enemy heavy tanks."),
                            RewardConditions("Honors: Cause 2500HP damage (5X)"))),
                    MissionData("Alliance-6", missionDescription, null,
                        Rewards(RewardConditions("Block 15000HP of damage."),
                            RewardConditions("Honors: Survive. (5X)"))),
                    MissionData("Alliance-7", missionDescription, null,
                        Rewards(RewardConditions("Cause 25000HP damage."),
                            RewardConditions("Honors: Be the top player on your team by XP. (5X)"))),
                    MissionData("Alliance-8", missionDescription, null,
                        Rewards(RewardConditions("Stun 2 enemy vehicles with 1 shot (7x)."),
                            RewardConditions("Honors: Stun enemy vehicles for 100s total. (5X)"))),
                    MissionData("Alliance-9", missionDescription, null,
                        Rewards(RewardConditions("Destroy 7 enemy vehicles that belong to the Alliance."),
                            RewardConditions("Honors: Cause 2500HP damage to enemy vehicles. (5X)"))),
                    MissionData("Alliance-10", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to cause 5000HP damage to enemy heavy or medium tanks by spotting them or destroying their tracks."),
                            RewardConditions("Honors: Survive the battle. (5X)"))),
                    MissionData("Alliance-11", missionDescription, null,
                        Rewards(RewardConditions("Cause 15000HP damage during the first 3 minutes of battle."),
                            RewardConditions("Honors: Win. (5X)"))),
                    MissionData("Alliance-12", missionDescription, null,
                        Rewards(RewardConditions("Block 5 enemy hits."),
                            RewardConditions("Honors: Survive (5X)"))),
                    MissionData("Alliance-13", missionDescription, null,
                        Rewards(RewardConditions("Destroy 12 enemy vehicles during the first 4 minutes of battle."),
                            RewardConditions("Honors: Cause 3000HP damage (5X)"))),
                    MissionData("Alliance-14", missionDescription, null,
                        Rewards(RewardConditions("Allies must destroy 7 enemy vehicles stunned by you."),
                            RewardConditions("Honors: Stun 2 enemies with 1 shot (5X)"))),
                    MissionData("Alliance-15", missionDescription, null,
                        Rewards(RewardConditions("Receive the Mastery Badge Class I or higher (5x)."),
                            RewardConditions("Honors: Receive the Ace Tanker mastery badge. (3X)")))
                )

                "coalition" -> listOf(
                    MissionData("Coalition-1", missionDescription, null,
                        Rewards(RewardConditions("Cause 12000HP damage to enemy heavy tanks."),
                            RewardConditions("Honors: Destroy 2 enemy vehicles. (5X)"))),
                    MissionData("Coalition-2", missionDescription, null,
                        Rewards(RewardConditions("Damage or destroy enemy modules or crew members, totaling at least 36 modules or crew members."),
                            RewardConditions("Honors: Stun 2 enemy vehicles with 1 shot (5X)"))),
                    MissionData("Coalition-3", missionDescription, null,
                        Rewards(RewardConditions("Destroy 20 enemy vehicles that do not belong to the coalition."),
                            RewardConditions("Honors: Cause 3000HP damage (5X)"))),
                    MissionData("Coalition-4", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to cause 15000HP daamge to enemy vehicles by spotting or destroying their tracks."),
                            RewardConditions("Honors: Survive (5X)"))),
                    MissionData("Coalition-5", missionDescription, null,
                        Rewards(RewardConditions("Cause 20000HP damage to vehicles not belonging to the Coalition."),
                            RewardConditions("Honors: Destroy 3 enemy vehicles (5X)"))),
                    MissionData("Coalition-6", missionDescription, null,
                        Rewards(RewardConditions("Be the top player on your team by damage caused."),
                            RewardConditions("Honors: Survive. (5X)"))),
                    MissionData("Coalition-7", missionDescription, null,
                        Rewards(RewardConditions("Destroy 20 enemy vehicles."),
                            RewardConditions("Honors: Cause 3000HP damage. (5X)"))),
                    MissionData("Coalition-8", missionDescription, null,
                        Rewards(RewardConditions("Enable allies to destroy 10 enemy vehicles by spotting them or destroying their tracks (7x)."),
                            RewardConditions("Honors: Destroy an immobilized enemy vehicle. (5X)"))),
                    MissionData("Coalition-9", missionDescription, null,
                        Rewards(RewardConditions("Cause 15000HP damage during the first 3 minutes of battle."),
                            RewardConditions("Honors: Win. (5X)"))),
                    MissionData("Coalition-10", missionDescription, null,
                        Rewards(RewardConditions("Be the top player on your team by XP (7x)."),
                            RewardConditions("Honors: Survive the battle. (5X)"))),
                    MissionData("Coalition-11", missionDescription, null,
                        Rewards(RewardConditions("Destroy 10 enemy tank destroyers."),
                            RewardConditions("Honors: Cause 2500HP damage to vehicles. (5X)"))),
                    MissionData("Coalition-12", missionDescription, null,
                        Rewards(RewardConditions("Cause 25000HP damage."),
                            RewardConditions("Honors: Destroy 2 enemy vehicles (5X)"))),
                    MissionData("Coalition-13", missionDescription, null,
                        Rewards(RewardConditions("Destroy 5 enemy SPG's."),
                            RewardConditions("Honors: Cause 3000HP damage (5X)"))),
                    MissionData("Coalition-14", missionDescription, null,
                        Rewards(RewardConditions("Cause 6 armor piercing shots during the first 4 minutes of battle."),
                            RewardConditions("Honors: Destroy 2 enemy vehicles (5X)"))),
                    MissionData("Coalition-15", missionDescription, null,
                        Rewards(RewardConditions("Earn a total of 8 rewards in the Battle Heroes or Epic Medals category."),
                            RewardConditions("Honors: Earn a total of 2 battle heroes or epic medals in a battle. (3X)")))
                )

                else -> emptyList()
            }

            "chimera" -> when (missionType.lowercase(Locale.getDefault())) {
                "union" -> listOf(
                    MissionData("Union-1", missionDescription, null,
                        Rewards(RewardConditions("Be among the top 3 players on your team by experience earned. Destroy all enemy vehicles / capture or defend the base."),
                            RewardConditions("Honors: Survive"))),
                    MissionData("Union-2", missionDescription, null,
                        Rewards(RewardConditions("Destroy 2 enemy vehicles during the first 4 minutes of the battle. Cause 2.000 HP of damage."),
                            RewardConditions("Honors: Survive"))),
                    MissionData("Union-3", missionDescription, null,
                        Rewards(RewardConditions("Be among the top 3 players on your team by experience earned. Cause 3.000 HP of damage."),
                            RewardConditions("Honors: Survive"))),
                    MissionData("Union-4", missionDescription, null,
                        Rewards(RewardConditions("Destroy 4 enemy vehicles of 2 different types."),
                            RewardConditions("Honors: Survive"))),
                    MissionData("Union-5", missionDescription, null,
                        Rewards(RewardConditions("Cause damage 12 times to enemy vehicles by shooting them."),
                            RewardConditions("Honors: Destroy 3 enemy vehicles"))),
                    MissionData("Union-6", missionDescription, null,
                        Rewards(RewardConditions("Destroy 3 enemy vehicles during the first 4 minutes of the battle."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Union-7", missionDescription, null,
                        Rewards(RewardConditions("Destroy 1 enemy SPG. Spot and damage 1 enemy SPG."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Union-8", missionDescription, null,
                        Rewards(RewardConditions("Damage or destroy enemy internal modules or crew members, totaling at least 5 modules or crew members."),
                            RewardConditions("Honors: Destroy 3 enemy vehicles"))),
                    MissionData("Union-9", missionDescription, null,
                        Rewards(RewardConditions("Cause 2.000 HP of damage to enemy heavy tanks. Destroy 2 enemy heavy tanks."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Union-10", missionDescription, null,
                        Rewards(RewardConditions("Destroy 2 enemy vehicles from a distance of 300 m or more."),
                            RewardConditions("Honors: Cause 2.000 HP of damage"))),
                    MissionData("Union-11", missionDescription, null,
                        Rewards(RewardConditions("Spot 4 enemy vehicles while remaining unspotted."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Union-12", missionDescription, null,
                        Rewards(RewardConditions("Stun enemy vehicles for 140 seconds in total."),
                            RewardConditions("Honors: Destroy 3 enemy vehicles"))),
                    MissionData("Union-13", missionDescription, null,
                        Rewards(RewardConditions("Destroy 6 enemy vehicles of 2 different types."),
                            RewardConditions("Honors: Cause 3.000 HP of damage"))),
                    MissionData("Union-14", missionDescription, null,
                        Rewards(RewardConditions("Destroy 2 enemy vehicles, having received no prior damage to your vehicle."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Union-15", missionDescription, null,
                        Rewards(RewardConditions("Cause, receive and block a total of 10.000 HP of damage."),
                            RewardConditions("Honors: Cause 20% of the total amount of damage caused by your team. Win.")))
                )

                "bloc" -> listOf(
                    MissionData("Bloc-1", missionDescription, null,
                        Rewards(RewardConditions("Cause 5.000 HP of damage to enemy vehicles."),
                            RewardConditions("Honors: Be among the top 3 by experience earned"))),
                    MissionData("Bloc-2", missionDescription, null,
                        Rewards(RewardConditions("Damage or destroy enemy internal modules or crew members, totaling at least 5 modules or crew members."),
                            RewardConditions("Honors: Survive without injured crew members or damaged modules"))),
                    MissionData("Bloc-3", missionDescription, null,
                        Rewards(RewardConditions("Cause 3.000 HP of damage to enemy vehicles within your current view range."),
                            RewardConditions("Honors: Destroy 4 enemy vehicles"))),
                    MissionData("Bloc-4", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to destroy 3 enemy vehicles spotted by you."),
                            RewardConditions("Honors: Enable your allies to cause damage to 5 enemy vehicles spotted by you"))),
                    MissionData("Bloc-5", missionDescription, null,
                        Rewards(RewardConditions("Destroy an enemy vehicle by ramming, causing more damage than you receive. Survive the ramming."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Bloc-6", missionDescription, null,
                        Rewards(RewardConditions("Cause damage 10 times to enemy vehicles by shooting them. Spot 2 enemy vehicles."),
                            RewardConditions("Honors: Destroy 3 enemy vehicles"))),
                    MissionData("Bloc-7", missionDescription, null,
                        Rewards(RewardConditions("Destroy 3 enemy vehicles, remaining unspotted at the moment of their destruction."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Bloc-8", missionDescription, null,
                        Rewards(RewardConditions("Set an enemy vehicle of the same tier or higher on fire."),
                            RewardConditions("Honors: Be among the top 3 by experience earned"))),
                    MissionData("Bloc-9", missionDescription, null,
                        Rewards(RewardConditions("Block 2 times more damage than the hit points of your vehicle. Cause 2.500 HP of damage."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Bloc-10", missionDescription, null,
                        Rewards(RewardConditions("Cause 4.000 HP of damage to enemy vehicles. At the end of the battle, have no injured crew members or damaged modules."),
                            RewardConditions("Honors: Destroy 3 enemy vehicles"))),
                    MissionData("Bloc-11", missionDescription, null,
                        Rewards(RewardConditions("Destroy 6 enemy vehicles."),
                            RewardConditions("Honors: Cause 3.000 HP of damage to enemy vehicles"))),
                    MissionData("Bloc-12", missionDescription, null,
                        Rewards(RewardConditions("Be the top player in the battle by damage caused. Cause 4.000 HP of damage."),
                            RewardConditions("Honors: Survive"))),
                    MissionData("Bloc-13", missionDescription, null,
                        Rewards(RewardConditions("Destroy 6 enemy vehicles of 2 different types."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Bloc-14", missionDescription, null,
                        Rewards(RewardConditions("Cause a total of 3.000 HP of damage to enemy heavy and/or medium tanks."),
                            RewardConditions("Honors: Destroy 3 enemy vehicles"))),
                    MissionData("Bloc-15", missionDescription, null,
                        Rewards(RewardConditions("Cause a total of 3.500 HP of damage to enemy tank destroyers and/or heavy tanks."),
                            RewardConditions("Honors: Cause damage to 6 different enemy vehicles. Win")))
                )

                "alliance" -> listOf(
                    MissionData("Alliance-1", missionDescription, null,
                        Rewards(RewardConditions("Cause 25% of the total amount of damage caused by your team. Win."),
                            RewardConditions("Honors: Survive"))),
                    MissionData("Alliance-2", missionDescription, null,
                        Rewards(RewardConditions("Immobilize 3 different enemy vehicles by destroying their tracks. Allies must cause 250 HP of damage to a vehicle immobilized by you."),
                            RewardConditions("Honors: Allies must destroy 2 vehicles immobilized by you"))),
                    MissionData("Alliance-3", missionDescription, null,
                        Rewards(RewardConditions("Stun an enemy vehicle 10 times."),
                            RewardConditions("Honors: Cause damage 6 times to enemy vehicles by shooting them"))),
                    MissionData("Alliance-4", missionDescription, null,
                        Rewards(RewardConditions("Cause 2.000 HP of damage, while remaining unspotted at the moment when the damage is caused."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Alliance-5", missionDescription, null,
                        Rewards(RewardConditions("Destroy an enemy vehicle by ramming. Survive the ramming attack."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Alliance-6", missionDescription, null,
                        Rewards(RewardConditions("Stun enemy vehicles for 120 seconds in total. Allies must destroy 2 enemy vehicles stunned by you."),
                            RewardConditions("Honors: Win"))),
                    MissionData("Alliance-7", missionDescription, null,
                        Rewards(RewardConditions("Be the top player on your team by damage caused."),
                            RewardConditions("Honors: Survive"))),
                    MissionData("Alliance-8", missionDescription, null,
                        Rewards(RewardConditions("Cause 4 times more damage than the hit points of your vehicle."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Alliance-9", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to cause 4.000 HP of damage to vehicles spotted by you."),
                            RewardConditions("Honors: Survive"))),
                    MissionData("Alliance-10", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to cause damage to 4 enemy vehicles by spotting or immobilizing them."),
                            RewardConditions("Honors: Enable your allies to destroy 2 enemy vehicles spotted or immobilized by you"))),
                    MissionData("Alliance-11", missionDescription, null,
                        Rewards(RewardConditions("Cause damage 6 times to enemy medium tanks. Destroy an enemy medium tank."),
                            RewardConditions("Honors: Cause 1.500 HP of damage to enemy vehicles"))),
                    MissionData("Alliance-12", missionDescription, null,
                        Rewards(RewardConditions("Destroy a total of 3 enemy heavy and/or medium tanks. Cause 4.000 HP of damage."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Alliance-13", missionDescription, null,
                        Rewards(RewardConditions("Destroy 2 enemy vehicles from a distance of no more than 100 meters."),
                            RewardConditions("Honors: Cause 3.000 HP of damage"))),
                    MissionData("Alliance-14", missionDescription, null,
                        Rewards(RewardConditions("Stun enemy vehicles for 140 seconds in total OR Your allies must cause 800 HP of damage to a vehicle immobilized by you."),
                            RewardConditions("Honors: Win"))),
                    MissionData("Alliance-15", missionDescription, null,
                        Rewards(RewardConditions("Cause 6.500 HP of damage to enemy vehicles."),
                            RewardConditions("Honors: Destroy 5 enemy vehicles. Win")))
                )

                "coalition" -> listOf(
                    MissionData("Coalition-1", missionDescription, null,
                        Rewards(RewardConditions("Destroy a total of 3 enemy heavy and/or medium tanks."),
                            RewardConditions("Honors: Cause 2.000 HP of damage to enemy vehicles"))),
                    MissionData("Coalition-2", missionDescription, null,
                        Rewards(RewardConditions("Immobilize 3 different enemy vehicles. Cause damage 5 times to immobilized enemy vehicles."),
                            RewardConditions("Honors: Destroy 2 immobilized enemy vehicles. Win"))),
                    MissionData("Coalition-3", missionDescription, null,
                        Rewards(RewardConditions("Be the top player in the battle by experience earned."),
                            RewardConditions("Honors: Win"))),
                    MissionData("Coalition-4", missionDescription, null,
                        Rewards(RewardConditions("Cause damage to 6 different enemy vehicles. Win."),
                            RewardConditions("Honors: Survive"))),
                    MissionData("Coalition-5", missionDescription, null,
                        Rewards(RewardConditions("Destroy 1 enemy SPG. Enable your allies to cause 1.500 HP of damage to enemy vehicles by spotting or immobilizing them."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Coalition-6", missionDescription, null,
                        Rewards(RewardConditions("Cause damage 7 times to enemy vehicles that are of the same tier as your vehicle or higher."),
                            RewardConditions("Honors: Cause 3.000 HP of damage to enemy vehicles"))),
                    MissionData("Coalition-7", missionDescription, null,
                        Rewards(RewardConditions("Destroy an enemy light tank during the first 2 minutes of the battle."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Coalition-8", missionDescription, null,
                        Rewards(RewardConditions("Finish the battle as the top player on your team by experience earned."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Coalition-9", missionDescription, null,
                        Rewards(RewardConditions("Cause 2.000 HP of damage from a distance of 300 m or more."),
                            RewardConditions("Honors: Destroy 2 enemy vehicles"))),
                    MissionData("Coalition-10", missionDescription, null,
                        Rewards(RewardConditions("Destroy 5 enemy vehicles."),
                            RewardConditions("Honors: Cause 3.000 HP of damage to enemy vehicles"))),
                    MissionData("Coalition-11", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to cause 2.500 HP of damage to vehicles spotted by you. Cause 2.000 HP of damage to enemy vehicles."),
                            RewardConditions("Honors: Win. Survive"))),
                    MissionData("Coalition-12", missionDescription, null,
                        Rewards(RewardConditions("Cause damage 12 times to enemy vehicles by shooting them."),
                            RewardConditions("Honors: Destroy 3 enemy vehicles"))),
                    MissionData("Coalition-13", missionDescription, null,
                        Rewards(RewardConditions("Participate in the successful capture of a base or reset the enemy capture points. Cause 2.000 HP of damage. Win."),
                            RewardConditions("Honors: Destroy 3 enemy vehicles"))),
                    MissionData("Coalition-14", missionDescription, null,
                        Rewards(RewardConditions("Destroy 6 enemy vehicles."),
                            RewardConditions("Honors: Survive"))),
                    MissionData("Coalition-15", missionDescription, null,
                        Rewards(RewardConditions("Damage caused (including assistance) must total 8.000 HP."),
                            RewardConditions("Honors: Enable allies to destroy 3 enemy vehicles by spotting or immobilizing them. Win")))
                )
                else -> emptyList()
            }
            "object279e" -> when (missionType.lowercase(Locale.getDefault())) {
                "union" -> listOf(
                    MissionData("Union-1", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to cause average of 2000 HP to enemy vehicles by spotting or destroying their tracks in 5 battles."),
                            RewardConditions("Honors: 2500 HP in 5 battles"))),
                    MissionData("Union-2", missionDescription, null,
                        Rewards(RewardConditions("Cause 2x your HP in 3 battles."),
                            RewardConditions("Honors: Cause 3x your HP"))),
                    MissionData("Union-3", missionDescription, null,
                        Rewards(RewardConditions("Destroy 15 vehicles in 10 battles."),
                            RewardConditions("Honors: Destroy 20 vehicles in 10 battles"))),
                    MissionData("Union-4", missionDescription, null,
                        Rewards(RewardConditions("Block an average of 8 enemy hits (minimum block 2 hits) in 5 battles."),
                            RewardConditions("Honors: Block an average of 12 enemy hits"))),
                    MissionData("Union-5", missionDescription, null,
                        Rewards(RewardConditions("Be among the top 3 players in the battle by damage caused 3 times in a row."),
                            RewardConditions("Honors: Be the top player by damage caused"))),
                    MissionData("Union-6", missionDescription, null,
                        Rewards(RewardConditions("Be the top player on your team by number of destroyed vehicles / earn more experience than the ally who destroyed the same number of vehicles (destroy minimum 2 enemy vehicles) 3 out of 7 battles."),
                            RewardConditions("Honors: 4 out of 7 battles"))),
                    MissionData("Union-7", missionDescription, null,
                        Rewards(RewardConditions("Destroy/Damage 3 internal modules or crew members in 3 battles."),
                            RewardConditions("Honors: Destroy/Damage 5 internal modules or crew members in 3 battles"))),
                    MissionData("Union-8", missionDescription, null,
                        Rewards(RewardConditions("Earn Fire for Effect award in 7 battles out of 10."),
                            RewardConditions("Honors: Earn Fire for Effect award in 10 battles out of 10"))),
                    MissionData("Union-9", missionDescription, null,
                        Rewards(RewardConditions("Destroy 3 enemy vehicles in 2 battles."),
                            RewardConditions("Honors: Destroy 4 enemy vehicles in 2 battles"))),
                    MissionData("Union-10", missionDescription, null,
                        Rewards(RewardConditions("Block 15000 HP of damage in 10 battles."),
                            RewardConditions("Honors: Block 17000 HP"))),
                    MissionData("Union-11", missionDescription, null,
                        Rewards(RewardConditions("Cause an average of 8 penetrations in 10 battles (pen 5 times)."),
                            RewardConditions("Honors: Cause 12 penetrations"))),
                    MissionData("Union-12", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to destroy 7 enemy vehicles by spotting/immobilizing in 10 battles."),
                            RewardConditions("Honors: Destroy 10 vehicles"))),
                    MissionData("Union-13", missionDescription, null,
                        Rewards(RewardConditions("Earn 2 Top Guns in 2 out of 12 battles."),
                            RewardConditions("Honors: Earn 3 Top Guns in 3 out of 12 battles"))),
                    MissionData("Union-14", missionDescription, null,
                        Rewards(RewardConditions("Stun an enemy vehicle 80 times in 10 battles."),
                            RewardConditions("Honors: Stun 90 times"))),
                    MissionData("Union-15", missionDescription, null,
                        Rewards(RewardConditions("Receive the Mastery Badge Class 1 or higher in 3 battles out of 20."),
                            RewardConditions("Honors: Earn Ace Tanker")))
                )

                "bloc" -> listOf(
                    MissionData("Bloc-1", missionDescription, null,
                        Rewards(RewardConditions("Cause 3500 HP of damage to enemy vehicles in 3 battles."),
                            RewardConditions("Honors: Cause 4000 HP of damage 3 times"))),
                    MissionData("Bloc-2", missionDescription, null,
                        Rewards(RewardConditions("Earn the Shellproof award in 4 battles out of 10."),
                            RewardConditions("Honors: Shellproof award, 5 battles out of 10"))),
                    MissionData("Bloc-3", missionDescription, null,
                        Rewards(RewardConditions("Destroy 3 enemy vehicles in 2 out of 5 battles."),
                            RewardConditions("Honors: Destroy 4 enemy vehicles"))),
                    MissionData("Bloc-4", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to destroy 2 enemy vehicles by spotting/immobilizing, 3 battles in a row."),
                            RewardConditions("Honors: Destroy 3 enemy vehicles"))),
                    MissionData("Bloc-5", missionDescription, null,
                        Rewards(RewardConditions("Cause at least 3000 damage on average in 10 battles."),
                            RewardConditions("Honors: Cause 3500 damage on average"))),
                    MissionData("Bloc-6", missionDescription, null,
                        Rewards(RewardConditions("Destroy 3 enemy vehicles of 2 different types in 2 battles."),
                            RewardConditions("Honors: Destroy 4 enemy vehicles"))),
                    MissionData("Bloc-7", missionDescription, null,
                        Rewards(RewardConditions("Be top player by number of kills (destroy 2 vehicles) in 3 out of 9 battles."),
                            RewardConditions("Honors: 4 out of 9 battles"))),
                    MissionData("Bloc-8", missionDescription, null,
                        Rewards(RewardConditions("Score 7 penetrations in 3 battles."),
                            RewardConditions("Honors: Score 12 penetrations"))),
                    MissionData("Bloc-9", missionDescription, null,
                        Rewards(RewardConditions("Accumulate 15000 HP of assisting damage in 10 battles."),
                            RewardConditions("Honors: 17000 HP of assisting damage"))),
                    MissionData("Bloc-10", missionDescription, null,
                        Rewards(RewardConditions("Earn High Caliber award in 4 battles out of 20."),
                            RewardConditions("Honors: 5 times out of 20"))),
                    MissionData("Bloc-11", missionDescription, null,
                        Rewards(RewardConditions("Cause damage to 4 different enemy vehicles in 3 battles."),
                            RewardConditions("Honors: 5 different enemy vehicles"))),
                    MissionData("Bloc-12", missionDescription, null,
                        Rewards(RewardConditions("Be top player on your team by damage blocked (minimum 1000 HP blocked) in 3 out of 7 battles."),
                            RewardConditions("Honors: 4 out of 7 battles"))),
                    MissionData("Bloc-13", missionDescription, null,
                        Rewards(RewardConditions("Destroy an average of 2 enemy vehicles per battle (complete within 10 battles)."),
                            RewardConditions("Honors: Destroy an average of 3 enemy vehicles"))),
                    MissionData("Bloc-14", missionDescription, null,
                        Rewards(RewardConditions("Be top player on your team by damage caused in 3 battles out of 5."),
                            RewardConditions("Honors: 5 out of 5 battles"))),
                    MissionData("Bloc-15", missionDescription, null,
                        Rewards(RewardConditions("Receive the Mastery Badge Class 1 or higher in 3 battles out of 20."),
                            RewardConditions("Honors: Earn Ace Tanker")))
                )

                "alliance" -> listOf(
                    MissionData("Alliance-1", missionDescription, null,
                        Rewards(RewardConditions("Earn Fire for Effect award in 5 battles out of 10."),
                            RewardConditions("Honors: 7 out of 10 battles"))),
                    MissionData("Alliance-2", missionDescription, null,
                        Rewards(RewardConditions("Be among top 3 on team by damage caused 3 times in a row."),
                            RewardConditions("Honors: Be top player by damage caused"))),
                    MissionData("Alliance-3", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to destroy an average of 2 enemy vehicles per battle by spotting/immobilizing in 10 battles."),
                            RewardConditions("Honors: Average of 3 enemy vehicles"))),
                    MissionData("Alliance-4", missionDescription, null,
                        Rewards(RewardConditions("Cause 30000 HP of damage in 10 battles."),
                            RewardConditions("Honors: Cause 35000 HP of damage"))),
                    MissionData("Alliance-5", missionDescription, null,
                        Rewards(RewardConditions("Stun 40 different enemy vehicles in 10 battles."),
                            RewardConditions("Honors: Stun 50"))),
                    MissionData("Alliance-6", missionDescription, null,
                        Rewards(RewardConditions("Earn Confederate award in 2 out of 7 battles."),
                            RewardConditions("Honors: 3 out of 7 battles"))),
                    MissionData("Alliance-7", missionDescription, null,
                        Rewards(RewardConditions("Block an average of at least 2000 damage (minimum 750) in 10 battles."),
                            RewardConditions("Honors: At least 2500 HP of damage blocked"))),
                    MissionData("Alliance-8", missionDescription, null,
                        Rewards(RewardConditions("Damage or destroy a total of 25 internal modules/crew members in 10 battles."),
                            RewardConditions("Honors: Damage/Destroy 30"))),
                    MissionData("Alliance-9", missionDescription, null,
                        Rewards(RewardConditions("Stun enemy vehicles for 1300 seconds total in 10 battles."),
                            RewardConditions("Honors: 1500 seconds total"))),
                    MissionData("Alliance-10", missionDescription, null,
                        Rewards(RewardConditions("Cause 2x your HP in damage, 4 out of 7 battles."),
                            RewardConditions("Honors: 5 out of 7 battles"))),
                    MissionData("Alliance-11", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to cause 1000 HP of damage to vehicles stunned by you in 4 battles out of 10."),
                            RewardConditions("Honors: 1300 HP of stun-assisting damage"))),
                    MissionData("Alliance-12", missionDescription, null,
                        Rewards(RewardConditions("Top player by damage blocked (minimum 1000) in 3 battles out of 7."),
                            RewardConditions("Honors: 4 battles out of 7"))),
                    MissionData("Alliance-13", missionDescription, null,
                        Rewards(RewardConditions("Be among top 3 players by experience earned 3 battles in a row."),
                            RewardConditions("Honors: Be top player by experience earned"))),
                    MissionData("Alliance-14", missionDescription, null,
                        Rewards(RewardConditions("Cause damage to 30 different enemy vehicles in 7 battles."),
                            RewardConditions("Honors: 40 different enemy vehicles"))),
                    MissionData("Alliance-15", missionDescription, null,
                        Rewards(RewardConditions("Receive the Mastery Badge Class 1 or higher in 3 battles out of 20."),
                            RewardConditions("Honors: Earn Ace Tanker")))
                )

                "coalition" -> listOf(
                    MissionData("Coalition-1", missionDescription, null,
                        Rewards(RewardConditions("Earn Fighter or Top Gun award 3 battles out of 10."),
                            RewardConditions("Honors: 4 out of 10 battles"))),
                    MissionData("Coalition-2", missionDescription, null,
                        Rewards(RewardConditions("Cause 3000 HP of damage in 3 battles."),
                            RewardConditions("Honors: 4000 HP of damage"))),
                    MissionData("Coalition-3", missionDescription, null,
                        Rewards(RewardConditions("Earn a total of 5 Battle Heroes/Epic Medals in 10 battles."),
                            RewardConditions("Honors: Earn 7 Battle Heroes/Epic Medals"))),
                    MissionData("Coalition-4", missionDescription, null,
                        Rewards(RewardConditions("Damage or destroy enemy modules or crew members, totaling at least 25 modules or crew members in 10 battles."),
                            RewardConditions("Honors: Damage or destroy 30 internal modules/crew members in 10 battles"))),
                    MissionData("Coalition-5", missionDescription, null,
                        Rewards(RewardConditions("Block 30 enemy hits in 10 battles."),
                            RewardConditions("Honors: Block 40"))),
                    MissionData("Coalition-6", missionDescription, null,
                        Rewards(RewardConditions("Score 75 penetrations in 10 battles."),
                            RewardConditions("Honors: 90 penetrations"))),
                    MissionData("Coalition-7", missionDescription, null,
                        Rewards(RewardConditions("Destroy 2 enemy vehicles of 2 different types, 3 battles in a row."),
                            RewardConditions("Honors: Destroy 3 enemy vehicles of 2 different types"))),
                    MissionData("Coalition-8", missionDescription, null,
                        Rewards(RewardConditions("Earn Patrol Duty award 2 out of 12 battles."),
                            RewardConditions("Honors: 3 out of 12 battles"))),
                    MissionData("Coalition-9", missionDescription, null,
                        Rewards(RewardConditions("Cause damage to average 4 different enemy vehicles per battle (minimum damage 2) in 10 battles."),
                            RewardConditions("Honors: Cause damage to 5 different enemy vehicles per battle"))),
                    MissionData("Coalition-10", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to cause 1500 HP of damage to enemy vehicles by immobilizing/spotting them, 3 battles in a row."),
                            RewardConditions("Honors: 1800 HP"))),
                    MissionData("Coalition-11", missionDescription, null,
                        Rewards(RewardConditions("Be top player by number of destroyed enemy vehicles (minimum 2) in 3 battles out of 9."),
                            RewardConditions("Honors: 4 battles out of 9"))),
                    MissionData("Coalition-12", missionDescription, null,
                        Rewards(RewardConditions("Enable your allies to destroy 7 enemy vehicles by spotting/immobilizing in 10 battles."),
                            RewardConditions("Honors: Destroy 10 enemy vehicles"))),
                    MissionData("Coalition-13", missionDescription, null,
                        Rewards(RewardConditions("Cause 2x your HP in damage, 3 battles out of 7."),
                            RewardConditions("Honors: 4 out of 7 battles"))),
                    MissionData("Coalition-14", missionDescription, null,
                        Rewards(RewardConditions("Be top player by damage caused, 3 battles out of 5."),
                            RewardConditions("Honors: 4 out of 5 battles"))),
                    MissionData("Coalition-15", missionDescription, null,
                        Rewards(RewardConditions("Receive the Mastery Badge Class 1 or higher in 3 battles out of 20."),
                            RewardConditions("Honors: Earn Ace Tanker")))
                )
                else -> emptyList()
            }
            else -> emptyList()
        }

    }

}
