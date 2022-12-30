package com.example.ourstory

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class NavigationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    internal lateinit var profileCircleImageView: CircleImageView
    internal var profileImageUrl = "https://lh3.googleusercontent.com/-4qy2DfcXBoE/AAAAAAAAAAI/AAAAAAAABi4/rYjrtntAi4/s640-il/photo.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        profileCircleImageView = navView.getHeaderView(0).findViewById(R.id.imageView)
        Glide.with(this)
            .load(profileImageUrl)
            .into(profileCircleImageView)
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_myprofile, R.id.nav_myfavorites,
            R.id.nav_signout)
            .setDrawerLayout(drawerLayout)
            .build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val SV = findViewById<SearchView>(R.id.nav_search)
        SV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                BookFragment.query = query
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, SearchFragment(), SearchFragment::class.java.simpleName)
                        .addToBackStack(SearchFragment::class.java.simpleName)
                        .commit()
                Toast.makeText(this@NavigationActivity, query, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        loadFragment(DiscoverFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun loadFragment(fragment: Fragment){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment::class.java.simpleName)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }
}






//class NavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
//    //new usiing , NavigationView.OnNavigationItemSelectedListener on top
////    lateinit var toolbar: Toolbar
////    lateinit var drawerLayout: DrawerLayout
////    lateinit var navView: NavigationView
//
//    //new2
////    lateinit var appBarConfiguration: AppBarConfiguration
////    internal lateinit var profileCircleImageView: CircleImageView
////    internal var profileImageUrl =
////            "https://lh3.googleusercontent.com/-4qy2DfcXBoE/AAAAAAAAAAI/AAAAAAAABi4/rY-jrtntAi4/s640-il/photo.jpg"
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_navigation)
////        setSupportActionBar(toolbar)
////
////        val toggle = ActionBarDrawerToggle(
////            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
////        drawer_layout.addDrawerListener(toggle)
////        toggle.syncState()
////
////        nav_view.setNavigationItemSelectedListener(this)
////
////        displayScreen(-1)
//
//        //new
////        toolbar = findViewById(R.id.toolbar)
////        setSupportActionBar(toolbar)
////
////        drawerLayout = findViewById(R.id.drawer_layout)
////        navView = findViewById(R.id.nav_view)
////
////        val toggle = ActionBarDrawerToggle(
////                this, drawerLayout, toolbar, 0, 0
////        )
////        drawerLayout.addDrawerListener(toggle)
////        toggle.syncState()
////        navView.setNavigationItemSelectedListener(this)
//
//        //new2
////        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
////        setSupportActionBar(toolbar)
////
////        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
////        val navView: NavigationView = findViewById(R.id.nav_view)
////        profileCircleImageView =
////                navView.getHeaderView(0).findViewById(R.id.imageView)
////        Glide.with(this)
////                .load(profileImageUrl)
////                .into(profileCircleImageView)
////
////        appBarConfiguration = AppBarConfiguration.Builder(
////                R.id.nav_home, R.id.nav_myfavorites, R.id.nav_myprofile, R.id.nav_signout
////        )
////                .setDrawerLayout(drawerLayout)
////                .build()
////
////        val navController = findNavController(R.id.nav_host_fragment)
////        // Passing each menu ID as a set of Ids because each
////        // menu should be considered as top level destinations.
////        appBarConfiguration = AppBarConfiguration(setOf(
////                R.id.nav_home, R.id.nav_myfavorites, R.id.nav_myprofile, R.id.nav_signout), drawerLayout)
////        setupActionBarWithNavController(navController, appBarConfiguration)
////        navView.setupWithNavController(navController)
//    }
//    //new
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
////        when (item.itemId) {
////            R.id.nav_home -> (Toast.makeText(this, "Menu Clicked", Toast.LENGTH_SHORT).show())
////            R.id.nav_myfavorites -> (Toast.makeText(this, "favorite Clicked", Toast.LENGTH_SHORT).show())
////            R.id.nav_myprofile -> (Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show())
////            R.id.nav_signout -> (Toast.makeText(this, "Signout Clicked", Toast.LENGTH_SHORT).show())
////        }
////        drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }
//    //old
////    override fun onBackPressed() {
////        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
////            drawer_layout.closeDrawer(GravityCompat.START)
////        } else {
////            super.onBackPressed()
////        }
////    }
////
////    override fun onCreateOptionsMenu(menu: Menu): Boolean {
////        menuInflater.inflate(R.menu.main, menu)
////        return true
////    }
////
////    override fun onOptionsItemSelected(item: MenuItem): Boolean {
////        when (item.itemId) {
////            R.id.action_settings -> return true
////            else -> return super.onOptionsItemSelected(item)
////        }
////    }
////
////    fun displayScreen(id: Int){
////
////        // val fragment =  when (id){
////
////        when (id){
////            R.id.nav_home -> {
////                supportFragmentManager.beginTransaction().replace(R.id DiscoverActivity()).commit()
////            }
////
////            R.id.nav_myprofile -> {
////                supportFragmentManager.beginTransaction().replace(R.id ProfileActivity()).commit()
////            }
////
////            R.id.nav_myfavorites -> {
////                supportFragmentManager.beginTransaction().replace(R.id FavoriteActivity()).commit()
////            }
////
////            R.id.nav_signout -> {
////
////            }
////        }
////    }
////
////    override fun onNavigationItemSelected(item: MenuItem): Boolean {
////        // Handle navigation view item clicks here.
////
////        displayScreen(item.itemId)
////
////        drawer_layout.closeDrawer(GravityCompat.START)
////        return true
////    }
//
//    //new2
////    override fun onCreateOptionsMenu(menu: Menu): Boolean {
////        // Inflate the menu; this adds items to the action bar if it is present.
////
////        menuInflater.inflate(R.menu.main, menu)
////        return true
////    }
////
////    override fun onSupportNavigateUp(): Boolean {
////        val navController = findNavController(R.id.nav_host_fragment)
////        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
////    }
//
//}