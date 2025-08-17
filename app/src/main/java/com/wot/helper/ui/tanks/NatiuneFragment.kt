package com.wot.helper.ui.tanks

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.wot.helper.R
import com.wot.helper.databinding.FragmentNatiunePageBinding
import com.wot.helper.domain.models.models.BasicCard
import com.wot.helper.ui.adapters.HomePageAdapter
import com.wot.helper.ui.core.BaseFragment
import javax.inject.Inject
import com.wot.helper.ui.core.MainActivity
import java.util.*
import com.wot.helper.domain.models.repository.RetrofitInstance


class NatiuneFragment : BaseFragment<FragmentNatiunePageBinding>(FragmentNatiunePageBinding::inflate),
    HomePageAdapter.OnCardClickListener {

    @Inject
    lateinit var applicationContext: Context

    private var adapter = HomePageAdapter(this)
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
            maps // If query is empty, show all cards
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
        filterCards("") // Show all cards initially
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

        private val maps = arrayListOf(
            BasicCard(
                "USSR",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/ussr_small.png"
            ),
            BasicCard(
                "Germany",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/germany_small.png"
            ),
            BasicCard(
                "USA",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/usa_small.png"
            ),
            BasicCard(
                "France",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/france_small.png"
            ),
            BasicCard(
                "Great Britain",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/uk_small.png"
            ),
            BasicCard(
                "Czechoslovakia",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/czech_small.png"
            ),
            BasicCard(
                "China",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/china_small.png"
            ),
            BasicCard(
                "Japan",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/japan_small.png"
            ),
            BasicCard(
                "Poland",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/poland_small.png"
            ),
            BasicCard(
                "Sweden",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/sweden_small.png"
            ),
            BasicCard(
                "Italy",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/italy_small.png"
            )
        )
    }

    override fun onCardClick(basicCard: BasicCard) {
            navigateToType(basicCard.title!!.lowercase())
    }

    private fun navigateToType(nation:String) {
        val navType = NatiuneFragmentDirections.actionNatiuneFragmentToTipTankFragment(nation = nation)
        findNavController().navigate(navType)
    }
}
