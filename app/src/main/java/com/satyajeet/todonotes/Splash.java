package com.satyajeet.todonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setUpSharedPreferences();
        checkLoginStatus();
    }

    private void setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefsConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }

    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean(PrefsConstant.IS_LOGGED_IN, false);

        if(isLoggedIn){
            // Open the MyNotesActivity.
            Intent intent = new Intent(Splash.this, MyNotesActivity.class);
            startActivity(intent);

        } else{
            // Open the LoginActivity.
            Intent intent = new Intent(Splash.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}