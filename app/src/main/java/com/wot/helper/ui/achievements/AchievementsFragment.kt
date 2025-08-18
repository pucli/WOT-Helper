package com.wot.helper.ui.achievements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wot.helper.R

class AchievementsFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private val categories = listOf("epic", "battle", "special", "group")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_achievements, container, false)

        tabLayout = root.findViewById(R.id.tabLayout)
        viewPager = root.findViewById(R.id.viewPager)

        val adapter = AchievementsPagerAdapter(this, categories)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (categories[position]) {
                "epic" -> tab.text = "Epic Medals"
                "battle" -> tab.text = "Battle Heroes"
                "special" -> tab.text = "Special"
                "group" -> tab.text = "Group"
            }
        }.attach()

        return root
    }
}
