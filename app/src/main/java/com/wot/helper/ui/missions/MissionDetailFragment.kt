package com.wot.helper.ui.missions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wot.helper.databinding.FragmentMissionDetailBinding
import com.wot.helper.ui.network.Mission
import com.wot.helper.ui.ArgKeys

class MissionDetailFragment : Fragment() {
    private var _binding: FragmentMissionDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var mission: Mission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mission = requireArguments().getSerializable(ArgKeys.MISSION) as Mission
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMissionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.title.text = mission.name
        binding.description.text = mission.description ?: "No description."
        binding.conditions.text =
            mission.conditions?.entries?.joinToString(separator = "\n") { "• ${it.key}: ${it.value}" }
                ?: "No conditions listed."
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
