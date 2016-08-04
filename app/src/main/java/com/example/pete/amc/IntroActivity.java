package com.example.pete.amc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class IntroActivity extends AppCompatActivity {

    Handler mHandler = new Handler();

    SharedPreferences sharedPreferences;
    String identification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        identification = sharedPreferences.getString("identification", "default");

        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (identification.equals("0"))
                {
                    Intent intentLogIn = new Intent(getApplicationContext(), IntroLogInActivity.class);
                    startActivity(intentLogIn);
                }
                else if (identification.equals("default"))
                {
                    Intent intentLogIn = new Intent(getApplicationContext(), IntroLogInActivity.class);
                    startActivity(intentLogIn);
                }
                else
                {
                    onBackPressed();
                }
            }
        }, 2500L);

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.intro);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mHandler.removeCallbacksAndMessages(null);
                if(identification.equals("0"))
                {
                    Intent intentLogIn = new Intent(getApplicationContext(), IntroLogInActivity.class);
                    startActivity(intentLogIn);
                }
                else if (identification.equals("default"))
                {
                    Intent intentLogIn = new Intent(getApplicationContext(), IntroLogInActivity.class);
                    startActivity(intentLogIn);
                }
                else
                {
                    onBackPressed();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(identification.equals("1"))
        {
            super.onBackPressed();
        }
    }
}
