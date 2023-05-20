package com.bashirli.watchme.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bashirli.watchme.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding:ActivityMainBinding?=null
    val binding:ActivityMainBinding get()=_binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(3200)
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}