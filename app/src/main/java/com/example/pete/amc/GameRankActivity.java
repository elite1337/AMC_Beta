package com.example.pete.amc;

import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class GameRankActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    TextView textViewTimer;

    private static final int TIMER_LENGTH = 7;
    private GameRankTimer mTimerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rank);

        getSupportActionBar().setTitle("單字積分");

        Button button = (Button)findViewById(R.id.buttonRankQuit);
        button.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);

        textViewTimer = (TextView)findViewById(R.id.textViewRankTimer);
        countDownTimer = new CountDownTimer(8000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTimer.setText(String.valueOf((int)millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

                textViewTimer.setText("0");
                onBackPressed();
            }
        }.start();



        mTimerView = (GameRankTimer)findViewById(R.id.timer);
        mTimerView.start(TIMER_LENGTH);

    }

    @Override
    protected void onPause() {
        mTimerView.stop();

        super.onPause();
    }

    @Override
    public void onBackPressed() {

        countDownTimer.cancel();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                mTimerView.stop();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
