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
    TextView textViewTimer, textViewVocab, textViewPoS, textViewA, textViewB, textViewC, textViewD, textViewE;

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

    ArrayList<Integer> choices = new ArrayList<>();
    ArrayList<String> choicesChi = new ArrayList<>();
    ArrayList<Integer> choicesChiInt = new ArrayList<>();

    ArrayList<String> rightVoc = new ArrayList<>();

    int rightInt = 0;
    int wrongInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rank);

        getSupportActionBar().setTitle("單字積分");

        textViewTimer = (TextView)findViewById(R.id.textViewRankTimer);
        textViewVocab = (TextView)findViewById(R.id.textViewRankVocab);
        textViewPoS = (TextView)findViewById(R.id.textViewRankPoS);
        textViewA = (TextView)findViewById(R.id.textViewRankA);
        textViewB = (TextView)findViewById(R.id.textViewRankB);
        textViewC = (TextView)findViewById(R.id.textViewRankC);
        textViewD = (TextView)findViewById(R.id.textViewRankD);
        textViewE = (TextView)findViewById(R.id.textViewRankE);
        Button button = (Button)findViewById(R.id.buttonRankQuit);
        button.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        realm = Realm.getDefaultInstance();
        QuestionGenerator();

        textViewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                if (textViewA.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                {
                    rightVoc.add(textViewVocab.getText().toString());
                    rightInt++;
                }
                else
                {
                    if (rightVoc.contains(textViewVocab.getText().toString()))
                    {
                        rightVoc.remove(textViewVocab.getText().toString());
                    }
                    wrongInt++;
                }

                QuestionGenerator();

                mTimerView.stop();
                mTimerView.start(8);
                stopService(new Intent(GameRankActivity.this, GameRankService.class));
                startService(new Intent(GameRankActivity.this, GameRankService.class));
            }
        });

        textViewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                if (textViewB.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                {
                    rightVoc.add(textViewVocab.getText().toString());
                    rightInt++;
                }
                else
                {
                    if (rightVoc.contains(textViewVocab.getText().toString()))
                    {
                        rightVoc.remove(textViewVocab.getText().toString());
                    }
                    wrongInt++;
                }

                QuestionGenerator();

                mTimerView.stop();
                mTimerView.start(8);
                stopService(new Intent(GameRankActivity.this, GameRankService.class));
                startService(new Intent(GameRankActivity.this, GameRankService.class));
            }
        });

        textViewC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                if (textViewC.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                {
                    rightVoc.add(textViewVocab.getText().toString());
                    rightInt++;
                }
                else
                {
                    if (rightVoc.contains(textViewVocab.getText().toString()))
                    {
                        rightVoc.remove(textViewVocab.getText().toString());
                    }
                    wrongInt++;
                }

                QuestionGenerator();

                mTimerView.stop();
                mTimerView.start(8);
                stopService(new Intent(GameRankActivity.this, GameRankService.class));
                startService(new Intent(GameRankActivity.this, GameRankService.class));
            }
        });

        textViewD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                if (textViewD.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                {
                    rightVoc.add(textViewVocab.getText().toString());
                    rightInt++;
                }
                else
                {
                    if (rightVoc.contains(textViewVocab.getText().toString()))
                    {
                        rightVoc.remove(textViewVocab.getText().toString());
                    }
                    wrongInt++;
                }

                QuestionGenerator();

                mTimerView.stop();
                mTimerView.start(8);
                stopService(new Intent(GameRankActivity.this, GameRankService.class));
                startService(new Intent(GameRankActivity.this, GameRankService.class));
            }
        });

        textViewE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                if (textViewE.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                {
                    rightVoc.add(textViewVocab.getText().toString());
                    rightInt++;
                }
                else
                {
                    if (rightVoc.contains(textViewVocab.getText().toString()))
                    {
                        rightVoc.remove(textViewVocab.getText().toString());
                    }
                    wrongInt++;
                }

                QuestionGenerator();

                mTimerView.stop();
                mTimerView.start(8);
                stopService(new Intent(GameRankActivity.this, GameRankService.class));
                startService(new Intent(GameRankActivity.this, GameRankService.class));
            }
        });


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

        realm.close();
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
                onBackPressed();
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

    void QuestionGenerator()
    {
        choices.clear();
        choicesChi.clear();
        choicesChiInt.clear();

        RealmResults<VocabDictionary> realmResultsQuestion = realm.where(VocabDictionary.class).lessThan("vocabLv", 4).findAll();

        Random random = new Random(System.nanoTime());
        int question = random.nextInt(realmResultsQuestion.size());
        VocabDictionary vocabDictionaryQuestion = realmResultsQuestion.get(question);

        textViewVocab.setText(vocabDictionaryQuestion.getVocab());
        textViewPoS.setText(vocabDictionaryQuestion.getVocabPoS());

        RealmResults<VocabDictionary> realmResultsChoice = realm.where(VocabDictionary.class).equalTo("vocabPoS", vocabDictionaryQuestion.getVocabPoS()).findAll();
        //choices
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

        //choicesChi
        choicesChi.add(vocabDictionaryQuestion.getVocabChi());
        choicesChi.add(vocabDictionaryChoice1.getVocabChi());
        choicesChi.add(vocabDictionaryChoice2.getVocabChi());
        choicesChi.add(vocabDictionaryChoice3.getVocabChi());
        choicesChi.add(vocabDictionaryChoice4.getVocabChi());

        //choicesChiInt
        while (choicesChiInt.size() < 5)
        {
            int choice = random.nextInt(5);
            if (!choicesChiInt.contains(choice))
            {
                choicesChiInt.add(choice);
            }
        }

        textViewA.setText(choicesChi.get(choicesChiInt.get(0)));
        textViewB.setText(choicesChi.get(choicesChiInt.get(1)));
        textViewC.setText(choicesChi.get(choicesChiInt.get(2)));
        textViewD.setText(choicesChi.get(choicesChiInt.get(3)));
        textViewE.setText(choicesChi.get(choicesChiInt.get(4)));
    }
}