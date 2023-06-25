package com.wot.helper.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.wot.helper.databinding.FragmentProfilBinding
import com.wot.helper.domain.models.models.profileinfo.ProfileInfo
import com.wot.helper.domain.models.repository.RetrofitInstanceProfile
import com.wot.helper.domain.models.use_case.auth.Response
import com.wot.helper.ui.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException




@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfilBinding>(FragmentProfilBinding::inflate){

    private val viewModel by viewModels<ProfileViewModel>()

    private var profileDetails: ProfileInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstanceProfile.api.getInfoProfile()
            } catch (e: IOException) {
                Log.e("IOException","No internet connection!")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e("HttpException","Unexpected response")
                return@launchWhenCreated
            }
            profileDetails = response.body()?.data!!.`533694329`

            addData()
            handleUserState()
        }

    }



    @SuppressLint("SetTextI18n")
    private fun addData(){
        binding.username.text = profileDetails?.nickname
        binding.VictoriesID.text = profileDetails?.statistics!!.all!!.wins.toString()
        binding.PersonalRatingID.text = profileDetails?.global_rating.toString()
        binding.BattlesID.text = profileDetails?.statistics?.all!!.battles.toString()
        binding.AvgExpID.text = profileDetails?.statistics?.all!!.battle_avg_xp.toString()
        binding.MostDestroyedID.text = profileDetails?.statistics?.all!!.max_frags.toString()
        binding.MaxExpID.text = profileDetails?.statistics?.all!!.max_xp.toString()
        binding.AvgDmgID.text = profileDetails?.statistics?.all!!.avg_damage_blocked.toString()
        binding.HitsID.text = profileDetails?.statistics?.all!!.hits_percents.toString() + "%"
        binding.MaxDmgID.text = profileDetails?.statistics?.all!!.max_damage.toString()
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