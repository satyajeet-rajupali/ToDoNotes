package com.satyajeet.todonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // To get hold of the widgets described in LoginActivity.
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextUserName = findViewById(R.id.editTextUserName);
        loginButton = findViewById(R.id.loginButton);

        // OnClickListener interface for the loginButton.
        View.OnClickListener loginButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String full_name = editTextFullName.getText().toString();
                final String user_name = editTextUserName.getText().toString();

                // To check if full_name and user_name != empty
                if(!TextUtils.isEmpty(full_name) && !TextUtils.isEmpty(user_name)) {
                    Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);
                    intent.putExtra("full_name", full_name);
                    intent.putExtra("user_name", user_name);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Full Name and User Name are empty.",Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Setting up the clickListener to the loginButton.
        loginButton.setOnClickListener(loginButtonListener);
    }
}