package com.example.pete.amc;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogInInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_info);

        getSupportActionBar().setTitle("AMC");

        EditText editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        EditText editTextPW = (EditText)findViewById(R.id.editTextPW);
        TextView textView = (TextView)findViewById(R.id.textViewForget);
        Button button = (Button)findViewById(R.id.buttonRealLogIn);

        button.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);

    }
}
