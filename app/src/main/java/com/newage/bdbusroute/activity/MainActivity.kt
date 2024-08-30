package com.newage.bdbusroute.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.newage.bdbusroute.R
import com.newage.bdbusroute.fragments.BusesFragment
import com.newage.bdbusroute.fragments.FavouriteFragment
import com.newage.bdbusroute.fragments.MenuFragment
import com.newage.bdbusroute.fragments.RouteFragment
import com.newage.bdbusroute.utils.shareTheApp
import com.newage.bdbusroute.utils.showRatingDialogue
import com.newage.bdbusroute.utils.showWarningMessage

class MainActivity : AppCompatActivity() {

    private lateinit var iconWarning:ImageView
    private lateinit var iconRating:ImageView
    private lateinit var iconShare:ImageView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.home_fragment, RouteFragment()).commit()
        }

        iconWarning = findViewById(R.id.icon_warning)
        iconRating = findViewById(R.id.icon_rating)
        iconShare = findViewById(R.id.icon_share)
        bottomNavigationView = findViewById(R.id.home_bottom_navigation_view)

        iconRating.setOnClickListener{
            showRatingDialogue(this, layoutInflater)
        }

        iconShare.setOnClickListener{
            shareTheApp(this)
        }

        iconWarning.setOnClickListener{
            showWarningMessage(this)
        }

        navBar()
    }

    var position = 0

    private fun navBar(){
        bottomNavigationView.selectedItemId = R.id.nav_route
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_route -> {
                    if(position!=0){
                        switchContent(RouteFragment())
                    }
                    position = 0
                    true
                }
                R.id.nav_favourite -> {
                    if(position!=1){
                        switchContent(FavouriteFragment())
                    }
                    position = 1
                    true
                }
                R.id.nav_buses -> {
                    if(position!=2){
                        switchContent(BusesFragment())
                    }
                    position = 2
                    true
                }
                R.id.nav_menu -> {
                    if(position!=3){
                        switchContent(MenuFragment())
                    }
                    position = 3
                    true
                }
                else -> false
            }
        }
    }

    private fun switchContent(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,0,0)
        transaction.replace(R.id.home_fragment, fragment)
        transaction.commit()
    }


}