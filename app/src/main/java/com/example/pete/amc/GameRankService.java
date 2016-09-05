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

    int pick;

    public int getPick() {
        return pick;
    }

    public void setPick(int pick) {
        this.pick = pick;
    }

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

                if (getPick() != 1)
                {
                    Intent intent = new Intent(getApplicationContext(), GameRankEndActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    stopSelf();
                }
                else
                {
                    intent.putExtra("pick", 2);
                    sendBroadcast(intent);
                }
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

//        Bundle bundle = intent.getExtras();
//        setPick(bundle.getInt("pick"));
        intent.getExtras();
        setPick(intent.getIntExtra("pick", 0));
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
