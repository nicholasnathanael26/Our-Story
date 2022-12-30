package com.example.ourstory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        loadFragment(DiscoverFragment())

    }

//    private fun loadFragment(fragment: Fragment){
//        supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.fragment_container, fragment, fragment::class.java.simpleName)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .commit()
//    }
}