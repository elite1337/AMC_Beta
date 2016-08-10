package com.example.pete.amc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FavoriteFAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_faq);

        getSupportActionBar().setTitle("Frequently Asked Questions");

    }
}
