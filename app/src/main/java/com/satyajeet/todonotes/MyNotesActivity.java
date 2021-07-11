package com.satyajeet.todonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

public class MyNotesActivity extends AppCompatActivity {

    String full_name, user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        Intent intent = getIntent();
        full_name = intent.getStringExtra("full_name");
        user_name = intent.getStringExtra("user_name");



        Objects.requireNonNull(getSupportActionBar()).setTitle(full_name);
    }
}