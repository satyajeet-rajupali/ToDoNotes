package com.satyajeet.todonotes.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.satyajeet.todonotes.utils.AppConstant
import com.satyajeet.todonotes.R

class DetailsActivity : AppCompatActivity() {

    lateinit var title: TextView
    lateinit var description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        bindViews()
        setIntentData()
    }

    private fun setIntentData() {
        val intent = intent
        title.text = intent.getStringExtra(AppConstant.TITLE)
        description.text = intent.getStringExtra(AppConstant.DESCRIPTION)
    }


    private fun bindViews() {
        title = findViewById(R.id.details_title)
        description = findViewById(R.id.details_description)
    }
}