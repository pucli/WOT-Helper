package com.wot.helper.ui.core

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.wot.helper.R
import com.wot.helper.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.wot.helper.domain.models.repository.RetrofitInstance
import com.wot.helper.domain.models.models.tanksinfo.TankInfoRequest
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var tankInfoList: TankInfoRequest ////////////


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        lifecycleScope.launchWhenCreated {
//            val response = try {
//                RetrofitInstance.api.getInfo()
//            } catch (e: IOException) {
//                Log.e("IOException","No internet connection!")
//               return@launchWhenCreated
//
//            } catch (e: HttpException) {
//                Log.e("HttpException","Unexpected response")
//               return@launchWhenCreated
//           }
//           Log.i("Response",response.toString())
//        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        this@MainActivity.bottomNav = binding.bottomNav

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNav.apply {
                if (destination.id == R.id.loginFragment && isVisible
                ) {
                    isVisible = false
                }
            }
        }
    }
}

