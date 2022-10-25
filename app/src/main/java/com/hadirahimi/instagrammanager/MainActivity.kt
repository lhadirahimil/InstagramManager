package com.hadirahimi.instagrammanager

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.hadirahimi.instagrammanager.databinding.ActivityMainBinding
import com.rommansabbir.animationx.Attention
import com.rommansabbir.animationx.Fade
import com.rommansabbir.animationx.animationXAttention
import com.rommansabbir.animationx.animationXFade
import kotlinx.coroutines.delay
import java.util.*


class MainActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupDarkMode()
        setContentView(binding.root)
        
        
        //init views
        binding.apply {
            val intent = Intent(this@MainActivity , WebActivity::class.java)
            btnLogin.setOnClickListener {
                
                intent.putExtra(INTENT_DATA , MODE_LOGIN)
                startActivity(intent)
            }
            btnLetsDisable.setOnClickListener {
                //animation when clicked
                btnLetsDisable.animationXAttention(Attention.ATTENTION_PULSE , 350)
                intent.putExtra(INTENT_DATA , MODE_DISABLE)
              
                lifecycleScope.launchWhenCreated {
                    delay(330)
                    startActivity(intent)
                    delay(200)
                    itemDisable.animationXFade(Fade.FADE)
                }
                
                
            }
            btnLetsDelete.setOnClickListener {
                //animation when clicked
                btnLetsDelete.animationXAttention(Attention.ATTENTION_PULSE , 350)
                intent.putExtra(INTENT_DATA , MODE_DELETE)
                lifecycleScope.launchWhenCreated {
                    delay(330)
                    startActivity(intent)
                    delay(200)
                    btnLetsDelete.animationXFade(Fade.FADE)
                }
            }
        }
    }
    
    
    
    private fun setupDarkMode()
    {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)
        {
            Configuration.UI_MODE_NIGHT_NO ->
            {
                // Night mode is not active, we're using the light theme
                // change ui to Light Mode
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                //change status bar color to light
                window.statusBarColor =
                    ResourcesCompat.getColor(resources , R.color.background , null)
                //change status bar color to light
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                {
                    window.decorView.windowInsetsController?.setSystemBarsAppearance(
                        APPEARANCE_LIGHT_STATUS_BARS ,
                        APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
            }
            Configuration.UI_MODE_NIGHT_YES ->
            {
                // Night mode is active, we're using dark theme
                // change ui to Dark Mode
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                //change status bar color to dark
                window.statusBarColor =
                    ResourcesCompat.getColor(resources , R.color.background_dark , null)
                
            }
        }
    }
    
}