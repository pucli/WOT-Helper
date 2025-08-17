package com.wot.helper.ui.missions

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wot.helper.R
import com.wot.helper.databinding.FragmentCampaigntankBinding
import com.wot.helper.ui.missions.CampaignTankFragmentArgs
import com.wot.helper.domain.models.models.BasicCard
import com.wot.helper.ui.adapters.HomePageAdapter
import com.wot.helper.ui.core.BaseFragment
import java.util.*

class CampaignTankFragment : BaseFragment<FragmentCampaigntankBinding>(FragmentCampaigntankBinding::inflate),
    HomePageAdapter.OnCardClickListener {

    private var adapter = HomePageAdapter(this)
    private val args: CampaignTankFragmentArgs by navArgs()   // ✅ FIXED
    private lateinit var searchView: SearchView
    private var campaignTanks: List<BasicCard> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.searchView)

        // Load tanks for the selected campaign
        campaignTanks = getTanksForCampaign(args.campaign)

        setAdapter()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false
            override fun onQueryTextChange(newText: String): Boolean {
                filterCards(newText)
                return true
            }
        })
    }

    private fun setAdapter() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        filterCards("") // show all by default
    }

    private fun filterCards(query: String) {
        val filteredList = if (query.isEmpty()) {
            campaignTanks
        } else {
            val lowerCaseQuery = query.lowercase(Locale.getDefault())
            campaignTanks.filter { card ->
                card.title!!.lowercase(Locale.getDefault()).contains(lowerCaseQuery)
            }
        }
        adapter.submitList(filteredList)
    }



    /**
     * Return campaign-specific tanks
     */
    private fun getTanksForCampaign(campaign: String): List<BasicCard> {
        return when (campaign.lowercase(Locale.getDefault())) {
            "the long-awaited backup" -> listOf(
                BasicCard("Stug IV", background = "https://raw.githubusercontent.com/pucli/wotimg/main/stug4.jpg"),
                BasicCard("T28 Concept", background = "https://raw.githubusercontent.com/pucli/wotimg/main/t28htc.jpg"),
                BasicCard("T 55A", background = "https://raw.githubusercontent.com/pucli/wotimg/main/t55a.jpg"),
                BasicCard("Object 260", background = "https://raw.githubusercontent.com/pucli/wotimg/main/obj260.jpg")
            )
            "the second front" -> listOf(
                BasicCard("Excalibur", background = "https://raw.githubusercontent.com/pucli/wotimg/main/excalibur.jpg"),
                BasicCard("Chimera", background = "https://raw.githubusercontent.com/pucli/wotimg/main/chimera.jpg"),
                BasicCard("Object 279e", background = "https://raw.githubusercontent.com/pucli/wotimg/main/wot279.jpg")
            )
            else -> emptyList()
        }
    }
    override fun onCardClick(basicCard: BasicCard) {
        val action = CampaignTankFragmentDirections
            .actionCampaignTankFragmentToMissionType(
                campaign = args.campaign,
                tank = basicCard.title!!
            )
        findNavController().navigate(action)
    }

}
