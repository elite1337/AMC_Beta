package com.example.pete.amc;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class GameRankService extends Service {

    public static final String COUNTDOWN_BR = "popo";
    Intent intent = new Intent(COUNTDOWN_BR);

    CountDownTimer countDownTimer = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("servicethis", "Starting timer...");

        countDownTimer = new CountDownTimer(8000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

//                Log.d("servicethis", "Countdown seconds remaining: " + millisUntilFinished / 1000);
                intent.putExtra("countdown", millisUntilFinished);
                sendBroadcast(intent);
            }

            @Override
            public void onFinish() {

                intent.putExtra("countdown", 0);
                sendBroadcast(intent);
                Log.d("servicethis", "Timer finished");
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onDestroy() {

        countDownTimer.cancel();
        Log.d("servicethis", "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
