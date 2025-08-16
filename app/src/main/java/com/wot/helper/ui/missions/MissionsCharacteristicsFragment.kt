/*package com.wot.helper.ui.missions

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import coil.load
import com.wot.helper.R
import com.wot.helper.databinding.FragmentMissionsCharacteristicsBinding
import com.wot.helper.ui.core.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MissionsCharacteristicsFragment : BaseFragment<FragmentMissionsCharacteristicsBinding>(FragmentMissionsCharacteristicsBinding::inflate) {

    private lateinit var missionId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        missionId = arguments?.getString("title") ?: "" // Corrected argument name

        fetchMissionDetails()

        binding.btnShareMission.setOnClickListener {
            shareMissionDetails()
        }
    }

    private fun fetchMissionDetails() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://api.worldoftanks.eu/wot/encyclopedia/personalmissions/?application_id=ace14516f4be72cde04425adca560339")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonResponse = JSONObject(response)
                val missionData = jsonResponse.getJSONObject("data").getJSONObject(missionId)

                withContext(Dispatchers.Main) {
                    updateUI(missionData)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Failed to fetch mission details", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateUI(missionData: JSONObject) {
        binding.missionTitle.text = missionData.getString("name")
        binding.missionDescription.text = missionData.getString("description")
        binding.imageViewMission.load(missionData.getString("image"))
    }

    private fun shareMissionDetails() {
        val shareText = "${binding.missionTitle.text}\n\n${binding.missionDescription.text}\n\nCheck out more on WoT Helper!"
        val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(android.content.Intent.EXTRA_TEXT, shareText)
        }
        startActivity(android.content.Intent.createChooser(shareIntent, "Share Mission"))
    }
}
*/