package com.hadirahimi.instagrammanager

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.hadirahimi.instagrammanager.databinding.ActivitySplashBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.util.*
import kotlin.coroutines.coroutineContext

class SplashActivity : AppCompatActivity()
{
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setupDarkMode()
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        lifecycleScope.launchWhenCreated {
            delay(2000)
            finish()
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
        }
        
    }
    private fun setupDarkMode()
    {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                // Night mode is not active, we're using the light theme
                // change ui to Light Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                //change status bar color to light
                window.statusBarColor = ResourcesCompat.getColor(resources,R.color.background,null)
                //change status bar color to light
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                {
                    window.decorView.windowInsetsController?.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS ,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                // Night mode is active, we're using dark theme
                // change ui to Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                //change status bar color to dark
                window.statusBarColor = ResourcesCompat.getColor(resources,R.color.background_dark,null)
                
                
                
            }
        }
    }
}