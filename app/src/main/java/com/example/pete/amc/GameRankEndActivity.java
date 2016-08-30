package com.example.pete.amc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class GameRankEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rank_end);

        getSupportActionBar().setTitle("單字積分 Summery");
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(GameRankEndActivity.this, MainActivity.class);
        intent.putExtra("gamerankend", "1");
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(GameRankEndActivity.this, MainActivity.class);
                intent.putExtra("gamerankend", "1");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
