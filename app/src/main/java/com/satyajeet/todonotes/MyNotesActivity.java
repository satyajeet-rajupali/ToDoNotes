package com.satyajeet.todonotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MyNotesActivity extends AppCompatActivity {

    String full_name, user_name;
    FloatingActionButton openButton;
    TextView title, description;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        bindingViews();
        setUpSharedPrefernce();
        getIntentValues();

        Objects.requireNonNull(getSupportActionBar()).setTitle(full_name);

        View.OnClickListener openClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpDialogBox();
            }
        };

        openButton.setOnClickListener(openClickListener);
    }

    private void setUpSharedPrefernce() {
        sharedPreferences = getSharedPreferences(PrefsConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }

    private void setUpDialogBox(){
        View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.dailog1, null);
        EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        EditText editTextDescription = view.findViewById(R.id.editTextDescription);
        Button button = view.findViewById(R.id.submit_button);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        dialog.show();

        View.OnClickListener submitButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText(editTextTitle.getText());
                description.setText(editTextDescription.getText());
                dialog.hide();
            }
        };

        button.setOnClickListener(submitButtonClickListener);
    }




    public void getIntentValues(){
        Intent intent = getIntent();
        full_name = intent.getStringExtra(AppConstant.FULL_NAME);
        user_name = intent.getStringExtra(AppConstant.USER_NAME);
        if(TextUtils.isEmpty(full_name)){
            full_name = sharedPreferences.getString(PrefsConstant.FULL_NAME, "");
        }
    }


    public void bindingViews(){
        openButton = findViewById(R.id.open_dialog_button);
        title = findViewById(R.id.textViewTitle);
        description = findViewById(R.id.textViewDescription);
    }
}