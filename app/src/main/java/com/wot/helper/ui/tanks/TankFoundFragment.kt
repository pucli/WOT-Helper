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
                background = R.drawable.exemplu_tank            // big_icon
            )
        )
    }

    override fun onCardClick(basicCard: BasicCard) {
            navigateToTankCharPage(title = basicCard.title!!)
//        if(basicCard.title== "Does Not Exist")
//        {
//            navigateToTierFragment()
//        }
    }


    private fun navigateToTankCharPage(title:String?) {
        val navTankCharPage = title?.let { TankFoundFragmentDirections.actionTankFoundFragmentToTankCharacteristicsFragment(title= it) }
        if (navTankCharPage != null) {
            findNavController().navigate(navTankCharPage)
        }
    }

//    private fun navigateToTierFragment(){
//        val navTankTierPage = TankFoundFragmentDirections.actionTankFoundFragmentToTierFragment()
//    }

    private fun ChangeCard(basicCard: BasicCard) {
        val mappings = mapOf(
            Triple("ussr", 10, "lightTank") to Pair("T-100 LT", R.drawable.t100lt),
            Triple("ussr", 10, "mediumTank") to Pair("Obj. 140", R.drawable.obj140),
            Triple("ussr", 10, "heavyTank") to Pair("IS-7", R.drawable.is7),
            Triple("ussr", 10, "AT-SPG") to Pair("Obj. 268/4", R.drawable.ob268v4),
            Triple("ussr", 9, "lightTank") to Pair("T-54 ltwt", R.drawable.t54ltwt),
            Triple("ussr", 9, "mediumTank") to Pair("Obj. 430", R.drawable.obj430),
            Triple("ussr", 9, "heavyTank") to Pair("Obj. 705", R.drawable.obj705),
            Triple("ussr", 9, "AT-SPG") to Pair("Obj. 263", R.drawable.obj263),
            Triple("ussr", 8, "lightTank") to Pair("LTTB", R.drawable.lttb),
            Triple("ussr", 8, "mediumTank") to Pair("T-44", R.drawable.t44),
            Triple("ussr", 8, "heavyTank") to Pair("IS-3", R.drawable.is3),
            Triple("ussr", 8, "AT-SPG") to Pair("ISU-152", R.drawable.isu152),
            Triple("ussr", 7, "lightTank") to Pair("LTG", R.drawable.ltg),
            Triple("ussr", 7, "mediumTank") to Pair("T-43", R.drawable.t43),
            Triple("ussr", 7, "heavyTank") to Pair("IS", R.drawable.`is`),
            Triple("ussr", 7, "AT-SPG") to Pair("SU-152", R.drawable.su152),
            Triple("ussr", 6, "lightTank") to Pair("MT-25", R.drawable.mt25),
            Triple("ussr", 6, "mediumTank") to Pair("T-34-85", R.drawable.t3485),
            Triple("ussr", 6, "heavyTank") to Pair("KV-85", R.drawable.kv85),
            Triple("ussr", 6, "AT-SPG") to Pair("SU-100", R.drawable.su100),
            Triple("ussr", 5, "lightTank") to Pair("A-20", R.drawable.a20),
            Triple("ussr", 5, "mediumTank") to Pair("T-34", R.drawable.t34),
            Triple("ussr", 5, "heavyTank") to Pair("KV-1", R.drawable.kv1),
            Triple("ussr", 5, "AT-SPG") to Pair("SU-85", R.drawable.su85),
            Triple("ussr", 4, "lightTank") to Pair("BT-7", R.drawable.bt7),
            Triple("ussr", 4, "mediumTank") to Pair("T-28", R.drawable.t28),
            Triple("ussr", 4, "AT-SPG") to Pair("SU-76M", R.drawable.su76m),
            Triple("ussr", 3, "lightTank") to Pair("BT-5", R.drawable.bt5),
            Triple("ussr", 2, "lightTank") to Pair("BT-2", R.drawable.bt2),
            Triple("ussr", 1, "lightTank") to Pair("MS-1", R.drawable.ms1),

            Triple("germany", 10, "lightTank") to Pair("Rhm. Pzw.", R.drawable.rhmpzw),
            Triple("germany", 10, "mediumTank") to Pair("Leopard 1", R.drawable.leopard1),
            Triple("germany", 10, "heavyTank") to Pair("Maus", R.drawable.maus),
            Triple("germany", 10, "AT-SPG") to Pair("Jg.Pz.E 100", R.drawable.jgpze100),
            Triple("germany", 9, "lightTank") to Pair("Ru 251", R.drawable.ru251),
            Triple("germany", 9, "mediumTank") to Pair("Leopard PT A", R.drawable.leopardpta),//
            Triple("germany", 9, "heavyTank") to Pair("E 75", R.drawable.e75),//
            Triple("germany", 9, "AT-SPG") to Pair("Jagdtiger", R.drawable.jagdtiger),//
            Triple("germany", 8, "lightTank") to Pair("HWK 12", R.drawable.hwk12),//
            Triple("germany", 8, "mediumTank") to Pair("Panther II", R.drawable.panther2),//
            Triple("germany", 8, "heavyTank") to Pair("Tiger II", R.drawable.tiger2),//
            Triple("germany", 8, "AT-SPG") to Pair("Rhm.-B.WT", R.drawable.rahimwaffle),//
            Triple("germany", 7, "lightTank") to Pair("SP I C", R.drawable.spic),//
            Triple("germany", 7, "mediumTank") to Pair("Panther I", R.drawable.panther),//
            Triple("germany", 7, "heavyTank") to Pair("Tiger I", R.drawable.tiger1),//
            Triple("germany", 7, "AT-SPG") to Pair("St. Emil", R.drawable.stureremil),//
            Triple("germany", 6, "lightTank") to Pair("VK 28.01", R.drawable.vk2801),//
            Triple("germany", 6, "mediumTank") to Pair("VK 30.02 M", R.drawable.vk3002m),//
            Triple("germany", 6, "heavyTank") to Pair("VK 36.01 H", R.drawable.vk3601h),//
            Triple("germany", 6, "AT-SPG") to Pair("Nashorn", R.drawable.nashhorn),//
            Triple("germany", 5, "lightTank") to Pair("Leopard", R.drawable.leopardlight),//
            Triple("germany", 5, "mediumTank") to Pair("Pz. IV H", R.drawable.pz4h),//
            Triple("germany", 5, "AT-SPG") to Pair("Stug III G", R.drawable.stug3g),//
            Triple("germany", 4, "lightTank") to Pair("Luchs", R.drawable.luchs),//
            Triple("germany", 4, "mediumTank") to Pair("Pz. III J", R.drawable.pz3j),//
            Triple("germany", 4, "AT-SPG") to Pair("Hetzer", R.drawable.hetzer),//
            Triple("germany", 3, "lightTank") to Pair("Pz. II G", R.drawable.pz2g),//
            Triple("germany", 2, "lightTank") to Pair("Pz. II", R.drawable.pz2),//
            Triple("germany", 1, "lightTank") to Pair("L. Tr", R.drawable.loltraktor),//

            Triple("usa", 10, "lightTank") to Pair("Sheridan", R.drawable.sheri),//
            Triple("usa", 10, "mediumTank") to Pair("M48 Patton", R.drawable.m48patton),//
            Triple("usa", 10, "heavyTank") to Pair("T110E5", R.drawable.t110e5),//
            Triple("usa", 10, "AT-SPG") to Pair("T110E3", R.drawable.t110e3),//
            Triple("usa", 9, "lightTank") to Pair("T49", R.drawable.t49),//
            Triple("usa", 9, "mediumTank") to Pair("M46 Patton", R.drawable.m46patton),//
            Triple("usa", 9, "heavyTank") to Pair("M103", R.drawable.m103),//
            Triple("usa", 9, "AT-SPG") to Pair("T95", R.drawable.t95),//
            Triple("usa", 8, "lightTank") to Pair("M41 Bulldog", R.drawable.m41bulldog),//
            Triple("usa", 8, "mediumTank") to Pair("Pershing", R.drawable.pershing),//
            Triple("usa", 8, "heavyTank") to Pair("T32", R.drawable.t32),//
            Triple("usa", 8, "AT-SPG") to Pair("T28", R.drawable.t28usa),//
            Triple("usa", 7, "lightTank") to Pair("T71 CMCD", R.drawable.t71cmcd),//
            Triple("usa", 7, "mediumTank") to Pair("T20", R.drawable.t20),//
            Triple("usa", 7, "heavyTank") to Pair("T29", R.drawable.t29),//
            Triple("usa", 7, "AT-SPG") to Pair("T25/2", R.drawable.t252),//
            Triple("usa", 6, "lightTank") to Pair("T37", R.drawable.t37),//
            Triple("usa", 6, "mediumTank") to Pair("M4A3E8 Sherman", R.drawable.m4a3e8sherman),//
            Triple("usa", 6, "heavyTank") to Pair("M6", R.drawable.m6),//
            Triple("usa", 6, "AT-SPG") to Pair("Hellcat", R.drawable.hellcat),//
            Triple("usa", 5, "lightTank") to Pair("Chaffee", R.drawable.chaffee),//
            Triple("usa", 5, "mediumTank") to Pair("M4A1 Sherman", R.drawable.m4sherman),//
            Triple("usa", 5, "heavyTank") to Pair("T1 Heavy", R.drawable.t1heavy),//
            Triple("usa", 5, "AT-SPG") to Pair("Wolverine", R.drawable.wolverine),
            Triple("usa", 4, "lightTank") to Pair("M5 Stuart", R.drawable.m5stuartusa),//
            Triple("usa", 4, "mediumTank") to Pair("T6 Medium", R.drawable.t6medium),//
            Triple("usa", 4, "AT-SPG") to Pair("M8A1", R.drawable.m8a1),//
            Triple("usa", 3, "lightTank") to Pair("M3 Stuart", R.drawable.m3stuart),//
            Triple("usa", 2, "lightTank") to Pair("M2 Light", R.drawable.m2light),//
            Triple("usa", 1, "lightTank") to Pair("T1", R.drawable.t1cunn),//

            Triple("france", 10, "lightTank") to Pair("AMX 13 105", R.drawable.amx13105),//
            Triple("france", 10, "mediumTank") to Pair("B-C 25 t", R.drawable.bc25t),//
            Triple("france", 10, "heavyTank") to Pair("AMX M4 54", R.drawable.amxm454),//
            Triple("france", 10, "AT-SPG") to Pair("Foch B", R.drawable.fochb),//
            Triple("france", 9, "lightTank") to Pair("AMX 13 90", R.drawable.amx1390),//
            Triple("france", 9, "mediumTank") to Pair("B-C 25 t AP", R.drawable.bc25tap),//
            Triple("france", 9, "heavyTank") to Pair("AMX M4 51", R.drawable.amxm451),//
            Triple("france", 9, "AT-SPG") to Pair("Foch", R.drawable.foch),//
            Triple("france", 8, "lightTank") to Pair("B-C 12 t", R.drawable.bc12t),//
            Triple("france", 8, "mediumTank") to Pair("A.P. AMX 30)", R.drawable.amxaltproto),//
            Triple("france", 8, "AT-SPG") to Pair("AMX AC 48", R.drawable.amxac48),//
            Triple("france", 7, "lightTank") to Pair("AMX 13 75", R.drawable.amx1375),//
            Triple("france", 7, "heavyTank") to Pair("AMX M4 45", R.drawable.amxm445),//
            Triple("france", 7, "AT-SPG") to Pair("AMX AC 46", R.drawable.amxac46),//
            Triple("france", 6, "lightTank") to Pair("AMX 12 t", R.drawable.amx12t),//
            Triple("france", 6, "heavyTank") to Pair("ARL 44", R.drawable.arl44),//
            Triple("france", 6, "AT-SPG") to Pair("AMX V 39", R.drawable.amx38),//
            Triple("france", 5, "lightTank") to Pair("AMX ELC bis", R.drawable.amxelc),//
            Triple("france", 5, "heavyTank") to Pair("BDR G1 B1", R.drawable.bdrg1b1),//
            Triple("france", 5, "AT-SPG") to Pair("S35 CA", R.drawable.s35ca),//
            Triple("france", 4, "lightTank") to Pair("AMX 40", R.drawable.amx40duck),//
            Triple("france", 4, "heavyTank") to Pair("B1", R.drawable.b1),//
            Triple("france", 4, "AT-SPG") to Pair("SAu 40", R.drawable.sau40),
            Triple("france", 3, "lightTank") to Pair("AMX 38", R.drawable.amx38),//
            Triple("france", 3, "mediumTank") to Pair("Somua S35", R.drawable.somuas35),//
            Triple("france", 3, "AT-SPG") to Pair("SAu 40", R.drawable.sau40),//
            Triple("france", 2, "lightTank") to Pair("FCM 36", R.drawable.fcm36),//
            Triple("france", 1, "lightTank") to Pair("FT", R.drawable.renaulftft),//

            Triple("great britain", 10, "lightTank") to Pair("Manticore", R.drawable.manti),//
            Triple("great britain", 10, "mediumTank") to Pair("Centurion AX", R.drawable.centurionax),//
            Triple("great britain", 10, "heavyTank") to Pair("Super Conqueror", R.drawable.superconq),
            Triple("great britain", 10, "AT-SPG") to Pair("FV4005", R.drawable.fv4005),//
            Triple("great britain", 9, "lightTank") to Pair("GSOR", R.drawable.gsor),//
            Triple("great britain", 9, "mediumTank") to Pair("Centurion 7/1", R.drawable.centrurion1),//
            Triple("great britain", 9, "heavyTank") to Pair("Conqueror", R.drawable.conqueror),//
            Triple("great britain", 9, "AT-SPG") to Pair("Conway", R.drawable.conway),//
            Triple("great britain", 8, "lightTank") to Pair("LHMTV", R.drawable.lhmtv),//
            Triple("great britain", 8, "mediumTank") to Pair("Centurion 1", R.drawable.centrurion1),//
            Triple("great britain", 8, "heavyTank") to Pair("Caernarvon", R.drawable.caerv),//
            Triple("great britain", 8, "AT-SPG") to Pair("Charioteer", R.drawable.charioteer),//
            Triple("great britain", 7, "lightTank") to Pair("Setter", R.drawable.setter),//
            Triple("great britain", 7, "mediumTank") to Pair("Comet", R.drawable.comet),
            Triple("great britain", 7, "heavyTank") to Pair("Black Prince", R.drawable.blackprince),//
            Triple("great britain", 7, "AT-SPG") to Pair("Challenger", R.drawable.challenger),//
            Triple("great britain", 6, "lightTank") to Pair("Crusader", R.drawable.crusader),//
            Triple("great britain", 6, "mediumTank") to Pair("Cromwell", R.drawable.cromwell),//
            Triple("great britain", 6, "heavyTank") to Pair("Churchill VII", R.drawable.churchill8),
            Triple("great britain", 6, "AT-SPG") to Pair("Achilles", R.drawable.achilles),//
            Triple("great britain", 5, "lightTank") to Pair("Covenanter", R.drawable.covenanter),//
            Triple("great britain", 5, "mediumTank") to Pair("Cavalier", R.drawable.cavalier),//
            Triple("great britain", 5, "heavyTank") to Pair("Churchill I", R.drawable.churchill1),//
            Triple("great britain", 5, "AT-SPG") to Pair("AT 2", R.drawable.at2),//
            Triple("great britain", 4, "lightTank") to Pair("Cruiser IV", R.drawable.cruiser4),//
            Triple("great britain", 4, "mediumTank") to Pair("Matilda", R.drawable.matilda),//
            Triple("great britain", 4, "AT-SPG") to Pair("Valentine AT", R.drawable.valentine),//
            Triple("great britain", 3, "lightTank") to Pair("Cruiser III", R.drawable.cruiser3),//
            Triple("great britain", 2, "lightTank") to Pair("Cruiser II", R.drawable.cruiser2),//
            Triple("great britain", 1, "lightTank") to Pair("Cruiser I", R.drawable.cruiser1),//

            Triple("czechoslovakia", 10, "mediumTank") to Pair("TVP T 50/51", R.drawable.tvp5051),//
            Triple("czechoslovakia", 10, "heavyTank") to Pair("Vz. 55", R.drawable.vz55),//
            Triple("czechoslovakia", 9, "mediumTank") to Pair("Skoda T 50", R.drawable.skodat50),//
            Triple("czechoslovakia", 9, "heavyTank") to Pair("TNH T Vz. 51", R.drawable.tnhvz51),//
            Triple("czechoslovakia", 8, "mediumTank") to Pair("TVP VTU", R.drawable.tvpvtu),//
            Triple("czechoslovakia", 8, "heavyTank") to Pair("TNH 105/1000", R.drawable.tnh1051000),//
            Triple("czechoslovakia", 7, "mediumTank") to Pair("T-34/100", R.drawable.t3485),//
            Triple("czechoslovakia", 7, "heavyTank") to Pair("Vz. 44-1", R.drawable.vz441),//
            Triple("czechoslovakia", 6, "mediumTank") to Pair("Skoda T 25", R.drawable.skodat25),//
            Triple("czechoslovakia", 5, "mediumTank") to Pair("Skoda T 24", R.drawable.skodat24),//
            Triple("czechoslovakia", 4, "mediumTank") to Pair("ST vz. 39", R.drawable.stvz39),//
            Triple("czechoslovakia", 3, "lightTank") to Pair("ST vz. 38", R.drawable.stvz38),//
            Triple("czechoslovakia", 2, "lightTank") to Pair("ST vz. 34", R.drawable.stvz34),//
            Triple("czechoslovakia", 1, "lightTank") to Pair("Kolohousenka", R.drawable.kolohou),//

            Triple("japan", 10, "mediumTank") to Pair("STB-1", R.drawable.stb1),//
            Triple("japan", 10, "heavyTank") to Pair("Type 5 Heavy", R.drawable.type5heavy),//
            Triple("japan", 10, "At-SPG") to Pair("Ho-Ri 3", R.drawable.hori3),//
            Triple("japan", 9, "mediumTank") to Pair("Type 61", R.drawable.type61),//
            Triple("japan", 9, "heavyTank") to Pair("Type 4 Heavy", R.drawable.type4),//
            Triple("japan", 9, "At-SPG") to Pair("Ho-Ri 1", R.drawable.hori1),//
            Triple("japan", 8, "mediumTank") to Pair("STA-1", R.drawable.sta1),//
            Triple("japan", 8, "heavyTank") to Pair("O-Ho", R.drawable.oho),//
            Triple("japan", 8, "At-SPG") to Pair("Ho-Ri 2", R.drawable.hori2),//
            Triple("japan", 7, "mediumTank") to Pair("Chi-Ri", R.drawable.chiri),//
            Triple("japan", 7, "heavyTank") to Pair("O-Ni", R.drawable.oni),//
            Triple("japan", 7, "At-SPG") to Pair("Chi-TO SP", R.drawable.chitospg),//
            Triple("japan", 6, "mediumTank") to Pair("Chi-To", R.drawable.chito),//
            Triple("japan", 6, "heavyTank") to Pair("O-I", R.drawable.oi),//
            Triple("japan", 6, "At-SPG") to Pair("Ji-Ro", R.drawable.jiro),//
            Triple("japan", 5, "mediumTank") to Pair("Chi-Nu", R.drawable.chinu),//
            Triple("japan", 5, "At-SPG") to Pair("Ho-Ni III", R.drawable.honi3),//
            Triple("japan", 4, "mediumTank") to Pair("Chi-He", R.drawable.chihe),//
            Triple("japan", 3, "lightTank") to Pair("Chi-Ha", R.drawable.chiha),//
            Triple("japan", 2, "lightTank") to Pair("Ha-Go", R.drawable.hago),//
            Triple("japan", 1, "lightTank") to Pair("R. Otsu", R.drawable.otsu),//

            Triple("poland", 10, "mediumTank") to Pair("CS-63", R.drawable.cs63),//
            Triple("poland", 10, "heavyTank") to Pair("60TP", R.drawable.tp60),//
            Triple("poland", 9, "mediumTank") to Pair("CS-59", R.drawable.cs53),//
            Triple("poland", 9, "heavyTank") to Pair("50TP", R.drawable.tp50),//
            Triple("poland", 8, "mediumTank") to Pair("CS-53", R.drawable.cs53),//
            Triple("poland", 8, "heavyTank") to Pair("53TP", R.drawable.tp53),//
            Triple("poland", 7, "mediumTank") to Pair("CS-44", R.drawable.cs44),//
            Triple("poland", 7, "heavyTank") to Pair("45TP", R.drawable.tp45),//
            Triple("poland", 6, "mediumTank") to Pair("B.U.G.I", R.drawable.bugi),//
            Triple("poland", 5, "mediumTank") to Pair("25TP", R.drawable.tp25),//
            Triple("poland", 4, "lightTank") to Pair("14TP", R.drawable.tp14),//
            Triple("poland", 3, "lightTank") to Pair("10TP", R.drawable.tp10),//
            Triple("poland", 2, "lightTank") to Pair("7TP", R.drawable.tp7),//
            Triple("poland", 1, "lightTank") to Pair("4TP", R.drawable.tp4),//


            Triple("sweden", 10, "mediumTank") to Pair("UDES 15/16", R.drawable.udes1516),//
            Triple("sweden", 10, "heavyTank") to Pair("Kranvagn", R.drawable.kranvagn),//
            Triple("sweden", 10, "AT-SPG") to Pair("Strv 103B", R.drawable.strv103b),//
            Triple("sweden", 9, "mediumTank") to Pair("UDES 16", R.drawable.udes15),//
            Triple("sweden", 9, "heavyTank") to Pair("Emil II", R.drawable.emil2),//
            Triple("sweden", 9, "AT-SPG") to Pair("Strv 103-0", R.drawable.strv1030),//
            Triple("sweden", 8, "mediumTank") to Pair("UDES 14 5", R.drawable.udes145),//
            Triple("sweden", 8, "heavyTank") to Pair("Emil I", R.drawable.emil1),//
            Triple("sweden", 8, "AT-SPG") to Pair("UDES 03", R.drawable.udes03),//
            Triple("sweden", 7, "mediumTank") to Pair("Leo", R.drawable.leosweden),//
            Triple("sweden", 7, "AT-SPG") to Pair("Ikv 90 B", R.drawable.ikv90b),//
            Triple("sweden", 6, "mediumTank") to Pair("Strv 74", R.drawable.strv74),//
            Triple("sweden", 6, "AT-SPG") to Pair("Ikv 65 II", R.drawable.ikv652),
            Triple("sweden", 5, "mediumTank") to Pair("Strv m/42", R.drawable.strvm42),//
            Triple("sweden", 5, "AT-SPG") to Pair("Ikv 103", R.drawable.ikv103),//
            Triple("sweden", 4, "mediumTank") to Pair("Lago", R.drawable.lago),//
            Triple("sweden", 4, "AT-SPG") to Pair("Sav m/43", R.drawable.savm43),//
            Triple("sweden", 3, "lightTank") to Pair("Strv m/40L", R.drawable.strvm40l),//
            Triple("sweden", 2, "lightTank") to Pair("Strv m/38", R.drawable.strvm38),//
            Triple("sweden", 1, "lightTank") to Pair("Strv fm/21", R.drawable.strvfm21),//

            Triple("italy", 10, "mediumTank") to Pair("Progetto 65", R.drawable.progetto65),//
            Triple("italy", 10, "heavyTank") to Pair("Rinoceronte", R.drawable.rinoceronte),//
            Triple("italy", 10, "AT-SPG") to Pair("Minotauro", R.drawable.minotauro),//
            Triple("italy", 9, "mediumTank") to Pair("Standard B", R.drawable.standardb),//
            Triple("italy", 9, "heavyTank") to Pair("Progetto 66", R.drawable.progetto66),//
            Triple("italy", 9, "AT-SPG") to Pair("CC-1 Mk. 2", R.drawable.cc1mk2),//
            Triple("italy", 8, "mediumTank") to Pair("P.44 Pantera", R.drawable.p44pantera),//
            Triple("italy", 8, "heavyTank") to Pair("Progetto 54", R.drawable.progetto54),//
            Triple("italy", 8, "AT-SPG") to Pair("SMV CC-67", R.drawable.smvcc67),//
            Triple("italy", 7, "mediumTank") to Pair("P.43 ter", R.drawable.p43ter),//
            Triple("italy", 7, "heavyTank") to Pair("Carro. P.88", R.drawable.carrop88),//
            Triple("italy", 7, "AT-SPG") to Pair("SMV CC-56", R.drawable.smvcc56),//
            Triple("italy", 6, "AT-SPG") to Pair("Bassotto", R.drawable.bassotto),//
            Triple("italy", 6, "mediumTank") to Pair("P.43 bis", R.drawable.p43bis),//
            Triple("italy", 5, "AT-SPG") to Pair("Semovente M41", R.drawable.semoventem41),//
            Triple("italy", 5, "mediumTank") to Pair("P.43", R.drawable.p43),//
            Triple("italy", 4, "mediumTank") to Pair("P.26/40", R.drawable.p2640),//
            Triple("italy", 3, "mediumTank") to Pair("M15/42", R.drawable.m1542),//
            Triple("italy", 2, "mediumTank") to Pair("M14/41", R.drawable.m1441),//
            Triple("italy", 1, "lightTank") to Pair("Fiat 3000", R.drawable.fiat3000),//


            Triple("china", 10, "lightTank") to Pair("WZ-132-1", R.drawable.wz1321),//
            Triple("china", 10, "mediumTank") to Pair("121", R.drawable.tank121),//
            Triple("china", 10, "heavyTank") to Pair("BZ-75", R.drawable.bz75),//
            Triple("china", 10, "AT-SPG") to Pair("WZ-113G FT", R.drawable.wz113gft),//
            Triple("china", 9, "lightTank") to Pair("WZ-132A", R.drawable.wz132a),//
            Triple("china", 9, "mediumTank") to Pair("WZ-120", R.drawable.wz120),//
            Triple("china", 9, "heavyTank") to Pair("BZ-68", R.drawable.bz68),//
            Triple("china", 9, "AT-SPG") to Pair("WZ-111G FT", R.drawable.wz111gft),//
            Triple("china", 8, "lightTank") to Pair("WZ-132", R.drawable.wz132),//
            Triple("china", 8, "mediumTank") to Pair("T-34-2", R.drawable.wz132a),//
            Triple("china", 8, "heavyTank") to Pair("BZ-166", R.drawable.bz166),//
            Triple("china", 8, "AT-SPG") to Pair("WZ-111-1 FT", R.drawable.wz1111ft),//
            Triple("china", 7, "lightTank") to Pair("WZ-131", R.drawable.wz131),//
            Triple("china", 7, "mediumTank") to Pair("T-34-1", R.drawable.wz120),//
            Triple("china", 7, "heavyTank") to Pair("BZ-58", R.drawable.bz58),//
            Triple("china", 7, "AT-SPG") to Pair("T-34-2G FT", R.drawable.t342gft),//
            Triple("china", 6, "lightTank") to Pair("59-16", R.drawable.tank5916),//
            Triple("china", 6, "mediumTank") to Pair("Type 58", R.drawable.type58),//
            Triple("china", 6, "AT-SPG") to Pair("WZ-131G FT", R.drawable.wz131gft),//
            Triple("china", 5, "mediumTank") to Pair("Type T-34", R.drawable.typet34),//
            Triple("china", 5, "AT-SPG") to Pair("60G FT", R.drawable.tank5916),//
            Triple("china", 4, "lightTank") to Pair("M5A1 Stuart", R.drawable.m5a1stuart),//
            Triple("china", 4, "AT-SPG") to Pair("SU-76G FT", R.drawable.su76gft),
            Triple("china", 3, "lightTank") to Pair("Chi-Ha", R.drawable.chiha),//
            Triple("china", 2, "lightTank") to Pair("VAE Type B", R.drawable.vaetypeb),//
            Triple("china", 1, "lightTank") to Pair("NC-31", R.drawable.nc31),//
        )

        val key = Triple(nation, tier, type)
        val mapping = mappings[key]

        if (mapping != null) {
            basicCard.title = mapping.first
            basicCard.background = mapping.second
        } else {
            basicCard.title = "Does Not Exist"
            basicCard.background = R.drawable.black_gradient
        }
    }
}
