package com.wot.helper.ui.skills

import android.os.Bundle
import android.util.Log
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
import com.wot.helper.ui.network.RetrofitClientSkills
import kotlinx.coroutines.launch

class SkillsCategoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: SkillsAdapter

    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString("category")
        Log.d("SkillsCategory", "Fragment created for category=$category")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_skills_category, container, false)

        recyclerView = root.findViewById(R.id.recyclerView)
        progressBar = root.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SkillsAdapter()
        recyclerView.adapter = adapter

        fetchData()

        return root
    }

    private fun fetchData() {
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            try {
                val response = RetrofitClientSkills.api.getSkills(
                    appId = "ace14516f4be72cde04425adca560339"
                )
                if (response.isSuccessful) {
                    val skillsMap = response.body()?.data ?: emptyMap()
                    val allSkills = skillsMap.values.toList()

                    Log.d("SkillsCategory", "Loaded ${allSkills.size} total skills")

                    allSkills.forEach {
                        Log.d("SkillsCategory", "Skill: ${it.name}, role=${it.role}")
                    }

                    val filtered = if (category != null) {
                        val result = allSkills.filter { it.role.equals(category, ignoreCase = true) }
                        Log.d("SkillsCategory", "Filtered size for $category = ${result.size}")
                        result
                    } else {
                        allSkills
                    }

                    adapter.submitList(filtered)
                } else {
                    Log.e("SkillsCategory", "Failed: ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "Failed to load skills", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("SkillsCategory", "Error: ${e.message}", e)
                Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    companion object {
        fun newInstance(category: String): SkillsCategoryFragment {
            val fragment = SkillsCategoryFragment()
            val args = Bundle()
            args.putString("category", category)
            fragment.arguments = args
            return fragment
        }
    }
}
