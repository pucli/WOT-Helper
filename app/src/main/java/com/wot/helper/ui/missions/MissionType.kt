package com.wot.helper.ui.missions

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wot.helper.R
import com.wot.helper.databinding.FragmentMissionTypeBinding
import com.wot.helper.domain.models.models.BasicCard
import com.wot.helper.ui.adapters.HomePageAdapter
import com.wot.helper.ui.core.BaseFragment
import java.util.*

class MissionType : BaseFragment<FragmentMissionTypeBinding>(FragmentMissionTypeBinding::inflate),
    HomePageAdapter.OnCardClickListener {

    private val args: MissionTypeArgs by navArgs()
    private var adapter = HomePageAdapter(this)
    private lateinit var searchView: SearchView
    private var missionTypes: List<BasicCard> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.searchView)

        missionTypes = getMissionTypes(args.campaign, args.tank)

        binding.recyclerView.adapter = adapter
        filterCards("")

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = false
            override fun onQueryTextChange(newText: String): Boolean {
                filterCards(newText)
                return true
            }
        })
    }

    private fun filterCards(query: String) {
        val filteredList = if (query.isEmpty()) missionTypes else {
            val lower = query.lowercase(Locale.getDefault())
            missionTypes.filter { it.title!!.lowercase(Locale.getDefault()).contains(lower) }
        }
        adapter.submitList(filteredList)
    }

    override fun onCardClick(basicCard: BasicCard) {
        findNavController().navigate(
            MissionTypeDirections.actionMissionTypeToMissionDescription(
                campaign = args.campaign,
                tank = args.tank,
                missionType = basicCard.title!!
            )
        )
    }

    private fun getMissionTypes(campaign: String, tank: String): List<BasicCard> {
        return when (campaign.lowercase(Locale.getDefault())) {
            "the long-awaited backup" -> listOf(
                BasicCard("Light Tank", background = "https://raw.githubusercontent.com/pucli/wotimg/main/light2.png"),
                BasicCard("Medium Tank", background = "https://raw.githubusercontent.com/pucli/wotimg/main/medium2.png"),
                BasicCard("Heavy Tank", background = "https://raw.githubusercontent.com/pucli/wotimg/main/heavy2.png"),
                BasicCard("Tank Destroyer", background = "https://raw.githubusercontent.com/pucli/wotimg/main/td2.png"),
                BasicCard("SPG", background = "https://raw.githubusercontent.com/pucli/wotimg/main/spg.jpg")
            )
            "the second front" -> listOf(
                BasicCard("Union", background = "https://raw.githubusercontent.com/pucli/wotimg/main/onsl.jpg"),
                BasicCard("Bloc", background = "https://raw.githubusercontent.com/pucli/wotimg/main/onsl.jpg"),
                BasicCard("Alliance", background = "https://raw.githubusercontent.com/pucli/wotimg/main/onsl.jpg"),
                BasicCard("Coalition", background = "https://raw.githubusercontent.com/pucli/wotimg/main/onsl.jpg")
            )
            else -> emptyList()
        }
    }
}
