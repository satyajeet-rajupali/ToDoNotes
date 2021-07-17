package com.satyajeet.todonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editTextFullName, editTextUserName;
    Button loginButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // To get hold of the widgets described in LoginActivity.
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextUserName = findViewById(R.id.editTextUserName);
        loginButton = findViewById(R.id.loginButton);
        setUpSharedPreferences();

        // OnClickListener interface for the loginButton.
        View.OnClickListener loginButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String full_name = editTextFullName.getText().toString();
                final String user_name = editTextUserName.getText().toString();

                // To check if full_name and user_name != empty
                if(!TextUtils.isEmpty(full_name) && !TextUtils.isEmpty(user_name)) {
                    Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);
                    intent.putExtra(AppConstant.FULL_NAME, full_name);
                    intent.putExtra(AppConstant.USER_NAME, user_name);
                    startActivity(intent);

                    // Login Status
                    saveLoginStatus();
                    saveFullName(full_name);
                } else {
                    Toast.makeText(LoginActivity.this, "Full Name and User Name are empty.",Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Setting up the clickListener to the loginButton.
        loginButton.setOnClickListener(loginButtonListener);
    }

    private void saveFullName(String full_name) {
        editor = sharedPreferences.edit();
        editor.putString(PrefsConstant.FULL_NAME, full_name);
        editor.apply();
    }


    private void saveLoginStatus(){
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefsConstant.IS_LOGGED_IN, true);
        editor.apply();
    }


    private void setUpSharedPreferences(){
        sharedPreferences = getSharedPreferences(PrefsConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }
}