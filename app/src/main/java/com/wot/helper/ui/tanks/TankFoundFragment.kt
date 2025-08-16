package com.wot.helper.ui.tanks

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.wot.helper.R
import com.wot.helper.databinding.FragmentTankFoundBinding
import com.wot.helper.domain.models.models.BasicCard
import com.wot.helper.ui.adapters.HomePageAdapter
import com.wot.helper.ui.core.BaseFragment
import com.wot.helper.domain.models.repository.RetrofitInstance
import androidx.navigation.fragment.navArgs
import coil.load
import kotlin.properties.Delegates
import com.wot.helper.domain.models.use_case.auth.Response
import android.annotation.SuppressLint
import android.util.Log
import androidx.appcompat.widget.SearchView
import com.wot.helper.ui.home.HomePageFragment
//import kotlinx.android.synthetic.main.fragment_tank_found.*
import java.util.*


private lateinit var nation: String
private var tier: Int = 0
private lateinit var type: String

class TankFoundFragment : BaseFragment<FragmentTankFoundBinding>(FragmentTankFoundBinding::inflate),
    HomePageAdapter.OnCardClickListener {

    private var adapter = HomePageAdapter(this)
    private val args: TankFoundFragmentArgs by navArgs()
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.searchView)


        getNavArgs()
        ChangeCard(maps[0])
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

    private fun getNavArgs() {
        nation = args.nation
        tier = args.tier
        type = args.type
    }

    private fun setAdapter() {
        adapter.submitList(maps)
        binding.recyclerView.adapter = adapter
    }

    companion object {

        private val maps = arrayListOf(
            BasicCard(
                "T-34 mod. 1943",                        //short_name or name
                background = "https://raw.githubusercontent.com/pucli/wotimg/main/exemplu_tank.png"          // big_icon
            )
        )
    }

    override fun onCardClick(basicCard: BasicCard) {
            navigateToTankCharPage(title = basicCard.title!!)
    }


    private fun navigateToTankCharPage(title:String?) {
        val navTankCharPage = title?.let { TankFoundFragmentDirections.actionTankFoundFragmentToTankCharacteristicsFragment(title= it) }
        if (navTankCharPage != null) {
            findNavController().navigate(navTankCharPage)
        }
    }


    private fun ChangeCard(basicCard: BasicCard) {
        val mappings = mapOf(
            Triple("ussr", 10, "lightTank") to Pair("T-100 LT", "https://raw.githubusercontent.com/pucli/wotimg/main/l100lt.png"),
            Triple("ussr", 10, "mediumTank") to Pair("Obj. 140", "https://raw.githubusercontent.com/pucli/wotimg/main/obj140.png"),
            Triple("ussr", 10, "heavyTank") to Pair("IS-7", "https://raw.githubusercontent.com/pucli/wotimg/main/is7.png"),
            Triple("ussr", 10, "AT-SPG") to Pair("Obj. 268/4", "https://raw.githubusercontent.com/pucli/wotimg/main/obj268v4.png"),
            Triple("ussr", 9, "lightTank") to Pair("T-54 ltwt", "https://raw.githubusercontent.com/pucli/wotimg/main/t54ltwt.png"),
            Triple("ussr", 9, "mediumTank") to Pair("Obj. 430", "https://raw.githubusercontent.com/pucli/wotimg/main/obj430.png"),
            Triple("ussr", 9, "heavyTank") to Pair("Obj. 705", "https://raw.githubusercontent.com/pucli/wotimg/main/obj705.png"),
            Triple("ussr", 9, "AT-SPG") to Pair("Obj. 263", "https://raw.githubusercontent.com/pucli/wotimg/main/obj263.png"),
            Triple("ussr", 8, "lightTank") to Pair("LTTB", "https://raw.githubusercontent.com/pucli/wotimg/main/lttb.png"),
            Triple("ussr", 8, "mediumTank") to Pair("T-44", "https://raw.githubusercontent.com/pucli/wotimg/main/t44.png"),
            Triple("ussr", 8, "heavyTank") to Pair("IS-3", "https://raw.githubusercontent.com/pucli/wotimg/main/is3.png"),
            Triple("ussr", 8, "AT-SPG") to Pair("ISU-152", "https://raw.githubusercontent.com/pucli/wotimg/main/isu152.png"),
            Triple("ussr", 7, "lightTank") to Pair("LTG", "https://raw.githubusercontent.com/pucli/wotimg/main/ltg.png"),
            Triple("ussr", 7, "mediumTank") to Pair("T-43", "https://raw.githubusercontent.com/pucli/wotimg/main/t43.png"),
            Triple("ussr", 7, "heavyTank") to Pair("IS", "https://raw.githubusercontent.com/pucli/wotimg/main/is.png"),
            Triple("ussr", 7, "AT-SPG") to Pair("SU-152", "https://raw.githubusercontent.com/pucli/wotimg/main/su152.png"),
            Triple("ussr", 6, "lightTank") to Pair("MT-25", "https://raw.githubusercontent.com/pucli/wotimg/main/mt25.png"),
            Triple("ussr", 6, "mediumTank") to Pair("T-34-85", "https://raw.githubusercontent.com/pucli/wotimg/main/t3485.png"),
            Triple("ussr", 6, "heavyTank") to Pair("KV-85", "https://raw.githubusercontent.com/pucli/wotimg/main/kv85.png"),
            Triple("ussr", 6, "AT-SPG") to Pair("SU-100", "https://raw.githubusercontent.com/pucli/wotimg/main/su100.png"),
            Triple("ussr", 5, "lightTank") to Pair("A-20", "https://raw.githubusercontent.com/pucli/wotimg/main/a20.png"),
            Triple("ussr", 5, "mediumTank") to Pair("T-34", "https://raw.githubusercontent.com/pucli/wotimg/main/t34.png"),
            Triple("ussr", 5, "heavyTank") to Pair("KV-1", "https://raw.githubusercontent.com/pucli/wotimg/main/kv1.png"),
            Triple("ussr", 5, "AT-SPG") to Pair("SU-85", "https://raw.githubusercontent.com/pucli/wotimg/main/su85.png"),
            Triple("ussr", 4, "lightTank") to Pair("BT-7", "https://raw.githubusercontent.com/pucli/wotimg/main/bt7.png"),
            Triple("ussr", 4, "mediumTank") to Pair("T-28", "https://raw.githubusercontent.com/pucli/wotimg/main/t28.png"),
            Triple("ussr", 4, "AT-SPG") to Pair("SU-76M", "https://raw.githubusercontent.com/pucli/wotimg/main/su76m.png"),
            Triple("ussr", 3, "lightTank") to Pair("BT-5", "https://raw.githubusercontent.com/pucli/wotimg/main/bt5.png"),
            Triple("ussr", 2, "lightTank") to Pair("BT-2", "https://raw.githubusercontent.com/pucli/wotimg/main/bt2.png"),
            Triple("ussr", 1, "lightTank") to Pair("MS-1", "https://raw.githubusercontent.com/pucli/wotimg/main/ms1.png"),


            Triple("germany", 10, "lightTank") to Pair("Rhm. Pzw.", "https://raw.githubusercontent.com/pucli/wotimg/main/rhmpzw.png"),
            Triple("germany", 10, "mediumTank") to Pair("Leopard 1", "https://raw.githubusercontent.com/pucli/wotimg/main/leopard1.png"),
            Triple("germany", 10, "heavyTank") to Pair("Maus", "https://raw.githubusercontent.com/pucli/wotimg/main/maus.png"),
            Triple("germany", 10, "AT-SPG") to Pair("Jg.Pz.E 100", "https://raw.githubusercontent.com/pucli/wotimg/main/jgpze100.png"),
            Triple("germany", 9, "lightTank") to Pair("Ru 251", "https://raw.githubusercontent.com/pucli/wotimg/main/ru251.png"),
            Triple("germany", 9, "mediumTank") to Pair("Leopard PT A", "https://raw.githubusercontent.com/pucli/wotimg/main/leopardpta.png"),
            Triple("germany", 9, "heavyTank") to Pair("E 75", "https://raw.githubusercontent.com/pucli/wotimg/main/e75.png"),
            Triple("germany", 9, "AT-SPG") to Pair("Jagdtiger", "https://raw.githubusercontent.com/pucli/wotimg/main/jagdtiger.png"),
            Triple("germany", 8, "lightTank") to Pair("HWK 12", "https://raw.githubusercontent.com/pucli/wotimg/main/hwk12.png"),
            Triple("germany", 8, "mediumTank") to Pair("Panther II", "https://raw.githubusercontent.com/pucli/wotimg/main/panther2.png"),
            Triple("germany", 8, "heavyTank") to Pair("Tiger II", "https://raw.githubusercontent.com/pucli/wotimg/main/tiger2.png"),
            Triple("germany", 8, "AT-SPG") to Pair("Rhm.-B.WT", "https://raw.githubusercontent.com/pucli/wotimg/main/rahimwaffle.png"),
            Triple("germany", 7, "lightTank") to Pair("SP I C", "https://raw.githubusercontent.com/pucli/wotimg/main/spic.png"),
            Triple("germany", 7, "mediumTank") to Pair("Panther I", "https://raw.githubusercontent.com/pucli/wotimg/main/panther.png"),
            Triple("germany", 7, "heavyTank") to Pair("Tiger I", "https://raw.githubusercontent.com/pucli/wotimg/main/tiger1.png"),
            Triple("germany", 7, "AT-SPG") to Pair("St. Emil", "https://raw.githubusercontent.com/pucli/wotimg/main/stureremil.png"),
            Triple("germany", 6, "lightTank") to Pair("VK 28.01", "https://raw.githubusercontent.com/pucli/wotimg/main/vk2801.png"),
            Triple("germany", 6, "mediumTank") to Pair("VK 30.02 M", "https://raw.githubusercontent.com/pucli/wotimg/main/vk3002m.png"),
            Triple("germany", 6, "heavyTank") to Pair("VK 36.01 H", "https://raw.githubusercontent.com/pucli/wotimg/main/vk3601h.png"),
            Triple("germany", 6, "AT-SPG") to Pair("Nashorn", "https://raw.githubusercontent.com/pucli/wotimg/main/nashhorn.png"),
            Triple("germany", 5, "lightTank") to Pair("Leopard", "https://raw.githubusercontent.com/pucli/wotimg/main/leopardlight.png"),
            Triple("germany", 5, "mediumTank") to Pair("Pz. IV H", "https://raw.githubusercontent.com/pucli/wotimg/main/pz4h.png"),
            Triple("germany", 5, "AT-SPG") to Pair("Stug III G", "https://raw.githubusercontent.com/pucli/wotimg/main/stug3g.png"),
            Triple("germany", 4, "lightTank") to Pair("Luchs", "https://raw.githubusercontent.com/pucli/wotimg/main/luchs.png"),
            Triple("germany", 4, "mediumTank") to Pair("Pz. III J", "https://raw.githubusercontent.com/pucli/wotimg/main/pz3j.png"),
            Triple("germany", 4, "AT-SPG") to Pair("Hetzer", "https://raw.githubusercontent.com/pucli/wotimg/main/hetzer.png"),
            Triple("germany", 3, "lightTank") to Pair("Pz. II G", "https://raw.githubusercontent.com/pucli/wotimg/main/pz2g.png"),
            Triple("germany", 2, "lightTank") to Pair("Pz. II", "https://raw.githubusercontent.com/pucli/wotimg/main/pz2.png"),
            Triple("germany", 1, "lightTank") to Pair("L. Tr", "https://raw.githubusercontent.com/pucli/wotimg/main/loltraktor.png"),


            Triple("usa", 10, "lightTank") to Pair("Sheridan", "https://raw.githubusercontent.com/pucli/wotimg/main/sheri.png"),
            Triple("usa", 10, "mediumTank") to Pair("M48 Patton", "https://raw.githubusercontent.com/pucli/wotimg/main/m48patton.png"),
            Triple("usa", 10, "heavyTank") to Pair("T110E5", "https://raw.githubusercontent.com/pucli/wotimg/main/t110e5.png"),
            Triple("usa", 10, "AT-SPG") to Pair("T110E3", "https://raw.githubusercontent.com/pucli/wotimg/main/t110e3.png"),
            Triple("usa", 9, "lightTank") to Pair("T49", "https://raw.githubusercontent.com/pucli/wotimg/main/t49.png"),
            Triple("usa", 9, "mediumTank") to Pair("M46 Patton", "https://raw.githubusercontent.com/pucli/wotimg/main/m46patton.png"),
            Triple("usa", 9, "heavyTank") to Pair("M103", "https://raw.githubusercontent.com/pucli/wotimg/main/m103.png"),
            Triple("usa", 9, "AT-SPG") to Pair("T95", "https://raw.githubusercontent.com/pucli/wotimg/main/t95.png"),
            Triple("usa", 8, "lightTank") to Pair("M41 Bulldog", "https://raw.githubusercontent.com/pucli/wotimg/main/m41bulldog.png"),
            Triple("usa", 8, "mediumTank") to Pair("Pershing", "https://raw.githubusercontent.com/pucli/wotimg/main/pershing.png"),
            Triple("usa", 8, "heavyTank") to Pair("T32", "https://raw.githubusercontent.com/pucli/wotimg/main/t32.png"),
            Triple("usa", 8, "AT-SPG") to Pair("T28", "https://raw.githubusercontent.com/pucli/wotimg/main/t28usa.png"),
            Triple("usa", 7, "lightTank") to Pair("T71 CMCD", "https://raw.githubusercontent.com/pucli/wotimg/main/t71cmcd.png"),
            Triple("usa", 7, "mediumTank") to Pair("T20", "https://raw.githubusercontent.com/pucli/wotimg/main/t20.png"),
            Triple("usa", 7, "heavyTank") to Pair("T29", "https://raw.githubusercontent.com/pucli/wotimg/main/t29.png"),
            Triple("usa", 7, "AT-SPG") to Pair("T25/2", "https://raw.githubusercontent.com/pucli/wotimg/main/t252.png"),
            Triple("usa", 6, "lightTank") to Pair("T37", "https://raw.githubusercontent.com/pucli/wotimg/main/t37.png"),
            Triple("usa", 6, "mediumTank") to Pair("M4A3E8 Sherman", "https://raw.githubusercontent.com/pucli/wotimg/main/m4a3e8sherman.png"),
            Triple("usa", 6, "heavyTank") to Pair("M6", "https://raw.githubusercontent.com/pucli/wotimg/main/m6.png"),
            Triple("usa", 6, "AT-SPG") to Pair("Hellcat", "https://raw.githubusercontent.com/pucli/wotimg/main/hellcat.png"),
            Triple("usa", 5, "lightTank") to Pair("Chaffee", "https://raw.githubusercontent.com/pucli/wotimg/main/chaffee.png"),
            Triple("usa", 5, "mediumTank") to Pair("M4A1 Sherman", "https://raw.githubusercontent.com/pucli/wotimg/main/m4sherman.png"),
            Triple("usa", 5, "heavyTank") to Pair("T1 Heavy", "https://raw.githubusercontent.com/pucli/wotimg/main/t1heavy.png"),
            Triple("usa", 5, "AT-SPG") to Pair("Wolverine", "https://raw.githubusercontent.com/pucli/wotimg/main/wolverine.png"),
            Triple("usa", 4, "lightTank") to Pair("M5 Stuart", "https://raw.githubusercontent.com/pucli/wotimg/main/m5stuartusa.png"),
            Triple("usa", 4, "mediumTank") to Pair("T6 Medium", "https://raw.githubusercontent.com/pucli/wotimg/main/t6medium.png"),
            Triple("usa", 4, "AT-SPG") to Pair("M8A1", "https://raw.githubusercontent.com/pucli/wotimg/main/m8a1.png"),
            Triple("usa", 3, "lightTank") to Pair("M3 Stuart", "https://raw.githubusercontent.com/pucli/wotimg/main/m3stuart.png"),
            Triple("usa", 2, "lightTank") to Pair("M2 Light", "https://raw.githubusercontent.com/pucli/wotimg/main/m2light.png"),
            Triple("usa", 1, "lightTank") to Pair("T1", "https://raw.githubusercontent.com/pucli/wotimg/main/t1cunn.png"),


            Triple("france", 10, "lightTank") to Pair("AMX 13 105", "https://raw.githubusercontent.com/pucli/wotimg/main/amx13105.png"),
            Triple("france", 10, "mediumTank") to Pair("B-C 25 t", "https://raw.githubusercontent.com/pucli/wotimg/main/bc25t.png"),
            Triple("france", 10, "heavyTank") to Pair("AMX M4 54", "https://raw.githubusercontent.com/pucli/wotimg/main/amxm454.png"),
            Triple("france", 10, "AT-SPG") to Pair("Foch B", "https://raw.githubusercontent.com/pucli/wotimg/main/fochb.png"),
            Triple("france", 9, "lightTank") to Pair("AMX 13 90", "https://raw.githubusercontent.com/pucli/wotimg/main/amx1390.png"),
            Triple("france", 9, "mediumTank") to Pair("B-C 25 t AP", "https://raw.githubusercontent.com/pucli/wotimg/main/bc25tap.png"),
            Triple("france", 9, "heavyTank") to Pair("AMX M4 51", "https://raw.githubusercontent.com/pucli/wotimg/main/amxm451.png"),
            Triple("france", 9, "AT-SPG") to Pair("Foch", "https://raw.githubusercontent.com/pucli/wotimg/main/foch.png"),
            Triple("france", 8, "lightTank") to Pair("B-C 12 t", "https://raw.githubusercontent.com/pucli/wotimg/main/bc12t.png"),
            Triple("france", 8, "mediumTank") to Pair("A.P. AMX 30)", "https://raw.githubusercontent.com/pucli/wotimg/main/amxaltproto.png"),
            Triple("france", 8, "AT-SPG") to Pair("AMX AC 48", "https://raw.githubusercontent.com/pucli/wotimg/main/amxac48.png"),
            Triple("france", 7, "lightTank") to Pair("AMX 13 75", "https://raw.githubusercontent.com/pucli/wotimg/main/amx1375.png"),
            Triple("france", 7, "heavyTank") to Pair("AMX M4 45", "https://raw.githubusercontent.com/pucli/wotimg/main/amxm445.png"),
            Triple("france", 7, "AT-SPG") to Pair("AMX AC 46", "https://raw.githubusercontent.com/pucli/wotimg/main/amxac46.png"),
            Triple("france", 6, "lightTank") to Pair("AMX 12 t", "https://raw.githubusercontent.com/pucli/wotimg/main/amx12t.png"),
            Triple("france", 6, "heavyTank") to Pair("ARL 44", "https://raw.githubusercontent.com/pucli/wotimg/main/arl44.png"),
            Triple("france", 6, "AT-SPG") to Pair("AMX V 39", "https://raw.githubusercontent.com/pucli/wotimg/main/amx38.png"),
            Triple("france", 5, "lightTank") to Pair("AMX ELC bis", "https://raw.githubusercontent.com/pucli/wotimg/main/amxelc.png"),
            Triple("france", 5, "heavyTank") to Pair("BDR G1 B1", "https://raw.githubusercontent.com/pucli/wotimg/main/bdrg1b1.png"),
            Triple("france", 5, "AT-SPG") to Pair("S35 CA", "https://raw.githubusercontent.com/pucli/wotimg/main/s35ca.png"),
            Triple("france", 4, "lightTank") to Pair("AMX 40", "https://raw.githubusercontent.com/pucli/wotimg/main/amx40duck.png"),
            Triple("france", 4, "heavyTank") to Pair("B1", "https://raw.githubusercontent.com/pucli/wotimg/main/b1.png"),
            Triple("france", 4, "AT-SPG") to Pair("SAu 40", "https://raw.githubusercontent.com/pucli/wotimg/main/sau40.png"),
            Triple("france", 3, "lightTank") to Pair("AMX 38", "https://raw.githubusercontent.com/pucli/wotimg/main/amx38.png"),
            Triple("france", 3, "mediumTank") to Pair("Somua S35", "https://raw.githubusercontent.com/pucli/wotimg/main/somuas35.png"),
            Triple("france", 3, "AT-SPG") to Pair("SAu 40", "https://raw.githubusercontent.com/pucli/wotimg/main/sau40.png"),
            Triple("france", 2, "lightTank") to Pair("FCM 36", "https://raw.githubusercontent.com/pucli/wotimg/main/fcm36.png"),
            Triple("france", 1, "lightTank") to Pair("FT", "https://raw.githubusercontent.com/pucli/wotimg/main/renaulftft.png"),

            Triple("great britain", 10, "lightTank") to Pair("Manticore", "https://raw.githubusercontent.com/pucli/wotimg/main/manti.png"),
            Triple("great britain", 10, "mediumTank") to Pair("Centurion AX", "https://raw.githubusercontent.com/pucli/wotimg/main/centurionax.png"),
            Triple("great britain", 10, "heavyTank") to Pair("Super Conqueror", "https://raw.githubusercontent.com/pucli/wotimg/main/superconq.png"),
            Triple("great britain", 10, "AT-SPG") to Pair("FV4005", "https://raw.githubusercontent.com/pucli/wotimg/main/fv4005.png"),
            Triple("great britain", 9, "lightTank") to Pair("GSOR", "https://raw.githubusercontent.com/pucli/wotimg/main/gsor.png"),
            Triple("great britain", 9, "mediumTank") to Pair("Centurion 7/1", "https://raw.githubusercontent.com/pucli/wotimg/main/centrurion1.png"),
            Triple("great britain", 9, "heavyTank") to Pair("Conqueror", "https://raw.githubusercontent.com/pucli/wotimg/main/conqueror.png"),
            Triple("great britain", 9, "AT-SPG") to Pair("Conway", "https://raw.githubusercontent.com/pucli/wotimg/main/conway.png"),
            Triple("great britain", 8, "lightTank") to Pair("LHMTV", "https://raw.githubusercontent.com/pucli/wotimg/main/lhmtv.png"),
            Triple("great britain", 8, "mediumTank") to Pair("Centurion 1", "https://raw.githubusercontent.com/pucli/wotimg/main/centrurion1.png"),
            Triple("great britain", 8, "heavyTank") to Pair("Caernarvon", "https://raw.githubusercontent.com/pucli/wotimg/main/caerv.png"),
            Triple("great britain", 8, "AT-SPG") to Pair("Charioteer", "https://raw.githubusercontent.com/pucli/wotimg/main/charioteer.png"),
            Triple("great britain", 7, "lightTank") to Pair("Setter", "https://raw.githubusercontent.com/pucli/wotimg/main/setter.png"),
            Triple("great britain", 7, "mediumTank") to Pair("Comet", "https://raw.githubusercontent.com/pucli/wotimg/main/comet.png"),
            Triple("great britain", 7, "heavyTank") to Pair("Black Prince", "https://raw.githubusercontent.com/pucli/wotimg/main/blackprince.png"),
            Triple("great britain", 7, "AT-SPG") to Pair("Challenger", "https://raw.githubusercontent.com/pucli/wotimg/main/challenger.png"),
            Triple("great britain", 6, "lightTank") to Pair("Crusader", "https://raw.githubusercontent.com/pucli/wotimg/main/crusader.png"),
            Triple("great britain", 6, "mediumTank") to Pair("Cromwell", "https://raw.githubusercontent.com/pucli/wotimg/main/cromwell.png"),
            Triple("great britain", 6, "heavyTank") to Pair("Churchill VII", "https://raw.githubusercontent.com/pucli/wotimg/main/churchill8.png"),
            Triple("great britain", 6, "AT-SPG") to Pair("Achilles", "https://raw.githubusercontent.com/pucli/wotimg/main/achilles.png"),
            Triple("great britain", 5, "lightTank") to Pair("Covenanter", "https://raw.githubusercontent.com/pucli/wotimg/main/covenanter.png"),
            Triple("great britain", 5, "mediumTank") to Pair("Cavalier", "https://raw.githubusercontent.com/pucli/wotimg/main/cavalier.png"),
            Triple("great britain", 5, "heavyTank") to Pair("Churchill I", "https://raw.githubusercontent.com/pucli/wotimg/main/churchill1.png"),
            Triple("great britain", 5, "AT-SPG") to Pair("AT 2", "https://raw.githubusercontent.com/pucli/wotimg/main/at2.png"),
            Triple("great britain", 4, "lightTank") to Pair("Cruiser IV", "https://raw.githubusercontent.com/pucli/wotimg/main/cruiser4.png"),
            Triple("great britain", 4, "mediumTank") to Pair("Matilda", "https://raw.githubusercontent.com/pucli/wotimg/main/matilda.png"),
            Triple("great britain", 4, "AT-SPG") to Pair("Valentine AT", "https://raw.githubusercontent.com/pucli/wotimg/main/valentine.png"),
            Triple("great britain", 3, "lightTank") to Pair("Cruiser III", "https://raw.githubusercontent.com/pucli/wotimg/main/cruiser3.png"),
            Triple("great britain", 2, "lightTank") to Pair("Cruiser II", "https://raw.githubusercontent.com/pucli/wotimg/main/cruiser2.png"),
            Triple("great britain", 1, "lightTank") to Pair("Cruiser I", "https://raw.githubusercontent.com/pucli/wotimg/main/cruiser1.png"),


            // Czechoslovakia
            Triple("czechoslovakia", 10, "mediumTank") to Pair("TVP T 50/51", "https://raw.githubusercontent.com/pucli/wotimg/main/tvp5051.png"),
            Triple("czechoslovakia", 10, "heavyTank") to Pair("Vz. 55", "https://raw.githubusercontent.com/pucli/wotimg/main/vz55.png"),
            Triple("czechoslovakia", 9, "mediumTank") to Pair("Skoda T 50", "https://raw.githubusercontent.com/pucli/wotimg/main/skodat50.png"),
            Triple("czechoslovakia", 9, "heavyTank") to Pair("TNH T Vz. 51", "https://raw.githubusercontent.com/pucli/wotimg/main/tnhvz51.png"),
            Triple("czechoslovakia", 8, "mediumTank") to Pair("TVP VTU", "https://raw.githubusercontent.com/pucli/wotimg/main/tvpvtu.png"),
            Triple("czechoslovakia", 8, "heavyTank") to Pair("TNH 105/1000", "https://raw.githubusercontent.com/pucli/wotimg/main/tnh1051000.png"),
            Triple("czechoslovakia", 7, "mediumTank") to Pair("T-34/100", "https://raw.githubusercontent.com/pucli/wotimg/main/t3485.png"),
            Triple("czechoslovakia", 7, "heavyTank") to Pair("Vz. 44-1", "https://raw.githubusercontent.com/pucli/wotimg/main/vz441.png"),
            Triple("czechoslovakia", 6, "mediumTank") to Pair("Skoda T 25", "https://raw.githubusercontent.com/pucli/wotimg/main/skodat25.png"),
            Triple("czechoslovakia", 5, "mediumTank") to Pair("Skoda T 24", "https://raw.githubusercontent.com/pucli/wotimg/main/skodat24.png"),
            Triple("czechoslovakia", 4, "mediumTank") to Pair("ST vz. 39", "https://raw.githubusercontent.com/pucli/wotimg/main/stvz39.png"),
            Triple("czechoslovakia", 3, "lightTank") to Pair("ST vz. 38", "https://raw.githubusercontent.com/pucli/wotimg/main/stvz38.png"),
            Triple("czechoslovakia", 2, "lightTank") to Pair("ST vz. 34", "https://raw.githubusercontent.com/pucli/wotimg/main/stvz34.png"),
            Triple("czechoslovakia", 1, "lightTank") to Pair("Kolohousenka", "https://raw.githubusercontent.com/pucli/wotimg/main/kolohou.png"),

// Japan
            Triple("japan", 10, "mediumTank") to Pair("STB-1", "https://raw.githubusercontent.com/pucli/wotimg/main/stb1.png"),
            Triple("japan", 10, "heavyTank") to Pair("Type 5 Heavy", "https://raw.githubusercontent.com/pucli/wotimg/main/type5heavy.png"),
            Triple("japan", 10, "At-SPG") to Pair("Ho-Ri 3", "https://raw.githubusercontent.com/pucli/wotimg/main/hori3.png"),
            Triple("japan", 9, "mediumTank") to Pair("Type 61", "https://raw.githubusercontent.com/pucli/wotimg/main/type61.png"),
            Triple("japan", 9, "heavyTank") to Pair("Type 4 Heavy", "https://raw.githubusercontent.com/pucli/wotimg/main/type4.png"),
            Triple("japan", 9, "At-SPG") to Pair("Ho-Ri 1", "https://raw.githubusercontent.com/pucli/wotimg/main/hori1.png"),
            Triple("japan", 8, "mediumTank") to Pair("STA-1", "https://raw.githubusercontent.com/pucli/wotimg/main/sta1.png"),
            Triple("japan", 8, "heavyTank") to Pair("O-Ho", "https://raw.githubusercontent.com/pucli/wotimg/main/oho.png"),
            Triple("japan", 8, "At-SPG") to Pair("Ho-Ri 2", "https://raw.githubusercontent.com/pucli/wotimg/main/hori2.png"),
            Triple("japan", 7, "mediumTank") to Pair("Chi-Ri", "https://raw.githubusercontent.com/pucli/wotimg/main/chiri.png"),
            Triple("japan", 7, "heavyTank") to Pair("O-Ni", "https://raw.githubusercontent.com/pucli/wotimg/main/oni.png"),
            Triple("japan", 7, "At-SPG") to Pair("Chi-TO SP", "https://raw.githubusercontent.com/pucli/wotimg/main/chitospg.png"),
            Triple("japan", 6, "mediumTank") to Pair("Chi-To", "https://raw.githubusercontent.com/pucli/wotimg/main/chito.png"),
            Triple("japan", 6, "heavyTank") to Pair("O-I", "https://raw.githubusercontent.com/pucli/wotimg/main/oi.png"),
            Triple("japan", 6, "At-SPG") to Pair("Ji-Ro", "https://raw.githubusercontent.com/pucli/wotimg/main/jiro.png"),
            Triple("japan", 5, "mediumTank") to Pair("Chi-Nu", "https://raw.githubusercontent.com/pucli/wotimg/main/chinu.png"),
            Triple("japan", 5, "At-SPG") to Pair("Ho-Ni III", "https://raw.githubusercontent.com/pucli/wotimg/main/honi3.png"),
            Triple("japan", 4, "mediumTank") to Pair("Chi-He", "https://raw.githubusercontent.com/pucli/wotimg/main/chihe.png"),
            Triple("japan", 3, "lightTank") to Pair("Chi-Ha", "https://raw.githubusercontent.com/pucli/wotimg/main/chiha.png"),
            Triple("japan", 2, "lightTank") to Pair("Ha-Go", "https://raw.githubusercontent.com/pucli/wotimg/main/hago.png"),
            Triple("japan", 1, "lightTank") to Pair("R. Otsu", "https://raw.githubusercontent.com/pucli/wotimg/main/otsu.png"),

// Poland
            Triple("poland", 10, "mediumTank") to Pair("CS-63", "https://raw.githubusercontent.com/pucli/wotimg/main/cs63.png"),
            Triple("poland", 10, "heavyTank") to Pair("60TP", "https://raw.githubusercontent.com/pucli/wotimg/main/tp60.png"),
            Triple("poland", 9, "mediumTank") to Pair("CS-59", "https://raw.githubusercontent.com/pucli/wotimg/main/cs53.png"),
            Triple("poland", 9, "heavyTank") to Pair("50TP", "https://raw.githubusercontent.com/pucli/wotimg/main/tp50.png"),
            Triple("poland", 8, "mediumTank") to Pair("CS-53", "https://raw.githubusercontent.com/pucli/wotimg/main/cs53.png"),
            Triple("poland", 8, "heavyTank") to Pair("53TP", "https://raw.githubusercontent.com/pucli/wotimg/main/tp53.png"),
            Triple("poland", 7, "mediumTank") to Pair("CS-44", "https://raw.githubusercontent.com/pucli/wotimg/main/cs44.png"),
            Triple("poland", 7, "heavyTank") to Pair("45TP", "https://raw.githubusercontent.com/pucli/wotimg/main/tp45.png"),
            Triple("poland", 6, "mediumTank") to Pair("B.U.G.I", "https://raw.githubusercontent.com/pucli/wotimg/main/bugi.png"),
            Triple("poland", 5, "mediumTank") to Pair("25TP", "https://raw.githubusercontent.com/pucli/wotimg/main/tp25.png"),
            Triple("poland", 4, "lightTank") to Pair("14TP", "https://raw.githubusercontent.com/pucli/wotimg/main/tp14.png"),
            Triple("poland", 3, "lightTank") to Pair("10TP", "https://raw.githubusercontent.com/pucli/wotimg/main/tp10.png"),
            Triple("poland", 2, "lightTank") to Pair("7TP", "https://raw.githubusercontent.com/pucli/wotimg/main/tp7.png"),
            Triple("poland", 1, "lightTank") to Pair("4TP", "https://raw.githubusercontent.com/pucli/wotimg/main/tp4.png"),



            // Sweden
            Triple("sweden", 10, "mediumTank") to Pair("UDES 15/16", "https://raw.githubusercontent.com/pucli/wotimg/main/udes1516.png"),
            Triple("sweden", 10, "heavyTank") to Pair("Kranvagn", "https://raw.githubusercontent.com/pucli/wotimg/main/kranvagn.png"),
            Triple("sweden", 10, "AT-SPG") to Pair("Strv 103B", "https://raw.githubusercontent.com/pucli/wotimg/main/strv103b.png"),
            Triple("sweden", 9, "mediumTank") to Pair("UDES 16", "https://raw.githubusercontent.com/pucli/wotimg/main/udes15.png"),
            Triple("sweden", 9, "heavyTank") to Pair("Emil II", "https://raw.githubusercontent.com/pucli/wotimg/main/emil2.png"),
            Triple("sweden", 9, "AT-SPG") to Pair("Strv 103-0", "https://raw.githubusercontent.com/pucli/wotimg/main/strv1030.png"),
            Triple("sweden", 8, "mediumTank") to Pair("UDES 14 5", "https://raw.githubusercontent.com/pucli/wotimg/main/udes145.png"),
            Triple("sweden", 8, "heavyTank") to Pair("Emil I", "https://raw.githubusercontent.com/pucli/wotimg/main/emil1.png"),
            Triple("sweden", 8, "AT-SPG") to Pair("UDES 03", "https://raw.githubusercontent.com/pucli/wotimg/main/udes03.png"),
            Triple("sweden", 7, "mediumTank") to Pair("Leo", "https://raw.githubusercontent.com/pucli/wotimg/main/leosweden.png"),
            Triple("sweden", 7, "AT-SPG") to Pair("Ikv 90 B", "https://raw.githubusercontent.com/pucli/wotimg/main/ikv90b.png"),
            Triple("sweden", 6, "mediumTank") to Pair("Strv 74", "https://raw.githubusercontent.com/pucli/wotimg/main/strv74.png"),
            Triple("sweden", 6, "AT-SPG") to Pair("Ikv 65 II", "https://raw.githubusercontent.com/pucli/wotimg/main/ikv652.png"),
            Triple("sweden", 5, "mediumTank") to Pair("Strv m/42", "https://raw.githubusercontent.com/pucli/wotimg/main/strvm42.png"),
            Triple("sweden", 5, "AT-SPG") to Pair("Ikv 103", "https://raw.githubusercontent.com/pucli/wotimg/main/ikv103.png"),
            Triple("sweden", 4, "mediumTank") to Pair("Lago", "https://raw.githubusercontent.com/pucli/wotimg/main/lago.png"),
            Triple("sweden", 4, "AT-SPG") to Pair("Sav m/43", "https://raw.githubusercontent.com/pucli/wotimg/main/savm43.png"),
            Triple("sweden", 3, "lightTank") to Pair("Strv m/40L", "https://raw.githubusercontent.com/pucli/wotimg/main/strvm40l.png"),
            Triple("sweden", 2, "lightTank") to Pair("Strv m/38", "https://raw.githubusercontent.com/pucli/wotimg/main/strvm38.png"),
            Triple("sweden", 1, "lightTank") to Pair("Strv fm/21", "https://raw.githubusercontent.com/pucli/wotimg/main/strvfm21.png"),

// Italy
            Triple("italy", 10, "mediumTank") to Pair("Progetto 65", "https://raw.githubusercontent.com/pucli/wotimg/main/progetto65.png"),
            Triple("italy", 10, "heavyTank") to Pair("Rinoceronte", "https://raw.githubusercontent.com/pucli/wotimg/main/rinoceronte.png"),
            Triple("italy", 10, "AT-SPG") to Pair("Minotauro", "https://raw.githubusercontent.com/pucli/wotimg/main/minotauro.png"),
            Triple("italy", 9, "mediumTank") to Pair("Standard B", "https://raw.githubusercontent.com/pucli/wotimg/main/standardb.png"),
            Triple("italy", 9, "heavyTank") to Pair("Progetto 66", "https://raw.githubusercontent.com/pucli/wotimg/main/progetto66.png"),
            Triple("italy", 9, "AT-SPG") to Pair("CC-1 Mk. 2", "https://raw.githubusercontent.com/pucli/wotimg/main/cc1mk2.png"),
            Triple("italy", 8, "mediumTank") to Pair("P.44 Pantera", "https://raw.githubusercontent.com/pucli/wotimg/main/p44pantera.png"),
            Triple("italy", 8, "heavyTank") to Pair("Progetto 54", "https://raw.githubusercontent.com/pucli/wotimg/main/progetto54.png"),
            Triple("italy", 8, "AT-SPG") to Pair("SMV CC-67", "https://raw.githubusercontent.com/pucli/wotimg/main/smvcc67.png"),
            Triple("italy", 7, "mediumTank") to Pair("P.43 ter", "https://raw.githubusercontent.com/pucli/wotimg/main/p43ter.png"),
            Triple("italy", 7, "heavyTank") to Pair("Carro. P.88", "https://raw.githubusercontent.com/pucli/wotimg/main/carrop88.png"),
            Triple("italy", 7, "AT-SPG") to Pair("SMV CC-56", "https://raw.githubusercontent.com/pucli/wotimg/main/smvcc56.png"),
            Triple("italy", 6, "AT-SPG") to Pair("Bassotto", "https://raw.githubusercontent.com/pucli/wotimg/main/bassotto.png"),
            Triple("italy", 6, "mediumTank") to Pair("P.43 bis", "https://raw.githubusercontent.com/pucli/wotimg/main/p43bis.png"),
            Triple("italy", 5, "AT-SPG") to Pair("Semovente M41", "https://raw.githubusercontent.com/pucli/wotimg/main/semoventem41.png"),
            Triple("italy", 5, "mediumTank") to Pair("P.43", "https://raw.githubusercontent.com/pucli/wotimg/main/p43.png"),
            Triple("italy", 4, "mediumTank") to Pair("P.26/40", "https://raw.githubusercontent.com/pucli/wotimg/main/p2640.png"),
            Triple("italy", 3, "mediumTank") to Pair("M15/42", "https://raw.githubusercontent.com/pucli/wotimg/main/m1542.png"),
            Triple("italy", 2, "mediumTank") to Pair("M14/41", "https://raw.githubusercontent.com/pucli/wotimg/main/m1441.png"),
            Triple("italy", 1, "lightTank") to Pair("Fiat 3000", "https://raw.githubusercontent.com/pucli/wotimg/main/fiat3000.png"),

// China
            Triple("china", 10, "lightTank") to Pair("WZ-132-1", "https://raw.githubusercontent.com/pucli/wotimg/main/wz1321.png"),
            Triple("china", 10, "mediumTank") to Pair("121", "https://raw.githubusercontent.com/pucli/wotimg/main/tank121.png"),
            Triple("china", 10, "heavyTank") to Pair("BZ-75", "https://raw.githubusercontent.com/pucli/wotimg/main/bz75.png"),
            Triple("china", 10, "AT-SPG") to Pair("WZ-113G FT", "https://raw.githubusercontent.com/pucli/wotimg/main/wz113gft.png"),
            Triple("china", 9, "lightTank") to Pair("WZ-132A", "https://raw.githubusercontent.com/pucli/wotimg/main/wz132a.png"),
            Triple("china", 9, "mediumTank") to Pair("WZ-120", "https://raw.githubusercontent.com/pucli/wotimg/main/wz120.png"),
            Triple("china", 9, "heavyTank") to Pair("BZ-68", "https://raw.githubusercontent.com/pucli/wotimg/main/bz68.png"),
            Triple("china", 9, "AT-SPG") to Pair("WZ-111G FT", "https://raw.githubusercontent.com/pucli/wotimg/main/wz111gft.png"),
            Triple("china", 8, "lightTank") to Pair("WZ-132", "https://raw.githubusercontent.com/pucli/wotimg/main/wz132.png"),
            Triple("china", 8, "mediumTank") to Pair("T-34-2", "https://raw.githubusercontent.com/pucli/wotimg/main/wz132a.png"),
            Triple("china", 8, "heavyTank") to Pair("BZ-166", "https://raw.githubusercontent.com/pucli/wotimg/main/bz166.png"),
            Triple("china", 8, "AT-SPG") to Pair("WZ-111-1 FT", "https://raw.githubusercontent.com/pucli/wotimg/main/wz1111ft.png"),
            Triple("china", 7, "lightTank") to Pair("WZ-131", "https://raw.githubusercontent.com/pucli/wotimg/main/wz131.png"),
            Triple("china", 7, "mediumTank") to Pair("T-34-1", "https://raw.githubusercontent.com/pucli/wotimg/main/wz120.png"),
            Triple("china", 7, "heavyTank") to Pair("BZ-58", "https://raw.githubusercontent.com/pucli/wotimg/main/bz58.png"),
            Triple("china", 7, "AT-SPG") to Pair("T-34-2G FT", "https://raw.githubusercontent.com/pucli/wotimg/main/t342gft.png"),
            Triple("china", 6, "lightTank") to Pair("59-16", "https://raw.githubusercontent.com/pucli/wotimg/main/tank5916.png"),
            Triple("china", 6, "mediumTank") to Pair("Type 58", "https://raw.githubusercontent.com/pucli/wotimg/main/type58.png"),
            Triple("china", 6, "AT-SPG") to Pair("WZ-131G FT", "https://raw.githubusercontent.com/pucli/wotimg/main/wz131gft.png"),
            Triple("china", 5, "mediumTank") to Pair("Type T-34", "https://raw.githubusercontent.com/pucli/wotimg/main/typet34.png"),
            Triple("china", 5, "AT-SPG") to Pair("60G FT", "https://raw.githubusercontent.com/pucli/wotimg/main/tank5916.png"),
            Triple("china", 4, "lightTank") to Pair("M5A1 Stuart", "https://raw.githubusercontent.com/pucli/wotimg/main/m5a1stuart.png"),
            Triple("china", 4, "AT-SPG") to Pair("SU-76G FT", "https://raw.githubusercontent.com/pucli/wotimg/main/su76gft.png"),
            Triple("china", 3, "lightTank") to Pair("Chi-Ha", "https://raw.githubusercontent.com/pucli/wotimg/main/chiha.png"),
            Triple("china", 2, "lightTank") to Pair("VAE Type B", "https://raw.githubusercontent.com/pucli/wotimg/main/vaetypeb.png"),
            Triple("china", 1, "lightTank") to Pair("NC-31", "https://raw.githubusercontent.com/pucli/wotimg/main/nc31.png")
        )

        val key = Triple(nation, tier, type)
        val mapping = mappings[key]

        if (mapping != null) {
            basicCard.title = mapping.first
            basicCard.background = mapping.second
        } else {
            basicCard.title = "Does Not Exist"
            basicCard.background = "https://raw.githubusercontent.com/pucli/wotimg/main/black_gradient.jpg"
        }
    }
}
