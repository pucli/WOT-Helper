package com.wot.helper.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wot.helper.databinding.FragmentProfilBinding
import com.wot.helper.domain.models.models.profileinfo.ProfileInfo
import com.wot.helper.domain.models.use_case.auth.Response
import com.wot.helper.ui.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfilBinding>(FragmentProfilBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    private var profileDetails: ProfileInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUserState()

        // Observe profileInfo LiveData
        viewModel.profileInfo.observe(viewLifecycleOwner) { details ->
            profileDetails = details
            addData()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addData() {
        binding.apply {
            username.text = profileDetails?.nickname ?: "N/A"
            VictoriesID.text = profileDetails?.statistics?.all?.wins?.toString() ?: "0"
            PersonalRatingID.text = profileDetails?.global_rating?.toString() ?: "0"
            BattlesID.text = profileDetails?.statistics?.all?.battles?.toString() ?: "0"
            AvgExpID.text = profileDetails?.statistics?.all?.battle_avg_xp?.toString() ?: "0"
            MostDestroyedID.text = profileDetails?.statistics?.all?.max_frags?.toString() ?: "0"
            MaxExpID.text = profileDetails?.statistics?.all?.max_xp?.toString() ?: "0"
            AvgDmgID.text = profileDetails?.statistics?.all?.avg_damage_blocked?.toString() ?: "0"
            HitsID.text = "${profileDetails?.statistics?.all?.hits_percents?.toString() ?: "0"}%"
            MaxDmgID.text = profileDetails?.statistics?.all?.max_damage?.toString() ?: "0"
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleUserState() {
        if (viewModel.isLoggedIn) {
            binding.apply {
                loggedInLayout.visibility = View.VISIBLE
                loggedOutLayout.visibility = View.GONE
                btnLogout.setOnClickListener {
                    signOut()
                }

                btnFeedback.setOnClickListener {
                    val url =
                        "https://docs.google.com/forms/d/e/1FAIpQLSfS5pQw1akZCxwAvGEy5q7yCbhMihkk_6j9Fmvd8XR4dw6zOA/viewform"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
        }
    }

    private fun navigateToAuth() {
        val navAuth = ProfileFragmentDirections.actionGlobalAuth()
        findNavController().navigate(navAuth)
    }

    private fun signOut() {
        viewModel.signOut().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> navigateToAuth()
                is Response.Failure -> print(response.errorMessage)
            }
        }
    }
}