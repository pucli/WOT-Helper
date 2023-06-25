package com.wot.helper.ui.tanks

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import com.wot.helper.databinding.FragmentTankCharacteristicsBinding
import com.wot.helper.ui.core.BaseFragment
import androidx.navigation.fragment.navArgs
import java.util.ArrayList
import com.wot.helper.R
import androidx.core.view.isVisible
import coil.load
import com.wot.helper.ui.core.MainActivity
import com.wot.helper.domain.models.models.TankInfo



class TankCharacteristicsFragment : BaseFragment<FragmentTankCharacteristicsBinding>(FragmentTankCharacteristicsBinding::inflate) {

    private lateinit var tutorial: ArrayList<ImageView>
    private lateinit var textview: TextView
    private lateinit var imageview: ImageView
    private lateinit var imageView2: ImageView

    private lateinit var TankInfo: TextView
    private lateinit var btnShare: Button
    private lateinit var title: String
    private val args: TankCharacteristicsFragmentArgs by navArgs()


    private val applicationContext: Context by lazy {
        requireContext().applicationContext
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNavArgs()
        changeDescription()

        tutorial = arrayListOf(binding.imageView2)

        textview = binding.TankInfo
        textview.movementMethod = ScrollingMovementMethod()

        val image2 = binding.imageView2


        binding.btnShare.setOnClickListener {
            val mBitmap2 = image2.drawable?.toBitmap()

            if (mBitmap2 != null) {
                val path2 = MediaStore.Images.Media.insertImage(
                    applicationContext.contentResolver,
                    mBitmap2,
                    "Check out more on WoT Helper!",
                    null
                )

                val uri2 = Uri.parse(path2)

                val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
                shareIntent.type = "image/*"
                shareIntent.putExtra(Intent.EXTRA_TEXT, binding.TankInfo.text.toString() +
                        "\n" + "\n" + "Check out more details on Wot Helper!")
                shareIntent.putParcelableArrayListExtra(
                    Intent.EXTRA_STREAM,
                    arrayListOf(uri2)
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

    private fun changeDescription() {
        val tankInfoMap = mapOf(
            "T-100 LT" to TankInfo(
                R.drawable.t100lt,
                "A project for a light tank with the T-100 armament. Jointly developed by Research Institute No. 100 and GSKB-47 (State Specialized Design Bureau) in the mid-1960s. The vehicle featured decent armor that could protect it from\\n90-mm shells (in case of frontal impact) at any distance.",
                "Top  light tanks in the game, good for spotting and doing damage",
                "A little bit big than other light tanks"
            ),
            "Obj. 140" to TankInfo(
                R.drawable.obj140,
                "An experimental medium tank. Developed between 1953 and 1958 in Nizhny Tagil to replace the T-54. Two prototypes were built, but in 1958 the project was terminated in favor of the Object 430.",
                "Very good dpm, penetration and gun handling. Good Accuracy, strong armor and speed",
                "Low alpha damage, low gun depression"
            ),
            "IS-7" to TankInfo(
                R.drawable.is7,
                "Development of the IS-7 started in the spring of 1945. Prototypes successfully underwent trials in 1946 and 1947. However, the IS-7 never saw mass production.",
            "Good alpha damage, very good turret armor, great speed",
            "Not the best DPM. Very bad aim and accuracy, but it is a russian tank so it is ok. Gets ammo rack."
            ),
            "Obj. 268/4" to TankInfo(
                R.drawable.ob268v4,
                "The Object 730 Version 4 was intended as another SPG project developed on the basis of the T-10 (Object 730) tank by December 18, 1952. At the same time, the concept project featured the Object 268 designation. The project allowed for a gun to be mounted in a closed stationary cabin placed in the rear of the hull. The project was canceled because a complex modification of the Object 730 hull was required.",
         "Very good frontal armor, one of the best TD in the game, very good speed and mobility, very good for ramming.",
            "Not the best apha damage, not very good traverse speed, no turret"
            ),
            "T-54 ltwt" to TankInfo(
                R.drawable.t54ltwt,
                "A proposed lightweight version of the T-54 medium tank. Development was started in May 1949. Compared to its series-produced predecessor, the tank had better performance and maneuverability. Therefore, the tank was planned to replace the T-54 in accomplishing complicated missions. However, the T-54 tank of 1949 was improved and mass produced, and the lightweight version was canceled while still in the design phase.",
"strong turret for a light tank, good speed and mobity, great capacity for spotting.",
"not the best gun depression, other light tanks are better for tier 9"
            ),
            "Obj. 430" to TankInfo(
                    R.drawable.obj430,
                "A prototype of this medium tank was developed from 1953 through 1957 to replace the T-54. Soviet authorities wanted a tank with greater nuclear survivability and firepower, so in 1961 the project was discontinued in favor of the Object 432. Several prototypes of the vehicle were manufactured.",
"very good armor, good alpha damage and DPM, one of the best to grind.",
"poor accuracy, bad gun depression, weak fuel tank"
            ),
            "Obj. 705" to TankInfo(
                R.drawable.obj705,
                "One of the first versions of the Object 705 heavy tank. The vehicle was developed by the Design Bureau of the Chelyabinsk Kirov Plant. Existed only in blueprints.",
"top tank for side scapring, very good frontal armor and low profile, easy tank to play with",
"no gun depression, can be tricky"
            ),"Obj. 263" to TankInfo(
                R.drawable.obj263,
                "The development of a heavy tank destroyer on the basis of the IS-7 heavy tank was started in 1950. Three design projects were developed, one of which resulted in a full-size wooden prototype. However, further development was discontinued due to phasing out mass-production of the IS-7.",
"very good tank, good armor, great mobility, great for ramming",
"no turret, traverse speed "
            ),"ISU-152" to TankInfo(
                R.drawable.isu152,
                "Developed on the basis of the IS tank. The ISU-152 was conceived as a replacement for the SU-152, which was based on the KV-1s chassis. A total of 4,635 vehicles were built from November 1943 through June 1945.",
"great alpha damage, good for trading",
"horrible accuracy and aim time "
            ),"SU-152" to TankInfo(
                R.drawable.su152,
                "Development of a tank destroyer on the basis of the KV-1S heavy tank began on January 4, 1943. The vehicle received the designation KV-14 (Object 236). A prototype underwent field trials at the beginning of February 1943 and entered service on February 14, 1943. In April, the KV-14 was renamed SU-152. By December 1943, 670 vehicles were manufactured. Thanks to its ability to successfully combat german Tiger I and Panther tanks, the SU-152 was dubbed \\\"Hunter\\\" by the troops.",
                "great alpha damage, good for trading",
                "horrible accuracy and aim time "

            ),"SU-100" to TankInfo(
                R.drawable.su100,
                "Tank destroyer on the basis of the T-34-85 and SU-85 with a total of 2,495 vehicles produced from September 1944 through June 1945. After the vehicle saw service, the Red Army praised it as a very effective tank destroyer with strong firepower, which could stand against any mass-produced German armored vehicle.",
"good accuracy, great camo, fast mobility",
"bad view range, long reload, easy module damage"
            ),"SU-85" to TankInfo(
                R.drawable.su85,
                "Developed on the basis of the T-34 medium tank and the SU-122 assault gun. Produced from August 1943 through July 1944. The 85-mm D-5S gun allowed the SU-85 to effectively hit enemy medium tanks at distances of more than 1,000 meters and to penetrate the front armor of heavy tanks at shorter distances.",
                "good accuracy, great camo, fast mobility",
                "bad view range, long reload, easy module damage"

            ),"SU-76M" to TankInfo(
                R.drawable.su76m,
                "Light vehicle with a 76-mm gun, the most widely produced Soviet SPG. Despite its weak armor and armament, the vehicle was used to support infantry and cavalry.",
"great rate of fire, good acceleration, greate damage",
"thin armor, narrow arc of fire, poor dpm"
            ),"LTTB" to TankInfo(
                R.drawable.lttb,
                "Development of the light tank project was started in early 1944 at Factory No. 174. The project was canceled during the design phase and the phase of setting tactical and technical requirements.",
"very good tank for spotting, great mobility and damage",
"nothing to say bad, maybe the scale"
            ),"LTG" to TankInfo(
                R.drawable.ltg,
                "A project for a light tank developed by Gavalov at the Gorky Automobile Plant in 1944. Several extremely unusual design solutions were used for the vehicle: a driver sat in the fighting compartment, and his observation device was installed in the hatch at the turret top. The device was synchronized with the driver's station and rotated with the seat against the turret. The project was discontinued at the development stage.",
"good armor, good camo and low profile",
                "ammorack, no gun depression, not the best view range"

            ),"MT-25" to TankInfo(
                R.drawable.mt25,
                "A proposed project of a light wheeled caterpillar vehicle. The draft was completed in February 1943. The most innovative feature was the suspension design. The suspension wheels were interleaved, and power was delivered to all wheels. No prototypes were manufactured.",
"terrain resistance, good for ram",
"slow, low DPM, not the best view range."
            ),"A-20" to TankInfo(
                R.drawable.a20,
                "Development of the A-20 tank started at Kharkov Factory No. 138 in December 1937. The project was a further development of the BT-7 tank and became a predecessor of the legendary T-34. Work on the vehicle was led by Mikhail Koshkin. On May 18, 1938, technical characteristics of the tank designated BT-20 were approved. Only a few experimental prototypes, with a wheeled caterpillar suspension, were built for training purposes.",
"good engine power, good dpm",
"not the most agile, not very good penetration"
            ),"BT-7" to TankInfo(
                R.drawable.bt7,
                "The Soviet wheeled caterpillar tank used in the 1930s–1940s. The third vehicle in the series of the Soviet light BT tanks. The BT-7 differed from its predecessors in the welded hull of a modified shape and a new engine. A total of 5,556 vehicles of different variants were produced and saw action during the Khalkhyn Gol battles, Polish Campaign, Winter War and World War II.",
"good speed, acceleration, low profile, decent for ramming",
"no armor, bad view range, not good damage and pen"
            ),"BT-5" to TankInfo(
                R.drawable.bt5,
                "A mass-produced tank in the BT vehicle series that was developed as an upgraded version of the BT-2 with improved gun armament, a Soviet engine, and slightly increased vehicle height and weight. The BT-5 was one of the Red Army's main tanks before the war. Vehicles of this modification were engaged in the Spanish Civil War as well as the Battle of Khalkhyn Gol, the Polish Campaign, the Winter War, and World War II. A total of 1,884 vehicles were manufactured.",
"low tier, no information about it",
                "low tier, no information about it"
            ),"BT-2" to TankInfo(
                R.drawable.bt2,
                "A U.S.S.R. wheeled caterpillar tank developed in the 1930s. The first vehicle of the BT light tanks. The tank was based on the U.S. Christie M1931 (Model 1940), but differed from its peer in various design solutions, and had higher speed and better mobility. A total of 620 vehicles of different modifications were manufactured. The vehicle with a new engine and elliptical turret featuring a 45-mm gun was designated BT-5. A total of 1,884 vehicles of this type were produced. Some of them were exported to Spain, others participated in the battle of Khalkhyn Gol, Polish campaign, Winter War, and Second World War.",
                "low tier, no information about it",
                "low tier, no information about it"
            ),"MS-1" to TankInfo(
                R.drawable.ms1,
                "The first Soviet mass-produced tank with a total of 959 vehicles manufactured from 1928 through 1931.",
                "low tier, no information about it",
                "low tier, no information about it"
            ),"T-44" to TankInfo(
                R.drawable.t44,
                "A reference sample of the vehicle designated T-44A. Developed by the Construction Bureau of Plant No. 183. The vehicle entered service on November 23, 1944, but never saw combat. From the end of November 1944 through September 1945, a total of 570 vehicles were produced.",
"good mobility, low profile, good camo, good aim time",
"bad pen, ammo rack, not good view range, gun dispersion"
            ),"T-43" to TankInfo(
                R.drawable.t43,
                "Designed as a replacement for the T-34 in the spring of 1942. The T-43 was recommended for service, but all work on the project was discontinued in favor of improving the T-34. The vehicle never entered mass production.",
"good DPM, good depressionm good rate of fire, good side armor",
"gun handling, stock tank is bad"
            ),"T-34-85" to TankInfo(
                R.drawable.t3485,
                "Final modification of the T-34 tank of 1943. A new three-man gun turret allowed a more powerful 85-mm gun to be mounted. This greatly increased the combat effectiveness of the tank compared to its predecessor, the T-34-76. A total of more than 35,000, in several variants, were produced. Today the tank is still in service in several countries.",
"good mobility, good gun",
"weak hull armor, bad gun depression and ground resistence."
            ),"T-34" to TankInfo(
                R.drawable.t34,
                "The legend of the Soviet armored forces and the most widely-produced Soviet tank of World War II, with a total of 33,805 vehicles manufactured. Three variants of this model were produced at several Soviet factories from 1940 through 1944.",
"versatile, good dpm, good armor, good camo",
                "no bad features"
            ),"T-28" to TankInfo(
                R.drawable.t28,
                "The T-28 tank was developed by the Experimental Machine Design Bureau of the Voroshilov plant. The vehicle saw service on August 11, 1933. A total of 503 tanks were manufactured at the Leningrad Kirov Plant between 1933 and 1940. During mass production, the T-28 tank underwent a number of changes in design and modifications. The vehicles were used in the war with Finland in the winter of 1939–1940 and at the beginning of WWII.",
                "low tier, no information about it",
                "low tier, no information about it"

            ),"IS-3" to TankInfo(
                R.drawable.is3,
                "Mass production of the vehicle started in May 1945. On September 7,1945, IS-3 tanks took part in the Allied Victory Parade through Berlin. A total of 1170 vehicles were manufactured by the end of 1946, when production was canceled. From 1948 through the late 1950s, the tanks underwent a number of modernization refits.",
"good turret armor, good gun, low profile, good mobility",
"poor view range, ammo rack quickly"
            ),"IS" to TankInfo(
                R.drawable.`is`,
                "The IS-1 (IS-85) heavy tank was a thorough modernization of the KV-1. The tank featured the 85-mm D-5T gun. The IS-1 entered mass production in October 1943, but work was discontinued at the beginning of 1944, as the IS-2 (IS-122) tank entered mass production in December 1943. A total of 107 vehicles were manufactured.",
"good top gun, good alpha damage, decent armor",
"horrible gun handling and depressiom, turret is bad"
                ),"KV-85" to TankInfo(
                R.drawable.kv85,
                "A further development of the KV-1S tank. Unlike the previous modification, the KV-85 had enhanced armament. The vehicle was developed in the spring of 1943 at the Chelyabinsk Kirov Plant. The tank entered service on August 8, 1943, and was mass produced until September, with a total of 148 vehicles manufactured. The KV-85 was used by the breakthrough armored regiments of the Red Army.",
"good guns, bouncy turret sometimes, decent speed fpr heavy tanks",
    "horrible stock tank"

            ),"KV-1" to TankInfo(
                R.drawable.kv1,
                "Development started at the end of 1938. A prototype was produced in August 1939. The vehicle first saw combat in December 1939 at the Mannerheim Line. The tank was mass-produced from March 1940 through August 1942, with a total of 2,769 vehicles manufactured.",
"decent tank for tier 5, decent gun and speed",
"not the best gun and stock tank is really bad"


            ),"Rhm. Pzw." to TankInfo(
                R.drawable.rhmpzw,
                "On June 8, 1957, Rheinmetall recorded a patent for a new light tank with an oscillating turret and a gun with trunnions. The proposed design allowed engineers to equip light vehicles with powerful 90-mm and 105-mm guns while preserving the minimum vehicle height. Rheinmetall did not have their own chassis for the subsequent tank production, and they offered their turret to other plants. The vehicle is a project on the basis of the Hanomag and Henschel chassis. Existed only in blueprints.",
"very good top speed, very good HE shells, great gun depression, very good view range, great gun",
"flips, low ammo capacity, low dpm and alpha damage, very big tank"
            ),"Leopard 1" to TankInfo(
                R.drawable.leopard1,
                "Main battle tank of the Federal Republic of Germany. Development was started in 1956. The first prototypes were built in 1965 at the Krauss-Maffei factory. The Leopard 1 saw service in the armies of more than 10 countries.",
"best gun in the game, great accuracy, good gun depressio, great speed, good viewrange.",
"no armor, module damage"
            ),"Maus" to TankInfo(
                R.drawable.maus,
                "Developed from June 1942 through July 1944, with two prototypes produced, only one of which received a turret and armament.",
"great armor for sidescraping, very big Hp pool, decent gun",
                "very slow, bad agains gold amoo, especially against HEAT"

            ),"Jg.Pz.E 100" to TankInfo(
                R.drawable.jgpze100,
                "The E 100 was conceived as the basis for a self-propelled gun, an antiaircraft vehicle, and a tank destroyer. However, development was never started.",
"amazing alpha damage but thats it",
"very slow, no armor against gold HEAT"
            ),"Ru 251" to TankInfo(
                R.drawable.ru251,
                "The vehicle was based on the Kanonenjagdpanzer 4-5 tank destroyer by the Henschel company in 1964. The tank was planned to replace the obsolete M41 Walker Bulldog vehicles used by the reconnaissance battalions of the armored divisions in the German Army. The tank never saw mass production, but some technical innovations were applied later in the development of other vehicles. The total number of prototypes built is unknown.",
"great mobility, good gun and good hull down performance, amazing HE",
                "thin armor, large profile, not many shells"

            ),"Leopard PT A" to TankInfo(
                R.drawable.leopardpta,
                "Prototype developed from 1960 through 1961 as a medium tank for the Bundeswehr, with a total of 26 prototypes manufactured. The prototypes were tested up to 1963. The vehicle was the predecessor of the Leopard medium tank.",
                "2nd best gun in the game, great accuracy, good gun depressio, great speed, good viewrange.",
                "no armor, module damage"

            ),"E 75" to TankInfo(
                R.drawable.e75,
                "In 1945 the E 75 was conceived as a standard heavy tank of the Panzerwaffe to replace the Tiger II. It existed only in blueprints.",
            "strong armor, decent speed, great gun, large calibre",
            "not the best DPM, brawler, not the most accurate tank"
            ),"Jagdtiger" to TankInfo(
                R.drawable.jagdtiger,
                "Developed from 1942 through 1944, the heavy tank destroyer, with the chassis of the Tiger II heavy tank, became the heaviest mass-produced armored vehicle ever. According to various sources, 70–79 tank destroyers were manufactured from 1944 through 1945. The vehicles saw service in the 653rd and 512th Heavy Panzerjäger Battalions.",
            "decent  tank , decent armor, good gun",
                "slow, armor not the best agains HEAT, "

            ),"HWK 12" to TankInfo(
                R.drawable.hwk12,
                "A series of HWK 10 light caterpillar armored carriers was developed by Henschel-Werke in the late 1950s–early 1960s. The vehicles were developed for export purposes. The first HWK 11 prototype was created in 1963, with 40 vehicles being sent to Mexico later. Two HWK 13 prototypes (reconnaissance vehicles) were built as well. The HWK 12 was supposed to be manufactured on the basis of the same chassis, but no prototypes were built.",
"good alpha damage, good speed, good ammo capacity",
"shell velocity is a joke, not the best camo, no armor."
            ),"SP I C" to TankInfo(
                R.drawable.spic,
                "The reconnaissance tank destroyer was being developed by Hotchkiss and Klöckner-Humboldt-Deutz companies from 1956 through 1962 in order to increase the anti-tank capability of recon tank battalions. A 90-mm anti-tank gun was planned to be mounted on the new vehicle. The project was eventually declined. The number of prototypes built is unknown.",
"good pen, good camo, turret is small",
"slow, poor shell velocity, lose crew"
            ),"VK 28.01" to TankInfo(
                R.drawable.vk2801,
                "Reconnaissance vehicle developed by the Daimler-Benz company. Existed only in blueprints.",
"good view range, good gun depressiom, good for ramming, decent armor, good mobility",
                "large size, no speed when turning"

            ),"Leopard" to TankInfo(
                R.drawable.leopardlight,
                "The design of the VK 16.02, known as the Leopard, was based on the Panzer II Ausf. J (VK 16.01) and Panzer II Ausf. M (VK 13.01) that had been developed earlier. A dummy vehicle was ready in May–June 1942, and the first prototype was built by September 1, 1942. According to \\\"Panzerprogramm 41\\\", that provided for the manufacturing of 339 vehicles (105 by December 1943 and 150 by the middle of 1944), mass production was to be launched in April 1943 but never started. Later, the Leopard turret was mounted on the Sd.Kfz.234/2 designated as Puma.",
"low tier, no info",
"low tier, no info"
            ),"Luchs" to TankInfo(
                R.drawable.luchs,
                "As compared to the Pz.Kpfw. II Ausf. C, the vehicle featured a new hull and turret, as well as a chassis with a staggered arrangement of roadwheels. The reconnaissance tank had strong characteristics, but only 131 vehicles of the L modification were built, after manufacture proved to be too expensive. ",
                "low tier, no info ",
                "low tier, no info "
            ),"Pz. II G" to TankInfo(
                R.drawable.pz2g,
                "The German WWII light reconnaissance tank was a modification of the Pz. II tank. The vehicle featured an improved hull and suspension, a more powerful engine, and observation devices for the radio operator and driver, which increased the view range.",
                "low tier, no info ",
                "low tier, no info "
            ),"Pz. II" to TankInfo(
                R.drawable.pz2,
                "The last modification of the light Pz. II tank. The vehicle featured enhanced armor, the 2 cm KwK 38 gun and improved observation devices. A total of 524 vehicles were built from March 1941 through December 1942.",
                "low tier, no info ",
                "low tier, no info "
            ),"L. Tr" to TankInfo(
                R.drawable.loltraktor,
                "Produced from 1930 to 1934. Four prototypes with different armament, crew, weight, and suspension features were manufactured.",
                "low tier, no info ",
                "low tier, no info "
            ),"Panther II" to TankInfo(
                R.drawable.panther2,
                "The vehicle was conceived in 1943. Two prototypes with the existing Panther I turrets were ordered in 1944. By 1945 only one of them had been built by the MAN company.",
                "good accuracy, good pend, good speed, good for ramming, good HP",
                "big size, power to weight ratio, not good armor"
            ),"Panther" to TankInfo(
                R.drawable.panther,
                "This famous tank was produced from January 1943 through April 1945, with a total of 5,796 vehicles built plus eight vehicles built on the F series chassis.",
                "very good pen, large HP, good gun depression, good viewrange",
                "large turret, low alpha damage, low dpm, no agility"
            ),"VK 30.02 M" to TankInfo(
                R.drawable.vk3002m,
                "A medium tank, developed by the MAN company and designed as a 30-ton tank. Simultaneously, the Daimler-Benz company developed a competing project. On May 13, 1942, the two projects were submitted to the Ministry of Armaments and War Production. The MAN design was preferred, and the tank eventually entered service as the Pz.Kpfw. V Panther.",
                "good gun mantlet, good HP, ammo capacity, accurate gun",
                "weak side armor, engine is knocked quickly"
            ),"Pz. IV H" to TankInfo(
                R.drawable.pz4h,
                "The G variant of the Pz. IV was produced starting in May 1942. In April 1943 production began on the H variant—eventually the most massively produced version of the Pz. IV. The Ausf. H shared the upgraded gun of the Ausf. G, and utilized a single 80-mm front glacis plate instead of layered plates. A new transmission slightly reduced vehicle speed but was more reliable. The tanks of this variant also featured side screens, 5–8 mm thick, on the hull and turret sides. A total of 3,774 vehicles of the Ausf. H were built.",
                "low tier, no info ",
                "low tier, no info "
            ),"Pz. III J" to TankInfo(
                R.drawable.pz3j,
                "Production of the J variant started in March 1941. In addition to the enhanced armor, the vehicle received the new 50-mm KwK 38 gun, which was deemed extremely successful; 1,067 other vehicles featured the KwK 39 gun, the improved version with better penetration rate. An attempt to mount a 75-mm gun (the N variant) was recognized as a failure, and the most large-scale vehicles were the J, L, and M variants (with 50-mm guns that had an impeccable track record). A total of 5,691 vehicles were produced, among which 1,969 were equipped with long-barrel guns and 2,391 with short-barrel 50-mm guns.",
                "low tier, no info ",
                "low tier, no info "
            ),"Tiger II" to TankInfo(
                R.drawable.tiger2,
                "The most heavily armored tank carrying the most powerful anti-tank gun. The vehicle's drawbacks included an overloaded suspension and engine-transmission group, as well as excessive general mass. When engaging enemy vehicles at long range, the Tiger II had an upper hand over any other vehicle in terms of the gun and armor protection. However, due to the excessive mass of the vehicle, relatively low durability of the engine and transmission, and small total number of vehicles built, the Tiger II did not have any significant impact on the course of war.",
                "decent speed, decent armor, gun gun",
                "large scale, agains gold ammo not the best"
            ),"Tiger I" to TankInfo(
                R.drawable.tiger1,
                "Development of the Tiger I was started in 1937 by the Henschel company. Mass production began in 1942, with an eventual total of 1,354 vehicles manufactured. The tank first saw combat in the fighting for Leningrad, and Tigers were at the forefront of battles from Tunisia to Kursk. Although production was discontinued in the summer of 1944, the Tiger I continued to see action until the end of the war.",
                "amazing gun, good dpm, decent gun depression, good HP, good frontal turret armor",
                "not the best aim time, slow turrent traverse speed, not the best armor, engine damage"
            ),"VK 36.01 H" to TankInfo(
                R.drawable.vk3601h,
                "An experimental German WWII heavy tank developed by Henschel. The vehicle was a further development of the VK 30.01 (H) project and a stage in the design process for the Tiger. A total of eight chassis and one prototype were built in 1942. During the development, various technical solutions were trialed. Some of them later served as a basis for German heavy tank designs during the WWII. The chassis was also used in armored recovery vehicles.",
                "good gun, decent speed",
                "not the best armor, can be penetrated by everyone"
            ),"Rhm.-B.WT" to TankInfo(
                R.drawable.rahimwaffle,
                "Developed by the Rheinmetall-Borsig company and designed as a special artillery carrier. The vehicle was designed to carry an artillery system as well as to fire from the chassis. The vehicle utilized the suspension of the Hetzer tank destroyer. Development continued from the end of 1942 through to the middle of 1944. However, the project was discontinued in favor of the Ardelt project because of a complicated design, high cost, and excessive weight.",
                "amazing gun accuracy, good alpha damage, has a good trolish gun for 750 alpha damage, excellent camo, good grind",
                "big alpha damage gun is trollish, no armor, no the best gun depression"
            ),"St. Emil" to TankInfo(
                R.drawable.stureremil,
                "An experimental tank destroyer. Based on the extended chassis of the\\nVK 30.01 (H) tank, this vehicle featured a prototype 128-mm anti-tank gun. Only two vehicles were manufactured. They fought on the Southern Front from the summer through autumn of 1942. The trials demonstrated a number of problems, and the vehicle never saw mass production.",
                "amazing alpha damage, amazin gun depression",
                "low HP, no armor, no premium ammo, long reload, low ammo capacity"
            ),"Nashorn" to TankInfo(
                R.drawable.nashhorn,
                "The official designation of the Nashorn (\\\"Rhinoceros\\\") tank destroyer was 8.8 cm PaK 43/1 auf Geschützwagen III/IV (Sf). The vehicle was designated the Hornisse (\\\"Hornet\\\") until 1944. Produced on the same chassis as the Hummel heavy SPG. The vehicle first saw combat on the Eastern Front in the summer of 1943. A total of 494 vehicles were manufactured.",
                "excelent gun, decent gun arc, reload not to bad, good camo",
                "no armor, very big, no armor, mobility is bad"
            ),"Stug III G" to TankInfo(
                R.drawable.stug3g,
                "This vehicle first saw combat in France in 1940. It was produced, in several modifications, on the basis of the Pz. III tank until 1945. By 1944, 9,346 of these self-propelled guns destroyed about 20,000 enemy vehicles. Some vehicles were in operation until 1967 and were even deployed in the Six-Day War by some countries of the Arab world against Israel.",
                "low tier, no info ",
                "low tier, no info "
            ),"Hetzer" to TankInfo(
                R.drawable.hetzer,
                "In 1943, the Wehrmacht were experiencing a dire shortage of StuG III tank destroyers because the joint U.S.A.-U.K. air raids caused considerable damage to the Alkett company's factory that produced these vehicles. December 1943, a decision was made to utilize the working capacities of the BMM company in Prague to start production of tank destroyers. Components of the Pz.Kpfw. 38(t) tank were widely used in the new vehicle. The first prototypes were produced in March 1944. More than 2,800 vehicles were built by May 1945.",
                "good upper plate armor, very good camo, good aim time, good guns",
                "view range, side and rear are paper, stick engine is so bad"
            ),"Sheridan" to TankInfo(
                R.drawable.sheri,
                "Work on the vehicle started under the AR/AAV project (Armored Reconnaissance/Airborne Assault Vehicle) in January 1959. In June 1962, the Cadillac Motor Car Division delivered the first vehicle prototype to the client. The first generation of vehicles included three prototypes. Work was continued, and the vehicle eventually saw service under the M551 Sheridan designation in 1966.",
                "amazing gun, good accuracy, good DPM, can play as medium tank, fun tank",
                "large scale, not the best for spotting"
            ),"T49" to TankInfo(
                R.drawable.t49,
                "A variant of the M41 light tank with an enlarged turret and 90-mm gun. Trials started on May 5, 1954. The trials were a success, but the tank never saw mass production due to lack of interest from the military authorities.",
                "the big gun is fun, can deal up to 1000 damage to paper tanks, HEAT premium shells, great gun depression, good mobility",
                "bad camo, low accuracy on big gun, not the best light tank"
            ),"M41 Bulldog" to TankInfo(
                R.drawable.m41bulldog,
                "A further development of the T37 experimental tank. Underwent trials in 1949. In 1950, the contract for mass production was signed. The tank was produced by the Cadillac Motor Car Division, a division of General Motors.",
                "default APCR ammo has very good shell velocity, good mobility and traverse speed, good gun for dealing damage",
                "not the best light tank for spotting"
            ),"T71 CMCD" to TankInfo(
                R.drawable.t71cmcd,
                "A project of an airborne light tank. Developed by the Cadillac company from 1952 through 1956 as a replacement for the M41 tank. In 1956, due to insufficient funds, the project was discontinued in favor of the more promising T92. No prototypes were built.",
                "decent tank, nothing special can be said",
                "decent tank, nothing special can be said"
            ),"T37" to TankInfo(
                R.drawable.t37,
                "The T37 light tank was developed by Detroit Arsenal in the late 1940s to replace the M24 Chaffee. An order was placed for three prototypes. Later, some technical innovations were applied in the development of the T41 and M41 tanks.",
                "excelent gun handling, good aim, good dpm, good gun depression, good HP, good viewrange",
                "poor accuracy, large turrent, ammo rack"
            ),"Chaffee" to TankInfo(
                R.drawable.chaffee,
                "The vehicle was intended as a replacement for the M3 Stuart. The M24 entered service in September 1943. They were produced at Massey-Harris and General Motors factories until July 1945, with a total of 4,731 vehicles manufactured.",
                "fast light tank, excelent view range, decent armor, easy grind",
                "penetration, high profile, low shell velocity, bad accuracy"
            ),"M5 Stuart" to TankInfo(
                R.drawable.m5stuartusa,
                "A further modification of the M3 Stuart, the M5 entered production in April 1942. By June 1944 a total of 8,884 vehicles in two variants had been produced. The tank was used in all theaters of war.",
                "good top speed, good traverse speed, good view and signal range, good gun depression",
                "poor armor, low penetration, alpha damage is a joke"
            ),"M3 Stuart" to TankInfo(
                R.drawable.m3stuart,
                "Developed in 1938 through 1941 on the basis of the M2. Mass-produced from 1941. More than 13,000 vehicles in various versions were built, from the M3 to the M3A3, all of which were supplied to almost every allied nation under Lend-Lease. The M3 tanks were designated Stuart I by the British, while the M3A1 version received the designation Stuart III. The M3 first saw action in the battle at Sidi Rezegh.",
                "good top speed, good rate of fire, good frontal armor, good radio",
                "poor damage, penetration is very low"
            ),"M2 Light" to TankInfo(
                R.drawable.m2light,
                "Development of the vehicle based on the M2A3 model was started in December 1938. The tank featured a new two-man turret with more powerful armament. A total of 365 vehicles were manufactured from May 1940 through March 1941. In April 1942, ten additional tanks were produced for training purposes. Some vehicles were supplied to the U.K. under Lend-Lease, others were deployed with the U.S. Marine Corps and saw combat in the Pacific Theater.",
                "good speed, good view range, good gun, good armor",
                "poor accuracy, low traverse speed, tall and bad gun depression"
            ),"T1" to TankInfo(
                R.drawable.t1cunn,
                "The experimental T1E1 light tank designed by the Cunningham company was a further development of the T1 tank. By April 1928, four vehicles were produced. After the vehicle underwent trials in November 1928, a decision was made to enhance the armor, which led to creating the subsequent modification designated the T1E2.",
                "low tier, no information",
                "low tier, no information"
            ),"M48 Patton" to TankInfo(
                R.drawable.m48patton,
                "A modification of the M48 tank of 1970, developed to modernize the remaining M48 tanks in service up to the M60 tank's level. The modification featured a new engine, armament, and fire control system. Apart from armor protection, the 2,570 upgraded vehicles were practically indistinguishable from the M60 that featured the same armament and engine.",
                "very good DPM, good shell velocity and penetration, good gun handliong and aim time, good turret",
                "large turret withg cupola, low top speed, large hull, not very good armor"
            ),"M46 Patton" to TankInfo(
                R.drawable.m46patton,
                "Developed in 1948 and 1949, the M46 Patton was a modernized and improved version of the M26 Pershing. A total of 1,168 M46 tanks, in two basic variants, were manufactured between 1949 and 1951. Pattons saw wide use in the Korean War.",
                "good DPM,good apha damage, gun gun handling, great gun depression, good acceleration, good viewrange",
                "not the best camouflage, stock grind is pain, not good armor"
            ),"Pershing" to TankInfo(
                R.drawable.pershing,
                "American medium tank, named in honor of General John Pershing, who led the American Expeditionary Force during World War I. In 1944–1946 in the U.S. Army, the M26 was temporarily classified as heavy tank. Starting in February 1945 these vehicles took part in World War II; in 1950–1951 the vehicle saw combat in the Korean War.",
                "decent gun, good gun depression, good view range, good premium ammo",
                "low penetration, dpm is pad, top speed, accuracy is bad"
            ),"T20" to TankInfo(
                R.drawable.t20,
                "The first in a series of 1942–1943 U.S. medium tank designs intended as replacements for the M4 Sherman. The first prototype was produced in May 1943, and trials went on until 1944. The vehicle was not approved for mass production, but subsequent prototypes, the T22 and T23, were created on the basis of this development. They, in turn, served as precursors of the T25 and T26. Eventually, the M26 Pershing emerged on their basis and was adopted for service.",
                "good alpha damage, good penetration, good speed, cun gun depression, low profile, good camo",
                "nu armor, ammo rack, DMP, bad accuracy, bad aim time"
            ),"M4A3E8 Sherman" to TankInfo(
                R.drawable.m4a3e8sherman,
                "The M4A3E8 is one of the most mass-produced modifications of the main U.S. armored vehicle of the World War II period. The tank featured the welded hull and Ford GAA carburetor engine. A total of 11,424 vehicles with the improved HVSS suspension were produced by Fisher Tank Arsenal and Detroit Tank Arsenal from June 1942 through March 1945. The vehicle is also known as M4A3(76)W. It took part in many after-war conflicts up to the Indo-Pakistani War of 1965.",
                "good rate of fire, good DPM, cu depression, good HP",
                "no armor, tall profile, poor penetration, not very fast"
            ),"M4A1 Sherman" to TankInfo(
                R.drawable.m4sherman,
                "The first production version of the Sherman, the most common American tank, with an amazing total of 49,234 vehicles manufactured. The Sherman first saw combat in North Africa.",
                "good guns",
                "armor is not very good, tall tank, stock is very very bad"
            ),"T6 Medium" to TankInfo(
                R.drawable.t6medium,
                "Despite the launch of mass production of the M3 medium tank in the summer of 1941, the development of an improved version with a 75 mm gun in a rotating turret was started earlier in winter of the same year. Two prototypes were produced by the autumn of 1941. The military was mostly satisfied with the improved version but also requested some improvements. As a result, the vehicle lost its side doors and the commander's cupola machine gun. On September 5, 1941, the Armaments committee recommended standardizing the vehicle under the Medium Tank M4 designation.",
                "good front turret armor, good selection of guns, good view range",
                "sluggish, hull armor is weak"
            ),"T110E5" to TankInfo(
                R.drawable.t110e5,
                "Developed from 1952 as a heavy tank with more powerful armament, compared to the T-43 (M103). Restrictions were placed on the vehicle sizing as the tank was supposed to pass through the narrow tunnels of the Bernese Alps. Several designs were considered, but the project was canceled. No vehicles were ever manufactured.",
                "good gun depression, great DPM, strong turret aror, decent gun handling",
                "poor hull armor, large cupola, vulnerable ammo rack"
            ),"M103" to TankInfo(
                R.drawable.m103,
                "The development started in 1948. In 1952 the order was placed for production of 300 vehicles to fight in the Korean War. The tank was designated as M103.",
                "strong turret armor",
                "good gun deppresion and DPM, low ammo capacity"
            ),"T32" to TankInfo(
                R.drawable.t32,
                "An experimental World War II American heavy tank. Developed on the basis of the M26 Pershing and T29 tanks. Prototypes were built in 1946; however, the T32 never entered mass production.",
                "strong turret armor, good gun depresision, good hulldown",
                "hull is weak, speed is low"
            ),"T29" to TankInfo(
                R.drawable.t29,
                "Development of this experimental heavy tank started in 1944. The prototype was built in 1947. The vehicle featured the 105 mm T5E1 gun. After the war, the mass production of heavy tanks was deemed unreasonable, and only a few prototypes of this vehicle were built.",
                "strong turret armor, good gun depresision, good hulldown, good gun handling",
                "hull is weak, speed is low"
            ),"M6" to TankInfo(
                R.drawable.m6,
                "A variant of the heavy tank developed in the U.S. from May 1940, this vehicle featured a cast hull and hydromechanical suspension with a two-disc hydraulic converter. The vehicle was standardized on May 26, 1942. A total of 9 vehicles were manufactured (8 serial and 1 experimental), but never saw action.",
                "decent speed, can be achieved quickly, good pen, great gun depression, turret has decent armor",
                "armor si bad (besides turret), accuracy is bad"
            ),"T1 Heavy" to TankInfo(
                R.drawable.t1heavy,
                "Development of the T1E2 started in the U.S.A. in May 1940. The vehicle was designed as a heavy tank. The distinctive features of the vehicle were a cast hull and hydromechanical transmission with a double-disc hydraulic converter. Later, the tank was designated M6. A total of 9 vehicles (1 of which was experimental) were produced. The tank never saw action.",
                "rate of fire is good, decent speed, decent frontal armor",
                "weak side and rear armor, large siluette, stock is bad, low alpha damage is pain, crew injures"
            ),"T110E3" to TankInfo(
                R.drawable.t110e3,
                "In 1954, a conference on future heavy tanks was held in Detroit. Alongside other advanced projects, the Chrysler Corporation suggested a new tank on the basis of the TS-31 project. The main goal was to make the tank fit the Bern national tunnel. Several variants were considered, but the project was canceled.",
                "amazing armor frontally, beast in hulldown position, great alpha damage, large HP pool",
                "very slow, gun is slightlty innacurate, no turret"
            ),"T95" to TankInfo(
                R.drawable.t95,
                "In 1954, a conference on future heavy tanks was held in Detroit. Alongside other advanced projects, the Chrysler Corporation suggested a new tank on the basis of the TS-31 project. The main goal was to make the tank fit the Bern national tunnel. Several variants were considered, but the project was canceled.",
                "amazing armor frontally, beast in hulldown position, great alpha damage, large HP pool",
                "very slow, gun is slightlty innacurate, no turret"
            ),"T28" to TankInfo(
                R.drawable.t28usa,
                "The development of a superheavy vehicle with enhanced armor started in the second half of 1943. The design featured the power unit and electrical transmission of the experimental T23 medium tank. The vehicle was supposed to feature a new 105-mm anti-aircraft gun. The design was later used for the creation of the T95 assault tank. Existed only in blueprints.",
                "armor is very good frontally, guns are decent, low profile",
                "speed is very low, no turret"
            ),"T25/2" to TankInfo(
                R.drawable.t252,
                "A project for a tank destroyer with a full-rotation turret and 90-mm gun on the T23 tank chassis proposed in 1943. No prototypes were manufactured, because the T23 tank and its modifications never entered mass production due to the end of war and vehicles with electric transmission falling out of favor.",
                "tank is fairly quick, gun is decent",
                "no armor, sometimes gun is innacurate"
            ),"Hellcat" to TankInfo(
                R.drawable.hellcat,
                "Development started in 1942. In April 1943 the General Motors company produced the first prototypes. One of a few American tank destroyers manufactured on its original chassis, not on a tank chassis. The tank destroyer became the fastest armored vehicle, of this type, of World War II. A total of 2,507 vehicles were produced from July 1943 through October 1944.",
                "fast, good damage, accurate gun, aim time is decent for the tier, good camo, good view range",
                "low ammo cappacity, no armor"
            ),"Wolverine" to TankInfo(
                R.drawable.wolverine,
                "The most widely produced American tank destroyer, with a total of 6,706 vehicles manufactured from September 1942 through January 1944. More than a third of all Wolverines were supplied to the Allied nations under Lend-Lease.",
                "good choice of guns, good view range, has a turret, good speed",
                "slow with no armor, tall tank, terraine resistance"
            ),"M8A1" to TankInfo(
                R.drawable.m8a1,
                "Developed in 1941 through 1942 on the basis of the M5 Stuart light tank. This SPG is an example of the successful use of an outdated chassis. The vehicle was equipped with a 75 mm short-barrel howitzer; however, attempts were made to fit a 75 mm tank gun into a modernized turret.",
                "has a turret, good mobility, good view range and signal range, good penetration",
                "no armor"
            ),"AMX 13 105" to TankInfo(
                R.drawable.amx13105,
                "A variant of the AMX 13 developed in the late 1950s. The FL 12 turret adjusted for a 105-mm gun was mounted on the vehicle. The new variant of the AMX 13 did not see service in the French army, but was exported to other countries; the vehicle was produced in Argentina under a license. Despite the firepower was improved, the new design had several disadvantages. For example, a greater recoil saw turrets crack sometimes.",
                "auto-loader, good aim time, good gun depression, good camo",
                "no armor, bad elevation of gun, low ammo, poor view range"
            ),"AMX 13 90" to TankInfo(
                R.drawable.amx1390,
                "In the early 1960s, the 75-mm gun with a muzzle velocity of 1,000 m/s was deemed outdated. The vehicle received the new 90-mm F3 gun that used more powerful HEAT shells. This modernization did not require any significant changes to the turret or vehicle design. The first vehicles were manufactured in February, 1967. The existing vehicles underwent the gun modernization as well.",
                "auto-laoder, great camo, stock suspension is enough, good gun handling for auto-loader",
                "no armor, long aim time, bad depression, limited ammo capacity, no view range"
            ),"B-C 12 t" to TankInfo(
                R.drawable.bc12t,
                "A light tank developed by Batignolles-Châtillon in the late 1940s. The vehicle featured an oscillating turret and 75-mm gun. Never saw mass production.",
                "auto-laoder, great camo, stock suspension is enough, good gun handling for auto-loader",
                "no armor, long aim time, bad depression, limited ammo capacity, no view range"
            ),"AMX 13 75" to TankInfo(
                R.drawable.amx1375,
                "Maneuverable light tank with an oscillating turret and a 75 mm autoloader gun that could fire up to 10 shots per minute. At that time, the gun on this vehicle had superior penetration compared to the guns of most other medium tanks.",
                "auto-laoder, great camo, stock suspension is enough, good gun handling for auto-loader",
                "no armor, long aim time, bad depression, limited ammo capacity, no view range"
            ),"AMX 12 t" to TankInfo(
                R.drawable.amx12t,
                "This design of a light high-speed tank was developed in 1946. The vehicle was never actually produced, but became the basis for the well-known AMX 13.",
                "auto-laoder, great camo, stock suspension is enough, good gun handling for auto-loader",
                "no armor, long aim time, bad depression, limited ammo capacity, no view range"
            ),"AMX ELC bis" to TankInfo(
                R.drawable.amxelc,
                "Developed from 1957 through 1961 to provide French airborne troops with an air-transportable vehicle that could engage heavy tanks. The vehicle mounted a 90-mm gun and had a very low silhouette. The two crew members were seated in the turret, which could turn through 360 degrees, but only when the vehicle was not moving. Variants with different guns were planned. Only one prototype was manufactured. The vehicle never saw service.",
                "good for spotting, gun is decent , decent view range",
                "no turret, speed is meh"
            ),"AMX 40" to TankInfo(
                R.drawable.amx40duck,
                "Development started in March 1940. Almost all armor plates had a complex and curvilinear shape, and the hull featured few, if any, right angles. The AMX 40 was supposed to replace the Somua S35 and S40 in 1941–1942, but the project was discontinued in July 1940. Existed only in blueprints.",
                "for a light tank, has the armor of a heavy, damage is good, has the form of a duck",
                "very slow, poor penetrations"
            ),"AMX 38" to TankInfo(
                R.drawable.amx38,
                "Developed in 1937–1938 under the \\\"20-ton vehicle\\\" project and was intended to replace the D2 tank. The project included two turret variants: with a 37-mm or a 47-mm gun. By 1938, two vehicles were built for trials. Never saw mass production.",
                "good armor, reduced engine fire, decent damage",
                "slow, very bad gun depression, useless in +2 matchmaking"
            ),"FCM 36" to TankInfo(
                R.drawable.fcm36,
                "Developed in the mid-1930s as an infantry-support tank. It was the only French vehicle to utilize a diesel engine. By 1939, a total of 100 vehicles were manufactured.",
                "low tier, no information",
                "low tier, no information"
            ),"FT" to TankInfo(
                R.drawable.renaulftft,
                "The vehicle entered service in 1917, with 3,177 vehicles manufactured by the end of World War I and 3,800 vehicles produced in total. At the beginning of World War II, a total of 1,560 vehicles were in service.",
                "low tier, no information",
                "low tier, no information"
            ),"B-C 25 t" to TankInfo(
                R.drawable.bc25t,
                "A medium tank with an oscillating turret and a 90 mm gun. Developed by Batignolles-Châtillon in the mid-1950s. Two prototypes were manufactured. The vehicle never entered mass production.",
                "amazing damage, very good speed and camo",
                "poor ammo, poor aim time, long reload, low ammo cappacity"
            ),"B-C 25 t AP" to TankInfo(
                R.drawable.bc25tap,
                "In 1950–1951, engineers of the FAMH company (Compagnie des forges et aciéries de la marine et d'Homécourt) worked on the development of a medium tank with a 90-mm gun and a rotating turret. Work was discontinued at the preliminary design stage. Later, all elements of the vehicle were incorporated in the Batignolles-Châtillon 25 t tank.",
                "amazing damage, very good speed and camo",
                "poor ammo, poor aim time, long reload, low ammo cappacity"
            ),"A.P. AMX 30" to TankInfo(
                R.drawable.amxaltproto,
                "In 1958, the AMX company started developing a new medium tank under the Franco-German Standardpanzer (Standard Tank) program. It was supposed to be a 30-ton vehicle with high mobility and a powerful gun. Initially, the project was designated the Char 30 AP and featured a French 105-mm F1 cannon. Over the course of its development, the vehicle was constantly modified and, finally, turned into the AMX 30. The project was discontinued after one scale wooden prototype had been built.",
                "good dpm and alpha damage, very fast, gun depression",
                "eak armor, bad hull traverse, accuracy"
            ),"Somua S35" to TankInfo(
                R.drawable.somuas35,
                "Good firepower, armor protection, and mobility made the S35 one of the best tanks of its time. However, the small turret forced the commander to handle multiple tasks at once: searching for enemy targets, aiming and reloading the gun, and coordinating the actions of the crew. This workload reduced his situational awareness and decreased the vehicle’s effectiveness on the battlefield. By the time France capitulated on June 22, 1940, a total of 427 vehicles had been manufactured.",
                "low tier, no information",
                "low tier, no information"
            ),"AMX M4 54" to TankInfo(
                R.drawable.amxm454,
                "A variant of the AMX M4 heavy tank based on the Project 141 with reinforced front armor. It was planned to enhance the front armor by mounting a 280-mm armor plate. Existed only in blueprints.",
                "very good turret armor and good gun handling",
                "not much to say badly"
            ),"AMX M4 51" to TankInfo(
                R.drawable.amxm451,
                "The Project 141 was a variant of the AMX M4 heavy tank with reinforced front armor. Development started in November 1949, but the vehicle was never built. The project also included many calculations for different hulls on a similar chassis.",
                "very good turret armor and good gun handling",
                "not much to say badly"
            ),"AMX M4 45" to TankInfo(
                R.drawable.amxm445,
                "Development of this heavy tank started in 1945. The vehicle incorporated several design features of the Pz.Kpfw. VI Ausf. B Tiger II tank. The vehicle existed only in blueprints. Later the AMX M4 (1945) became a prototype for the AMX 50 100.",
                "good guns, turret is good, good speed",
                "turret armor si bad, very big and armor si bad"
            ),"ARL 44" to TankInfo(
                R.drawable.arl44,
                "Development of the vehicle started while France was still occupied by the German forces. It was an attempt to use the obsolete B1 Bis chassis and fit it with a modern, more powerful gun. The project was finished by the Atelier de Construction de Rueil Design Bureau. A total of 60 vehicles were manufactured. However, the tank was considered unsuccessful compared to similar foreign vehicles.",
                "decent armor for a heavy tank of this tier, decent gun and viewrange",
                "not the best armor"
            ),"BDR G1 B1" to TankInfo(
                R.drawable.bdrg1b1,
                "The project of a new vehicle was suggested by engineers of the Baudet-Donon-Roussel company in June 1938, but the design was never developed.",
                "good penetration and DPM, very good alpha damage, good credit printer, high HP, good gun elevation",
                "as every french, not the best armor"
            ),"B1" to TankInfo(
                R.drawable.b1,
                "Development was started in 1921. The vehicle entered service in 1934. A total of 403 tanks were manufactured at Renault, FCM, Schneider, FAHM, and AMX factories. The vehicle was upgraded numerous times. Despite the outdated configuration, this tank earned a reputation as an invincible vehicle with reliable armor in battles against German troops from May through June 1940.",
                "decent armor, good HP, good aim time, decent top speed",
                "nothing to be said"
            ),"Foch B" to TankInfo(
                R.drawable.fochb,
                "The project of the AMX 50 Foch tank destroyer with a 120 mm gun provided for installing the automatic loading system. However, after work on the AMX 50 120 with the same armament started, the project was deemed potentially inefficient. Existed only in blueprints.",
                "amazing mobility and burst damage capability",
                "horrible platform and reload time"
            ),"Foch" to TankInfo(
                R.drawable.foch,
                "From the late 1940s to the early 1950s, France was developing heavy tank destroyers. This project was developed under the influence of German tank destroyers of WWII. The vehicle featured a 120-mm gun. At least one prototype was built, which took part in the military parade in Paris on July 14, 1950. Never saw mass production.",
                "amazing mobility and burst damage capability",
                "horrible platform and reload time"
            ),"AMX AC 48" to TankInfo(
                R.drawable.amxac48,
                "A draft project of a heavy tank destroyer, developed in 1947–1948 under the influence of the Jagdpanther. The vehicle was to feature a 120 mm gun as the main armament. The distinctive feature of the vehicle was three 20 mm MG 151/20 machineguns, mounted on top of the fighting compartment and rear. Existed only in blueprints.",
                "amazing mobility and burst damage capability",
                "horrible platform and reload time"
            ),"AMX AC 46" to TankInfo(
                R.drawable.amxac46,
                "Proposed plan for a heavy tank destroyer on the basis of the M4. Development was started in 1946. The configuration of the vehicle had a strong resemblance to the Jagdpanther. Existed only in blueprints.",
                "amazing mobility and burst damage capability",
                "horrible platform and reload time"
            ),"AMX V 39" to TankInfo(
                R.drawable.amx38,
                "A prototype of the ARL 40. The vehicle was intended as an assault SPG. Mass production was to be launched in 1940 but never started due to the occupation of France.",
                "amazing mobility and burst damage capability",
                "horrible platform and reload time"
            ),"S35 CA" to TankInfo(
                R.drawable.s35ca,
                "Developed on the basis of the obsolete R35 and S35 vehicles. The simple design allowed the open cabin with a British 17-pounder gun to be mounted on the chassis. However, the project was discontinued during the design phase due to a shortage and obsolescence of pre-war chassis, as well as an ineffective and unconventional gun.",
                "very good gun penetration, good guns, good gun traverse",
                "weak armor, not good view range"
            ),"SAu 40" to TankInfo(
                R.drawable.luchs,
                "A project for an SPG to support light mechanized units. Designed from 1935 through 1937 on the basis of the S35 and S40 vehicles. The design featured a modified upper part of the armored hull with the 75-mm casemate Mle 1929 gun. The top of the cabin featured a single-seated turret with the 7,5-mm Mle 1931 machinegun. The mass production of the SAu 40 was requested in October 1939. A prototype (and, according to some historical sources, four more pilot SAu 40) were sent to the frontline and used in action in June 1940.",
                "low tier, nothing special to be said",
                "low tier, nothing special to be said"
            ),"FV4005" to TankInfo(
                R.drawable.fv4005,
                "This tank destroyer project was developed on the basis of the Centurion Mk3 tank in the early 1950s. The vehicle was initially tested with a mechanized ammo rack. However, the ammo rack did not fit the turret, and it was canceled. One prototype was manufactured and underwent trials, but the vehicle never entered mass production.",
                "amazing capability for dealing damage, fun tank to play with",
                "shell cost, reload time, no armor and low speed backwards"
            ),"Conway" to TankInfo(
                R.drawable.conway,
                "The tank destroyer prototype was created on the basis of the Centurion medium tank. Developed for use in combat against heavy tanks of that period, including the IS-3.",
                "amazing gun , nice platform good mobility",
                "no armor"
            ),"Charioteer" to TankInfo(
                R.drawable.charioteer,
                "The upgraded version of the Cromwell tank. A new turret and a 20-pounder 83.4-mm caliber gun made this tank a potent adversary. According to different sources, between 200 and 442 Cromwell tanks were converted to the new Charioteer standards.",
                "nice platform and mobility, good reload time, nice fire rate",
                "no armor and no alpha damage"
            ),"Challenger" to TankInfo(
                R.drawable.challenger,
                "Created in 1942 based on the extended chassis of the Cromwell. One definite advantage of the new vehicle was its 17-pounder gun, which was the most powerful British gun at that time. This allowed the tank destroyer to effectively hit the enemy at distances up to 1,000 meters. However, the vehicle had a large silhouette and the chassis was overloaded. A total of about 200 vehicles were built.",
                "nice platform and mobility, good reload time, nice fire rate",
                "no armor and no alpha damage"
            ),"Achilles" to TankInfo(
                R.drawable.achilles,
                "The Achilles was a British variant of the American M10 GMC tank destroyer. Standard M10 gun was replaced by a more powerful British anti-tank Ordnance Quick-Firing 17-pounder gun. That allowed the tank destroyer to effectively battle against the German vehicles. In 1944 the re-equipping of the tank destroyer was launched, with a total of 1,100 vehicles re-equipped by the end of the war.",
                "good DPM, good pen, decent mobility, nice accuracy",
                "slow turret traverse, low alpha damage, pooer gun depression"
            ),"AT 2" to TankInfo(
                R.drawable.at2,
                "A design for an assault tank to break through enemy defensive lines. The design was completed by May 15, 1943. No prototypes were built, but the project helped to set the stage for another heavy assault tank, the A39 Tortoise.",
                "",
                ""
            ),"Valentine AT" to TankInfo(
                R.drawable.valentine,
                "This experimental tank destroyer had the gun mounted in the middle of the hull behind a gun shield. A prototype was built, but work on the vehicle was discontinued when a new tank destroyer was developed, based on the Valentine and mounting the powerful 17-pounder.",
                "good armor, bunker",
                "very slow, no DMG, no turret"
            ),"Manticore" to TankInfo(
                R.drawable.manti,
                "A light tank project with an oscillating turret for providing anti-tank defense behind infantry positions. No prototypes were built, and it existed only in blueprints.",
                "standard penetration is very good, amazing spotting, good speed and camo",
                "horrible dpm, low ammo capability, bad gun elevation"
            ),"GSOR" to TankInfo(
                R.drawable.gsor,
                "The GSOR3301 AVR FS presents a variant of a light tank developed in the 1960s. The project considered different variants of the vehicle with different armament, engines, and turrets. It existed only in blueprints.",
                "very good standard penetration, good gun depression, good camo, very small tank",
                "very low DPM, low ammo capacity,low penetration, vulnerable for ramming"
            ),"LHMTV" to TankInfo(
                R.drawable.lhmtv,
                "A light tank project with minimal weight, allowing for easy air transportation of the vehicle. The tank was developed for three years, with one prototype manufactured.",
                "best camo in class by tier, good gun depression, small tank,  good pen",
                "poor DPM, low ammo capacity, poor elevation"
            ),"Setter" to TankInfo(
                R.drawable.setter,
                "In 1964, the General Staff laid out the requirements for a new battle reconnaissance vehicle. Research and development, a full-scale prototype, and two stands for testing engineering solutions (one for testing of the cooling system, the other for testing the suspension and transmission) were finished in 1965. Further development resulted in the production of CVR(T) vehicles and their adoption into service with the British Army.",
                "good camo, good gun depression, small tank, good pen",
                "DPM and gun accuracy"
            ),"Crusader" to TankInfo(
                R.drawable.crusader,
                "The Crusader was developed by Nuffield Mechanizations Ltd. from 1938 through 1940. More than 5,300 vehicles were mass-produced from 1941 through 1943. They were most extensively used in the North African campaign in 1941–1942.",
                "very good fire rate, DPM is amazing, low profile, can hide in bushes easily, decent view range",
                "slow, poor armor, gun depression, low penetration"
            ),"Covenanter" to TankInfo(
                R.drawable.covenanter,
                "A new cruiser tank that featured such innovations as an opposed-piston engine, front placement of cooling radiators, and the wide use of welding. The vehicle was ordered for production on April 17, 1939. A total of 1,771 tanks in four base variants were mass-produced. The Covenanter tanks were used mostly for training purposes in the U.K. from 1940 through 1943.",
                "good mobility, 15 degrees of gun depression, god rate of fire",
                "weak armor, poor pen, very bad dpm"
            ),"Cruiser IV" to TankInfo(
                R.drawable.cruiser4,
                "An upgraded version of the cruiser tank Mk. III. The tank featured armor enhanced by the additional screens. The Cruiser Mk. IV fought in France in 1940 and in the early stages of the North African Campaign. This vehicle last saw action in the winter of 1941–1942. A total of 655 tanks were mass-produced.",
                "low tier,nothing special about it",
                "low tier,nothing special about it"
            ),"Cruiser III" to TankInfo(
                R.drawable.cruiser3,
                "The vehicle was developed on the basis of the M1931 Christie tanks, purchased by the British Army in 1936. The vehicle was intended to be a fast, lightly-armored breakthrough tank. The modified design was deemed successful and became the basis for other cruiser tanks. A total of 65 vehicles were built. They saw combat in France and North Africa in 1940–1941.",
                "low tier,nothing special about it",
                "low tier,nothing special about it"
            ),"Cruiser II" to TankInfo(
                R.drawable.cruiser2,
                "The A10 Cruiser Mk. II was a further development of the A9 modification designed by John Carden. The A10 had enhanced armor and no machinegun turrets. A prototype was built in July 1937 and adopted for service as a heavy cruiser tank. A total of 175 vehicles were ordered between 1938–1939, which were manufactured by September 1940. The vehicles saw combat in France (1940), Greece (1941) and North Africa (1941).",
                "low tier,nothing special about it",
                "low tier,nothing special about it"
            ),"Cruiser I" to TankInfo(
                R.drawable.cruiser1,
                "The first cruiser tank in the British arsenal. Development was started in 1934 by Vickers. A total of 125 vehicles were mass-produced from 1936 through 1937.",
                "low tier,nothing special about it",
                "low tier,nothing special about it"
            ),"Centurion AX" to TankInfo(
                R.drawable.centurionax,
                "The Action X turret was planned to be used for later variants of the Centurion tank. One prototype of the turret was mounted on the chassis of the Centurion Mark 7, and another was used for ballistic trials. The vehicles with the Action X turret never entered mass production or saw service.",
                "good top speed, hight power to weight ratio, good mobility, good armor, HESH shells",
                "ammo rack, large size, vulnerable to arty"
            ),"Centurion 7/1" to TankInfo(
                R.drawable.centrurion1,
                "This upgraded version of the Centurion was developed by Leyland Motors and featured an expanded hull, improved cabin design, larger fuel tanks, and enhanced armor.",
                "very good damage, pen, shell velocity and accuracy, good DPM, good turret armor, nice viewrange",
                "poor gun handling, poor camouflage, module hits are vulnerable"
            ),"Centurion 1" to TankInfo(
                R.drawable.centrurion1,
                "Development of the Centurion started in 1943. The vehicle was designed as a \\\"universal tank\\\" to replace existing infantry and cruiser tanks. The Centurion was the first British vehicle that featured sloped armor plates. The tank entered service in 1947. A total of 100 vehicles of this series were manufactured from 1945 through 1946.",
                "good pen, good gun depression, strong mantlet, good view range, good HP",
                "poor hull armor, large size, horrible stock"
            ),"Comet" to TankInfo(
                R.drawable.comet,
                "A further development of the Cromwell cruiser tank, this British tank saw service in World War II. A total of 1,186 vehicles were manufactured from September 1944 through to late 1945.",
                "nice fire rate, good accuracy, nice gun depression, good top speed, good viewrange",
                "weak armor, low aplha damage, poor gun handling"
            ),"Cromwell" to TankInfo(
                R.drawable.cromwell,
                "The Cromwell was developed in 1941–1942 by BRC&W. A total of 1,070 vehicles were mass-produced from late 1943 through 1945. They were extensively used by the British Army in the Northwest Europe Campaign of 1944–1945.",
                "amazing mobility, good camo, good viewrange,good alpha damage, good pen, good flanker",
                "weak armor, poor shell velocity, bad radio signal, gun handling is very very bad"
            ),"Cavalier" to TankInfo(
                R.drawable.cavalier,
                "This project was developed by Nuffield Mechanizations and Aero in 1941 based on the Crusader's parts and components. Due to a lack of vehicles, Britain started mass production without trials. A total of 500 vehicles were manufactured.",
                "high alpha damage, fast, decent terrain resistence",
                "no agility, gun depression, weak radio, cannot scout"
            ),"Matilda" to TankInfo(
                R.drawable.matilda,
                "Developed from 1936 through 1938. A total of 2987 vehicles were manufactured by August 1943. It was the only British tank to remain in service throughout World War II.",
                "nice HP, nice armor, good elevation and depression, nice gun dispersion",
                "it is very slow and no alpha damage"
            ),"Super Conqueror" to TankInfo(
                R.drawable.superconq,
                "A variant of the Conqueror tank with extra armor protection. Manufactured during the first half of the 50s and used for testing the Dart and Malkara guided anti-tank missiles. During testing, the vehicle played the role of a heavy tank that could potentially appear in the future. Never saw mass-production.",
                "amazing tureet armor and one of the best guns in the game",
                "turret armor is kinda weak and alpha damage is low"
            ),"Conqueror" to TankInfo(
                R.drawable.conqueror,
                "Developed from 1949 through 1952, this tank was intended to confront new Soviet heavy tanks. A total of 185 vehicles were mass-produced from 1955 through 1959.",
                "amazing tureet armor and one of the best guns in the game",
                "turret armor is kinda weak and alpha damage is low"
            ),"Caernarvon" to TankInfo(
                R.drawable.caerv,
                "Development of this infantry support tank started in 1944. The first prototype was ready for trials in 1952. A total of 21 tanks were manufactured but never entered service. Some of the vehicles were later converted into Conqueror heavy tanks.",
                "amazing tureet armor and very good DPM + rate of fire",
                "turret armor is kinda weak and alpha damage is low"
            ),"Black Prince" to TankInfo(
                R.drawable.blackprince,
                "Developed from 1943 through 1945 on the basis of the Churchill tank. It featured a wider and elongated hull to carry the upgraded turret with a 17-pounder gun. A total of six Black Prince prototypes were produced from 1944 through 1945. A few vehicles underwent trials but never saw action.",
                "great DPM, rate of fire",
                "very slow, armor is not relieble"
            ),"Churchill VII" to TankInfo(
                R.drawable.churchill8,
                "A modification of the A22 with enhanced armor. First vehicles of this type were produced just before the Allied landing in Normandy. This model was also the basis for the Churchill Crocodile flame-throwing tank.",
                "great DPM, rate of fire",
                "very slow, armor is not relieble"
            ),"Churchill I" to TankInfo(
                R.drawable.churchill1,
                "The A22 prototype was built by Vauxhall Motors in the fall of 1940. The vehicle first entered mass production in the summer of 1941. Early modifications had no track fenders, a different fan, and a 3-inch howitzer in the hull. A total of 300 Churchill I tanks were manufactured.",
                "great DPM, rate of fire",
                "very slow, armor is not relieble"
            ),"Vz. 55" to TankInfo(
                R.drawable.vz55,
                "A blueprint project for a heavy tank from the late 1950s to the early 1960s. It combined concepts of the Soviet and Czechoslovakian tank-building schools with original solutions. The project was discontinued due to the unification of armament of Warsaw Pact countries. No prototypes were built.",
                "amazing tank overall, good burst damage, amazing alpha damage, good turret armor and very good mobility",
                "gun handling is kinda poor, reload time"
            ),"TNH T Vz. 51" to TankInfo(
                R.drawable.tnhvz51,
                "In the early 1950s, the ČKD company developed a project of a heavy tank for the Czechoslovakian Army based on concepts of the Soviet and German tank-building schools combined with its own ideas. The project was discontinued due to the unification of armament of Warsaw Pact countries. No prototypes were ever built.",
                "amazing tank overall, good burst damage, amazing alpha damage, good turret armor and very good mobility",
                "gun handling is kinda poor, reload time"
            ),"TNH 105/1000" to TankInfo(
                R.drawable.tnh1051000,
                "In the latter half of the 1940s, the ČKD and Škoda engineers developed a joint project of a heavy tank for the Czechoslovakian Army and for possible export purposes. The TNH 105/1000 design was based on available concepts of German and Soviet tank-building and original ideas of Czechoslovakian engineers. In November 1949, the Communist Party of Czechoslovakia decided to discontinue the development of its own vehicles and switched to producing a licensed copy of the Soviet T-34-85 tank. All activity on the TNH 105/1000 was canceled. No prototypes were ever built.",
                "burst damage and mobility",
                "platform and armor is bad"
            ),"Vz. 44-1" to TankInfo(
                R.drawable.vz441,
                "A project for a heavy tank developed in the mid-1940s based on concepts developed by Škoda at the request of Porsche. The plans called for using an electromechanical transmission and a diesel engine. Existed only in blueprints.",
                "burst damage and mobility",
                "platform and armor is bad"
            ),"TVP T 50/51" to TankInfo(
                R.drawable.tvp5051,
                "A joint project by Škoda and ČKD. Discussions to determine the tank’s characteristics started on January 14, 1950, but the project was discontinued on March 4, 1950 by the Czechoslovakian Ministry of Defense. No prototypes were manufactured. Existed only in blueprints.",
                "amazing DPM, clip reload is amazing, good shells, good depression, good viewrange, good aim time and accuracy",
                "not the best alpha damage, penetration is bad, no armor, large profile"
            ),"Skoda T 50" to TankInfo(
                R.drawable.skodat50,
                "A further development of the post-war concept for the Czechoslovakian general purpose tank. The project was developed by Škoda in 1947. In 1948, significant changes were introduced to the project. There were plans to mount a 100 mm gun. The project was discontinued after one full-size wooden prototype had been built.",
                "amazing DPM, clip reload is amazing, good shells, good depression, good viewrange, good aim time and accuracy",
                "not the best alpha damage, penetration is bad, no armor, large profile"
            ),"TVP VTU" to TankInfo(
                R.drawable.tvpvtu,
                "A medium tank project, proposed by the Škoda company as part of the general purpose vehicle concept developed in Czechoslovakia. No prototypes were manufactured.",
                "gun is ok",
                "platform is very bad, it is not a good tank to play"
            ),"T-34/100" to TankInfo(
                R.drawable.t3485,
                "Development project of the Soviet T-34-85 tank, proposed in April 1954. The Czechoslovakian variant of the vehicle was to receive a 100 mm gun and an enlarged turret. Production was planned to start in 1955; however, the work never started.",
                "excelent gun, alpha damage and penetration are very good, has HEAT as permium ammo",
                "nothing bad about it"
            ),"Skoda T 25" to TankInfo(
                R.drawable.skodat25,
                "A further development of the medium T-24 tank, this vehicle was developed for the German Army in 1942. The project was deemed outdated. No prototypes were manufactured.",
                "autoloader with fast clip, good gun depression, good sprrd",
                "low alpha damage, hull traverse is bad, vulnerable to module damage, no armor"
            ),"Skoda T 24" to TankInfo(
                R.drawable.skodat24,
                "Developed in occupied Czechoslovakia for the Wehrmacht in 1941. The project featured sloped armor plates and enhanced armament based on the Soviet T-34. The project was deemed outdated. No prototypes were manufactured.",
                "moibler, good signal range, good penetration",
                "slow reload time, stock gun is bad, worst dpm, big cupola, ammo rack"
            ),"ST vz. 39" to TankInfo(
                R.drawable.stvz39,
                "V-8-H was a further development of the PS-II-b medium tank and the P-II-b light infantry tank. The vehicle underwent trials from the summer of 1937 through the spring of 1938. In the fall of 1938, additional trials were held. After all faults were removed, the tank saw service under the designation ST vz.39. The vehicle never entered mass production.",
                "small tank, good camo, accurate, good gun depression",
                "low damage per shot, low DPM, armor is weak, slow"
            ),"ST vz. 38" to TankInfo(
                R.drawable.stvz38,
                "Developed by the ČKD design group in 1938, this light tank saw service in the Wehrmacht under the designation Pz.Kpfw. 38 (t). It is considered to be the best Czechoslovakian vehicle: a total of 1,400 tanks of eight modifications were produced.",
                "low tier, nothins special to be said",
                "low tier, nothins special to be said"
            ),"ST vz. 34" to TankInfo(
                R.drawable.stvz34,
                "Developed by the Škoda company. The vehicle saw service in the Czechoslovakian army as a cavalry and infantry support vehicle. From 1936 through 1937, 298 vehicles were manufactured.",
                "low tier, nothins special to be said",
                "low tier, nothins special to be said"
            ),"Kolohousenka" to TankInfo(
                R.drawable.kolohou,
                "The light wheeled caterpillar tank developed on the chassis of the Hanomag WD-50 tractor. The vehicle never saw service because it was deemed obsolete after the results of trials. Several vehicles, in three variations, were manufactured; three of them were supplied to Italy and the U.S.S.R. As of the time of the German occupation, the only vehicle left in Czechoslovakia was used as a monument.",
                "low tier, nothins special to be said",
                "low tier, nothins special to be said"
            ),"STB-1" to TankInfo(
                R.drawable.stb1,
                "In the early 1960s, Mitsubishi started development of a new tank that complied with government requirements for armament and defense. The first two prototypes of the STB-1 were manufactured in September 1969 for trials. In 1970 the prototypes were shown to journalists and participated in a military parade.",
                "amazing tank, high DPM, good turret armor, 15 degrees of gun depression, good hitpoints, great mobility",
                "not very accurate, top speed, module damage, crew damage"
            ),"Type 61" to TankInfo(
                R.drawable.type61,
                "The first tank developed in Japan after WWII. It was developed from 1954 through 1961 on the basis of the American M47 tank. The dimensions of the vehicle were decreased and the armor was reduced. As a result, the weight of the tank was lighter, and the tank complied with the requirements of the Japanese armed forces.",
                "good DPM, good gun depression, good viewrange, HP pool, good mobility",
                "tall tank, armor is bad, bad gun elevation, poor top speed"
            ),"STA-1" to TankInfo(
                R.drawable.sta1,
                "The first Japanese post-war tank was developed on the basis of U.S. vehicles. The STA-1 was developed, taking into consideration Japanese terrain, rail network specifications, and anthropometric data of Japanese tankers. Only one prototype was built, in December 1956.",
                "good DPM, good gun depression, good viewrange, HP pool, good mobility",
                "tall tank, armor is bad, bad gun elevation, poor top speed"
            ),"Chi-Ri" to TankInfo(
                R.drawable.chiri,
                "A single prototype was manufactured at the beginning of 1945. The vehicle underwent trials and was recommended for service. However, Japan was defeated before mass production could begin. After the surrender, the prototype was confiscated by American occupation authorities and was shipped to the United States.",
                "3 shot autoloader, good aiming time, good gun depression, decent viewrange",
                "large, no armor, low speed"
            ),"Chi-To" to TankInfo(
                R.drawable.chito,
                "Type 4 Chi-To medium tank was the first Japanese tank\\nto be developed with an emphasis on anti-tank combat capabilities. Although the vehicle entered development in 1941, prior to the Type 3 Chi-Nu medium tank, material shortages and war situation resulted in only 2 prototypes\\nbeing completed before the end of the war.",
                "decent tank for the tier, nothing special",
                "decent tank for the tier, nothing special"
            ),"Chi-Nu" to TankInfo(
                R.drawable.chinu,
                "The Type 3 Chi-Nu medium tank is a modification of the Type 1 Chi-He with a new turret and gun. The tank was the most powerful among wartime Japanese mass-produced vehicles. However, only 60 vehicles were manufactured due to shortages of components and materials.",
                "gun depression, good firepower, nice accuracy, good agility",
                "armor is very bad, top speed is horrible, bad grind"
            ),"Chi-He" to TankInfo(
                R.drawable.chihe,
                "The Type 1 Chi-He medium tank was a further development of the Type 97 Chi-Ha. It was developed in 1941, but due to the lack of steel, production did not start until 1943. A total of 170 tanks were manufactured. Most of them were held for the defense of Japan and never saw combat.",
                "decent armor, good gun depression, nice camo value",
                "armor not relieble on higher tier, low penetration, stock engine is a pain"
            ),"Type 5 Heavy" to TankInfo(
                R.drawable.type5heavy,
                "The Type 5, also known as the Type 2605, was one of the variants of the O-I superheavy tank, developed during WWII. The vehicle was planned to be used for breaking through fortified enemy lines and for coastal defense.",
                "good armor if enemies does not load gold ammo, hood HP pool",
                "slow and very big, against gold you are a meatball"
            ),"Type 4 Heavy" to TankInfo(
                R.drawable.type4,
                "The Type 4, also known as the Type 2604, is a variant of the superheavy O-I tank that was developed during World War II. The vehicle was designed for breakthrough attacks on enemy fortifications as well as for coastal defense.",
                "good armor if enemies does not load gold ammo, hood HP pool",
                "slow and very big, against gold you are a meatball"
            ),"O-Ho" to TankInfo(
                R.drawable.oho,
                "The O-Ho was a further development of the idea of a superheavy tank with improved armor protection. The vehicle had a good set of guns that allowed for all-angle fire. The project was discontinued due to the war ending. No finished prototypes were built and the vehicle never saw action.",
                "good armor if enemies does not load gold ammo, hood HP pool",
                "slow and very big, against gold you are a meatball"
            ),"O-Ni" to TankInfo(
                R.drawable.oni,
                "A further development of the O-I with improved anti-tank guns. The military command's plan was to improve the earlier models and achieve a higher level of armor protection on the vehicle. Existed only in blueprints. No finished prototypes were built and the vehicle never saw action.",
                "good armor if enemies does not load gold ammo, hood HP pool",
                "slow and very big, against gold you are a meatball"
            ),"O-I" to TankInfo(
                R.drawable.oi,
                "Development of the superheavy tank was started after the Battles of Khalkhyn Gol in 1939. The vehicle was designed as a maneuverable fire unit that was unprecedented in scale—its hull alone weighed 100 tons. Only one prototype was built, without a turret and made of construction steel. Trials were discontinued due to the unreliable engine; at the end of 1944, the prototype was scrapped. The 15 cm howitzer was to be mounted on the tank after its assembly.",
                "good armor if enemies does not load gold ammo, hood HP pool",
                "slow and very big, against gold you are a meatball"
            ),"Ho-Ri 3" to TankInfo(
                R.drawable.hori3,
                "A heavy assault tank destroyer, developed based on designs of the Japanese Ho-Ri 1. Here, the armor thickness was increased, and the front part of the hull and suspension were significantly changed. The vehicle was never produced, and by the end of World War II, only a wooden prototype had been built.",
                "",
                ""
            ),"Ho-Ri 1" to TankInfo(
                R.drawable.hori1,
                "In the early 1940s, Japan began working on developing their own tank destroyers. One of these projects was for the Ho-Ri 1. The fighting compartment of this vehicle was located in the rear, and the rearranged chassis of the Chi-Ri medium tank was supposed to be used as a suspension. There were also plans to mount an experimental 105 mm gun. However, development was discontinued in favor of other projects.",
                "",
                ""
            ),"Ho-Ri 2" to TankInfo(
                R.drawable.hori2,
                "The designs for this experimental Japanese tank destroyer used the Chi-Ri medium tank as a base. By 1945, tank destroyers were being developed with the primary goal of being able to fight enemy vehicles, so there were plans to mount a powerful 105 mm anti-tank gun as the armament for the Ho-Ri 2. The vehicle was also supposed to be quite mobile. Due to the fact that work on the Chi-Ri was delayed, engineers did not manage to construct a prototype of the Ho-Ri 2 before the end of World War II.",
                "",
                ""
            ),"Chi-TO SP" to TankInfo(
                R.drawable.chitospg,
                "Designs of this assault tank destroyer used the Chi-To medium tank as a base. The suspension made it possible to mount powerful artillery systems, such as the 10 cm Cannon Type 92 gun, capable of fighting against most vehicles of that time. The gun was mounted in a fully closed fighting compartment with anti-projectile armor, but the basic chassis remained virtually unchanged. Due to the unreliability of the vehicle, as well as the existence of the more promising Chi-Ri suspension, the project was canceled at the drafting stage.",
                "",
                ""
            ),"Ji-Ro" to TankInfo(
                R.drawable.jiro,
                "In 1940, Japan started developing designs for a new tank destroyer. For this vehicle, they used a modified chassis of the Type 95 Ro-Go, which never went into production. Due to its large size, high-caliber guns could be placed on the vehicle, and a choice was made in favor of the 105 mm Type 92 field gun, which was already being used in the army. Its engine compartment was placed in the front of the hull, and the closed fighting compartment was located in the rear. However, work on the vehicle was discontinued around the beginning of 1943 due to heightened requirements for mobility and armor.",
                "",
                ""
            ),"Ho-Ni III" to TankInfo(
                R.drawable.honi3,
                "At the beginning of 1944, Hitachi Ltd. was tasked with developing a tank destroyer. This small series TD based on the Type 97 Chi-Ha tank was designated the Type 3 Ho Ni III. Instead of a rotating turret, a stationary welded cabin was mounted on the ready-made chassis. Its combat weight reached 17 tons but this did not affect its mobility. A total of 31 vehicles were produced. However, due to the Japanese capitulation, these tank destroyers never saw combat.",
                "",
                ""
            ),"R. Otsu" to TankInfo(
                R.drawable.otsu,
                "Developed from 1925 through 1928 in France as an upgraded modification of the Renault FT. The modernization project was finished and the vehicle saw mass production. In 1929, a total of 10 vehicles were purchased by Japan and were designated the Otsu-Gata Sensha (Tank B Model). The Japanese vehicles were slightly modernized and were widely used both in action and for training purposes.",
                "low tier, nothing special about it",
                "low tier, nothing special about it"
            ),"Ha-Go" to TankInfo(
                R.drawable.hago,
                "Japanese light tank also known as the Type 95. Developed from 1933 through 1935 as a cavalry support vehicle. However, the tank was often used to support infantry. The first prototype was built by Mitsubishi. The vehicle entered mass production in 1936, and a total of 2,378 vehicles were manufactured through 1943.",
                "low tier, nothing special about it",
                "low tier, nothing special about it"
            ),"Chi-Ha" to TankInfo(
                R.drawable.chiha,
                "Developed by Mitsubishi from 1935 through 1937. The vehicle was mass-produced from 1938 through 1942, alongside an upgraded Shinhoto Chi-Ha from 1941 through 1942. A total of 1,220 vehicles of both types were manufactured. The Chi-Ha and the Shinhoto Chi-Ha tanks were widely used by Japanese forces in China, the Pacific Theater, and the Kuril Islands. After the surrender of Japan, these vehicles were used by both PLA and Kuomintang forces in the Chinese Civil War from 1946 through 1949.",
                "low tier, nothing special about it",
                "low tier, nothing special about it"
            ),"60TP" to TankInfo(
                R.drawable.tp60,
                "A project for a heavy tank developed in 1956 by Richard Lewandowski who was a cadet at the Military Technical Academy of Warsaw. The tank was supposed to weigh 60 tons and feature 200 mm armor. Development was discontinued at the drafting stage.",
                "nice tank, good aplha damage, turret armor is good",
                "gun handling is very bad"
            ),"50TP" to TankInfo(
                R.drawable.tp50,
                "A project for a heavy tank proposed by cadet Tadeusz Tyszkiewicz at the Military Technical Academy of Warsaw in the early 1950s. The new vehicle was supposed to weigh up to 50 tons. Existed only in blueprints.",
                "nice tank, good aplha damage, turret armor is good",
                "gun handling is very bad"
            ),"53TP" to TankInfo(
                R.drawable.tp53,
                "A proposed plan for a heavy tank that was to be developed in 1940. Due to the outbreak of World War II, development was discontinued at the blueprints stage.",
                "strong top gun, nice apha damage, good mobility, good gun depression",
                "hull armor, HP is low, long stock grind"
            ),"45TP" to TankInfo(
                R.drawable.tp45,
                "A proposed project for a heavy tank. This vehicle was supposed to be a further development of the Habich medium tank. The hull was to feature thicker sloped armor plates and involved equipping a more powerful gun. However, a prototype was never built.",
                "good gun handling and nice mobility",
                "HP pool and ammo rack"
            ),"CS-63" to TankInfo(
                R.drawable.cs63,
                "A medium tank intended as a replacement for the T-34-85. Developed in Poland, it combined available designs of the Soviet tank-building school with original ideas. However, in 1968, T-55 production was launched in the country and the project was discontinued. No prototypes were built.",
                "has very good mobiliy, turbo mode, good dpm, good gun depression, good HP, decent camo",
                "aim time si very bad in turbo mode, does not have anything special "
            ),"CS-59" to TankInfo(
                R.drawable.cs53,
                "In the first half of the 1950s, the Military Technical Academy of Warsaw was developing several projects of medium tanks. These projects were greatly influenced by Soviet tank-building. During the development process, Polish experts adopted the technical and configurational concepts used in the experimental, mass-produced Soviet T-54 tank. Existed only in blueprints.",
                "good DPM, good gun depression, good acceleration, good camo",
                "lomg aim time, modules damage, not good viewrange"
            ),"CS-44" to TankInfo(
                R.drawable.cs44,
                "A project of a medium tank developed by students of Warsaw University of Technology in the latter half of the 1940s. The project was based on concepts from Soviet and French tank-building. Existed only in blueprints.",
                "good alpha damage, good gun depression, good camo, good for ramming",
                "bad DPM and gun depression with one of the guns, module amage, armor is not good"
            ),"B.U.G.I" to TankInfo(
                R.drawable.bugi,
                "The B.U.G.I. uses the first letters of the last names of the engineers who developed this project (Bujnowicz, Ulrych, Grabski, and Iwanicki). The engineers took the concept of the KSUS II medium tank but adjusted the new vehicle to feature a one-turret design. The project was sent for consideration during the Grudziądz conference in 1938–1939. The project was finalized in May 1939 in the Armed Forces Training Center in Modlin. In mid-August 1939, the engineering group presented the vehicle concept and blueprints. However, further development was discontinued due to World War II.",
                "aamzing tank for this tier, gun is amazing",
                "ground ressistence, weak modules, armor is not the best"
            ),"25TP" to TankInfo(
                R.drawable.tp25,
                "Development of a medium tank (working designation 20/25TP) started in Poland in January 1937. The Armament and Technical Equipment Committee of the Polish Ministry of Military Affairs (KSUST, as abbreviated in Polish) suggested two versions of the vehicle. The second version featured 50 mm armor and an improved power unit. Existed only in blueprints.",
                "mobile, relieble gun, good gun depression,",
                "huge tank, hull armor is bad, stock is horrible"
            ),"10TP" to TankInfo(
                R.drawable.tp10,
                "A project for a wheeled caterpillar cruiser tank developed in the late 1930s. The project was based on the Christie tank. The tank prototype passed trials in 1938–1939, but its development was discontinued once World War II started. The prototype was destroyed.",
                "low tier, nothing special about it",
                "low tier, nothing special about it"
            ),"7TP" to TankInfo(
                R.drawable.luchs,
                "The 7TP was a modification of the Vickers Mk.E British tank produced in Poland under license. Unlike the original, this vehicle featured a more powerful PZInż. 235 engine (Saurer VBLDb), an improved turret, and better armament. The first tanks manufactured had two turrets. In 1935, the Swedish Bofors company signed an agreement to produce a turret and gun that were intended for a single-turret model of the tank. The first prototype of the single-turret 7TP tank underwent trials in February 1937 and mass production started. A total of 110 vehicles were built during September 1939.",
                "low tier, nothing special about it",
                "low tier, nothing special about it"
            ),"4TP" to TankInfo(
                R.drawable.tp4,
                "The 7TP was a modification of the Vickers Mk.E British tank produced in Poland under license. Unlike the original, this vehicle featured a more powerful PZInż. 235 engine (Saurer VBLDb), an improved turret, and better armament. The first tanks manufactured had two turrets. In 1935, the Swedish Bofors company signed an agreement to produce a turret and gun that were intended for a single-turret model of the tank. The first prototype of the single-turret 7TP tank underwent trials in February 1937 and mass production started. A total of 110 vehicles were built during September 1939.",
                "low tier, nothing special about it",
                "low tier, nothing special about it"
            ),"UDES 15/16" to TankInfo(
                R.drawable.udes1516,
                "Further development of the UDES 14. The project combined the best elements of the UDES 15 (developed by Bofors) and the UDES 16 (developed by Hägglunds). The project became the main variant of traditional design for a Swedish tank, but later it was discontinued in favor of a variant with a remotely controlled turret. The full-scale prototype was built, and elements of the vehicle were used in subsequent Swedish projects.",
                "good gun depression with the suspension, good DPM, high alpha damage, good acceleration, low profile, good camo",
                "armor can be overmatrched, vulnerable for HE, viewrange is bad"
            ),"UDES 16" to TankInfo(
                R.drawable.udes15,
                "Further development of the UDES 14E2 project was proposed by the Hägglunds company. The vehicle was to have a lowered silhouette and be equipped with a 105 mm autoloader gun. In 1974, the project was merged with the Bofors project, which resulted in the UDES 15/16 project. No prototypes were built.",
                "good gun depression with the suspension, good alpha damage and penetartion, good camo, low profile",
                "low rate of fire and DPM, weak premium ammo, armor can be overmatched"
            ),"UDES 14 5" to TankInfo(
                R.drawable.udes145,
                "A UDES 14 tank project developed by Bofors. This project was to feature a gun autoloader and hydraulic suspension, as well as the option of manual gun loading. Existed only in blueprints.",
                "good gun depression with the suspension,good alpha damage and penetartion, good camo, low profile",
                "DPM is low, thin armor can be overmatched, accuracy"
            ),"Leo" to TankInfo(
                R.drawable.leosweden,
                "In the late 1940s, Landsverk developed a series of tanks that were primarily intended for export. The Strv Leo medium tank was the heaviest in the series. The army took an interest in the project, but it did not offer considerable advantages over foreign peers that were available for purchase. Eventually, they favored another project.",
                "",
                ""
            ),"Strv 74" to TankInfo(
                R.drawable.strv74,
                "In the early 1950s, the Centurion medium tank entered service in the Swedish army, yet a lighter and more maneuverable vehicle was required. It was decided to modernize the old Strv m/42 vehicles by equipping them with a new turret and armament. The tank received a new name, Strv 74. A total of 225 vehicles were manufactured from 1957 through 1960. The Swedish army continued to use the vehicle until the end of 1984.",
                "",
                ""
            ),"Strv m/42" to TankInfo(
                R.drawable.strvm42,
                "Developed on the basis of the Lago medium tank in the 1930s. The 75-mm Bofors gun was mounted on the 22.5-ton tank. A total of 282 vehicles of different variants were produced. The TM, TH, and TV variants used the Scania-Vabis L-603 twin engines, which provided 160 h.p., while the Strv m/42 EH tanks were powered by a single Volvo A8B engine that provided 370 h.p.",
                "",
                ""
            ),"Lago" to TankInfo(
                R.drawable.lago,
                "Development was started in 1936 by the Landsverk company. The new vehicle incorporated some elements\\nof the L-60 light tank. In 1939, Hungary took an interest in the project. By the time the first prototype was produced in 1940, Hungary decided to develop their own medium tank on the basis of the Škoda T-21. Later, some technical solutions were applied in the development of the Strv m/42 tank.",
                "nice tank with a good aplha damage and mobility",
                "reload time and armor is bad"
            ),"Kranvagn" to TankInfo(
                R.drawable.kranvagn,
                "A project for a new vehicle for the Swedish army that started in 1949. For confidentiality purposes, the vehicle was given an unusual name, KRV, which stands for self-propelled crane (in Swedish). The project was based on the French AMX 50. A lightweight chassis was built and tested; in addition, a dummy with a turret was produced. A prototype was never built.",
                "strongest turret whet hull down, 3 clip gun with good alpha damage, good mobility",
                "gun handling is poor"
            ),"Emil II" to TankInfo(
                R.drawable.emil2,
                "A variant of the heavy tank developed under the project of 1949. In 1952, three heavy tank projects were proposed. The EMIL 1952 E2 was the second variant. Depending on the armor and mounted engine, the weight of the vehicle varied from 34 to 39 tons. However, at the end of 1952, development of the E2 version was discontinued in favor of the E3 version, which had similar characteristics but improved armor.",
                "strong turret whet hull down, 3 clip gun with good alpha damage, good mobility",
                "gun handling is poor"
            ),"Emil I" to TankInfo(
                R.drawable.emil1,
                "A variant of the heavy tank developed for the Swedish army under the EMIL project in the 1950s. Design solutions used for the French AMX 13 tank influenced the project, which is obvious from the design of the turret. In 1951, a draft design was prepared, but development was discontinued in favor of later versions.",
                "decent hull-down performance, autoloader gun, decent mobility",
                "can be penetrated in the face by HEAT"
            ),"Strv 103B" to TankInfo(
                R.drawable.strv103b,
                "Developed from 1969 through 1971 as a modernization of the Strv 103. Unlike the previous Strv 103A version, the Strv 103B was equipped with a more powerful gas-turbine engine, laser rangefinder, and infrared devices. The vehicle also accommodated the mounting of amphibious add-on equipment. In 1970, 220 vehicles went into service. At the same time, the vehicles of the previous modification were converted to the Strv 103B, which resulted in a total of 290 vehicles by 1971.",
                "best sniper in the game, amazing DPM and alpha damage, god armor when sloped, good camo and good mobility",
                "armor can be overmatched"
            ),"Strv 103-0" to TankInfo(
                R.drawable.strv1030,
                "A contract for the production of a turretless vehicle was concluded with the Bofors company in 1960. A total of 10 prototypes of the \\\"zero series\\\" were produced. The vehicles differed from the mass-produced Strv 103 in weaker armor and engine, as well as in the commander's cupola. In 1964, the vehicles were used as a basis for the Strv 103A production.",
                "best sniper in the game, amazing DPM and alpha damage, god armor when sloped, good camo and good mobility",
                "armor can be overmatched"
            ),"UDES 03" to TankInfo(
                R.drawable.udes03,
                "Developed in the early 1970s by the UDES research group, Bofors and Hägglunds companies. In 1972, the project was provided by the Bofors company. Later, it was decided to return to the concept with a traversable turret, and development was continued within the UDES 14 project. The Bofors project remained only in blueprints.",
                "amazing sniper in the game, amazing DPM and alpha damage, god armor when sloped, good camo and good mobility",
                "armor can be overmatched"
            ),"Ikv 90 B" to TankInfo(
                R.drawable.ikv90b,
                "A vehicle project by the Bofors company. Developed in 1965-1966 as part of the competitive search for a light tank by the Swedish Ministry of Defence. The lightly-armored vehicle was to combine high speed, maneuverability, and a powerful gun, which would be effective against modern tanks and support troops on the battlefield. The design project was ready in January 1966 and further work was scheduled. However, no prototype was produced.",
                "decent tank destroyer, good gun and nice mobility",
                "poor armor"
            ),"Ikv 65 II" to TankInfo(
                R.drawable.ikv652,
                "In the mid-1960s, the Swedish Ministry of Defense laid out requirements for a new light tank. The lightly armored vehicle should combine high speed and maneuverability with powerful armament that would be sufficient to oppose modern tanks and provide fire support to the infantry. Landsverk developed several projects with both a rotating turret and a gun mounted on a stationary superstructure. They were essentially tank destroyers. Development of the project was discontinued at the drafting and modelling stage.",
                "decent tank destroyer, good gun and nice mobility",
                "poor armor"
            ),"Ikv 103" to TankInfo(
                R.drawable.ikv103,
                "In 1956, production of a modernized variant of the Ikv 102 SPG, designated Ikv 103, was started. These vehicles were designed to provide fire support to infantry. A total of around 80 vehicles were manufactured and remained in service till the early 1980s.",
                "decent tank destroyer, good gun and nice mobility",
                "poor armor"
            ),"Sav m/43" to TankInfo(
                R.drawable.savm43,
                "Swedish assault SPG. The vehicle was developed in 1941–1944. In March of 1944, Scania-Vabis received an order for the production of 36 vehicles. The first 18 vehicles were re-equipped from the Strv m/41 SII. Initially, the SPG was equipped with a 75-mm gun, but in March of 1946, all vehicles were re-equipped with a 105-mm gun. A total of 36 SPGs were manufactured and remained in service until 1973.",
                "good accuracy, fast reverse speed, good gun arc",
                "stock tank is very bad, poor maneuverability"
            ),"Strv m/40L" to TankInfo(
                R.drawable.strvm40l,
                "In 1940, the Swedish army experienced an acute need for armored vehicles. One of the solutions to the problem was the modernization of the L-60 tanks. The first 100 modernized vehicles received improved armor and a new automatic transmission. Modernization was performed by AB Landsverk; this is why these vehicles entered service named Strv m/40L. Later, vehicles that were manufactured at the factory in Karlstad were equipped with more powerful engines, improved suspension, and armor. These vehicles were designated Strv m/40K.",
                "low tier tank, nothing special about it",
                "low tier tank, nothing special about it"
            ),"Strv m/38" to TankInfo(
                R.drawable.strvm38,
                "Developed on the basis of the L-60 light tank by AB Landsverk. During mass production, the vehicle underwent several modernizations. A total of 216 vehicles of different variants were produced from 1939 through 1944. The vehicle remained in the service of the Swedish army until the 1960s.",
                "low tier tank, nothing special about it",
                "low tier tank, nothing special about it"
            ),"Strv fm/21" to TankInfo(
                R.drawable.strvfm21,
                "The LK II tank was developed in Germany in 1918. The vehicle underwent trials, and the German army placed an order for 1,000 tanks. However, the order was not completed before the end of World War I. In 1920, Germany sold 10 LK II vehicles to Sweden, which entered service under the designation Stridsvagn fm/21. In 1929-1932, the vehicles underwent modernization and received more powerful engines and improved frontal armor. All modernized vehicles were designated Stridsvagn m/21-29 and were used for training purposes until 1940.",
                "low tier tank, nothing special about it",
                "low tier tank, nothing special about it"
            ),"Progetto 65" to TankInfo(
                R.drawable.progetto65,
                "In 1969, the Italian military delegation visited Germany to discuss the purchase of Leopard tanks. However, not all members of the delegation agreed with the acquisition of foreign vehicles. The Italian military experts and engineers specified the main requirements for the future tank: the slope angle of armor plates, the cast turret and gun mantlet, as well as the powerful engine from Mitsubishi that allowed production of a small, light, maneuverable, but perfectly-armored vehicle. The British and Soviet design plans collected by SIFAR-SID were taken into account. Development of the project was discontinued at the drafting and modelling stage.",
                "amazing burst fire capability, good mobility and good gun depression, armor can bounce ",
                "gun handling is kinda mid, gun reload system can trick"
            ),"Standard B" to TankInfo(
                R.drawable.standardb,
                "In 1969, the Italian military delegation visited Germany to discuss the purchase of Leopard tanks. However, not all members of the delegation agreed with the acquisition of foreign vehicles. The Italian military experts and engineers specified the main requirements for the future tank: the slope angle of armor plates, the cast turret and gun mantlet, as well as the powerful engine from Mitsubishi that allowed production of a small, light, maneuverable, but perfectly-armored vehicle. The British and Soviet design plans collected by SIFAR-SID were taken into account. Development of the project was discontinued at the drafting and modelling stage.",
                "amazing burst fire capability, good mobility and good gun depression, armor can bounce ",
                "gun handling is kinda mid, gun reload system can trick"
            ),"P.44 Pantera" to TankInfo(
                R.drawable.p44pantera,
                "The P.44 Pantera was one of the variants of the Italian medium tank developed during WWII. It existed only in blueprints",
                "nice tank to play with, good gun and mobility",
                "cannot rely on armor"
            ),"P.43 ter" to TankInfo(
                R.drawable.p43ter,
                "The P.43 ter is an Italian project for a medium tank developed in the mid-1940s. The work was discontinued at the blueprints stage.",
                "good alpha for the tier, good DPM, nice power to weight ratio, nice armor when angling",
                "low penetration, bad dispersion, not very agile"
            ),"P.43 bis" to TankInfo(
                R.drawable.p43bis,
                "The P.43 bis is one of the versions of the Italian medium tank developed in the mid-1940s. A 90-mm gun was planned to be mounted on the new vehicle. The tank was intended to demonstrate effective performance against existing vehicles.",
                "decent gun, high alpha damage, good agility, above average gun depression, good camo",
                "cannot rely on armor"
            ),"P.43" to TankInfo(
                R.drawable.p43,
                "The P.43 is an Italian project medium tank developed in the mid-1940s. Only a wooden prototype at a scale of 1:10 was built.",
                "good frontal hull armor, good speed, good gun depression, nice HE",
                "engine damage, stock turret is bad"
            ),"P.26/40" to TankInfo(
                R.drawable.p2640,
                "Development of this vehicle started at the end of 1940, but due to difficulties in finding a suitable engine, the first prototype was manufactured only at the beginning of 1942. Mass production was launched in 1943 in Turin. After Italy's capitulation in the autumn of 1943, the tank entered service under the designation Panzerkampfwagen P40 737(i) and was used by the German reserve divisions as permanent fire positions. A total of 100 vehicles were produced.",
                "low tier tank, nothing special about it",
                "low tier tank, nothing special about it"
            ),"M15/42" to TankInfo(
                R.drawable.m1542,
                "Italian medium tank produced from the beginning of 1943. After the surrender of Italy, a total of 90 vehicles were manufactured; some of which were confiscated by the Germans. Later they built another 28 vehicles. The tank was used by the Germans against the partisans in Yugoslavia, as well as in the Leonessa and Leoncello Battalions of the Armed Forces of the Italian Social Republic.",
                "low tier tank, nothing special about it",
                "low tier tank, nothing special about it"
            ),"M14/41" to TankInfo(
                R.drawable.m1441,
                "The vehicle was an upgraded modification of the M13/40. The first tanks were manufactured in the summer of 1941. Unlike the M13/40, this vehicle featured a more powerful engine, and new air and fuel filters that were better adapted to harsh climatic conditions of Africa. A total of 752 vehicles were produced.",
                "low tier tank, nothing special about it",
                "low tier tank, nothing special about it"
            ),"Fiat 3000" to TankInfo(
                R.drawable.fiat3000,
                "The vehicle was developed on the basis of the Renault FT French light tank. The first prototype was manufactured in 1919. From 1921 through 1930, a total of 150 vehicles with autoloading guns and machineguns were produced, 26 of them were exported. The vehicle saw action until 1943.",
                "low tier tank, nothing special about it",
                "low tier tank, nothing special about it"
            ),"Rinoceronte" to TankInfo(
                R.drawable.rinoceronte,
                "At the turn of the 1960s and 1970s, a number of countries started developing vehicles with unmanned turrets to improve crew survival. Italian military engineers decided to adopt these innovations, but chose to use existing designs. The turret was based on the German turret from the advanced KPz 3 tank. The hull configuration was also adopted from German vehicles. At the same time, Italian engineers tried to increase the armor thickness to the maximum. To improve the projectile resistance of the frontal armor, it was decided to make it similar to the \\\"pike nose\\\" shape. Development was discontinued at the drafting stage.",
                "gun with autoloader, great shell vellocity, nice gun depresion, low profile, frontal armor is decent",
                ""
            ),"Progetto 66" to TankInfo(
                R.drawable.progetto66,
                "In 1966, General Vittorio La Rosa wrote an article in which he outlined his vision of a tank to replace the M60A1 in the Italian Armed Forces, the development and production of which could have been managed by Italian industry. The project existed only in blueprints.",
                "very long relaod, HP is low, gun handling is bad, shell velocity with HEAT and HE are poor",
                ""
            ),"Progetto 54" to TankInfo(
                R.drawable.progetto54,
                "In 1954, Captain Quinzio suggested the idea of a tank division consisting of two types of tracked vehicles: \\\"carro da combattimento\\\" (battle tank) and \\\"carro per tutto il resto\\\" (general purpose tank). His concept of \\\"carro da combattimento\\\" presented a 50 ton vehicle with a powerful gun. No prototypes were built.",
                "3 shot autoloader, turret is decent, good gun depression, top speed is nice",
                "bad penetratio, reload and DPM are not good, hull armor is bad"
            ),"Carro. P.88" to TankInfo(
                R.drawable.carrop88,
                "The Carro d'assalto P.88 is an Italian project for a heavy assault tank, developed in 1942. The work was discontinued at the blueprints stage in favor of more advanced developments.",
                "3 shot autoloader, turret is decent, good gun depression, top speed is nice",
                "bad penetratio, reload and DPM are not good, hull armor is bad"
            ),"Minotauro" to TankInfo(
                R.drawable.minotauro,
                "This vehicle was developed in the 1960s by OTO Melara using both elements of American and German design. It was supposed to combine a German power unit with home-grown developments in naval weaponry and loading systems. A distinguishing feature of the vehicle was its placement of the driver in the turret with limited gun traverse, as well as its mechanized loading system.",
                "",
                ""
            ),"CC-1 Mk. 2" to TankInfo(
                R.drawable.cc1mk2,
                "In the 1960s, the Italian company OTO Melara started working on a tank destroyer with a limited turret traverse arc. The design was greatly influenced by promising advancements in German vehicles, and there were plans to incorporate design elements from both American and German vehicles. The vehicle was supposed to be equipped with a magazine loading system that had already been successfully implemented in naval artillery. Another unique feature of the vehicle was the placement of the driver in the turret with limited gun traverse. Eventually, work on this tank destroyer was suspended due to its dependence on its conceptual predecessor. All activity on the MBT-70 was canceled, leading to the discontinuation of the conceptual design, and the vehicle was never built.",
                "good armor, nice mobility, good alpha damage, rotatable turret",
                "gun handling is bad"
            ),"SMV CC-67" to TankInfo(
                R.drawable.smvcc67,
                "An Italian tank destroyer with a limited turret traverse arc. The vehicle was developed in the second half of the 1960s as part of a collaborative effort between the countries of the Western European Union to create new prototypes of armored vehicles. The project was discontinued at the development stage.",
                "good armor, nice mobility, good alpha damage, rotatable turret",
                "gun handling is bad"
            ),"SMV CC-56" to TankInfo(
                R.drawable.smvcc56,
                "One of the first postwar designs for Italian tank destroyers with a limited turret traverse arc (up to 60 degrees). The project was heavily influenced by designs for American tank destroyers, such as the T95 tank program, in particular. A distinguishing feature of the Italian tank destroyer was the placement of the entire crew in a rotating turret. The vehicle was supposed to use components of the American T95, but the latter did not reach mass production. Further development was discontinued.",
                "good armor, nice mobility, good alpha damage, rotatable turret",
                "gun handling is bad"
            ),"Semovente M41" to TankInfo(
                R.drawable.semoventem41,
                "After the Germans first adopted the use of StuG III assault guns with closed cabins in 1940, the Italians started working on their own vehicle of a similar type. As a basis for their design, they used the chassis of the Carro Armato M 13/40 medium tank. The first prototype of the Semovente da 75/18 was completed in February 1941. Instead of a turret and turret platform, this vehicle featured a closed stationary cabin with a mounted 75 mm Obice da 75/18 modello 34 howitzer. The vehicle armament was sufficient for both providing fire support and fighting against most tanks at the time. Its 50 mm frontal armor could, in theory, withstand most common British two-pounder tank and anti-tank guns from long and medium ranges. Most of the Semovente M41 da 75/18s that fought were lost in North Africa, while others were captured by the Germans in the fall of 1943.",
                "decent gun and mobility",
                "weak armor and no turret"
            ),"Bassotto" to TankInfo(
                R.drawable.bassotto,
                "In April of 1942, the Italian command came up with the idea to develop an assault tank destroyer with a 105 mm howitzer on the chassis of the Carro Armato P 40 heavy tank. The project was developing rather slowly, so instead, officials decided to use the improved chassis of the mass-produced Carro Armato M 15/42 for a new vehicle. This vehicle featured a 105 mm howitzer with a length of 23 calibers. The prototype was completed in January 1943, and it became the first Italian vehicle to be partially welded. The serial model, designated Semovente da 105/25 M43, received a 25 caliber gun and a newly designed hull and cabin. Before Italy's capitulation on September 8, 1943, 30 vehicles had already been manufactured and sent to military units. Later these vehicles all fell into the hands of the Germans, who set up and continued production. Between 1943 and 1944, 121 more vehicles were produced.",
                "amazing gun and mobility",
                "no turret and weak armor"
            ),"WZ-132-1" to TankInfo(
                R.drawable.wz1321,
                "The Chinese experimental vehicle that united various Type 62 modernization projects. The name is conventional. No prototypes were built",
                "",
                ""
            ),"WZ-132A" to TankInfo(
                R.drawable.wz132a,
                "Further development of the WZ-132 project. The main difference was supposed to lie in a shorter vehicle chassis. Engineers wanted to combine high mobility and firepower. A prototype passed trials in the late 1960s, but the project was discontinued. The vehicle never saw mass production; the prototype did not survive to the present day.",
                "armor and shells are good, speed is decent",
                "gun handling is bad, not the best spotter"
            ),"WZ-132" to TankInfo(
                R.drawable.wz132,
                "The order for the WZ-132 tank was placed in 1964. The design was based on the WZ-131, but with the goal of enhancing firepower and armor protection. A few prototypes were built before the project was canceled in the late 1960s. All prototypes were destroyed during Chinese nuclear weapon testing.",
                "mobility is decent",
                "the worst tier 9 light tank. Bad armor, bad gun handling, shells are pretty bad"
            ),"WZ-131" to TankInfo(
                R.drawable.wz131,
                "Development of a lightweight version of the Type 59 tank started in 1957. The new tank was designed to fight in the mountainous and boggy terrain of South China. The first prototype was built in 1962. Later, the vehicle underwent several stages of modernization.",
                "really good tank, mobility is very good, ammuniton is decent ",
                "cannot rely on armor"
            ),"59-16" to TankInfo(
                R.drawable.tank5916,
                "In 1957 a new light tank project was initiated by the Chinese government. Development of vehicle was completed in 1959. The new tank, weighing 16 tons, was equipped with a four-wheel torsion-bar suspension and a rear placement of the drive wheel, and mounted a 57-mm gun (later upgraded to 76-mm).",
                "good penetration, low profile, good agility",
                "poor accuracy, bad view range, bad gun depression"
            ),"M5A1 Stuart" to TankInfo(
                R.drawable.m5a1stuart,
                "The first tanks of the M5 series were produced in April 1942, and a new modification, the M5A1, was preferred over other variants. After the M24 Chaffee was developed, the M5A1 tanks were deemed obsolete and were exported to other countries, including China. A total of 100 tanks of this type were supplied to the Kuomintang. During the Civil War (1946–1949), many of these vehicles were captured by the PLA.",
                "low tier tank, nothing special",
                "low tier tank, nothing special"
            ), "Type T-34" to TankInfo(
                R.drawable.typet34,
                "The first tanks of the M5 series were produced in April 1942, and a new modification, the M5A1, was preferred over other variants. After the M24 Chaffee was developed, the M5A1 tanks were deemed obsolete and were exported to other countries, including China. A total of 100 tanks of this type were supplied to the Kuomintang. During the Civil War (1946–1949), many of these vehicles were captured by the PLA.",
                "high ammo capacity, good rate of fire, good agility",
                "cannot rely on armor"
            ),"Chi-Ha" to TankInfo(
                R.drawable.chiha,
                "Developed by Mitsubishi from 1935 through 1937. The vehicle was mass-produced from 1938 through 1942, alongside an upgraded Shinhoto Chi-Ha for the last two years, with a total of 1,220 vehicles of both types manufactured. The Chi-Ha and Shinhoto Chi-Ha tanks were widely used by Japanese forces in China, and after Japan capitulation, these vehicles were used by both the PLA and Kuomintang forces in the Civil War of 1946.",
                "low tier tank, nothing special",
                "low tier tank, nothing special"
            ),"VAE Type B" to TankInfo(
                R.drawable.vaetypeb,
                "Developed in 1928 by J. V. Carden and V. Loyd. The vehicle never entered service in Great Britain. However, it was exported to other countries, including China. In 1937, twenty vehicles of this type fought against Japanese troops at Shanghai. Soviet Vickers-based T-26 vehicles were also exported to China, and 82 vehicles of that type were deployed in South China and Burma in 1941–1944.",
                "low tier tank, nothing special",
                "low tier tank, nothing special"
            ),"NC-31" to TankInfo(
                R.drawable.nc31,
                "A Renault FT modification with a new chassis that used long-stroke suspension elements and Kégresse-Hinstin fanged rubber tracks with metal shoes. These features, along with an upgraded 45 h.p. engine, increased the top speed to 16 km/h. A total of 15 vehicles of this type were exported to China.",
                "low tier tank, nothing special",
                "low tier tank, nothing special"
            ),"121" to TankInfo(
                R.drawable.tank121,
                "Development of a new medium tank based on the Type 59 began in 1962. The new tank was expected to feature improvements in firepower and armor protection. However, the project was canceled shortly after. Some technical innovations were applied later, in the development of the Type 69 tank. All 121 tank prototypes were destroyed during Chinese nuclear weapon testing.",
                "armor is decent, mobility is decent, amunition is nice",
                "gun handling is really bad"
            ),"WZ-120" to TankInfo(
                R.drawable.wz120,
                "Initially, the WZ-120 (Type 59) tank was a copy of the Soviet medium T-54A tank. In later modifications the tank was upgraded. Between 6,000 and 9,500 vehicles of all variants were manufactured from 1958 through 1987.",
                "armor is decent and mobility is decent",
                "gun is horrible and shells hava bad penetration"
            ),"T-34-2" to TankInfo(
                R.drawable.wz132a,
                "The T-34-2 was not an upgraded modification of the Soviet T-34 tank, but was a totally different vehicle. Chinese engineers conceived the T-34-2 as an analog of the Soviet T-54. Later the U.S.S.R. passed the technology of T-54 production to China, and the T-34-2 project was discontinued.",
                "small tank, good armor, nice alpha daamge, good agility",
                "hull armor is bad, bad gun depression, stock grind is a pain"
            ),"T-34-1" to TankInfo(
                R.drawable.wz120,
                "In 1954, the Chinese government considered the possibility of launching production of the T-34-85 in China. At the same time, Chinese engineers proposed an alternative project: the T-34-1. While based on the T-34-85, the T-34-1's transmission compartment and suspension were to be rearranged, reducing the overall weight and lowering the hull. In 1954, several designs of the vehicle, with varying turrets and armament, were developed. However, a prototype was never built.",
                "low profile, good agility, long signal range, nice turret armor, good alhpa damage",
                "poor hull armor, bad gun handling, view range is bad"
            ),"Type 58" to TankInfo(
                R.drawable.type58,
                "The U.S.S.R. exported a total of 1,800 T-34-76 and T-34-85 tanks to China. In 1954 the Chinese government made the decision to begin domestic production of the T-34-85 in 1958, with the new vehicle designated the Type 58. However, production was never initiated, and the Chinese army settled for modifications to the Soviet-made T-34s.",
                "",
                ""
            ),"Type T-34" to TankInfo(
                R.drawable.typet34,
                "Among the 1,800 T-34 tanks supplied by the U.S.S.R. to China in the early 1950s, there was a number of T-34-76s. The usefulness of these tanks was extended by Chinese-designed upgrades, including a new engine and modernized suspension.",
                "decent agility, nice HP pool, nice view range, high rate of fire",
                "alpha damage and handling"
            ),"BZ-75" to TankInfo(
                R.drawable.bz75,
                "In the 1960s, amid tense relations with the U.S.S.R., China came up with the concept of creating \\\"frontier-covering forces.\\\" The main firepower of this formation was to be units with \\\"frontier-covering tanks\\\" in service. One of the projects for such vehicles was the BZ-75, developed in 1975 using the experience gained developing the WZ 111 heavy tank. While working on the turret, engineers incorporated the design features of the WZ 1224 project. No BZ 75 prototypes were manufactured.\"",
                "",
                ""
            ),"BZ-68" to TankInfo(
                R.drawable.bz68,
                "One of the variants of the \\\"frontier-covering tank.\\\" The vehicle featured quite a simple yet technologically advanced hull and turret welded from flat plates. It was supposed to mount two solid-propellant jet boosters for crossing tough terrain, as well as for self-recovery. No prototypes were manufactured. The project was canceled during the design phase in 1969.",
                "",
                ""
            ),"BZ-166" to TankInfo(
                R.drawable.bz166,
                "In the 1960s, Chinese engineers tried to keep up with the leading tank-building countries, so their projects included design solutions of different schools. The distinctive features of the BZ-166 heavy tank were the \\\"wavy\\\" surfaces of the frontal hull armor and the jet boosters. According to the design concept, such armor was to increase the chances of ricochets. The jet boosters could increase the vehicle's speed for a short time. It was planned to equip the tank with 122 to 160 mm guns and use it for operations in fortified areas, as well as to destroy enemy engineering structures. It existed only in blueprints.",
                "",
                ""
            ),"BZ-58" to TankInfo(
                R.drawable.bz58,
                "Development began in the late 1950s under the concept of creating \\\"frontier-covering tanks.\\\" The project's distinctive feature was the combined hull: Its frontal armor was cast with large upper glacis slopes, while the rest of the hull was welded. The vehicle was supposed to feature an autoloader in the rear recess of the turret. The design of the turret itself was combined: The welded base was complemented by cast blocks on the vehicle's cheeks. No prototypes were manufactured, and the project was canceled in 1970.",
                "",
                ""
            ),"WZ-113G FT" to TankInfo(
                R.drawable.wz113gft,
                "The concept of the WZ-113G FT tank destroyer was based on the design of the 113 tank, the development of which started in 1963. Neither project saw production.",
                "great alpha damage, decent armor when angling",
                "mobility and gun handling"
            ),"WZ-111G FT" to TankInfo(
                R.drawable.wz111gft,
                "A self-propelled gun developed on the basis of the WZ-111 heavy tank in the first half of the 1960s. The development of the WZ-111 was discontinued, and all activity on the vehicle was canceled. No prototypes were built.",
                "great alpha damage, decent armor when angling",
                "mobility and gun handling"
            ),"WZ-111-1 FT" to TankInfo(
                R.drawable.wz1111ft,
                "The development of the WZ-111-1G FT tank destroyer was based on the experience of the WZ-111 heavy tank creation. A prototype of the latter was produced in the early 1960s. It underwent running trials, which revealed a number of disadvantages in the vehicle's design, and the development of the WZ-111 was discontinued in 1964. The design of the WZ-111-1G FT shows the Soviet tank-building influence. There is no information about any produced prototypes.",
                "nice gun alpha damage and penatration",
                "shell velocity and accuracy, low hitpoint, armor is bad, stock grind is bad"
            ),"T-34-2G FT" to TankInfo(
                R.drawable.t342gft,
                "A proposed project for a tank destroyer on the basis of the T-34-2 medium tank, which was supposed to incorporate the technical innovations from the Soviet SU-122-54 and SPGs of the WWII period. No prototypes were built.",
                "decent tank for its tier, nothing special",
                "decent tank for its tier, nothing special"
            ),"WZ-131G FT" to TankInfo(
                R.drawable.wz131gft,
                "A project for a tank destroyer based on the Type 62 tank also known as WZ-131. The vehicle was developed in 1963. Like the basic vehicle, this tank destroyer was designed to fight in the mountainous and boggy areas of South China with multiple bridges. There is no information about any produced prototypes.",
                "decent tank for its tier, nothing special",
                "decent tank for its tier, nothing special"
            ),"60G FT" to TankInfo(
                R.drawable.tank5916,
                "A project for a tank destroyer based on the Soviet AT-T artillery tractor, which was exported to many countries and repeatedly copied. The vehicle served as a basis for the development of a wide range of engineering and transportation vehicles. It was planned to use the tractor as a basis for combat vehicles, but development did not go beyond proposed plans.",
                "low tier tank, nothing special",
                "low tier tank, nothing special"
            ),"SU-76G FT" to TankInfo(
                R.drawable.su76gft,
                "A proposed modification of the Soviet SU-76M tank destroyer. In the early 1950s, about 90 SU-76M vehicles were exported to the Democratic People's Republic of China and studied by military experts. No prototypes were built.",
                "low tier tank, nothing special",
                "low tier tank, nothing special"
            )

        )
        if(title=="Does Not Exist"){
            binding.favTankId.text = "Please Select Other Tank"
            binding.imageView2.load(R.drawable.obj279e)
            binding.TankInfo.text = "There is no tank in the game with this combination of filters!"
            binding.textCons.text = ""
            binding.textPros.text = ""
            binding.textConsID.text = ""
            binding.textProsID.text = ""
            binding.btnShare.visibility = View.GONE
        }


        val tankInfo = tankInfoMap[title]
        if (tankInfo != null) {
            binding.favTankId.text = title
            binding.imageView2.load(tankInfo.imageResId)
            binding.TankInfo.text = tankInfo.description
            binding.textConsID.text = tankInfo.pros
            binding.textProsID.text = tankInfo.cons
        }
    }
}
