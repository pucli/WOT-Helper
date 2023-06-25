package com.wot.helper.ui.tanks

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wot.helper.R
import com.wot.helper.databinding.FragmentTipTankPageBinding
import com.wot.helper.domain.models.models.BasicCard
import com.wot.helper.ui.adapters.HomePageAdapter
import com.wot.helper.ui.core.BaseFragment
import java.util.*


class TipTankFragment : BaseFragment<FragmentTipTankPageBinding>(FragmentTipTankPageBinding::inflate),
    HomePageAdapter.OnCardClickListener {

    private var adapter = HomePageAdapter(this)
    private val args: TipTankFragmentArgs by navArgs()
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.searchView)

        setAdapter()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Handle search submission if needed
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
            maps
        } else {
            val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
            maps.filter { basicCard ->
                basicCard.title!!.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)
            }
        }
        adapter.submitList(filteredList)
    }

    private fun setAdapter() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        filterCards("")
    }

    companion object {

        private val maps = arrayListOf(
            BasicCard(
                "Light Tanks",
                background = R.drawable.light_tanks,
                type = "lightTank"
            ),
            BasicCard(
                "Medium Tanks",
                background = R.drawable.medium_tanks,
                type = "mediumTank"
            ),
            BasicCard(
                "Heavy Tanks",
                background = R.drawable.heavy_tanks,
                type = "heavyTank"
            ),
            BasicCard(
                "Tank Destroyers",
                background = R.drawable.tank_destroyers,
                type = "AT-SPG"
            )
        )
    }


    override fun onCardClick(basicCard: BasicCard) {
            navigateToTier(nation = args.nation, type = basicCard.type!!)
    }

    private fun navigateToTier(type:String, nation:String) {
        val navTier = TipTankFragmentDirections.actionTipTankFragmentToTierFragment(type = type, nation = nation)
        findNavController().navigate(navTier)
    }
}