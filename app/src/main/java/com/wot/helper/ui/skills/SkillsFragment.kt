package com.wot.helper.ui.skills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wot.helper.R

class SkillsFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val categories = listOf("commander", "driver", "gunner", "loader", "radio_operator")

    private val categoryTitles = listOf("Commander", "Driver", "Gunner", "Loader", "Radio Operator")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_skills, container, false)

        viewPager = root.findViewById(R.id.viewPager)
        tabLayout = root.findViewById(R.id.tabLayout)

        viewPager.adapter = SkillsPagerAdapter(this, categories)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = categoryTitles[position]
        }.attach()

        return root
    }
}
