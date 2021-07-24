package com.satyajeet.todonotes.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.satyajeet.todonotes.utils.AppConstant
import com.satyajeet.todonotes.utils.PrefsConstant
import com.satyajeet.todonotes.R
import java.util.*

class LoginActivity : AppCompatActivity() {

    lateinit var editTextFullName: EditText
    lateinit var editTextUserName: EditText
    lateinit var loginButton: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        loginButton = findViewById(R.id.loginButton)

        setUpSharedPrefernces()

        val loginButtonListener = object: View.OnClickListener{
            override fun onClick(v: View?) {
                val full_name  = editTextFullName.text.toString()
                val user_name = editTextUserName.text.toString()

                if(full_name.isNotEmpty() && user_name.isNotEmpty()){
                    val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                    intent.putExtra(AppConstant.FULL_NAME, full_name)
                    intent.putExtra(AppConstant.USER_NAME, user_name)
                    startActivity(intent)

                    saveLoginStatus()
                    saveFullName(full_name)

                } else{
                    Toast.makeText(this@LoginActivity, "Full Name and User Name are empty.", Toast.LENGTH_SHORT).show()

                }
            }

        }
        loginButton.setOnClickListener(loginButtonListener)
    }

    private fun saveFullName(fullName: String) {
        editor = sharedPreferences.edit()
        editor.putString(PrefsConstant.FULL_NAME, fullName)
        editor.apply()

    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefsConstant.IS_LOGGED_IN, true)
        editor.apply()
    }

    private fun setUpSharedPrefernces() {
        sharedPreferences = getSharedPreferences(PrefsConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    }

}