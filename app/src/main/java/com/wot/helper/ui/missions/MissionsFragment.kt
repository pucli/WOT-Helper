package com.wot.helper.ui.missions

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.wot.helper.R
import com.wot.helper.databinding.FragmentMissionsPageBinding
import com.wot.helper.domain.models.models.BasicCard
import com.wot.helper.ui.adapters.HomePageAdapter
import com.wot.helper.ui.core.BaseFragment
import com.wot.helper.ui.core.MainActivity
import javax.inject.Inject
import java.util.*

class MissionsFragment : BaseFragment<FragmentMissionsPageBinding>(FragmentMissionsPageBinding::inflate),
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
            BasicCard(
                "STUG IV",
                background = R.drawable.stug4
            ),
            BasicCard(
                "T28 Concept",
                background = R.drawable.t28usa
            ),
            BasicCard(
                "T 55A",
                background = R.drawable.t55a
            ),
            BasicCard(
                "Object 260",
                background = R.drawable.obj260
            ),
            BasicCard(
                "Excalibur",
                background = R.drawable.excalibur
            ),
            BasicCard(
                "Chimera",
                background = R.drawable.chimera
            ),
            BasicCard(
                "Object 279e",
                background = R.drawable.wot279
            )
        )
    }

    override fun onCardClick(basicCard: BasicCard) {
        navigateToMissionsCharacteristics(basicCard.title!!)
    }

    private fun navigateToMissionsCharacteristics(title: String) {
        val navMissionsCharacteristics = MissionsFragmentDirections.actionMissionsFragmentToMissionsCharacteristicsFragment(title = title)
        findNavController().navigate(navMissionsCharacteristics)
    }
}
