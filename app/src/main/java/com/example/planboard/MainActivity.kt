package com.example.planboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.coroutines.Runnable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val runnable = Runnable {
            val splashIntent = Intent(this, PlanboardActivity::class.java)
            startActivity(splashIntent)
            finish()
        }
        Handler().postDelayed(runnable, 1700)
    }
}