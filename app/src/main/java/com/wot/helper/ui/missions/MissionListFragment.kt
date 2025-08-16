package com.wot.helper.ui.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wot.helper.R
import com.wot.helper.databinding.FragmentListBinding
import com.wot.helper.ui.network.Operation
import com.wot.helper.ui.ArgKeys
import com.wot.helper.ui.adapters.MissionAdapter

class MissionListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var operation: Operation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        operation = requireArguments().getSerializable(ArgKeys.OPERATION) as Operation
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.title.text = operation.name
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val missions = operation.missions.values.sortedBy { it.mission_id }
        binding.recyclerView.adapter = MissionAdapter(missions) { mission ->
            val b = Bundle().apply { putSerializable(ArgKeys.MISSION, mission) }
            findNavController().navigate(R.id.action_missionListFragment_to_missionDetailFragment, b)
        }
        binding.progress.visibility = View.GONE
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
