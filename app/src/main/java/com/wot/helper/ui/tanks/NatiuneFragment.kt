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
                background = R.drawable.ussr_small
            ),
            BasicCard(
                "Germany",
                background = R.drawable.germany_small
            ),
            BasicCard(
                "USA",
                background = R.drawable.usa_small
            ),
            BasicCard(
                "France",
                background = R.drawable.france_small
            ),
            BasicCard(
                "Great Britain",
                background = R.drawable.uk_small
            ),
            BasicCard(
                "Czechoslovakia",
                background = R.drawable.czech_small
            ),
            BasicCard(
                "China",
                background = R.drawable.china_small
            ),
            BasicCard(
                "Japan",
                background = R.drawable.japan_small
            ),
            BasicCard(
                "Poland",
                background = R.drawable.poland_small
            ),
            BasicCard(
                "Sweden",
                background = R.drawable.sweden_small
            ),
            BasicCard(
                "Italy",
                background = R.drawable.italy_small
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