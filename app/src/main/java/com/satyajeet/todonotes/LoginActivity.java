package com.satyajeet.todonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

                Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);
                intent.putExtra("full_name", full_name);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
            }
        };

        // Setting up the clickListener to the loginButton.
        loginButton.setOnClickListener(loginButtonListener);
    }
}