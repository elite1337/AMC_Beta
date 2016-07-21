package com.example.pete.amc;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IntroLogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_log_in);

        getSupportActionBar().hide();

        Button buttonLogIn = (Button)findViewById(R.id.buttonIntroLogIn);
        Button buttonLater = (Button)findViewById(R.id.buttonIntroLater);
        TextView textView = (TextView)findViewById(R.id.textViewIntroCreate);

        buttonLogIn.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);
        buttonLater.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentLogInInfo = new Intent(getApplicationContext(), LogInInfoActivity.class);
                startActivity(intentLogInInfo);
            }
        });

        buttonLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentCreateAccount = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intentCreateAccount);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}