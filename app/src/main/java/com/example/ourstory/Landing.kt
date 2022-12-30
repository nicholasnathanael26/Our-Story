package com.example.ourstory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth

class Landing : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@Landing, LoginActivity::class.java))
            finish()
//            if (user != null){
//                startActivity(Intent(this@Landing, DiscoverActivity::class.java))
//                finish()
//            }else{
//                startActivity(Intent(this@Landing, LoginActivity::class.java))
//                finish()
//            }
        },2000)

    }
}