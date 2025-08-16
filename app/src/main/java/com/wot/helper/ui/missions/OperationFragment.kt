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
import com.wot.helper.ui.network.Campaign
import com.wot.helper.ui.ArgKeys
import com.wot.helper.ui.adapters.OperationAdapter

class OperationFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var campaign: Campaign

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        campaign = requireArguments().getSerializable(ArgKeys.CAMPAIGN) as Campaign
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.title.text = campaign.name
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val ops = campaign.operations.values.toList()
        binding.recyclerView.adapter = OperationAdapter(ops) { op ->
            val b = Bundle().apply { putSerializable(ArgKeys.OPERATION, op) }
            findNavController().navigate(R.id.action_operationFragment_to_missionListFragment, b)
        }
        binding.progress.visibility = View.GONE
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
