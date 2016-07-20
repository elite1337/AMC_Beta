package com.example.pete.amc;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class PopUpActivity extends AppCompatActivity {

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        getSupportActionBar().hide();

        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                onBackPressed();
            }
        }, 2500L);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.popup);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacksAndMessages(null);
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        mHandler.removeCallbacksAndMessages(null);
    }
}
