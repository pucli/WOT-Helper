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
import com.wot.helper.ui.network.VehicleMission
import com.wot.helper.ui.ArgKeys
import com.wot.helper.ui.adapters.CampaignAdapter

class CampaignFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var vehicle: VehicleMission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vehicle = requireArguments().getSerializable(ArgKeys.VEHICLE) as VehicleMission
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.title.text = vehicle.name
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val campaigns = vehicle.campaigns.values.toList()
        binding.recyclerView.adapter = CampaignAdapter(campaigns) { campaign ->
            val b = Bundle().apply { putSerializable(ArgKeys.CAMPAIGN, campaign) }
            findNavController().navigate(R.id.action_campaignFragment_to_operationFragment, b)
        }
        binding.progress.visibility = View.GONE
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
