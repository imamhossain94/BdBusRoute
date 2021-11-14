package com.newagedevs.bdbusroute.activity.splash

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.activity.MainActivity
import com.newagedevs.bdbusroute.helper.switchIntent

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        @RequiresApi(Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        Handler(Looper.getMainLooper()).postDelayed({
            this.switchIntent(MainActivity())
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 2000)


    }

}