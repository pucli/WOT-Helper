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
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/abbey.jpg"
            ),
            BasicCard(
                "Berlin",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/berlin.png"
            ),
            BasicCard(
                "Cliff",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/cliff.png"
            ),
            BasicCard(
                "Empire's Border",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/empiresborder.jpg"
            ),
            BasicCard(
                "Ensk",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/ensk.jpg"
            ),
            BasicCard(
                "Fisherman's Bay",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/fishbay.jpg"
            ),
            BasicCard(
                "Fjords",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/fjord.jpg"
            ),
            BasicCard(
                "Highway",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/highway.jpg"
            ),
            BasicCard(
                "Himmelsdorf",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/maps_bg.jpg"
            ),
            BasicCard(
                "Karelia",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/karelia.jpg"
            ),
            BasicCard(
                "Lakeville",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/lakeville.jpg"
            ),
            BasicCard(
                "Live Oaks",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/liveoaks.jpg"
            ),
            BasicCard(
                "Malinovka",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/malinovka.png"
            ),
            BasicCard(
                "Mines",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/mines.jpg"
            ),
            BasicCard(
                "Mountain Pass",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/mountainpass.jpg"
            ),
            BasicCard(
                "Murovanka",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/murovanka.jpg"
            ),
            BasicCard(
                "Overlord",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/overlord.jpg"
            ),
            BasicCard(
                "Paris",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/paris.jpg"
            ),
            BasicCard(
                "Pearl River",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/pearlriver.jpg"
            ),
            BasicCard(
                "Pilsen",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/pilsen.jpg"
            ),
            BasicCard(
                "Prokhorovka",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/prokhorovka.jpg"
            ),
            BasicCard(
                "Province",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/province.jpg"
            ),
            BasicCard(
                "Redshire",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/redshire.jpg"
            ),
            BasicCard(
                "Ruinberg",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/ruineberg.jpg"
            ),
            BasicCard(
                "Serene Coast",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/serenecoast.jpg"
            ),
            BasicCard(
                "Siegfried Line",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/siegfriedline.jpg"
            ),
            BasicCard(
                "Steppes",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/steppes.jpg"
            ),
            BasicCard(
                "Studzianki",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/studzianki.jpg"
            ),
            BasicCard(
                "Tundra",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/tundra.jpg"
            ),
            BasicCard(
                "Westfield",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/westfield.jpg"
            ),
            BasicCard(
                "Widepark",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/widepark.jpg"
            ),
            BasicCard(
                "Erlenberg",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/erlenberg.jpg"
            ),
            BasicCard(
                "Mannerheim Line",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/mannerheimline.png"
            ),
            BasicCard(
                "Glacier",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/glacier.jpg"
            ),
            BasicCard(
                "Airfield",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/airfield.jpg"
            ),
            BasicCard(
                "El Halluf",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/el_halluf.jpg"
            ),
            BasicCard(
                "Ghost Town",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/ghosttown.jpg"
            ),
            BasicCard(
                "Sand River",
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/sand_river.jpg"
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


