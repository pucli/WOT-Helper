package com.wot.helper.ui.maps

import android.os.Bundle
import android.view.View
import com.wot.helper.R
import com.wot.helper.databinding.FragmentMapsBinding
import com.wot.helper.domain.models.models.BasicCard
import com.wot.helper.ui.adapters.HomePageAdapter
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.wot.helper.ui.core.BaseFragment
import kotlin.collections.ArrayList


class MapsFragment : BaseFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate),
    HomePageAdapter.OnCardClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var adapter = HomePageAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        searchView = view.findViewById(R.id.searchView)

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

    fun filterCards(query: String) {
        val filteredList = if (query.isEmpty()) {
            maps
        } else {
            maps.filter { card -> card.title!!.contains(query, ignoreCase = true) }
        }
        adapter.submitList(filteredList)
    }

    private fun setAdapter() {

        recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        filterCards("") // Show all cards initially
    }

    companion object {

        private val maps = arrayListOf(
            BasicCard(
                "Abbey",
                background = R.drawable.abbey
            ),
            BasicCard(
                "Berlin",
                background = R.drawable.berlin
            ),
            BasicCard(
                "Cliff",
                background = R.drawable.cliff
            ),
            BasicCard(
                "Empire's Border",
                background = R.drawable.empiresborder
            ),
            BasicCard(
                "Ensk",
                background = R.drawable.ensk
            ),
            BasicCard(
                "Fisherman's Bay",
                background = R.drawable.fishbay
            ),
            BasicCard(
                "Fjords",
                background = R.drawable.fjord
            ),
            BasicCard(
                "Highway",
                background = R.drawable.highway
            ),
            BasicCard(
                "Himmelsdorf",
                background = R.drawable.maps_bg
            ),
            BasicCard(
                "Karelia",
                background = R.drawable.karelia
            ),
            BasicCard(
                "Lakeville",
                background = R.drawable.lakeville
            ),
            BasicCard(
                "Live Oaks",
                background = R.drawable.liveoaks
            ),
            BasicCard(
                "Malinovka",
                background = R.drawable.malinovka
            ),
            BasicCard(
                "Mines",
                background = R.drawable.mines
            ),
            BasicCard(
                "Mountain Pass",
                background = R.drawable.mountainpass
            ),
            BasicCard(
                "Murovanka",
                background = R.drawable.murovanka
            ),
            BasicCard(
                "Overlord",
                background = R.drawable.overlord
            ),
            BasicCard(
                "Paris",
                background = R.drawable.paris
            ),
            BasicCard(
                "Pearl River",
                background = R.drawable.pearlriver
            ),
            BasicCard(
                "Pilsen",
                background = R.drawable.pilsen
            ),
            BasicCard(
                "Prokhorovka",
                background = R.drawable.prokhorovka
            ),
            BasicCard(
                "Province",
                background = R.drawable.province
            ),
            BasicCard(
                "Redshire",
                background = R.drawable.redshire
            ),
            BasicCard(
                "Ruinberg",
                background = R.drawable.ruineberg
            ),
            BasicCard(
                "Serene Coast",
                background = R.drawable.serenecoast
            ),
            BasicCard(
                "Siegfried Line",
                background = R.drawable.siegfriedline
            ),
            BasicCard(
                "Steppes",
                background = R.drawable.steppes
            ),
            BasicCard(
                "Studzianki",
                background = R.drawable.studzianki
            ),
            BasicCard(
                "Tundra",
                background = R.drawable.tundra
            ),
            BasicCard(
                "Westfield",
                background = R.drawable.westfield
            ),
            BasicCard(
                "Widepark",
                background = R.drawable.widepark
            ),
            BasicCard(
                "Erlenberg",
                background = R.drawable.erlenberg
            ),
            BasicCard(
                "Mannerheim Line",
                background = R.drawable.mannerheimline
            ),
            BasicCard(
                "Glacier",
                background = R.drawable.glacier
            ),
            BasicCard(
                "Airfield",
                background = R.drawable.airfield
            ),
            BasicCard(
                "El Halluf",
                background = R.drawable.el_halluf
            ),
            BasicCard(
                "Ghost Town",
                background = R.drawable.ghosttown
            ),
            BasicCard(
                "Sand River",
                background = R.drawable.sand_river
            )
        )
    }

    override fun onCardClick(basicCard: BasicCard) {
        if (basicCard.title == "Abbey") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        } else if (basicCard.title == "Berlin") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        } else if (basicCard.title == "Cliff") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Empire's Border") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Ensk") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Fisherman's Bay") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Fjords") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Highway") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Himmelsdorf") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Karelia") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Lakeville") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Live Oaks") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Malinovka") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Mines") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Mountain Pass") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Murovanka") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Overlord") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Paris") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Pearl River") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Pilsen") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Prokhorovka") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Province") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Redshire") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Ruinberg") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Serene Coast") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Siegfried Line") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Steppes") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Studzianki") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Tundra") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Westfield") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Widepark") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Erlenberg") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Mannerheim Line") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Glacier") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Airfield") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "El Halluf") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Ghost Town") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }else if (basicCard.title == "Sand River") {
            navigateToMapCharacteristics(title = basicCard.title!!)
        }
    }

    private fun navigateToMapCharacteristics(title: String) {
        val navMapCharact = MapsFragmentDirections.actionMapsFragmentToMapCharacteristicsFragment(title = title)
        findNavController().navigate(navMapCharact)
    }
}


