package com.example.pete.amc;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getSupportActionBar().setTitle("AMC");

        EditText editTextUser = (EditText)findViewById(R.id.editTextCreateUser);
        EditText editTextEmail = (EditText)findViewById(R.id.editTextCreateEmail);
        EditText editTextPW = (EditText)findViewById(R.id.editTextCreatePW);
        Button button = (Button)findViewById(R.id.buttonCreateAcc);

        button.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);

    }
}
