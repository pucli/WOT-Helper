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
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/tank_charact.jpg"
            ),
            BasicCard(
                "Maps",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/maps_bg.jpg"
            ),
            BasicCard(
                "Profile",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/poza_t49_login.jpg"
            ),
            BasicCard(
                "Missions",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/comming_soon.jpg"
            ),
            BasicCard(
                "Tank Stats",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/tankstats.jpg"
            ),
            BasicCard(
                "Map Stats",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/mapstats.png"
            ),
            BasicCard(
                "Coming soon",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/cliff.png"
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
        } else if (basicCard.title == "Missions") {
             navigateToMissions()
        } else if (basicCard.title == "Tank Stats") {
            navigateToTankStats()
        } else if (basicCard.title == "Map Stats") {
            navigateToMapStats()
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

        private fun navigateToMissions() {
            val navMissions = HomePageFragmentDirections.actionHomePageFragmentToMissionsFragment()
            findNavController().navigate(navMissions)
    }

        private fun navigateToTankStats() {
            val navNations = HomePageFragmentDirections.actionHomePageFragmentToTankStats()
            findNavController().navigate(navNations)
    }

        private fun navigateToMapStats() {
            val navNations = HomePageFragmentDirections.actionHomePageFragmentToMapStats()
            findNavController().navigate(navNations)
    }

    }
