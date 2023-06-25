package com.wot.helper.ui.maps

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.wot.helper.R
import android.text.method.ScrollingMovementMethod
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import coil.load
import com.wot.helper.databinding.FragmentMapCharacteristicsBinding
import com.wot.helper.ui.core.MainActivity
import com.wot.helper.ui.core.BaseFragment
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.wot.helper.common.Constants
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.android.synthetic.main.fragment_map_characteristics.view.*
import kotlinx.android.synthetic.main.fragment_map_characteristics.*
import android.widget.Toast;
import androidx.core.graphics.drawable.toBitmap
import kotlinx.serialization.json.JsonNull.content
import java.io.ByteArrayOutputStream;
import java.util.ArrayList




class MapCharacteristicsFragment : BaseFragment<FragmentMapCharacteristicsBinding>(FragmentMapCharacteristicsBinding::inflate) {

    private lateinit var tutorial: ArrayList<ImageView>
    private lateinit var textview: TextView
    private lateinit var imageview: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var imageView3: ImageView
    private lateinit var MapInfoDescriptionText: TextView
    private lateinit var btnShare: Button
    private lateinit var title: String
    private val args: MapCharacteristicsFragmentArgs by navArgs()


    private val applicationContext: Context by lazy {
        requireContext().applicationContext
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNavArgs()
        changeDescription()

        tutorial = arrayListOf(binding.imageView2, binding.imageView3)

        textview = binding.MapInfoDescriptionText
        textview.movementMethod = ScrollingMovementMethod()

        val image2 = binding.imageView2
        val image3 = binding.imageView3

        binding.btnShare.setOnClickListener {
            val mBitmap2 = image2.drawable?.toBitmap()
            val mBitmap3 = image3.drawable?.toBitmap()

            if (mBitmap2 != null && mBitmap3 != null) {
                val path2 = MediaStore.Images.Media.insertImage(
                    applicationContext.contentResolver,
                    mBitmap2,
                    "Check out more on WoT Helper!",
                    null
                )
                val path3 = MediaStore.Images.Media.insertImage(
                    applicationContext.contentResolver,
                    mBitmap3,
                    "Check out more on WoT Helper!",
                    null
                )
                val uri2 = Uri.parse(path2)
                val uri3 = Uri.parse(path3)
                val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
                shareIntent.type = "image/*"
                shareIntent.putExtra(Intent.EXTRA_TEXT, binding.MapInfoDescriptionText.text.toString() +
                        "\n" + "\n" + "Check out more details on Wot Helper!")
                shareIntent.putParcelableArrayListExtra(
                    Intent.EXTRA_STREAM,
                    arrayListOf(uri2, uri3)
                )

                startActivity(Intent.createChooser(shareIntent, "Share image"))
            }
        }

    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavBar()
    }


    private fun getNavArgs() {
        title = args.title
    }

    private fun setUpBottomNavBar() {
        val bottomNav = (requireActivity() as MainActivity).bottomNav
        bottomNav.apply {
            if (!isVisible) {
                isVisible = true
            }
        }
    }

