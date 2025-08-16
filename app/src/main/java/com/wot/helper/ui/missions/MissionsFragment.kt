package com.wot.helper.ui.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wot.helper.R
import com.wot.helper.databinding.FragmentListBinding
import com.wot.helper.ui.ArgKeys
import com.wot.helper.ui.adapters.VehicleMissionAdapter
import com.wot.helper.ui.network.VehicleMission
import com.wot.helper.viewmodel.MissionsViewModel
import kotlinx.coroutines.flow.collectLatest


class MissionsFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val vm: MissionsViewModel by viewModels()
    private lateinit var adapter: VehicleMissionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = getString(R.string.title_vehicle_sets)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter once
        adapter = VehicleMissionAdapter(emptyList()) { vehicle: VehicleMission ->
            val bundle = Bundle().apply { putSerializable(ArgKeys.VEHICLE, vehicle) }
            findNavController().navigate(R.id.action_missionsFragment_to_campaignFragment, bundle)
        }
        binding.recyclerView.adapter = adapter

        vm.loadMissions()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.missions.collectLatest { map: Map<String, VehicleMission> ->
                val list = map.values.toList()
                adapter.updateItems(list)
                binding.progress.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }
/**/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


