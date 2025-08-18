package com.wot.helper.ui.achievements

import android.os.Bundle
import android.util.Log // Add this import for logging
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wot.helper.R
// import com.wot.helper.ui.achievements.model.AchievementData
  // ✅ correct import
import com.wot.helper.ui.network.RetrofitClientAchievement
import kotlinx.coroutines.launch

class AchievementsCategoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: AchievementsAdapter

    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString("category")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_achievements_category, container, false)

        recyclerView = root.findViewById(R.id.recyclerView)
        progressBar = root.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AchievementsAdapter()
        recyclerView.adapter = adapter

        fetchData()

        return root
    }

    private fun fetchData() {
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            val response = RetrofitClientAchievement.api.getAchievements(
                appId = "ace14516f4be72cde04425adca560339" // Explicitly pass application_id
            )
            if (response.isSuccessful) {
                val allAchievements = response.body()?.data?.values?.toList() ?: emptyList()
                
                // Debug log to verify the response data
                Log.d("AchievementsCategory", "Fetched achievements: $allAchievements")

                val filtered = allAchievements.filter { it.section == category }
                adapter.submitList(filtered)
            } else {
                Log.e("AchievementsCategory", "Failed to load achievements: ${response.errorBody()?.string()}")
                Toast.makeText(requireContext(), "Failed to load", Toast.LENGTH_SHORT).show()
            }

            progressBar.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(category: String): AchievementsCategoryFragment {
            val fragment = AchievementsCategoryFragment()
            val args = Bundle()
            args.putString("category", category)
            fragment.arguments = args
            return fragment
        }
    }
}
