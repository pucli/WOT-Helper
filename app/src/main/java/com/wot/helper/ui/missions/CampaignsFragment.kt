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
        binding.recyclerView.adapter = adapter
        filterCards("")
    }

    private fun setUpSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = false
            override fun onQueryTextChange(newText: String): Boolean {
                filterCards(newText)
                return true
            }
        })
    }

    private fun filterCards(query: String) {
        val filteredList = if (query.isEmpty()) missions else {
            val lower = query.lowercase(Locale.getDefault())
            missions.filter { it.title!!.lowercase(Locale.getDefault()).contains(lower) }
        }
        adapter.submitList(filteredList)
    }

    private fun setUpBottomNavBar() {
        val bottomNav = (requireActivity() as MainActivity).bottomNav
        bottomNav.isVisible = true
    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavBar()
    }

    override fun onCardClick(basicCard: BasicCard) {
        findNavController().navigate(
            CampaignsFragmentDirections.actionCampaignFragmentToCampaignTankFragment(
                campaign = basicCard.title!!
            )
        )
    }

    companion object {
        private val missions = arrayListOf(
            BasicCard("The Long-Awaited Backup", background = "https://raw.githubusercontent.com/pucli/wotimg/main/thelongawaitedbackup.jpg"),
            BasicCard("The Second Front", background = "https://raw.githubusercontent.com/pucli/wotimg/main/secondfront.jpg")
        )
    }
}
