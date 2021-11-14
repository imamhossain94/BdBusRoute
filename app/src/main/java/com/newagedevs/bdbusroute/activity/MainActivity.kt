package com.newagedevs.bdbusroute.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.fragments.BusesFragment
import com.newagedevs.bdbusroute.fragments.FavouriteFragment
import com.newagedevs.bdbusroute.fragments.MenuFragment
import com.newagedevs.bdbusroute.fragments.RouteFragment
import com.newagedevs.bdbusroute.utils.shareTheApp
import com.newagedevs.bdbusroute.utils.showRatingDialogue
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var iconRating:ImageView
    private lateinit var iconShare:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        @RequiresApi(Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        navBar()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.home_fragment, RouteFragment()).commit()
        }


        iconRating = findViewById(R.id.icon_rating)
        iconShare = findViewById(R.id.icon_share)

        iconRating.setOnClickListener{
            showRatingDialogue(this, layoutInflater)
        }

        iconShare.setOnClickListener{
            shareTheApp(this)
        }

    }

    var position = 0

    private fun navBar(){
        home_bottom_navigation_view.selectedItemId = R.id.nav_route
        home_bottom_navigation_view.setOnItemSelectedListener {
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