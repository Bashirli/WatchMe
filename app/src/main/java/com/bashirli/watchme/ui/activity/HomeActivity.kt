package com.bashirli.watchme.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bashirli.watchme.R
import com.bashirli.watchme.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private var _binding:ActivityHomeBinding?=null
    private val binding:ActivityHomeBinding get()=_binding!!

    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup(){
        val navFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController=navFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener(object:NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?,
            ) {
                when(destination.id){
                    R.id.detailsFragment->{binding.bottomNavigationView.visibility = View.GONE}
                    R.id.genreFragment->{binding.bottomNavigationView.visibility = View.GONE}
                    R.id.profileFragment->{binding.bottomNavigationView.visibility=View.GONE}
                    R.id.logRegFragment->{binding.bottomNavigationView.visibility = View.GONE}
                    R.id.loginFragment->{binding.bottomNavigationView.visibility = View.GONE}
                    R.id.registerFragment->{binding.bottomNavigationView.visibility=View.GONE}
                    else->{if(binding.bottomNavigationView.visibility==View.GONE){binding.bottomNavigationView.visibility=View.VISIBLE}}
                }
            }

        })

    }


}