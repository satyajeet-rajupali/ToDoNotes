package com.satyajeet.todonotes.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.satyajeet.todonotes.utils.PrefsConstant
import com.satyajeet.todonotes.R

class Splash : AppCompatActivity() {

    lateinit var sharedPrefernces: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setUpSharedPrefernces()
        checkLoginStatus()
    }

    private fun setUpSharedPrefernces() {
        sharedPrefernces = getSharedPreferences(PrefsConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = sharedPrefernces.getBoolean(PrefsConstant.IS_LOGGED_IN, false)

        if(isLoggedIn){
            // Opens the MyNotesActivity
            val intent = Intent(this@Splash, MyNotesActivity::class.java)
            startActivity(intent)
        } else {
            // Opens the LoginActivity
            val intent = Intent(this@Splash, LoginActivity::class.java)
            startActivity(intent)
        }
    }


}