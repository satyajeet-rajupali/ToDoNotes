package com.satyajeet.todonotes.onboarding

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import androidx.viewpager2.widget.ViewPager2
import com.satyajeet.todonotes.R
import com.satyajeet.todonotes.activities.LoginActivity
import com.satyajeet.todonotes.utils.PrefsConstant

class onBoardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick, OnBoardingTwoFragment.OnOptionClick {

    lateinit var viewPager: ViewPager2
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindViews()
        setUpSharedPreferences()
    }

    private fun setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefsConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    }

    private fun bindViews() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(this)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }

    override fun onOptionNext() {
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefsConstant.ON_BOARDED_SUCCESSFULLY, true)
        editor.apply()
        val intent = Intent(this@onBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionBack() {
        viewPager.currentItem = 0
    }




}