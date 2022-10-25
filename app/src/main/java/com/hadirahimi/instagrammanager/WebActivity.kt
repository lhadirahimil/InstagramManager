package com.hadirahimi.instagrammanager

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.hadirahimi.instagrammanager.databinding.ActivityWebBinding
import com.rommansabbir.animationx.Attention
import com.rommansabbir.animationx.animationXAttention
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WebActivity : AppCompatActivity()
{
    //binding
    private lateinit var binding : ActivityWebBinding
    
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        //change status bar color to light
        setupDarkMode()
        setContentView(binding.root)
        
        //get mode from intent
        val mode = intent.getIntExtra(INTENT_DATA , - 1)
        
        //init view
        binding.apply {
            // init webView
            webView.apply {
                settings.javaScriptEnabled = true
                settings.databaseEnabled = true
                webViewClient = object : WebViewClient()
                {
                    override fun onPageStarted(view : WebView? , url : String? , favicon : Bitmap?)
                    {
                        super.onPageStarted(view , url , favicon)
                        cardLoading.visibility = View.VISIBLE
                    }
                    
                    override fun onPageFinished(view : WebView? , url : String?)
                    {
                        super.onPageFinished(view , url)
                        cardLoading.visibility = View.GONE
                    }
                    
                    
                    override fun onReceivedError(
                        view : WebView? , request : WebResourceRequest? , error : WebResourceError?
                    )
                    {
                        super.onReceivedError(view , request , error)
                        cardLoading.visibility = View.GONE
                        Toast.makeText(
                            this@WebActivity ,
                            "سایت باز نشد! اگه فیلتر شکنت خاموشه روشنش کن" ,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                
            }
            
            //init Mode
            when (mode)
            {
                MODE_DELETE ->
                {
                    webView.loadUrl(URL_DELETE)
                }
                
                MODE_DISABLE ->
                {
                    webView.loadUrl(URL_DISABLE)
                    tvHint.visibility = View.VISIBLE
                    
                    //attention shake every 3 sec
                    lifecycleScope.launch(Dispatchers.IO) {
                        (1 .. Int.MAX_VALUE).asFlow().onEach { delay(3000) }.collect {
                                runOnUiThread {
                                    tvHint.animationXAttention(Attention.ATTENTION_BOUNCE)
                                }
                            }
                    }
                }
                
                MODE_LOGIN ->
                {
                    webView.loadUrl(URL_LOGIN)
                }
            }
            
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