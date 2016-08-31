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

import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

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

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rank);

        getSupportActionBar().setTitle("單字積分");

        textViewTimer = (TextView)findViewById(R.id.textViewRankTimer);
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

        realm = Realm.getDefaultInstance();
        RealmResults<VocabDictionary> realmResultsQuestion = realm.where(VocabDictionary.class).lessThan("vocabLv", 4).findAll();

        Random random = new Random(System.nanoTime());
        int question = random.nextInt(realmResultsQuestion.size());
        VocabDictionary vocabDictionaryQuestion = realmResultsQuestion.get(question);

        textViewVocab.setText(vocabDictionaryQuestion.getVocab());
        textViewPoS.setText(vocabDictionaryQuestion.getVocabPoS());
        textViewA.setText("(A) " + vocabDictionaryQuestion.getVocabChi());

        RealmResults<VocabDictionary> realmResultsChoice = realm.where(VocabDictionary.class).equalTo("vocabPoS", vocabDictionaryQuestion.getVocabPoS()).findAll();
        ArrayList<Integer> choices = new ArrayList<>();
        while (choices.size() < 4)
        {
            int choice = random.nextInt(realmResultsChoice.size());
            if (!choices.contains(choice))
            {
                VocabDictionary vocabDictionaryChoice = realmResultsChoice.get(choice);
                if (!vocabDictionaryQuestion.getVocabChi().equals(vocabDictionaryChoice.getVocabChi()))
                {
                    choices.add(choice);
                }
            }
        }
        VocabDictionary vocabDictionaryChoice1 = realmResultsChoice.get(choices.get(0));
        VocabDictionary vocabDictionaryChoice2 = realmResultsChoice.get(choices.get(1));
        VocabDictionary vocabDictionaryChoice3 = realmResultsChoice.get(choices.get(2));
        VocabDictionary vocabDictionaryChoice4 = realmResultsChoice.get(choices.get(3));

        ArrayList<String> choicesChi = new ArrayList<>();
        choicesChi.add(vocabDictionaryQuestion.getVocabChi());
        choicesChi.add(vocabDictionaryChoice1.getVocabChi());
        choicesChi.add(vocabDictionaryChoice2.getVocabChi());
        choicesChi.add(vocabDictionaryChoice3.getVocabChi());
        choicesChi.add(vocabDictionaryChoice4.getVocabChi());

        textViewB.setText("(B) " + vocabDictionaryChoice1.getVocabChi());
        textViewC.setText("(C) " + vocabDictionaryChoice2.getVocabChi());
        textViewD.setText("(D) " + vocabDictionaryChoice3.getVocabChi());
        textViewE.setText("(E) " + vocabDictionaryChoice4.getVocabChi());

//        for (int i = 0; i < realmResultsQuestion.size(); i++)
//        {
//            VocabDictionary vocabDictionary = realmResultsQuestion.get(i);
//            Log.d("popo", vocabDictionary.getVocab());
//        }
//
//        for (VocabDictionary vocabDictionary: realmResultsQuestion)
//        {
//            Log.d("lala", vocabDictionary.getVocab());
//        }

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putInt("millisuntilfinished", getB());
    }
}