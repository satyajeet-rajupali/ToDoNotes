package com.satyajeet.todonotes.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.satyajeet.todonotes.utils.PrefsConstant
import com.satyajeet.todonotes.R
import com.satyajeet.todonotes.onboarding.onBoardingActivity

class Splash : AppCompatActivity() {

    lateinit var handler: Handler
    lateinit var runnable: Runnable
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUpSharedPreferences()
        goToNext()
    }

    private fun goToNext() {
        handler = Handler()
        runnable = Runnable {
            checkLoginStatus()
        }
        handler.postDelayed(runnable, 2000)
    }


    private fun setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefsConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = sharedPreferences.getBoolean(PrefsConstant.IS_LOGGED_IN, false)
        val onBoardedSuccesfully = sharedPreferences.getBoolean(PrefsConstant.ON_BOARDED_SUCCESSFULLY, false)

        if(isLoggedIn){
            // Opens the MyNotesActivity
            val intent = Intent(this@Splash, MyNotesActivity::class.java)
            startActivity(intent)
        } else {
            if (onBoardedSuccesfully) {
                // Opens the LoginActivity
                val intent = Intent(this@Splash, LoginActivity::class.java)
                startActivity(intent)
            } else{
                val intent = Intent(this@Splash, onBoardingActivity::class.java)
                startActivity(intent)
            }
        }

        finish()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        handler.removeCallbacks(runnable)
    }


}