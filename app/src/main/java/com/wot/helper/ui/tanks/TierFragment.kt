package com.wot.helper.ui.tanks

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wot.helper.R
import com.wot.helper.databinding.FragmentTierBinding
import com.wot.helper.domain.models.models.BasicCard
import com.wot.helper.ui.adapters.HomePageAdapter
import com.wot.helper.ui.core.BaseFragment
import java.util.*
import com.wot.helper.domain.models.repository.RetrofitInstance


class TierFragment : BaseFragment<FragmentTierBinding>(FragmentTierBinding::inflate),
    HomePageAdapter.OnCardClickListener {

    private var adapter = HomePageAdapter(this)
    private val args: TierFragmentArgs by navArgs()
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = view.findViewById(R.id.searchView)

        setAdapter()

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
            maps
        } else {
            val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
            maps.filter { basicCard ->
                basicCard.tierString!!.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)
            }
        }
        adapter.submitList(filteredList)
    }

    private fun setAdapter() {
        adapter.submitList(maps)
        binding.recyclerView.adapter = adapter
    }

    companion object {

        private val maps = arrayListOf(
            BasicCard(
                "TIER",
                background = R.drawable.tier10,
                tier = 10,
                tierString = "10 X"
            ),
            BasicCard(
                "TIER",
                background = R.drawable.tier9,
                tier = 9,
                tierString = "9 IX"
            ),
            BasicCard(
                "TIER",
                background = R.drawable.tier8,
                tier = 8,
                tierString = "8 VIII"
            ),
            BasicCard(
                "TIER",
                background = R.drawable.tier7,
                tier = 7,
                tierString = "7 VII"
            ),
            BasicCard(
                "TIER",
                background = R.drawable.tier6,
                tier = 6,
                tierString = "6 VI"
            ),
            BasicCard(
                "TIER",
                background = R.drawable.tier5,
                tier = 5,
                tierString = "5 V"
            ),
            BasicCard(
                "TIER",
                background = R.drawable.tier4,
                tier = 4,
                tierString = "4 IV"
            ),
            BasicCard(
                "TIER",
                background = R.drawable.tier3,
                tier = 3,
                tierString = "3 III"
            ),
            BasicCard(
                "TIER",
                background = R.drawable.tier2 ,
                tier = 2,
                tierString = "2 II"
            ),
            BasicCard(
                "TIER",
                background = R.drawable.tier1,
                tier = 1,
                tierString = "1 I"
            )
        )
    }

    override fun onCardClick(basicCard: BasicCard) {
        if (basicCard.title == "TIER") {
            navigateToFound(nation = args.nation, type = args.type, tier = basicCard.tier!! )
        }
    }

    private fun navigateToFound(type:String, nation:String, tier:Int) {
        val navFound = TierFragmentDirections.actionTierFragmentToTankFoundFragment(type = type, nation = nation, tier = tier)
        findNavController().navigate(navFound)
    }
}