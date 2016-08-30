package com.example.pete.amc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameRankActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    TextView textViewTimer;

//    int b;
//
//    public int getB() {
//        return b;
//    }
//
//    public void setB(int b) {
//        this.b = b;
//    }

    private GameRankTimer mTimerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rank);

        getSupportActionBar().setTitle("單字積分");

        TextView textViewVocab = (TextView)findViewById(R.id.textViewRankVocab);
        TextView textViewPoS = (TextView)findViewById(R.id.textViewRankPoS);
        TextView textViewA = (TextView)findViewById(R.id.textViewRankA);
        TextView textViewB = (TextView)findViewById(R.id.textViewRankB);
        TextView textViewC = (TextView)findViewById(R.id.textViewRankC);
        TextView textViewD = (TextView)findViewById(R.id.textViewRankD);
        TextView textViewE = (TextView)findViewById(R.id.textViewRankE);
        Button button = (Button)findViewById(R.id.buttonRankQuit);
        button.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        textViewTimer = (TextView)findViewById(R.id.textViewRankTimer);

        startService(new Intent(this, GameRankService.class));
        Log.i("servicethis", "Started service");


//        if (savedInstanceState == null)
//        {
//            setB(8000);
//        }
//        else
//        {
//            setB(savedInstanceState.getInt("millisuntilfinished"));
//        }
//        countDownTimer = new CountDownTimer(8000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//                setB((int) millisUntilFinished);
//                textViewTimer.setText(String.valueOf((int) millisUntilFinished / 1000));
//            }
//            @Override
//            public void onFinish() {
//
//                textViewTimer.setText("0");
//                onBackPressed();
//            }
//        }.start();

        mTimerView = (GameRankTimer)findViewById(R.id.timer);
        mTimerView.start(8);
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        countDownTimer.cancel();
//    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            Log.d("servicethis", "Countdown seconds remaining: " +  millisUntilFinished / 1000);

            textViewTimer.setText(String.valueOf((int) millisUntilFinished / 1000));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        registerReceiver(br, new IntentFilter(GameRankService.COUNTDOWN_BR));
        Log.d("servicethis", "Registered broacast receiver");
    }

    @Override
    protected void onPause() {
        super.onPause();

        mTimerView.stop();

        unregisterReceiver(br);
        Log.d("servicethis", "Unregistered broacast receiver");
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {

//        stopService(new Intent(this, GameRankService.class));
//        Log.d("servicethis", "Stopped service");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        stopService(new Intent(this, GameRankService.class));
        Intent intent = new Intent(getApplicationContext(), GameRankEndActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                mTimerView.stop();
                Intent intent = new Intent(getApplicationContext(), GameRankEndActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putInt("millisuntilfinished", getB());
//    }
}