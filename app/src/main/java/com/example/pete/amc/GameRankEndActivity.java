package com.example.pete.amc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class GameRankEndActivity extends AppCompatActivity {

    GameRankActivity gameRankActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rank_end);

        getSupportActionBar().setTitle("單字積分 Summery");

        ListView listView = (ListView) findViewById(R.id.listViewRankEnd);
        Intent intent = getIntent();
        ArrayList<HashMap<String, String>> arrayList = (ArrayList<HashMap<String,String>>) intent.getSerializableExtra("sumvoc");
        GameRankEndBaseAdapter historyAdapter = new GameRankEndBaseAdapter(this, arrayList, gameRankActivity);

        listView.setAdapter(historyAdapter);

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
