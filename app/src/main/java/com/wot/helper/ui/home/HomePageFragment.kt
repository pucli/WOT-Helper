package com.wot.helper.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wot.helper.R
import com.wot.helper.databinding.FragmentHomePageBinding
import com.wot.helper.domain.models.models.BasicCard
import com.wot.helper.ui.adapters.HomePageAdapter
import androidx.core.view.isVisible
import androidx.activity.OnBackPressedCallback
import javax.inject.Inject
import com.wot.helper.common.Constants
import java.util.*
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.wot.helper.ui.core.BaseFragment
import com.wot.helper.ui.core.MainActivity


class HomePageFragment : BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate),
    HomePageAdapter.OnCardClickListener {



    @Inject
    lateinit var applicationContext: Context

    private var backPressedTime: Long = 0L
    private lateinit var backPressedToast: Toast
    private var adapter = HomePageAdapter(this)
    private lateinit var searchView: SearchView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.searchView)

        setAdapter()
        doubleTapToExit()

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
            maps // If query is empty, show all cards
        } else {
            val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
            maps.filter { basicCard ->
                basicCard.title!!.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)
            }
        }
        adapter.submitList(filteredList)
    }

    private fun doubleTapToExit() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressedTime + Constants.EXIT_TIME > System.currentTimeMillis()) {
                        backPressedToast.cancel()
                        requireActivity().finish()
                        return
                    } else {
                        backPressedToast =
                            Toast.makeText(activity, "Press back again to exit", Toast.LENGTH_SHORT)
                        backPressedToast.show()
                    }
                    backPressedTime = System.currentTimeMillis()
                }
            })
    }

    private fun setAdapter() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        filterCards("")
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
                "Tank Characteristics",
                background = R.drawable.tank_charact
            ),
            BasicCard(
                "Maps",
                background = R.drawable.maps_bg
            ),
            BasicCard(
                "Profile",
                background = R.drawable.poza_t49_login
            ),
            BasicCard(
                "Comming Soon",
                background = R.drawable.comming_soon
            )
        )
    }
    override fun onCardClick(basicCard: BasicCard) {
        if (basicCard.title == "Tank Characteristics") {
            navigateToNations()
        } else if (basicCard.title == "Maps") {
            navigateToMaps()
        } else if (basicCard.title == "Profile") {
            navigateToProfile()
        }
    }

    private fun navigateToNations() {
        val navNations = HomePageFragmentDirections.actionHomePageFragmentToNatiuneFragment()
        findNavController().navigate(navNations)
    }

    private fun navigateToMaps() {
        val navMaps = HomePageFragmentDirections.actionHomePageFragmentToMapsFragment()
        findNavController().navigate(navMaps)
    }

    private fun navigateToProfile() {
        val navProfile = HomePageFragmentDirections.actionHomePageFragmentToProfilFragment()
        findNavController().navigate(navProfile)
    }
}


