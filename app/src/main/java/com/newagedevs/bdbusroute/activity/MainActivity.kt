package com.newagedevs.bdbusroute.activity

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.fragments.AccountFragment
import com.newagedevs.bdbusroute.fragments.BagFragment
import com.newagedevs.bdbusroute.fragments.HomeFragment
import com.newagedevs.bdbusroute.fragments.WishFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        @RequiresApi(Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        mContext = this

        navBar()
//        allButtonClickEventListeners()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.home_fragment, HomeFragment()).commit()
        }

    }


    private fun navBar(){
        home_bottom_navigation_view.selectedItemId = R.id.nav_route

        home_bottom_navigation_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_route -> {
                    switchContent(HomeFragment())
                    true
                }
                R.id.nav_favourite -> {
                    switchContent(WishFragment())
                    true
                }
                R.id.nav_buses -> {
                    switchContent(BagFragment())
                    true
                }
                R.id.nav_menu -> {
                    switchContent(AccountFragment())
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

    override fun onResume() {
        super.onResume()
    }


}