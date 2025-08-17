package com.wot.helper.ui.missions

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
        searchView = view.findViewById(com.wot.helper.R.id.searchView)

        // Get mission types based on campaign + tank
        missionTypes = getMissionTypes(args.campaign, args.tank)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
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
        val filteredList = if (query.isEmpty()) {
            missionTypes
        } else {
            val lowerCaseQuery = query.lowercase(Locale.getDefault())
            missionTypes.filter { card ->
                card.title!!.lowercase(Locale.getDefault()).contains(lowerCaseQuery)
            }
        }
        adapter.submitList(filteredList)
    }

    override fun onCardClick(basicCard: BasicCard) {
        val action = MissionTypeDirections
            .actionMissionTypeToMissionDescription(
                campaign = args.campaign,
                tank = args.tank,
                missionType = basicCard.title!!
            )
        findNavController().navigate(action)
    }


    private fun getMissionTypes(campaign: String, tank: String): List<BasicCard> {
        return when (campaign.lowercase(Locale.getDefault())) {
            "the long-awaited backup" -> listOf(
                BasicCard("Light Tank", background = "https://raw.githubusercontent.com/pucli/wotimg/main/light.jpg"),
                BasicCard("Medium Tank", background = "https://raw.githubusercontent.com/pucli/wotimg/main/medium.jpg"),
                BasicCard("Heavy Tank", background = "https://raw.githubusercontent.com/pucli/wotimg/main/heavy.jpg"),
                BasicCard("Tank Destroyer", background = "https://raw.githubusercontent.com/pucli/wotimg/main/td.jpg"),
                BasicCard("SPG", background = "https://raw.githubusercontent.com/pucli/wotimg/main/spg.jpg")
            )
            "the second front" -> listOf(
                BasicCard("Union", background = "https://raw.githubusercontent.com/pucli/wotimg/main/union.jpg"),
                BasicCard("Bloc", background = "https://raw.githubusercontent.com/pucli/wotimg/main/bloc.jpg"),
                BasicCard("Alliance", background = "https://raw.githubusercontent.com/pucli/wotimg/main/alliance.jpg"),
                BasicCard("Coalition", background = "https://raw.githubusercontent.com/pucli/wotimg/main/coalition.jpg")
            )
            else -> emptyList()
        }
    }
}
