package com.example.pete.amc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

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
        TextView textViewPt = (TextView) findViewById(R.id.textViewRankEndPt);
        TextView textViewCC = (TextView) findViewById(R.id.textViewRankEndCC);

        Intent intent = getIntent();
        ArrayList<HashMap<String, String>> arrayList = (ArrayList<HashMap<String,String>>) intent.getSerializableExtra("sumvoc");
        GameRankEndBaseAdapter historyAdapter = new GameRankEndBaseAdapter(this, arrayList, gameRankActivity);

        double pt = intent.getDoubleExtra("point", 0);
        double counter = intent.getDoubleExtra("counter", 0);
        double right = intent.getDoubleExtra("right", 0);
        int pt2Int = (int) pt;
        int counter2Int = (int) counter;

        double rate = Math.round(right/counter*10000.00)/100.00;

        textViewPt.setText(Point(pt2Int));
        textViewCC.setText(counter2Int + "/ " + rate + "%");

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

    String point;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    String Point(int i)
    {
        if (i > 0)
        {
            setPoint("+" + i);
        }
        else
        {
            setPoint(i+"");
        }

        return getPoint();
    }
}
