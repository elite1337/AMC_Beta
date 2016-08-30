package com.example.pete.amc;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class GameRankService extends Service {

    public static final String COUNTDOWN_BR = "popo";
    Intent bi = new Intent(COUNTDOWN_BR);

    CountDownTimer countDownTimer = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("servicethis", "Starting timer...");

        countDownTimer = new CountDownTimer(8000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

//                Log.d("servicethis", "Countdown seconds remaining: " + millisUntilFinished / 1000);
                bi.putExtra("countdown", millisUntilFinished);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish() {

                bi.putExtra("countdown", 0);
                sendBroadcast(bi);
                Log.d("servicethis", "Timer finished");


                Intent intent = new Intent(getApplicationContext(), GameRankEndActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                stopSelf();
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
