package com.example.psychologycareapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed({

            val user = FirebaseAuth.getInstance().currentUser

            if (user == null) {
                startActivity(Intent(this,LoginActivity::class.java))
            } else {
                startActivity(Intent(this,MainActivity::class.java))
            }

        }, 3000)
    }
}