    private fun changeDescription(){

        if( title == "Abbey" )
        {
            binding.imageView2.load(R.drawable.abbey2)
            binding.imageView3.load(R.drawable.abbey1)
            binding.textMapName.text = "Abbey"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoDescriptionText.text = "A large abbey and town fill the center of the map with three roads allowing passage from the north and south, and includes an elevated firing position in the abbeys courtyard at the highest point on the hill."
            binding.MapInfoStrategy.text = "Map Strategy"
            binding.MapInfoStrategyTextLight.text = "In the middle to give support for East and West flank."
            binding.MapInfoStrategyTextMedium.text = "Try getting the East Flank with Support of Tank Destroyers."
            binding.MapInfoStrategyTextHeavy.text = "Go in the West, where there are a lot of oportunities to sidescrap and stay hulldown."
            binding.MapInfoStrategyTextTD.text = "Give support on the East flank or stay near the base in the bushes to snipe from the back."

        }
        else if(title == "Berlin")
        {
            binding.imageView2.load(R.drawable.berlin2)
            binding.imageView3.load(R.drawable.berlin1)
            binding.textMapName.text = "Berlin"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Map situated in the capital of Germany. On the field you can see a lot of historical landscapes from the WWII. On the north side, there are a lot of builigs for the heavy tanks, in the middle good oportunities for spotting and dealing damage and in the South, balconies and bushes for snipers."
            binding.MapInfoStrategyTextLight.text = "Take a bush in the middle and wait for mistakes or rush the bunker for dealing damage to the crossing heavy tanks."
            binding.MapInfoStrategyTextMedium.text = "In the South flank you can play near the ruins or if you are a good sniper, take a bush and wait for action."
            binding.MapInfoStrategyTextHeavy.text = "With heavy tanks, go in the North and play baloconies or sidescrap near the corners."
            binding.MapInfoStrategyTextTD.text = "Not many oportunities, just in the South where are bushes for good cover. "
        }
        else if(title == "Cliff")
        {
            binding.imageView2.load(R.drawable.cliff2)
            binding.imageView3.load(R.drawable.cliff1)
            binding.textMapName.text = "Cliff"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Starting in the north and south, both teams are connected through a valley on the extreme west of the map. Large boulders offer cover south of the valley, and a small town provides cover to the north. The eastern portion of the map is elevated, with two large jutting hills providing cover from enemy fire. An overlooking position near the center of the map allows tanks to fire into the valley from above. "
            binding.MapInfoStrategyTextLight.text = "Rush on top of the hill or donut, from there you have good oportunities for damage and spotting."
            binding.MapInfoStrategyTextMedium.text = "Good flanking oportunities on the East front, also you can rush donut if you have good mobiity."
            binding.MapInfoStrategyTextHeavy.text = "Hard map for heavy tanks, you can play in the middle or East flank."
            binding.MapInfoStrategyTextTD.text = "Good positions on the East and in the balcony for dealing lots of damage."
        }
        else if(title == "Empire's Border")
        {
            binding.imageView2.load(R.drawable.empires_border2)
            binding.imageView3.load(R.drawable.empiresborder1)
            binding.textMapName.text = "Empire's Border"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Empire’s Border will become the first Asia-set locale to join the game since the release of Update 1.0. Featuring what many consider the 8th Wonder of the World, the map is sliced into two halves by the Great Wall of China, commanding the landscape from higher ground. The whole locale is split into several altitudes, each offering tactical versatility of its own."
            binding.MapInfoStrategyTextLight.text = "Very had map for light tanks, low oportunities. Yo can rush on top of the hill in the middle of the map from to different sides."
            binding.MapInfoStrategyTextMedium.text = "You can rush on top of the middle hill with light tanks or play in the north."
            binding.MapInfoStrategyTextHeavy.text = "You have only 1 or 2 spots to play with the heavy tanks. Go in the south of the map where you have hull down positions and capabilities for good depression tanks."
            binding.MapInfoStrategyTextTD.text = "Very low oportunities, only 2 possitions: balcony in the north-east flank and balcony in the West."
        }
        else if(title == "Ensk")
        {
            binding.imageView2.load(R.drawable.ensk2)
            binding.imageView3.load(R.drawable.ensk1)
            binding.textMapName.text = "Ensk"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "600 x 600"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "A mixture of city outskirts and suburban areas with railroad tracks spanning and dividing this map. Plan your tactics to take advantage of defensive choke points and cover open areas with your vehicles. Artillery is great for deterring enemy breakthroughs, but remains almost defenseless when exposed to enemy vehicles attacking from cover."
            binding.MapInfoStrategyTextLight.text = "One of the worst maps for light tanks, no oportunities for spotting."
            binding.MapInfoStrategyTextMedium.text = "With a medium tank, you can play anywhere. If you have good turret armor and gun depression, you can play on the field."
            binding.MapInfoStrategyTextHeavy.text = "Dream map for heavy tanks. Play in the city or near the railway. "
            binding.MapInfoStrategyTextTD.text = "Only 1 position good for snipers: balcony in A-line or maybe 1-2 bushes on the field"
        }
        else if(title == "Fisherman's Bay")
        {
            binding.imageView2.load(R.drawable.fishermanbay2)
            binding.imageView3.load(R.drawable.fishermanbay1)
            binding.textMapName.text = "Fisherman's Bay"
            binding.MapInfoTierText.text = "6 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "The map is a combination of open spaces and irregular terrain. Meandering narrow streets of the seaport on the flank can be surprisingly advantageous. Sloping hills covered with bushes offer excellent ambush opportunities."
            binding.MapInfoStrategyTextLight.text = "Nice map for light tanks, they can rush middle or go in the west for good oportunitites and bushes to camp and spot."
            binding.MapInfoStrategyTextMedium.text = "with a medium tank, you can play anywhere. East if you have good armor and also you can rush West valley to spot tank destroyers."
            binding.MapInfoStrategyTextHeavy.text = "The East flank of the map is designed for the haeavies. Good cover and oprtunities for sidescraping."
            binding.MapInfoStrategyTextTD.text = "With a triangle, you can camp near the redline in the top right of the map. From there you can defend the base, deal damage in the middle or the valley."
        }
        else if(title == "Fjords")
        {
            binding.imageView2.load(R.drawable.fjords2)
            binding.imageView3.load(R.drawable.fjords1)
            binding.textMapName.text = "Fjords"
            binding.MapInfoTierText.text = "7 - 10"
            binding.MapInfoSizeText.text = "850 x 850"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Huge mountains and narrow valleys offer great advantages in a battle. You can engage in a fire duel across the gulf or enter a close quarter battle at the nearest port town or flank and capture enemy base."
            binding.MapInfoStrategyTextLight.text = "From the West, get in the line of bushes near the windmill to spot on top the hill. From the East, get in the bushes near the main mountain to spot near the windmill."
            binding.MapInfoStrategyTextMedium.text = "Generally, with a medium tank, you can play for the North part of the map or rush the dip in the middle."
            binding.MapInfoStrategyTextHeavy.text = "Hull down positions in the South of the map near the port."
            binding.MapInfoStrategyTextTD.text = "Plenty of sniper positions in the north part of the map."
        }
        else if(title == "Highway")
        {
            binding.imageView2.load(R.drawable.highway2)
            binding.imageView3.load(R.drawable.highway1)
            binding.textMapName.text = "Highway"
            binding.MapInfoTierText.text = "4 - 10 "
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Highway is a bit unique in the fact that it can be quite easy to get to the other teams base. The map is very wide open, except toward the SE, where there is an industrial area. "
            binding.MapInfoStrategyTextLight.text = "Rush middle near the bridge and north for good spotting oportunities"
            binding.MapInfoStrategyTextMedium.text = "You can play where you want on this map, but it is recomended to take the North part where you can trade and engage."
            binding.MapInfoStrategyTextHeavy.text = "Play in the city for good oportunities. There are plenty of places to hide or engage."
            binding.MapInfoStrategyTextTD.text = "Bushes near the base from each side"
        }
        else if(title == "Himmelsdorf")
        {
            binding.imageView2.load(R.drawable.himmelsdorf2)
            binding.imageView3.load(R.drawable.himmelsdorf1)
            binding.textMapName.text = "Himmelsdorf"
            binding.MapInfoTierText.text = "3 - 10"
            binding.MapInfoSizeText.text = "700 x 700"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Translated as sky village from German, this labyrinth of streets and squares are a large hindrance for artillery and tank destroyers, but ideal for speedy breakthrough and bypass by medium and light vehicles. With the castle dominating the area, access roads running by the hill and along the railway lines to help you out of the impasse."
            binding.MapInfoStrategyTextLight.text = "Very bad map for the light tank. Being a city map, there are no oportunities to spot."
            binding.MapInfoStrategyTextMedium.text = "Very good map for mediums. If you are very fast, take the castle area. Instead, play near the buildings or train."
            binding.MapInfoStrategyTextHeavy.text = "Very good map for heavies, tall buildings offer a good cover. The main battle is near the central park and on the 'banana'"
            binding.MapInfoStrategyTextTD.text = "Good map only if you have good armor because it can be played as a heavy tank."
        }
        else if(title == "Karelia")
        {
            binding.imageView2.load(R.drawable.karelia2)
            binding.imageView3.load(R.drawable.karelia1)
            binding.textMapName.text = "Karelia"
            binding.MapInfoTierText.text = "3 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Swamps, rocks, and cliffs determine major avenues of approach on this map. This map, with sparse protection and no buildings, gives the combat advantage to artillery. Concentrate your attacks along the path of your main advance, while conducting delaying and defensive actions along the rest of your front lines."
            binding.MapInfoStrategyTextLight.text = "Beautiful map for light tanks, you can play on the whole map and outspot enemies crossing. Main place for it is the middle swamp."
            binding.MapInfoStrategyTextMedium.text = "Good map for medium tanks, you can rush the hill or play in the north if you have gun depression."
            binding.MapInfoStrategyTextHeavy.text = "Not a good map for heavies, the main position is in the South-East flank where there are some spots for hulldown."
            binding.MapInfoStrategyTextTD.text = "Decent map for snipers, you can guard the base or play from the bushes in the South-West or North."
        }
        else if(title == "Lakeville")
        {
            binding.imageView2.load(R.drawable.lakeville2)
            binding.imageView3.load(R.drawable.lakeville1)
            binding.textMapName.text = "Lakeville"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "800 x 800"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "An isolated mountain valley on one flank and city blocks on the other allow you to thrust close to the enemy. A large lake in the middle gives long-range weapons a clear field of fire."
            binding.MapInfoStrategyTextLight.text = "Very limited for these tanks, there are some bushes near the lake or you can push the main path in the middle."
            binding.MapInfoStrategyTextMedium.text = "Mediums can play in the valley if they have good gun depression or wait for enemies to make mistakes in the city, sniping them."
            binding.MapInfoStrategyTextHeavy.text = "Two main paths for the heavy tanks. The first is in the city and the second is in the valley if they have amazing gun depression."
            binding.MapInfoStrategyTextTD.text = "Red line snipers can camp in the bushes in the South or North waiting for enemies to make mistakes."
        }
        else if(title == "Live Oaks")
        {
            binding.imageView2.load(R.drawable.liveoaks2)
            binding.imageView3.load(R.drawable.liveoaks1)
            binding.textMapName.text = "Live Oaks"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "In the center of the map there is a large bog lake. A railway bridge and a fording side on the one flank allow to defend the area using scarce forces, and to concentrate main forces on the other flank. The teams start in the NE and the SW corners of this map."
            binding.MapInfoStrategyTextLight.text = "Scouts will have a hard time deciding what to do on this map. You may drown if you go straight through the swamp, so their best bet is to hug the swamps edge just enough so that they can still zip around."
            binding.MapInfoStrategyTextMedium.text = "Mediums and heavies will love the N/NW where the city is. This is another place where the peek-a-boo tactic will work wonders."
            binding.MapInfoStrategyTextHeavy.text = "Sniping near the swamp to other side, city or valley."
            binding.MapInfoStrategyTextTD.text = "City or valley if they have perfect gun depression."
        }
        else if(title == "Malinovka")
        {
            binding.imageView2.load(R.drawable.malinovka2)
            binding.imageView3.load(R.drawable.malinovka1)
            binding.textMapName.text = "Malinovka (Campinovka)"
            binding.MapInfoTierText.text = "1 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Initial staging areas are separated by a wide open field, ideal for artillery and defensive operations. Make use of flanking maneuvers and natural defenses like woods, hills, and farmhouses, as these can be decisive. Another option is a well-coordinated high-speed combined arms attack across the field which could bring victory, but at the risk of a bloody loss. Colloquially also known as \"Campinovka\" for the tendency of most players to just sit behind cover on their side of the starting field and snipe each other."
            binding.MapInfoStrategyTextLight.text = "Top 3 maps for light tanks. There is 1 bush near spawn on each side for initial spotting, after that they can roam the map and outspot enemies."
            binding.MapInfoStrategyTextMedium.text = "Good map for mediums, they can hide in the forest and wait for enemies to push the hill or get outspotted."
            binding.MapInfoStrategyTextHeavy.text = "Worst top 3 maps for heavies. No cover, the single way to play this map is on top of the hill or maybe play agressive in the swamp near the enemies to permaspot them."
            binding.MapInfoStrategyTextTD.text = "Amazing map for triangles, they can hide and snipe from the back."
        }
        else if(title == "Mines")
        {
            binding.imageView2.load(R.drawable.mines2)
            binding.imageView3.load(R.drawable.mines1)
            binding.textMapName.text = "Mines"
            binding.MapInfoTierText.text = "1 - 10"
            binding.MapInfoSizeText.text = "800 x 800"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "A flat hilltop in the center of this map provides an excellent firing position, but the short drive from either starting position makes climbing the hill a dangerous endeavor. Nestled in the canyon northeast of the hill is the small village of Pagorki; offering a tempting route with plenty of cover, but one that is exposed if the enemy controls the hill. West of the hill, surrounded by shallow water, is a small island which offers good cover while providing a large fire zone."
            binding.MapInfoStrategyTextLight.text = "1, maybe 2 options for this type of vehicle: Rush the hill or try spot near the island."
            binding.MapInfoStrategyTextMedium.text = "Play near the insula and try to crossfire enemies in the middle"
            binding.MapInfoStrategyTextHeavy.text = "Play hulldown in the middle or near the city where is plenty of cover"
            binding.MapInfoStrategyTextTD.text = "Not the best map, but it works. Play in the bushes near the redline in the North or South."
        }
        else if(title == "Mountain Pass")
        {
            binding.imageView2.load(R.drawable.mountainpass2)
            binding.imageView3.load(R.drawable.mountainpass1)
            binding.textMapName.text = "Mountain Pass"
            binding.MapInfoTierText.text = "5 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "A large map with many turns and rough ground. The bridge near the center features natural choke points on both sides, and provides a good sniping position against opponents in the north passes if held. "
            binding.MapInfoStrategyTextLight.text = "Horrible map for light tanks, no oportunities to spot, just to play aggressive in the valley."
            binding.MapInfoStrategyTextMedium.text = "Decent map for mediums, they can help push the South front."
            binding.MapInfoStrategyTextHeavy.text = "Decent map for heavies, play in the South or in the Ice Valley (North)."
            binding.MapInfoStrategyTextTD.text = "A few positions, on top of the hill near each spawn or near the valley balcony."
        }
        else if(title == "Murovanka")
        {
            binding.imageView2.load(R.drawable.murovanka2)
            binding.imageView3.load(R.drawable.murovanka1)
            binding.textMapName.text = "Murovanka"
            binding.MapInfoTierText.text = "5 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "A small town dominates the center of the map, with a large forest providing concealment in the east and high ground to the west. The forest provides effective concealment but lacks cover from fire; whereas the town has a plentiful cover, but lacks concealment."
            binding.MapInfoStrategyTextLight.text = "Rush the middle or roam near the city in the West flank."
            binding.MapInfoStrategyTextMedium.text = "If you have very good gun depression, play in the forest, if you have good armor, help push in the West."
            binding.MapInfoStrategyTextHeavy.text = "One choke point on the map, in West."
            binding.MapInfoStrategyTextTD.text = "Snipe from the back, near the redline. You can play in any flank."
        }
        else if(title == "Overlord")
        {
            binding.imageView2.load(R.drawable.overlord2)
            binding.imageView3.load(R.drawable.overlord1)
            binding.textMapName.text = "Overlord"
            binding.MapInfoTierText.text = "5 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "A relatively flat beachhead leads up to fields surrounded by hedgerows. Teams start up by the hedgerows, so the beach is only a corridor for flanking."
            binding.MapInfoStrategyTextLight.text = "Play near the middle, there are a lot of bushes to outspot enemies if they make mistakes."
            binding.MapInfoStrategyTextMedium.text = "Play on the field, there are posibilities to trade or snipe."
            binding.MapInfoStrategyTextHeavy.text = "2 playstyles: On the beach where you can flank enemies, or near the rocks in the middle where you can sidescrape."
            binding.MapInfoStrategyTextTD.text = "Snipe from the back bushes."
        }
        else if(title == "Paris")
        {
            binding.imageView2.load(R.drawable.paris2)
            binding.imageView3.load(R.drawable.paris1)
            binding.textMapName.text = "Paris"
            binding.MapInfoTierText.text = "8 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "In sight of the Eiffel Tower, the avenues of the City of Lights converge on the Place du Trocadero. It is here, in its heart, that the fate of Paris will be decided."
            binding.MapInfoStrategyTextLight.text = "Bad map for light, only one oportunity to spot, that being in the bushes from the North."
            binding.MapInfoStrategyTextMedium.text = "Help light tank in the North or play near heavies if you have the oportunity."
            binding.MapInfoStrategyTextHeavy.text = "The focus of attention in the map is at the extreme \"south\", under and around the bridge; this is where the heavies want to be, fighting it out in close quarters in the pit."
            binding.MapInfoStrategyTextTD.text = "In the bushes near the light tank, or helping the heavies in the south."
        }
        else if(title == "Pearl River")
        {
            binding.imageView2.load(R.drawable.pearlriver2)
            binding.imageView3.load(R.drawable.pearlriver1)
            binding.textMapName.text = "Pearl River"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Rugged terrain is favorable for various battle tactics; ambushes, unexpected flanking maneuvers, and close encounters. Moving along the riverbed allows you to swiftly approach the enemy base and get into action. Go though the middle will let you the possibility to flank the north and south. However, do not leave your rear undefended! Many routes can be taken across the map, situational awareness and communication is a must!"
            binding.MapInfoStrategyTextLight.text = "Rush in the middle or play in the East where you can hide yourself in the bushes or fallen trees."
            binding.MapInfoStrategyTextMedium.text = "You can play with the mediums on all the map, but especially in the East."
            binding.MapInfoStrategyTextHeavy.text = "Play in the Right side of the map or in the middle corridors where are oportunities for hulldown."
            binding.MapInfoStrategyTextTD.text = "Snipe from the back bushes or help pushing if you have good armor."
        }
        else if(title == "Pilsen")
        {
            binding.imageView2.load(R.drawable.pilsen2)
            binding.imageView3.load(R.drawable.pilsen1)
            binding.textMapName.text = "Pilsen"
            binding.MapInfoTierText.text = "6 - 10"
            binding.MapInfoSizeText.text = "800 x 800"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "The map features several smaller buildings on the left flank with corridors positioned along either side of them, a large, empty factory building in the center, and some more open terrain on the right flank, featuring slight hills and a small scattering of houses for cover."
            binding.MapInfoStrategyTextLight.text = "On Pilsen, there are small oportunities for light armored tanks. The main one is in East where there is a line of bushes that can help you outspot."
            binding.MapInfoStrategyTextMedium.text = "Help light tanks in the East or snipe from the top of the hill."
            binding.MapInfoStrategyTextHeavy.text = "Play near the ruins where you can sidescrape and play hulldown."
            binding.MapInfoStrategyTextTD.text = "Snipe from the top of the hill or in the bushes from the East."
        }
        else if(title == "Prokhorovka")
        {
            binding.imageView2.load(R.drawable.prokho2)
            binding.imageView3.load(R.drawable.prokho1)
            binding.textMapName.text = "Prokhorovka"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Generally open, but hilly, terrain around a vital railroad, with groups of trees providing shelter for tank destroyers. On the offensive, watch your own flanks while striking against the enemy flanks. Artillery has free reign, with the only real hindrance being the train cars themselves, but the open terrain makes them very vulnerable to raids by light vehicles."
            binding.MapInfoStrategyTextLight.text = "Top 3 maps for light tanks, you can literally win the game by yourself by spotting. Play in the line of bushes in the West, push carefully or wait for enemies."
            binding.MapInfoStrategyTextMedium.text = "Play on top of the hill in the East of snipe from the back in the bushes in the West."
            binding.MapInfoStrategyTextHeavy.text = "Hard map for heavies. You can try playing in the middle and deal damage when enemies make mistakes or get outspotted."
            binding.MapInfoStrategyTextTD.text = "Hide in the bushes in West and wait for spotting."
        }
        else if(title == "Province")
        {
            binding.imageView2.load(R.drawable.province2)
            binding.imageView3.load(R.drawable.province1)
            binding.textMapName.text = "Province"
            binding.MapInfoTierText.text = "4 - 7"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Hills on the periphery of the map are covered with numerous houses and winding streets advantageous for surprise attacks. An open area between the bases is favorable for daring strikes. This is map si exculsevly to low tier matchmaking."
            binding.MapInfoStrategyTextLight.text = "Being a low tier matchmaking map and the map being on an elevated terrain + in mirror, there are not many things to be said. All classes of tanks can be played the same. You can rush on the other side or stay on the spawn side and defend the base. "
            binding.MapInfoStrategyTextMedium.text = null
            binding.MapInfoStrategyTextLightID.text = "General Strategy:"
            binding.MapInfoStrategyTextHeavy.text = null
            binding.MapInfoStrategyTextTD.text = null
            binding.MapInfoStrategyTextMediumID.text = null
            binding.MapInfoStrategyTextHeavyID.text = null
            binding.MapInfoStrategyTextTDID.text = null
        }
        else if(title == "Redshire")
        {
            binding.imageView2.load(R.drawable.redshire2)
            binding.imageView3.load(R.drawable.redshire1)
            binding.textMapName.text = "Redshire"
            binding.MapInfoTierText.text = "5 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = ""
            binding.MapInfoDescriptionText.text = "A rolling area, dominated by a couple of hills. The village of Redshire gives places to hide from hill to its northeast. Artillery will play a powerful role on this map. In the past, this was one of the more unbalanced maps in the map pool, now after the changes, is more balanced."
            binding.MapInfoStrategyTextLight.text = "Beautiful map for experienced spotters, there are some bushes and trees near the river from where the light tank can spot. Also, you can be more agressive and roam for a bit near the middle of the map because you can spot tanks sniping from the hills."
            binding.MapInfoStrategyTextMedium.text = "Mediums can roam around the map. They have good positions near the middle hills and a castle on the West with a few trenches. Here is one of the main battles on this map."
            binding.MapInfoStrategyTextHeavy.text = "Usualy, the heavies have an intense battle in the East flank. Here are some hull down positions where you can show you turret performance."
            binding.MapInfoStrategyTextTD.text = "The triangles have a good time on Karelia. They can play in the forest on the hills on help heavies in the East flank."
        }
        else if(title == "Ruinberg")
        {
            binding.imageView2.load(R.drawable.ruinberg2)
            binding.imageView3.load(R.drawable.ruinberg1)
            binding.textMapName.text = "Ruinberg"
            binding.MapInfoTierText.text = "2 - 10"
            binding.MapInfoSizeText.text = "800 x 800"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Concentric city streets, along with dense forests and bushes in the overgrown park allow for hidden maneuvers and redeployment of your troops. Although artillery fire is of limited use in the city, the debris provides effective cover for your troops."
            binding.MapInfoStrategyTextLight.text = "Light tanks may be able to get around the enemy if they are not vigilant enough in covering their rear. "
            binding.MapInfoStrategyTextMedium.text = "Mediums will excel at the peek-a-boo tactic here because of the fact that they are faster than the heavies and don't need to duck out too much to take a shot. "
            binding.MapInfoStrategyTextHeavy.text = "Ruinberg is made up of a labyrinth of streets to the east. Heavies will feel right at home here because of the closeness of the enemy tanks. The peek-a-boo tactic will be useful here as the streets meet up in on place, and everyone will be using the buildings as cover. "
            binding.MapInfoStrategyTextTD.text = "The west of the map is very open and a great area for scouts. You will find tank destroyers sometimes wait for lights or mediums to make their way across the map so that they can snipe."
        }
        else if(title == "Serene Coast")
        {
            binding.imageView2.load(R.drawable.serenecoast2)
            binding.imageView3.load(R.drawable.serenecoast1)
            binding.textMapName.text = "Serene Coast"
            binding.MapInfoTierText.text = "5 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "This coastal area is bounded by ocean and mountains. A railroad crosses the territory from north to south. Small hills on the western flank are favorable for a swift attack. A straight road connecting the bases passes through a small town, which often becomes a contested piece of terrain. There is also a low ground beside the town, which could be used for flanking the west and the middle."
            binding.MapInfoStrategyTextLight.text = "There are 2 main strategies for the light: play near the lake where you can spot crossing forces or play near the valley and train tracks where you can spot mediums pushing."
            binding.MapInfoStrategyTextMedium.text = "One main option: play on top of the hill or push the valley."
            binding.MapInfoStrategyTextHeavy.text = "If you have good mobility and good gun depression, play on top of the hill, if you are a superheavy tank, play on the West where is plenty of cover. Take care for triangles."
            binding.MapInfoStrategyTextTD.text = "With the TD's, you can play behind the heavies on the East flank, or wait for enemies to push the hill."
        }
        else if(title == "Siegfried Line")
        {
            binding.imageView2.load(R.drawable.siegfried2)
            binding.imageView3.load(R.drawable.siegfried1)
            binding.textMapName.text = "Siegfried Line"
            binding.MapInfoTierText.text = "5 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "A wonderful map combining the best of both worlds. The west side is open fields with little cover, excellent for quick flanks around the side for those looking for an early victory. To the east, the town offers concealment and cover for those brave enough to enter its confines."
            binding.MapInfoStrategyTextLight.text = "With a light tank, you have to play agressive near the middle, from here you can peek for info and spot snipers."
            binding.MapInfoStrategyTextMedium.text = "Help light tanks in the middle or if the tank has decent armor, play in the city."
            binding.MapInfoStrategyTextHeavy.text = "The only option for the heavies, is to play in the city. Here are plenty of spots from where you can peek safely and show your dominance."
            binding.MapInfoStrategyTextTD.text = "With the TD's you can play near the lake in the South or on top of the hills in the North."
        }
        else if(title == "Steppes")
        {
            binding.imageView2.load(R.drawable.steppes2)
            binding.imageView3.load(R.drawable.steppes1)
            binding.textMapName.text = "Steppes"
            binding.MapInfoTierText.text = "3 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "A seemingly endless open expanse of fields and hills, scattered through this farmland-esque map. Open fields, varying ditches, and small hills and rises litter this map, allowing for some prime spots to lay ambushes. Long, open fields are great for those who prefer long-distance combat, with little cover available to your target. Just remember; This works both ways."
            binding.MapInfoStrategyTextLight.text = "In the middle of the map is a road leading to enemy's base. This route isn't used by many, but scouts will find it easy to spot or take out the enemy's artillery here. There are many tall rock structures along the way if you ever need to take cover from enemy fire."
            binding.MapInfoStrategyTextMedium.text = "Play near the base if you have good aim cirle, of on top of the hills in the West part of the map, where there is good cover."
            binding.MapInfoStrategyTextHeavy.text = "On the west side of the map is usually plugged by heavies. Once again, there are a lot of rock formations, but usually the braver team comes out on top"
            binding.MapInfoStrategyTextTD.text = "A somehow similar style of play as the mediums, also you can play behind the base circle because there are good oprtunities to snipe around the map."
        }
        else if(title == "Studzianki")
        {
            binding.imageView2.load(R.drawable.studzanki2)
            binding.imageView3.load(R.drawable.studzanki1)
            binding.textMapName.text = "Studzianki"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "Studzianki was the location of a weeklong battle between the invading German forces and the native Polish 1st Armoured Brigade and Soviet 8th Army on the Eastern Front. For the allied forces, it was a resounding success and was later commemorated in 1969, becoming Studzianki Pancerne (Armoured Studzianki). The map features large fields, lots of vegetation, and small village houses centered around a tall Catholic church. A small brick factory sits right in the middle of this map."
            binding.MapInfoStrategyTextLight.text = "Play near the middle dried riverbed, where are some bushes for good spotting."
            binding.MapInfoStrategyTextMedium.text = "Push the South part of the map where 1 of the 2 main battles take place. Secondary option, on top of the hill in the nort."
            binding.MapInfoStrategyTextHeavy.text = "Play near the factory or if you have very good mobility, play with the mediums."
            binding.MapInfoStrategyTextTD.text = "Play along side of the redline or in front of the base where is a row of bushes for extra damage."
        }
        else if(title == "Tundra")
        {
            binding.imageView2.load(R.drawable.tundra2)
            binding.imageView3.load(R.drawable.tundra1)
            binding.textMapName.text = "Tundra"
            binding.MapInfoTierText.text = "3 - 10"
            binding.MapInfoSizeText.text = "800 x 800"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "A mountain in the east offers the possibility of dominating from the high ground, while the central part of the map offers a wide range of tactical advantages. The village in the west could offer some linear combat for heavy tanks.Tundra can be described as one of the least tactical maps in World of Tanks. Because there are so many routes one can choose to go down, it is almost impossible to describe how to go about your business on this map. "
            binding.MapInfoStrategyTextLight.text = "There is no strategy on this map, you can go where you want with any tank, but watch out for the flanks. Two main battles are on this map, one on top of the hill and the second one in the East where is a path between hills."
            binding.MapInfoStrategyTextMedium.text = "Medium tanks can take the east hill and fare pretty well, or they can take the bridge that connects the two bases. This is a natural choke point though, so you'll have to bring some backup."
            binding.MapInfoStrategyTextHeavy.text = "Heavy tanks can brawl anywhere from the river valley to western valley to the city in the west."
            binding.MapInfoStrategyTextTD.text = "Tank destroyers with good frontal armor will love the western valley as well as the bridge. "
        }
        else if(title == "Westfield")
        {
            binding.imageView2.load(R.drawable.westfield2)
            binding.imageView3.load(R.drawable.westfield1)
            binding.textMapName.text = "Westfield"
            binding.MapInfoTierText.text = "5 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "A wonderfully lush area, Westfield features forests, fields, farmland, and multiple villages throughout the map. Included is a large war-damaged bridge crossing the eastern valley. Plentiful ridges give ambush positions along nearly every route, and large fields can leave a tank vulnerable when moving between positions."
            binding.MapInfoStrategyTextLight.text = "Play on top of the South-East hill or near the middle city for spotting."
            binding.MapInfoStrategyTextMedium.text = "Push the South-East hill or in the middle"
            binding.MapInfoStrategyTextHeavy.text = "Not a very good map for heavies, there is only one option, in the North-West hills, where you can dominate if you have good gun depression."
            binding.MapInfoStrategyTextTD.text = "Snipe from behind the heavies, you will always find damage. Alternatevly, you can play near the valley for a good crossfire."
        }
        else if(title == "Widepark")
        {
            binding.imageView2.load(R.drawable.widepark2)
            binding.imageView3.load(R.drawable.widepark1)
            binding.textMapName.text = "Widepark"
            binding.MapInfoTierText.text = "3 - 6"
            binding.MapInfoSizeText.text = "600 x 600"
            binding.MapInfoTypeText.text = "Summer"
            binding.MapInfoDescriptionText.text = "This map depicts an industrial city devastated by war. A railway embankment divides the map in two, making it possible to concentrate forces for an attack or counterattack."
            binding.MapInfoStrategyTextLight.text = "Being one of the smallest map in World of Tanks, and having a lowtier matchmaking, there is no such a strategy. You can call it 'free for all' because there is constant shooting and chaos around this map."
            binding.MapInfoStrategyTextMediumID.text = null
            binding.MapInfoStrategyTextLightID.text = "General Strategy:"
            binding.MapInfoStrategyTextHeavyID.text = null
            binding.MapInfoStrategyTextTDID.text = null
            binding.MapInfoStrategyTextMedium.text = null
            binding.MapInfoStrategyTextHeavy.text = null
            binding.MapInfoStrategyTextTD.text = null
        }
        else if(title == "Erlenberg")
        {
            binding.imageView2.load(R.drawable.erlenberg2)
            binding.imageView3.load(R.drawable.erlenberg1)
            binding.textMapName.text = "Erlenberg"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Winter"
            binding.MapInfoDescriptionText.text = "A large river splits this map in half, with three bridges acting as choke points between the halves. A small town surrounds the center bridge, while the north and south bridges have fewer buildings providing cover. A large castle overlooks the west half of the map on a large hilltop, offering cover and supporting fire positions. Hills overlook the city in the east, with sufficient vegetation to provide some concealment for tanks.Players spawn in the north and south ends of the map, making the chokepoints on the bridges a moot point, as both sides have access to either side of the map. The castle and the eastern hills both tend to become the primary battle zones, as holding one or two of those places allows for domination of large parts of the map."
            binding.MapInfoStrategyTextLight.text = "Spot forces in the West or East flank."
            binding.MapInfoStrategyTextMedium.text = "Help light tanks push the West or the East"
            binding.MapInfoStrategyTextHeavy.text = "Play in the city. Typically the heavies split 50:50 or slightly more to the west."
            binding.MapInfoStrategyTextTD.text = "Play alongside the bushes near the base redline. Northern TDs tend to favor east more, while the south is usually more evenly split."
        }
        else if(title == "Glacier")
        {
            binding.imageView2.load(R.drawable.glacier2)
            binding.imageView3.load(R.drawable.glacier1)
            binding.textMapName.text = "Glacier"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Winter"
            binding.MapInfoDescriptionText.text = "The map isn’t specifically based on any locale or any historical battle (due to Sweden’s neutrality during World War II). The key element is its charming winter setting. Besides, if you’re looking for a bit of variety, Glacier certainly makes for a breath of fresh air. Glacier might not be set in any particular era of time but the conditions signal the end of winter on Sweden’s coastline."
            binding.MapInfoStrategyTextLight.text = "Mobile light tanks will feel at home in the central area, where they can leverage multiple open spaces and use their view range to uncover enemy positions and hunt down enemy LTs."
            binding.MapInfoStrategyTextMedium.text = "The northern area close to the aircraft carrier and the south-east part of the map are best suited for medium tanks. Secure the spot near the aircraft carrier to help friendly heavies break through or support your team in the center."
            binding.MapInfoStrategyTextHeavy.text = "If playing a heavy tank, consider the north-west with destroyed ships and a large aircraft carrier stuck in the river of ice. It offers decent protection from arty shells and ample space for maneuvering."
            binding.MapInfoStrategyTextTD.text = "Support vehicles can thrive in spawning areas to the south-west and north-east. Use concealed positions and the good viewpoints they offer to wear down enemies and support a push."
        }
        else if(title == "Mannerheim Line")
        {
             binding.imageView2.load(R.drawable.mannerheim2)
             binding.imageView3.load(R.drawable.mannerheim1)
             binding.textMapName.text = "Mannerheim Line"
             binding.MapInfoTierText.text = "4 - 10"
             binding.MapInfoSizeText.text = "1000 x 1000"
             binding.MapInfoTypeText.text = "Winter"
             binding.MapInfoDescriptionText.text = "A large ice-and-snow covered area stretches through this map. Steep hills, rock encampments, twists and turns, and plenty of places to stay and wait will have you on your toes as you battle through this icy deathtrap. Ridge, hills, and lots of rocky outcrops give you many places to hide and ambush your enemy as they come about."
             binding.MapInfoStrategyTextLight.text = "Play near the hill in the North or push the valley in the middle to offer good assist."
             binding.MapInfoStrategyTextMedium.text = "Help the light tank or snipe with the TD's."
             binding.MapInfoStrategyTextHeavy.text = "Play near the river where you can dominate if you have good gun depression."
             binding.MapInfoStrategyTextTD.text = "Snipe from the back near the hill or behind the heavy tanks, near the base."
        }
        else if(title == "Airfield")
        {
            binding.imageView2.load(R.drawable.airfield2)
            binding.imageView3.load(R.drawable.airfield1)
            binding.textMapName.text = "Airfield"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Desert"
            binding.MapInfoDescriptionText.text = "This map offers many great ambush spots, as well as possibilities for maneuvers and breakthroughs. A large airfield in the upper part of the map and a rocky highland in the center offer a major tactical challenge. Occupy the highland to get a perfect view of the surrounding terrain and dominate routes to the enemy base."
            binding.MapInfoStrategyTextLight.text = "Light tanks tend to scout the hill in the lower center of the map, counting on the combined support of the tank destroyers that camp back at the bases."
            binding.MapInfoStrategyTextMedium.text = " Medium tanks can choose to go to the southern hill, get to the small pocket at E7 (east base only), go to the B line, skulk at the low path next to the hill at F5-F6, or support the heavies at the chokepoint at E6."
            binding.MapInfoStrategyTextHeavy.text = "Heavy tanks tend to attack the valley at E6 and flank around at D6 Those at E6 benefit from supporting fire from the tank on the South hill, as well as artillery support. "
            binding.MapInfoStrategyTextTD.text = "Tank destroyers tend to camp at various locations at the front of the base, D2 and D9 to provide support for the tanks in the center or northern part of the map, or support the south coast."
        }
        else if(title == "El Halluf")
        {
            binding.imageView2.load(R.drawable.el_halluf2)
            binding.imageView3.load(R.drawable.el_halluf1)
            binding.textMapName.text = "El Halluf"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Desert"
            binding.MapInfoDescriptionText.text = "Horrible for stock engine tanks. A large valley filled with rocks, vegetation, and a small village surrounding a dried out river bed separate the two teams. The large hills on either side of the valley offer many firing positions, and very little cover in the valley will protect a large tank completely against all positions. Regardless of approach, attackers will face a long climb into the enemy's camp, and effective use of the cover en route is essential. The northern approach offers plentiful protection to attack either hill, but the southern approach makes up for lack of protection with shorter distances and better concealment."
            binding.MapInfoStrategyTextLight.text = "For light tanks, a dash to the south-eastern corner can also be used. The winner of this corner can spot the tanks on the ridges for their own snipers, as well as advance up into the enemies base to wreak havoc on TDs and SPGs. Less contested but also less effective positions can also be found elsewhere in the valley."
            binding.MapInfoStrategyTextMedium.text = "Help light tank push the valley."
            binding.MapInfoStrategyTextHeavy.text = "Heavily armored tanks should head north or west (depending on the spawn) towards the A1 corner. Traditionally, the northern flank takes a more defensive approach and allows the southern flank to cross the valley and advance partially up into A2. "
            binding.MapInfoStrategyTextTD.text = "Snipe from near the base or push with heavy tanks if good armor."
        }
        else if(title == "Ghost Town")
        {
            binding.imageView2.load(R.drawable.ghosttown2)
            binding.imageView3.load(R.drawable.ghosttown1)
            binding.textMapName.text = "Ghost Town"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "800 x 800"
            binding.MapInfoTypeText.text = "Desert"
            binding.MapInfoDescriptionText.text = "This presidio located in the desert sands is an ideal setting for head-on heavy vehicle encounters, while the more maneuvrable vehicles can launch surprise attacks by flanking the enemy. This map stands out in being almost completely symmetrical and was designed for clan-wars. Now it is available in random battles."
            binding.MapInfoStrategyTextLight.text = "2 main strategies for the light tank. The first one is to play in the line of bushes in the East and secondary one is on top of the hills in the West"
            binding.MapInfoStrategyTextMedium.text = "Play near the light tanks and give support"
            binding.MapInfoStrategyTextHeavy.text = "Play in the city where you can trade and sidescrape."
            binding.MapInfoStrategyTextTD.text = "Offer support to other tanks, there are endless postions on this map."
        }
        else if(title == "Sand River")
        {
            binding.imageView2.load(R.drawable.sandriver2)
            binding.imageView3.load(R.drawable.sandriver1)
            binding.textMapName.text = "Sand River"
            binding.MapInfoTierText.text = "4 - 10"
            binding.MapInfoSizeText.text = "1000 x 1000"
            binding.MapInfoTypeText.text = "Desert"
            binding.MapInfoDescriptionText.text = "A mostly open map offering little concealment, but providing plentiful cover due to the large number of sand dunes. Three small villages provide temporary cover at best, but are easily destroyed, so they must be used cautiously. Each flag is well protected with a ridge offering defenders cover, concealment, and excellent firing positions. Plentiful cover allows an excellent arena for run and gun tactics while offering many routes for tanks to engage the enemy and make use of their mobility."
            binding.MapInfoStrategyTextLight.text = "Roam the dunes of sand and spot tanks crossing or hiding in the back."
            binding.MapInfoStrategyTextMedium.text = "Play on top of the dunes helping the light tank"
            binding.MapInfoStrategyTextHeavy.text = "Play in the valley or in the front line in the dunes in the South and North."
            binding.MapInfoStrategyTextTD.text = "2 main positons on this map, both near the spawn, on top of the hill in West and in the bushes in the East."
        }
    }
}


