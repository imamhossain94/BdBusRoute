package com.newagedevs.bdbusroute.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.newagedevs.bdbusroute.R
import com.newagedevs.bdbusroute.fragments.AccountFragment
import com.newagedevs.bdbusroute.fragments.BagFragment
import com.newagedevs.bdbusroute.fragments.HomeFragment
import com.newagedevs.bdbusroute.fragments.WishFragment
import kotlinx.android.synthetic.main.activity_app_base.*


class AppBaseActivity : AppCompatActivity() {

    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_base)

        @RequiresApi(Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        mContext = this

        navBar()
        allButtonClickEventListeners()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.home_fragment, HomeFragment()).commit()
        }
    }


    private fun allButtonClickEventListeners(){


        drawer_home_button.setOnClickListener{
            drawerMenuBehaviour()
            switchContent(HomeFragment())
            title_text.text = getString(R.string.home)
        }
        drawer_shop_button
        drawer_catagory_button
        drawer_features_button
        drawer_blog_button
        drawer_about_button
        drawer_contact_button


    }

    private fun navBar(){
        home_bottom_navigation_view.selectedItemId = R.id.nav_home

        home_bottom_navigation_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    switchContent(HomeFragment())
                    title_text.text = getString(R.string.home)
                    true
                }
                R.id.nav_wish -> {

                    if(isUserLogeIn()){
                        switchContent(WishFragment())
                        title_text.text = getString(R.string.wish_list)
                        true
                    }else{
                        this.switchIntent(SignInActivity())
                        false
                    }

                }
                R.id.nav_bag -> {
                    if(isUserLogeIn()){
                        switchContent(BagFragment())
                        title_text.text = getString(R.string.my_bag)
                        true
                    }else{
                        this.switchIntent(SignInActivity())
                        false
                    }
                }
                R.id.nav_account -> {
                    if(isUserLogeIn()){
                        switchContent(AccountFragment())
                        title_text.text = getString(R.string.account)
                        true
                    }else{
                        this.switchIntent(SignInActivity())
                        false
                    }
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
    @SuppressLint("WrongConstant")
    fun drawerMenu(view: View){
        val navDrawer: DrawerLayout = findViewById(R.id.drawer_layout)

        if(!navDrawer.isDrawerOpen(Gravity.START)) {
            navDrawer.openDrawer(Gravity.START)
        } else {
            navDrawer.closeDrawer(Gravity.END)
        }
    }

    @SuppressLint("WrongConstant")
    private fun drawerMenuBehaviour(){
        val navDrawer: DrawerLayout = findViewById(R.id.drawer_layout)
        navDrawer.closeDrawer(Gravity.START)

    }



}

