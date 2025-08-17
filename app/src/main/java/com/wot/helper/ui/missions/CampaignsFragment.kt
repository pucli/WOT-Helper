package com.wot.helper.ui.missions

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.wot.helper.R
import com.wot.helper.databinding.FragmentCampaingsPageBinding
import com.wot.helper.domain.models.models.BasicCard
import com.wot.helper.ui.adapters.HomePageAdapter
import com.wot.helper.ui.core.BaseFragment
import com.wot.helper.ui.core.MainActivity
import javax.inject.Inject
import java.util.*

class CampaignsFragment : BaseFragment<FragmentCampaingsPageBinding>(FragmentCampaingsPageBinding::inflate),
    HomePageAdapter.OnCardClickListener {

    @Inject
    lateinit var applicationContext: Context

    private var adapter = HomePageAdapter(this)
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.searchView)

        setAdapter()
        setUpSearchView()
    }

    private fun setAdapter() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        filterCards("") // Show all cards initially
    }

    private fun setUpSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterCards(newText)
                return true
            }
        })
    }

    private fun filterCards(query: String) {
        val filteredList = if (query.isEmpty()) {
            missions // If query is empty, show all cards
        } else {
            val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
            missions.filter { basicCard ->
                basicCard.title!!.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)
            }
        }
        adapter.submitList(filteredList)
    }

    private fun setUpBottomNavBar() {
        val bottomNav = (requireActivity() as MainActivity).bottomNav
        bottomNav.apply {
            if (!isVisible) {
                isVisible = true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavBar()
    }

    companion object {
        private val missions = arrayListOf(
            /* BasicCard(
                "STUG IV",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/stug4.jpg"
            ),
            BasicCard(
                "T28 Concept",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/t28htc.jpg"
            ),
            BasicCard(
                "T 55A",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/t55a.jpg"
            ),
            BasicCard(
                "Object 260",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/obj260.jpg"
            ),
            BasicCard(
                "Excalibur",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/excalibur.jpg"
            ),
            BasicCard(
                "Chimera",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/chimera.jpg"
            ),
            BasicCard(
                "Object 279e",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/wot279.jpg"
            ) */

            BasicCard(
                "The Long-Awaited Backup",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/thelongawaitedbackup.jpg"

            ),
            BasicCard(
                "The Second Front",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/secondfront.jpg"
            )
        )
    }

    override fun onCardClick(basicCard: BasicCard) {
        navigateToCampaignTank(basicCard.title!!)
    }

    private fun navigateToCampaignTank(campaign: String) {
        val navCampaignTankCharact = CampaignsFragmentDirections.actionCampaignFragmentToCampaignTankFragment(campaign = campaign)
        findNavController().navigate(navCampaignTankCharact)
    }
}