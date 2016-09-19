package com.example.pete.amc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

public class GameRankActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    TextView textViewTimer, textViewVocab, textViewPoS, textViewA, textViewB, textViewC, textViewD, textViewE;
    LinearLayout linearLayoutA, linearLayoutB, linearLayoutC, linearLayoutD, linearLayoutE;
    Button button;

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

    ArrayList<Integer> choicesArray = new ArrayList<>();
    ArrayList<String> choicesChiArray = new ArrayList<>();
    ArrayList<Integer> choicesChiIntArray = new ArrayList<>();
    ArrayList<Integer> questionChoiceArray = new ArrayList<>();

    ArrayList<HashMap<String, String>> sumVoc = new ArrayList<>();
    HashMap<String, String> hashMap = new HashMap<>();
    ArrayList<String> arrayListVoc = new ArrayList<>();
    ArrayList<String> arrayListRightVoc = new ArrayList<>();
    ArrayList<Integer> arrayListLevelSize = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getSumVoc() {
        return sumVoc;
    }

    public void setSumVoc(ArrayList<HashMap<String, String>> sumVoc) {
        this.sumVoc = sumVoc;
    }

    double point = 0;
    double counter = 0;
    double right = 0;
    int wrongCount;
    int level;
    int questionCurrentRight;

    public int getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(int wrongCount) {
        this.wrongCount = wrongCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getQuestionCurrentRight() {
        return questionCurrentRight;
    }

    public void setQuestionCurrentRight(int questionCurrentRight) {
        this.questionCurrentRight = questionCurrentRight;
    }

    ColorStateList oldColor;

    int pickA;
    int pickB;
    int pickC;
    int pickD;
    int pickE;
    int pick;
    int quit;

    public int getPickE() {
        return pickE;
    }

    public void setPickE(int pickE) {
        this.pickE = pickE;
    }

    public int getPickD() {
        return pickD;
    }

    public void setPickD(int pickD) {
        this.pickD = pickD;
    }

    public int getPickC() {
        return pickC;
    }

    public void setPickC(int pickC) {
        this.pickC = pickC;
    }

    public int getPickB() {
        return pickB;
    }

    public void setPickB(int pickB) {
        this.pickB = pickB;
    }

    public int getPickA() {
        return pickA;
    }

    public void setPickA(int pickA) {
        this.pickA = pickA;
    }

    public int getPick() {
        return pick;
    }

    public void setPick(int pick) {
        this.pick = pick;
    }

    public int getQuit() {
        return quit;
    }

    public void setQuit(int quit) {
        this.quit = quit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rank);

        getSupportActionBar().setTitle("單字積分");

        textViewTimer = (TextView)findViewById(R.id.textViewRankTimer);
        textViewVocab = (TextView)findViewById(R.id.textViewRankVocab);
        textViewPoS = (TextView)findViewById(R.id.textViewRankPoS);
        linearLayoutA = (LinearLayout)findViewById(R.id.linearLayoutRankA);
        linearLayoutB = (LinearLayout)findViewById(R.id.linearLayoutRankB);
        linearLayoutC = (LinearLayout)findViewById(R.id.linearLayoutRankC);
        linearLayoutD = (LinearLayout)findViewById(R.id.linearLayoutRankD);
        linearLayoutE = (LinearLayout)findViewById(R.id.linearLayoutRankE);
        textViewA = (TextView)findViewById(R.id.textViewRankA);
        textViewB = (TextView)findViewById(R.id.textViewRankB);
        textViewC = (TextView)findViewById(R.id.textViewRankC);
        textViewD = (TextView)findViewById(R.id.textViewRankD);
        textViewE = (TextView)findViewById(R.id.textViewRankE);
        button = (Button)findViewById(R.id.buttonRankQuit);

        setQuit(0);
        button.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setQuit(1);
            }
        });

        realm = Realm.getDefaultInstance();

        setPickA(0);
        setPickB(0);
        setPickC(0);
        setPickD(0);
        setPickE(0);
        setPick(0);
        setWrongCount(0);
        setLevel(1);
        //Recording all the levels' size for the ranking system
        for (int i = 1; i <= 25; i++)
        {
            RealmResults<VocabDictionary> realmResultsLevel = realm.where(VocabDictionary.class).equalTo("vocabLv", i).findAll();
            arrayListLevelSize.add(realmResultsLevel.size());
        }
        oldColor = textViewA.getTextColors();

        QuestionGenerator();

        Intent intent = new Intent(this, GameRankService.class);
        startService(intent);
        Log.d("servicethis", "Started service");
        mTimerView = (GameRankTimer)findViewById(R.id.timer);
        mTimerView.start(8);

        linearLayoutA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getPickA() == 0)
                {
                    textViewA.setTextColor(0xFF3F51B5);
                    textViewB.setTextColor(oldColor);
                    textViewC.setTextColor(oldColor);
                    textViewD.setTextColor(oldColor);
                    textViewE.setTextColor(oldColor);
                    setPickA(1);
                    setPickB(0);
                    setPickC(0);
                    setPickD(0);
                    setPickE(0);
                    setPick(1);
                }
                else
                {
                    if (!arrayListVoc.contains(textViewVocab.getText().toString()))
                    {
                        hashMap = new HashMap<>();
                        hashMap.put("voc", textViewVocab.getText().toString());

                        arrayListVoc.add(textViewVocab.getText().toString());

                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                        if (textViewA.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            point++;
                            right++;
                            hashMap.put("status", "1");
                            arrayListRightVoc.add(textViewVocab.getText().toString());
                        }
                        else
                        {
                            hashMap.put("status", "0");
                            setWrongCount(getWrongCount()+1);
                        }
                        sumVoc.add(hashMap);
                    }
                    else
                    {
                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                        if (!textViewA.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (!sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point--;
                                        setWrongCount(getWrongCount()+1);
                                    }
                                    sumVoc.get(i).put("status", "0");
                                    arrayListRightVoc.remove(textViewVocab.getText().toString());
                                }
                            }
                        }
                        else
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point++;
                                    }
                                    sumVoc.get(i).put("status", "1");
                                    arrayListRightVoc.add(textViewVocab.getText().toString());
                                }
                            }
                            right++;
                            setWrongCount(0);
                        }
                    }
                    setSumVoc(sumVoc);

                    QuestionGenerator();

                    mTimerView.stop();
                    mTimerView.start(8);
                    stopService(new Intent(GameRankActivity.this, GameRankService.class));
                    startService(new Intent(GameRankActivity.this, GameRankService.class));

                    textViewA.setTextColor(oldColor);
                    setPickA(0);
                    setPick(0);
                }
            }
        });

        linearLayoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getPickB() == 0)
                {
                    textViewA.setTextColor(oldColor);
                    textViewB.setTextColor(0xFF3F51B5);
                    textViewC.setTextColor(oldColor);
                    textViewD.setTextColor(oldColor);
                    textViewE.setTextColor(oldColor);
                    setPickA(0);
                    setPickB(1);
                    setPickC(0);
                    setPickD(0);
                    setPickE(0);
                    setPick(1);
                }
                else
                {
                    if (!arrayListVoc.contains(textViewVocab.getText().toString()))
                    {
                        hashMap = new HashMap<>();
                        hashMap.put("voc", textViewVocab.getText().toString());

                        arrayListVoc.add(textViewVocab.getText().toString());

                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                        if (textViewB.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            point++;
                            right++;
                            hashMap.put("status", "1");
                            arrayListRightVoc.add(textViewVocab.getText().toString());
                        }
                        else
                        {
                            hashMap.put("status", "0");
                            setWrongCount(getWrongCount()+1);
                        }
                        sumVoc.add(hashMap);
                    }
                    else
                    {
                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                        if (!textViewB.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (!sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point--;
                                        setWrongCount(getWrongCount()+1);
                                    }
                                    sumVoc.get(i).put("status", "0");
                                    arrayListRightVoc.remove(textViewVocab.getText().toString());
                                }
                            }
                        }
                        else
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point++;
                                    }
                                    sumVoc.get(i).put("status", "1");
                                    arrayListRightVoc.add(textViewVocab.getText().toString());
                                }
                            }
                            right++;
                            setWrongCount(0);
                        }
                    }
                    setSumVoc(sumVoc);

                    QuestionGenerator();

                    mTimerView.stop();
                    mTimerView.start(8);
                    stopService(new Intent(GameRankActivity.this, GameRankService.class));
                    startService(new Intent(GameRankActivity.this, GameRankService.class));

                    textViewB.setTextColor(oldColor);
                    setPickB(0);
                    setPick(0);
                }
            }
        });

        linearLayoutC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getPickC() == 0)
                {
                    textViewA.setTextColor(oldColor);
                    textViewB.setTextColor(oldColor);
                    textViewC.setTextColor(0xFF3F51B5);
                    textViewD.setTextColor(oldColor);
                    textViewE.setTextColor(oldColor);
                    setPickA(0);
                    setPickB(0);
                    setPickC(1);
                    setPickD(0);
                    setPickE(0);
                    setPick(1);
                }
                else
                {
                    if (!arrayListVoc.contains(textViewVocab.getText().toString()))
                    {
                        hashMap = new HashMap<>();
                        hashMap.put("voc", textViewVocab.getText().toString());

                        arrayListVoc.add(textViewVocab.getText().toString());

                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                        if (textViewC.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            point++;
                            right++;
                            hashMap.put("status", "1");
                            arrayListRightVoc.add(textViewVocab.getText().toString());
                        }
                        else
                        {
                            hashMap.put("status", "0");
                            setWrongCount(getWrongCount()+1);
                        }
                        sumVoc.add(hashMap);
                    }
                    else
                    {
                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                        if (!textViewC.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (!sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point--;
                                        setWrongCount(getWrongCount()+1);
                                    }
                                    sumVoc.get(i).put("status", "0");
                                    arrayListRightVoc.remove(textViewVocab.getText().toString());
                                }
                            }
                        }
                        else
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point++;
                                    }
                                    sumVoc.get(i).put("status", "1");
                                    arrayListRightVoc.add(textViewVocab.getText().toString());
                                }
                            }
                            right++;
                            setWrongCount(0);
                        }
                    }
                    setSumVoc(sumVoc);

                    QuestionGenerator();

                    mTimerView.stop();
                    mTimerView.start(8);
                    stopService(new Intent(GameRankActivity.this, GameRankService.class));
                    startService(new Intent(GameRankActivity.this, GameRankService.class));

                    textViewC.setTextColor(oldColor);
                    setPickC(0);
                    setPick(0);
                }
            }
        });

        linearLayoutD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getPickD() == 0)
                {
                    textViewA.setTextColor(oldColor);
                    textViewB.setTextColor(oldColor);
                    textViewC.setTextColor(oldColor);
                    textViewD.setTextColor(0xFF3F51B5);
                    textViewE.setTextColor(oldColor);
                    setPickA(0);
                    setPickB(0);
                    setPickC(0);
                    setPickD(1);
                    setPickE(0);
                    setPick(1);
                }
                else
                {
                    if (!arrayListVoc.contains(textViewVocab.getText().toString()))
                    {
                        hashMap = new HashMap<>();
                        hashMap.put("voc", textViewVocab.getText().toString());

                        arrayListVoc.add(textViewVocab.getText().toString());

                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                        if (textViewD.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            point++;
                            right++;
                            hashMap.put("status", "1");
                            arrayListRightVoc.add(textViewVocab.getText().toString());
                        }
                        else
                        {
                            hashMap.put("status", "0");
                            setWrongCount(getWrongCount()+1);
                        }
                        sumVoc.add(hashMap);
                    }
                    else
                    {
                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                        if (!textViewD.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (!sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point--;
                                        setWrongCount(getWrongCount()+1);
                                    }
                                    sumVoc.get(i).put("status", "0");
                                    arrayListRightVoc.remove(textViewVocab.getText().toString());
                                }
                            }
                        }
                        else
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point++;
                                    }
                                    sumVoc.get(i).put("status", "1");
                                    arrayListRightVoc.add(textViewVocab.getText().toString());
                                }
                            }
                            right++;
                            setWrongCount(0);
                        }
                    }
                    setSumVoc(sumVoc);

                    QuestionGenerator();

                    mTimerView.stop();
                    mTimerView.start(8);
                    stopService(new Intent(GameRankActivity.this, GameRankService.class));
                    startService(new Intent(GameRankActivity.this, GameRankService.class));

                    textViewD.setTextColor(oldColor);
                    setPickD(0);
                    setPick(0);
                }
            }
        });

        linearLayoutE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getPickE() == 0)
                {
                    textViewA.setTextColor(oldColor);
                    textViewB.setTextColor(oldColor);
                    textViewC.setTextColor(oldColor);
                    textViewD.setTextColor(oldColor);
                    textViewE.setTextColor(0xFF3F51B5);
                    setPickA(0);
                    setPickB(0);
                    setPickC(0);
                    setPickD(0);
                    setPickE(1);
                    setPick(1);
                }
                else
                {
                    if (!arrayListVoc.contains(textViewVocab.getText().toString()))
                    {
                        hashMap = new HashMap<>();
                        hashMap.put("voc", textViewVocab.getText().toString());

                        arrayListVoc.add(textViewVocab.getText().toString());

                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                        if (textViewE.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            point++;
                            right++;
                            hashMap.put("status", "1");
                            arrayListRightVoc.add(textViewVocab.getText().toString());
                        }
                        else
                        {
                            hashMap.put("status", "0");
                            setWrongCount(getWrongCount()+1);
                        }
                        sumVoc.add(hashMap);
                    }
                    else
                    {
                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                        if (!textViewE.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (!sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point--;
                                        setWrongCount(getWrongCount()+1);
                                    }
                                    sumVoc.get(i).put("status", "0");
                                    arrayListRightVoc.remove(textViewVocab.getText().toString());
                                }
                            }
                        }
                        else
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point++;
                                    }
                                    sumVoc.get(i).put("status", "1");
                                    arrayListRightVoc.add(textViewVocab.getText().toString());
                                }
                            }
                            right++;
                            setWrongCount(0);
                        }
                    }
                    setSumVoc(sumVoc);

                    QuestionGenerator();

                    mTimerView.stop();
                    mTimerView.start(8);
                    stopService(new Intent(GameRankActivity.this, GameRankService.class));
                    startService(new Intent(GameRankActivity.this, GameRankService.class));

                    textViewE.setTextColor(oldColor);
                    setPickE(0);
                    setPick(0);
                }
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
//
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
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        countDownTimer.cancel();
//    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
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

            //Quit when time out
            if (millisUntilFinished == 0 & getPick() == 0)
            {
                if (!arrayListVoc.contains(textViewVocab.getText().toString()))
                {
                    hashMap = new HashMap<>();
                    hashMap.put("voc", textViewVocab.getText().toString());

                    arrayListVoc.add(textViewVocab.getText().toString());

                    hashMap.put("status", "0");
                    sumVoc.add(hashMap);
                }
                else
                {
                    RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                    VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                    if (!textViewA.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                    {
                        for (int i = 0; i < sumVoc.size(); i++)
                        {
                            if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                            {
                                if (!sumVoc.get(i).get("status").equals("0"))
                                {
                                    point--;
                                }
                                sumVoc.get(i).put("status", "0");
                            }
                        }
                    }
                }
                setSumVoc(sumVoc);

                stopService(new Intent(this, GameRankService.class));
                Intent intentGameRankEndActivity = new Intent(getApplicationContext(), GameRankEndActivity.class);
                intentGameRankEndActivity.putExtra("sumvoc", getSumVoc());
                intentGameRankEndActivity.putExtra("point", point);
                intentGameRankEndActivity.putExtra("counter", counter);
                intentGameRankEndActivity.putExtra("right", right);
                startActivity(intentGameRankEndActivity);
            }

            //Quit
            if (getQuit() == 1)
            {
                button.setText("Quit in " + String.valueOf((int) millisUntilFinished / 1000) + " sec(s)...");

                if (millisUntilFinished == 0)
                {
                    if (!arrayListVoc.contains(textViewVocab.getText().toString()))
                    {
                        hashMap = new HashMap<>();
                        hashMap.put("voc", textViewVocab.getText().toString());

                        arrayListVoc.add(textViewVocab.getText().toString());

                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);

                        if (getPickA() == 1)
                        {
                            if (textViewA.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                point++;
                                right++;
                                hashMap.put("status", "1");
                            }
                            else
                            {
                                hashMap.put("status", "0");
                            }
                        }

                        if (getPickB() == 1)
                        {
                            if (textViewB.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                point++;
                                right++;
                                hashMap.put("status", "1");
                            }
                            else
                            {
                                hashMap.put("status", "0");
                            }
                        }

                        if (getPickC() == 1)
                        {
                            if (textViewC.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                point++;
                                right++;
                                hashMap.put("status", "1");
                            }
                            else
                            {
                                hashMap.put("status", "0");
                            }
                        }

                        if (getPickD() == 1)
                        {
                            if (textViewD.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                point++;
                                right++;
                                hashMap.put("status", "1");
                            }
                            else
                            {
                                hashMap.put("status", "0");
                            }
                        }

                        if (getPickE() == 1)
                        {
                            if (textViewE.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                point++;
                                right++;
                                hashMap.put("status", "1");
                            }
                            else
                            {
                                hashMap.put("status", "0");
                            }
                        }

                        sumVoc.add(hashMap);
                    }
                    else
                    {
                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);

                        if (getPickA() == 1)
                        {
                            if (!textViewA.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (!sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point--;
                                        }
                                        sumVoc.get(i).put("status", "0");
                                    }
                                }
                            }
                            else
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point++;
                                        }
                                        sumVoc.get(i).put("status", "1");
                                        arrayListRightVoc.add(textViewVocab.getText().toString());
                                    }
                                }
                                right++;
                                setWrongCount(0);
                            }
                        }

                        if (getPickB() == 1)
                        {
                            if (!textViewB.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (!sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point--;
                                        }
                                        sumVoc.get(i).put("status", "0");
                                    }
                                }
                            }
                            else
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point++;
                                        }
                                        sumVoc.get(i).put("status", "1");
                                        arrayListRightVoc.add(textViewVocab.getText().toString());
                                    }
                                }
                                right++;
                                setWrongCount(0);
                            }
                        }

                        if (getPickC() == 1)
                        {
                            if (!textViewC.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (!sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point--;
                                        }
                                        sumVoc.get(i).put("status", "0");
                                    }
                                }
                            }
                            else
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point++;
                                        }
                                        sumVoc.get(i).put("status", "1");
                                        arrayListRightVoc.add(textViewVocab.getText().toString());
                                    }
                                }
                                right++;
                                setWrongCount(0);
                            }
                        }

                        if (getPickD() == 1)
                        {
                            if (!textViewD.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (!sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point--;
                                        }
                                        sumVoc.get(i).put("status", "0");
                                    }
                                }
                            }
                            else
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point++;
                                        }
                                        sumVoc.get(i).put("status", "1");
                                        arrayListRightVoc.add(textViewVocab.getText().toString());
                                    }
                                }
                                right++;
                                setWrongCount(0);
                            }
                        }

                        if (getPickE() == 1)
                        {
                            if (!textViewE.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (!sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point--;
                                        }
                                        sumVoc.get(i).put("status", "0");
                                    }
                                }
                            }
                            else
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point++;
                                        }
                                        sumVoc.get(i).put("status", "1");
                                        arrayListRightVoc.add(textViewVocab.getText().toString());
                                    }
                                }
                                right++;
                                setWrongCount(0);
                            }
                        }
                    }
                    setSumVoc(sumVoc);

                    stopService(new Intent(this, GameRankService.class));
                    Intent intentGameRankEndActivity = new Intent(getApplicationContext(), GameRankEndActivity.class);
                    intentGameRankEndActivity.putExtra("sumvoc", getSumVoc());
                    intentGameRankEndActivity.putExtra("point", point);
                    intentGameRankEndActivity.putExtra("counter", counter);
                    intentGameRankEndActivity.putExtra("right", right);
                    startActivity(intentGameRankEndActivity);
                }
            }

            //Picked and time out
            if (getPick() == 1 & millisUntilFinished == 0 & getQuit() != 1)
            {
                if (!arrayListVoc.contains(textViewVocab.getText().toString()))
                {
                    hashMap = new HashMap<>();
                    hashMap.put("voc", textViewVocab.getText().toString());

                    arrayListVoc.add(textViewVocab.getText().toString());

                    RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                    VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);

                    if (getPickA() == 1)
                    {
                        if (textViewA.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            point++;
                            right++;
                            hashMap.put("status", "1");
                            arrayListRightVoc.add(textViewVocab.getText().toString());
                        }
                        else
                        {
                            hashMap.put("status", "0");
                            setWrongCount(getWrongCount()+1);
                        }
                    }

                    if (getPickB() == 1)
                    {
                        if (textViewB.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            point++;
                            right++;
                            hashMap.put("status", "1");
                            arrayListRightVoc.add(textViewVocab.getText().toString());
                        }
                        else
                        {
                            hashMap.put("status", "0");
                            setWrongCount(getWrongCount()+1);
                        }
                    }

                    if (getPickC() == 1)
                    {
                        if (textViewC.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            point++;
                            right++;
                            hashMap.put("status", "1");
                            arrayListRightVoc.add(textViewVocab.getText().toString());
                        }
                        else
                        {
                            hashMap.put("status", "0");
                            setWrongCount(getWrongCount()+1);
                        }
                    }

                    if (getPickD() == 1)
                    {
                        if (textViewD.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            point++;
                            right++;
                            hashMap.put("status", "1");
                            arrayListRightVoc.add(textViewVocab.getText().toString());
                        }
                        else
                        {
                            hashMap.put("status", "0");
                            setWrongCount(getWrongCount()+1);
                        }
                    }

                    if (getPickE() == 1)
                    {
                        if (textViewE.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            point++;
                            right++;
                            hashMap.put("status", "1");
                            arrayListRightVoc.add(textViewVocab.getText().toString());
                        }
                        else
                        {
                            hashMap.put("status", "0");
                            setWrongCount(getWrongCount()+1);
                        }
                    }

                    sumVoc.add(hashMap);
                }
                else
                {
                    RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                    VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);

                    if (getPickA() == 1)
                    {
                        if (!textViewA.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (!sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point--;
                                        setWrongCount(getWrongCount()+1);
                                    }
                                    sumVoc.get(i).put("status", "0");
                                    arrayListRightVoc.remove(textViewVocab.getText().toString());
                                }
                            }
                        }
                        else
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point++;
                                    }
                                    sumVoc.get(i).put("status", "1");
                                    arrayListRightVoc.add(textViewVocab.getText().toString());
                                }
                            }
                            right++;
                            setWrongCount(0);
                        }
                    }

                    if (getPickB() == 1)
                    {
                        if (!textViewB.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (!sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point--;
                                        setWrongCount(getWrongCount()+1);
                                    }
                                    sumVoc.get(i).put("status", "0");
                                    arrayListRightVoc.remove(textViewVocab.getText().toString());
                                }
                            }
                        }
                        else
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point++;
                                    }
                                    sumVoc.get(i).put("status", "1");
                                    arrayListRightVoc.add(textViewVocab.getText().toString());
                                }
                            }
                            right++;
                            setWrongCount(0);
                        }
                    }

                    if (getPickC() == 1)
                    {
                        if (!textViewC.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (!sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point--;
                                        setWrongCount(getWrongCount()+1);
                                    }
                                    sumVoc.get(i).put("status", "0");
                                    arrayListRightVoc.remove(textViewVocab.getText().toString());
                                }
                            }
                        }
                        else
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point++;
                                    }
                                    sumVoc.get(i).put("status", "1");
                                    arrayListRightVoc.add(textViewVocab.getText().toString());
                                }
                            }
                            right++;
                            setWrongCount(0);
                        }
                    }

                    if (getPickD() == 1)
                    {
                        if (!textViewD.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (!sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point--;
                                        setWrongCount(getWrongCount()+1);
                                    }
                                    sumVoc.get(i).put("status", "0");
                                    arrayListRightVoc.remove(textViewVocab.getText().toString());
                                }
                            }
                        }
                        else
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point++;
                                    }
                                    sumVoc.get(i).put("status", "1");
                                    arrayListRightVoc.add(textViewVocab.getText().toString());
                                }
                            }
                            right++;
                            setWrongCount(0);
                        }
                    }

                    if (getPickE() == 1)
                    {
                        if (!textViewE.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (!sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point--;
                                        setWrongCount(getWrongCount()+1);
                                    }
                                    sumVoc.get(i).put("status", "0");
                                    arrayListRightVoc.remove(textViewVocab.getText().toString());
                                }
                            }
                        }
                        else
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point++;
                                    }
                                    sumVoc.get(i).put("status", "1");
                                    arrayListRightVoc.add(textViewVocab.getText().toString());
                                }
                            }
                            right++;
                            setWrongCount(0);
                        }
                    }
                }
                setSumVoc(sumVoc);

                QuestionGenerator();

                mTimerView.stop();
                mTimerView.start(8);
                stopService(new Intent(GameRankActivity.this, GameRankService.class));
                startService(new Intent(GameRankActivity.this, GameRankService.class));
                textViewA.setTextColor(oldColor);
                textViewB.setTextColor(oldColor);
                textViewC.setTextColor(oldColor);
                textViewD.setTextColor(oldColor);
                textViewE.setTextColor(oldColor);
                setPickA(0);
                setPickB(0);
                setPickC(0);
                setPickD(0);
                setPickE(0);
                setPick(0);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        registerReceiver(broadcastReceiver, new IntentFilter(GameRankService.COUNTDOWN_BR));
        Log.d("servicethis", "Registered broacast receiver");
    }

    @Override
    protected void onPause() {
        super.onPause();

        mTimerView.stop();

        unregisterReceiver(broadcastReceiver);
        Log.d("servicethis", "Unregistered broacast receiver");
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(broadcastReceiver);
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

        setQuit(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:

                if (getPickA() == 0 & getPickB() == 0 & getPickC() == 0 & getPickD() == 0 & getPickE() == 0)
                {
                    if (!arrayListVoc.contains(textViewVocab.getText().toString()))
                    {
                        hashMap = new HashMap<>();
                        hashMap.put("voc", textViewVocab.getText().toString());

                        arrayListVoc.add(textViewVocab.getText().toString());

                        hashMap.put("status", "0");
                        sumVoc.add(hashMap);
                    }
                    else
                    {
                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);
                        if (!textViewA.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                        {
                            for (int i = 0; i < sumVoc.size(); i++)
                            {
                                if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                {
                                    if (!sumVoc.get(i).get("status").equals("0"))
                                    {
                                        point--;
                                    }
                                    sumVoc.get(i).put("status", "0");
                                }
                            }
                        }
                    }
                }
                else
                {
                    if (!arrayListVoc.contains(textViewVocab.getText().toString()))
                    {
                        hashMap = new HashMap<>();
                        hashMap.put("voc", textViewVocab.getText().toString());

                        arrayListVoc.add(textViewVocab.getText().toString());

                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);

                        if (getPickA() == 1)
                        {
                            if (textViewA.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                point++;
                                right++;
                                hashMap.put("status", "1");
                            }
                            else
                            {
                                hashMap.put("status", "0");
                            }
                        }

                        if (getPickB() == 1)
                        {
                            if (textViewB.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                point++;
                                right++;
                                hashMap.put("status", "1");
                            }
                            else
                            {
                                hashMap.put("status", "0");
                            }
                        }

                        if (getPickC() == 1)
                        {
                            if (textViewC.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                point++;
                                right++;
                                hashMap.put("status", "1");
                            }
                            else
                            {
                                hashMap.put("status", "0");
                            }
                        }

                        if (getPickD() == 1)
                        {
                            if (textViewD.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                point++;
                                right++;
                                hashMap.put("status", "1");
                            }
                            else
                            {
                                hashMap.put("status", "0");
                            }
                        }

                        if (getPickE() == 1)
                        {
                            if (textViewE.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                point++;
                                right++;
                                hashMap.put("status", "1");
                            }
                            else
                            {
                                hashMap.put("status", "0");
                            }
                        }

                        sumVoc.add(hashMap);
                    }
                    else
                    {
                        RealmResults<VocabDictionary> realmResultsQA = realm.where(VocabDictionary.class).equalTo("vocab", textViewVocab.getText().toString()).findAll();
                        VocabDictionary vocabDictionaryQA = realmResultsQA.get(0);

                        if (getPickA() == 1)
                        {
                            if (!textViewA.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (!sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point--;
                                        }
                                        sumVoc.get(i).put("status", "0");
                                    }
                                }
                            }
                            else
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point++;
                                        }
                                        sumVoc.get(i).put("status", "1");
                                        arrayListRightVoc.add(textViewVocab.getText().toString());
                                    }
                                }
                                right++;
                                setWrongCount(0);
                            }
                        }

                        if (getPickB() == 1)
                        {
                            if (!textViewB.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (!sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point--;
                                        }
                                        sumVoc.get(i).put("status", "0");
                                    }
                                }
                            }
                            else
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point++;
                                        }
                                        sumVoc.get(i).put("status", "1");
                                        arrayListRightVoc.add(textViewVocab.getText().toString());
                                    }
                                }
                                right++;
                                setWrongCount(0);
                            }
                        }

                        if (getPickC() == 1)
                        {
                            if (!textViewC.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (!sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point--;
                                        }
                                        sumVoc.get(i).put("status", "0");
                                    }
                                }
                            }
                            else
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point++;
                                        }
                                        sumVoc.get(i).put("status", "1");
                                        arrayListRightVoc.add(textViewVocab.getText().toString());
                                    }
                                }
                                right++;
                                setWrongCount(0);
                            }
                        }

                        if (getPickD() == 1)
                        {
                            if (!textViewD.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (!sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point--;
                                        }
                                        sumVoc.get(i).put("status", "0");
                                    }
                                }
                            }
                            else
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point++;
                                        }
                                        sumVoc.get(i).put("status", "1");
                                        arrayListRightVoc.add(textViewVocab.getText().toString());
                                    }
                                }
                                right++;
                                setWrongCount(0);
                            }
                        }

                        if (getPickE() == 1)
                        {
                            if (!textViewE.getText().toString().equals(vocabDictionaryQA.getVocabChi()))
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (!sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point--;
                                        }
                                        sumVoc.get(i).put("status", "0");
                                    }
                                }
                            }
                            else
                            {
                                for (int i = 0; i < sumVoc.size(); i++)
                                {
                                    if (sumVoc.get(i).get("voc").equals(textViewVocab.getText().toString()))
                                    {
                                        if (sumVoc.get(i).get("status").equals("0"))
                                        {
                                            point++;
                                        }
                                        sumVoc.get(i).put("status", "1");
                                        arrayListRightVoc.add(textViewVocab.getText().toString());
                                    }
                                }
                                right++;
                                setWrongCount(0);
                            }
                        }
                    }
                }
                setSumVoc(sumVoc);

                stopService(new Intent(this, GameRankService.class));
                Intent intentGameRankEndActivity = new Intent(getApplicationContext(), GameRankEndActivity.class);
                intentGameRankEndActivity.putExtra("sumvoc", getSumVoc());
                intentGameRankEndActivity.putExtra("point", point);
                intentGameRankEndActivity.putExtra("counter", counter);
                intentGameRankEndActivity.putExtra("right", right);
                startActivity(intentGameRankEndActivity);
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
        counter++;

        choicesArray.clear();
        choicesChiArray.clear();
        choicesChiIntArray.clear();
        questionChoiceArray.clear();

        //Start spawning current level correctly answered questions after strike three until the user answers right
        RealmResults<VocabDictionary> realmResultsQuestion;
        if (getWrongCount() < 3)
        {
            //Level up when all the vocabs in the current tier are answered correctly
            int pastRightVoc = 0;
            for (int i = 1; i <= getLevel()-1; i++)
            {
                pastRightVoc += arrayListLevelSize.get(i-1);
            }
            if (arrayListRightVoc.size() == pastRightVoc+arrayListLevelSize.get(getLevel()-1))
            {
                setLevel(getLevel()+1);
            }
            realmResultsQuestion = realm.where(VocabDictionary.class).equalTo("vocabLv", getLevel()).findAll();

            Random random = new Random(System.nanoTime());
            int question = random.nextInt(realmResultsQuestion.size());
            VocabDictionary vocabDictionaryQuestion = realmResultsQuestion.get(question);

            //The vocab(s) that answered right won't appear again
            while (questionChoiceArray.size() < 1)
            {
                if (sumVoc.isEmpty())
                {
                    questionChoiceArray.add(question);
                }
                else
                {
                    for (int i = 0; i < sumVoc.size(); i++)
                    {
                        //如果sumVoc的單字=選取單字時
                        if (sumVoc.get(i).get("voc").equals(vocabDictionaryQuestion.getVocab()))
                        {
                            //選取的單字是對的時 換單字
                            if (sumVoc.get(i).get("status").equals("1"))
                            {
                                random = new Random(System.nanoTime());
                                question = random.nextInt(realmResultsQuestion.size());
                                vocabDictionaryQuestion = realmResultsQuestion.get(question);
                            }
                            //選取的單字是錯的時 跳出迴圈
                            else if (sumVoc.get(i).get("status").equals("0"))
                            {
                                questionChoiceArray.add(question);
                            }
                        }
                        //如果sumVoc(arrayListVoc)不存在選取單字時 跳出迴圈
                        else if (!arrayListVoc.contains(vocabDictionaryQuestion.getVocab()))
                        {
                            questionChoiceArray.add(question);
                        }
                    }
                }
            }
            vocabDictionaryQuestion = realmResultsQuestion.get(questionChoiceArray.get(0));

            //Multiple choice algorithm
            textViewVocab.setText(vocabDictionaryQuestion.getVocab());
            textViewPoS.setText(vocabDictionaryQuestion.getVocabPoS());

            RealmResults<VocabDictionary> realmResultsChoice = realm.where(VocabDictionary.class).equalTo("vocabPoS", textViewPoS.getText().toString()).findAll();
            //choices
            while (choicesArray.size() < 4)
            {
                int choice = random.nextInt(realmResultsChoice.size());
                if (!choicesArray.contains(choice))
                {
                    VocabDictionary vocabDictionaryChoice = realmResultsChoice.get(choice);
                    if (!vocabDictionaryQuestion.getVocabChi().equals(vocabDictionaryChoice.getVocabChi()))
                    {
                        choicesArray.add(choice);
                    }
                }
            }
            VocabDictionary vocabDictionaryChoice1 = realmResultsChoice.get(choicesArray.get(0));
            VocabDictionary vocabDictionaryChoice2 = realmResultsChoice.get(choicesArray.get(1));
            VocabDictionary vocabDictionaryChoice3 = realmResultsChoice.get(choicesArray.get(2));
            VocabDictionary vocabDictionaryChoice4 = realmResultsChoice.get(choicesArray.get(3));

            //choicesChi
            choicesChiArray.add(vocabDictionaryQuestion.getVocabChi());
            choicesChiArray.add(vocabDictionaryChoice1.getVocabChi());
            choicesChiArray.add(vocabDictionaryChoice2.getVocabChi());
            choicesChiArray.add(vocabDictionaryChoice3.getVocabChi());
            choicesChiArray.add(vocabDictionaryChoice4.getVocabChi());

            //choicesChiInt
            while (choicesChiIntArray.size() < 5)
            {
                int choice = random.nextInt(5);
                if (!choicesChiIntArray.contains(choice))
                {
                    choicesChiIntArray.add(choice);
                }
            }

            textViewA.setText(choicesChiArray.get(choicesChiIntArray.get(0)));
            textViewB.setText(choicesChiArray.get(choicesChiIntArray.get(1)));
            textViewC.setText(choicesChiArray.get(choicesChiIntArray.get(2)));
            textViewD.setText(choicesChiArray.get(choicesChiIntArray.get(3)));
            textViewE.setText(choicesChiArray.get(choicesChiIntArray.get(4)));
        }
        else
        {
            if (arrayListRightVoc.isEmpty())
            {
                int pastRightVoc = 0;
                for (int i = 1; i <= getLevel()-1; i++)
                {
                    pastRightVoc += arrayListLevelSize.get(i-1);
                }
                if (arrayListRightVoc.size() == pastRightVoc+arrayListLevelSize.get(getLevel()-1))
                {
                    setLevel(getLevel()+1);
                }
                realmResultsQuestion = realm.where(VocabDictionary.class).equalTo("vocabLv", getLevel()).findAll();

                Random random = new Random(System.nanoTime());
                int question = random.nextInt(realmResultsQuestion.size());
                VocabDictionary vocabDictionaryQuestion = realmResultsQuestion.get(question);

                //The vocab(s) that answered right won't appear again
                while (questionChoiceArray.size() < 1)
                {
                    if (sumVoc.isEmpty())
                    {
                        questionChoiceArray.add(question);
                    }
                    else
                    {
                        for (int i = 0; i < sumVoc.size(); i++)
                        {
                            //如果sumVoc的單字=選取單字時
                            if (sumVoc.get(i).get("voc").equals(vocabDictionaryQuestion.getVocab()))
                            {
                                //選取的單字是對的時 換單字
                                if (sumVoc.get(i).get("status").equals("1"))
                                {
                                    random = new Random(System.nanoTime());
                                    question = random.nextInt(realmResultsQuestion.size());
                                    vocabDictionaryQuestion = realmResultsQuestion.get(question);
                                }
                                //選取的單字是錯的時 跳出迴圈
                                else if (sumVoc.get(i).get("status").equals("0"))
                                {
                                    questionChoiceArray.add(question);
                                }
                            }
                            //如果sumVoc(arrayListVoc)不存在選取單字時 跳出迴圈
                            else if (!arrayListVoc.contains(vocabDictionaryQuestion.getVocab()))
                            {
                                questionChoiceArray.add(question);
                            }
                        }
                    }
                }
                vocabDictionaryQuestion = realmResultsQuestion.get(questionChoiceArray.get(0));

                //Multiple choice algorithm
                textViewVocab.setText(vocabDictionaryQuestion.getVocab());
                textViewPoS.setText(vocabDictionaryQuestion.getVocabPoS());

                RealmResults<VocabDictionary> realmResultsChoice = realm.where(VocabDictionary.class).equalTo("vocabPoS", textViewPoS.getText().toString()).findAll();
                //choices
                while (choicesArray.size() < 4)
                {
                    int choice = random.nextInt(realmResultsChoice.size());
                    if (!choicesArray.contains(choice))
                    {
                        VocabDictionary vocabDictionaryChoice = realmResultsChoice.get(choice);
                        if (!vocabDictionaryQuestion.getVocabChi().equals(vocabDictionaryChoice.getVocabChi()))
                        {
                            choicesArray.add(choice);
                        }
                    }
                }
                VocabDictionary vocabDictionaryChoice1 = realmResultsChoice.get(choicesArray.get(0));
                VocabDictionary vocabDictionaryChoice2 = realmResultsChoice.get(choicesArray.get(1));
                VocabDictionary vocabDictionaryChoice3 = realmResultsChoice.get(choicesArray.get(2));
                VocabDictionary vocabDictionaryChoice4 = realmResultsChoice.get(choicesArray.get(3));

                //choicesChi
                choicesChiArray.add(vocabDictionaryQuestion.getVocabChi());
                choicesChiArray.add(vocabDictionaryChoice1.getVocabChi());
                choicesChiArray.add(vocabDictionaryChoice2.getVocabChi());
                choicesChiArray.add(vocabDictionaryChoice3.getVocabChi());
                choicesChiArray.add(vocabDictionaryChoice4.getVocabChi());

                //choicesChiInt
                while (choicesChiIntArray.size() < 5)
                {
                    int choice = random.nextInt(5);
                    if (!choicesChiIntArray.contains(choice))
                    {
                        choicesChiIntArray.add(choice);
                    }
                }

                textViewA.setText(choicesChiArray.get(choicesChiIntArray.get(0)));
                textViewB.setText(choicesChiArray.get(choicesChiIntArray.get(1)));
                textViewC.setText(choicesChiArray.get(choicesChiIntArray.get(2)));
                textViewD.setText(choicesChiArray.get(choicesChiIntArray.get(3)));
                textViewE.setText(choicesChiArray.get(choicesChiIntArray.get(4)));
            }
            else
            {
                int pastRightVoc = 0;
                for (int i = 1; i <= getLevel()-1; i++)
                {
                    pastRightVoc += arrayListLevelSize.get(i-1);
                }
                setQuestionCurrentRight(arrayListRightVoc.size()-pastRightVoc-1);

                //Random nextInt() function's parameter can't be <= 0
                if (getQuestionCurrentRight() == 0)
                {
                    Random randomRight = new Random(System.nanoTime());
                    realmResultsQuestion = realm.where(VocabDictionary.class).equalTo("vocab", arrayListRightVoc.get(0)).findAll();
                    VocabDictionary vocabDictionaryQuestion = realmResultsQuestion.get(0);

                    //Multiple choice algorithm
                    textViewVocab.setText(vocabDictionaryQuestion.getVocab());
                    textViewPoS.setText(vocabDictionaryQuestion.getVocabPoS());

                    RealmResults<VocabDictionary> realmResultsChoice = realm.where(VocabDictionary.class).equalTo("vocabPoS", textViewPoS.getText().toString()).findAll();
                    //choices
                    while (choicesArray.size() < 4)
                    {
                        int choice = randomRight.nextInt(realmResultsChoice.size());
                        if (!choicesArray.contains(choice))
                        {
                            VocabDictionary vocabDictionaryChoice = realmResultsChoice.get(choice);
                            if (!vocabDictionaryQuestion.getVocabChi().equals(vocabDictionaryChoice.getVocabChi()))
                            {
                                choicesArray.add(choice);
                            }
                        }
                    }
                    VocabDictionary vocabDictionaryChoice1 = realmResultsChoice.get(choicesArray.get(0));
                    VocabDictionary vocabDictionaryChoice2 = realmResultsChoice.get(choicesArray.get(1));
                    VocabDictionary vocabDictionaryChoice3 = realmResultsChoice.get(choicesArray.get(2));
                    VocabDictionary vocabDictionaryChoice4 = realmResultsChoice.get(choicesArray.get(3));

                    //choicesChi
                    choicesChiArray.add(vocabDictionaryQuestion.getVocabChi());
                    choicesChiArray.add(vocabDictionaryChoice1.getVocabChi());
                    choicesChiArray.add(vocabDictionaryChoice2.getVocabChi());
                    choicesChiArray.add(vocabDictionaryChoice3.getVocabChi());
                    choicesChiArray.add(vocabDictionaryChoice4.getVocabChi());

                    //choicesChiInt
                    while (choicesChiIntArray.size() < 5)
                    {
                        int choice = randomRight.nextInt(5);
                        if (!choicesChiIntArray.contains(choice))
                        {
                            choicesChiIntArray.add(choice);
                        }
                    }

                    textViewA.setText(choicesChiArray.get(choicesChiIntArray.get(0)));
                    textViewB.setText(choicesChiArray.get(choicesChiIntArray.get(1)));
                    textViewC.setText(choicesChiArray.get(choicesChiIntArray.get(2)));
                    textViewD.setText(choicesChiArray.get(choicesChiIntArray.get(3)));
                    textViewE.setText(choicesChiArray.get(choicesChiIntArray.get(4)));
                }
                else
                {
                    //Demoted when all the current tier's vocabs are answered wrongly
                    if (arrayListRightVoc.isEmpty())
                    {
                        Random randomRight = new Random(System.nanoTime());
                        int questionCurrentRight = randomRight.nextInt((arrayListRightVoc.size()-pastRightVoc)-1)+pastRightVoc;
                        realmResultsQuestion = realm.where(VocabDictionary.class).equalTo("vocab", arrayListRightVoc.get(questionCurrentRight)).findAll();
                        VocabDictionary vocabDictionaryQuestion = realmResultsQuestion.get(questionCurrentRight);

                        //Multiple choice algorithm
                        textViewVocab.setText(vocabDictionaryQuestion.getVocab());
                        textViewPoS.setText(vocabDictionaryQuestion.getVocabPoS());

                        RealmResults<VocabDictionary> realmResultsChoice = realm.where(VocabDictionary.class).equalTo("vocabPoS", textViewPoS.getText().toString()).findAll();
                        //choices
                        while (choicesArray.size() < 4)
                        {
                            int choice = randomRight.nextInt(realmResultsChoice.size());
                            if (!choicesArray.contains(choice))
                            {
                                VocabDictionary vocabDictionaryChoice = realmResultsChoice.get(choice);
                                if (!vocabDictionaryQuestion.getVocabChi().equals(vocabDictionaryChoice.getVocabChi()))
                                {
                                    choicesArray.add(choice);
                                }
                            }
                        }
                        VocabDictionary vocabDictionaryChoice1 = realmResultsChoice.get(choicesArray.get(0));
                        VocabDictionary vocabDictionaryChoice2 = realmResultsChoice.get(choicesArray.get(1));
                        VocabDictionary vocabDictionaryChoice3 = realmResultsChoice.get(choicesArray.get(2));
                        VocabDictionary vocabDictionaryChoice4 = realmResultsChoice.get(choicesArray.get(3));

                        //choicesChi
                        choicesChiArray.add(vocabDictionaryQuestion.getVocabChi());
                        choicesChiArray.add(vocabDictionaryChoice1.getVocabChi());
                        choicesChiArray.add(vocabDictionaryChoice2.getVocabChi());
                        choicesChiArray.add(vocabDictionaryChoice3.getVocabChi());
                        choicesChiArray.add(vocabDictionaryChoice4.getVocabChi());

                        //choicesChiInt
                        while (choicesChiIntArray.size() < 5)
                        {
                            int choice = randomRight.nextInt(5);
                            if (!choicesChiIntArray.contains(choice))
                            {
                                choicesChiIntArray.add(choice);
                            }
                        }

                        textViewA.setText(choicesChiArray.get(choicesChiIntArray.get(0)));
                        textViewB.setText(choicesChiArray.get(choicesChiIntArray.get(1)));
                        textViewC.setText(choicesChiArray.get(choicesChiIntArray.get(2)));
                        textViewD.setText(choicesChiArray.get(choicesChiIntArray.get(3)));
                        textViewE.setText(choicesChiArray.get(choicesChiIntArray.get(4)));
                    }
                    else
                    {
                        if (arrayListRightVoc.size() == pastRightVoc)
                        {
                            setLevel(getLevel()-1);

                            Random randomRight = new Random(System.nanoTime());
                            int questionCurrentRight = randomRight.nextInt((arrayListRightVoc.size()-pastRightVoc)-1)+pastRightVoc;
                            realmResultsQuestion = realm.where(VocabDictionary.class).equalTo("vocab", arrayListRightVoc.get(questionCurrentRight)).findAll();
                            VocabDictionary vocabDictionaryQuestion = realmResultsQuestion.get(questionCurrentRight);

                            //Multiple choice algorithm
                            textViewVocab.setText(vocabDictionaryQuestion.getVocab());
                            textViewPoS.setText(vocabDictionaryQuestion.getVocabPoS());

                            RealmResults<VocabDictionary> realmResultsChoice = realm.where(VocabDictionary.class).equalTo("vocabPoS", textViewPoS.getText().toString()).findAll();
                            //choices
                            while (choicesArray.size() < 4)
                            {
                                int choice = randomRight.nextInt(realmResultsChoice.size());
                                if (!choicesArray.contains(choice))
                                {
                                    VocabDictionary vocabDictionaryChoice = realmResultsChoice.get(choice);
                                    if (!vocabDictionaryQuestion.getVocabChi().equals(vocabDictionaryChoice.getVocabChi()))
                                    {
                                        choicesArray.add(choice);
                                    }
                                }
                            }
                            VocabDictionary vocabDictionaryChoice1 = realmResultsChoice.get(choicesArray.get(0));
                            VocabDictionary vocabDictionaryChoice2 = realmResultsChoice.get(choicesArray.get(1));
                            VocabDictionary vocabDictionaryChoice3 = realmResultsChoice.get(choicesArray.get(2));
                            VocabDictionary vocabDictionaryChoice4 = realmResultsChoice.get(choicesArray.get(3));

                            //choicesChi
                            choicesChiArray.add(vocabDictionaryQuestion.getVocabChi());
                            choicesChiArray.add(vocabDictionaryChoice1.getVocabChi());
                            choicesChiArray.add(vocabDictionaryChoice2.getVocabChi());
                            choicesChiArray.add(vocabDictionaryChoice3.getVocabChi());
                            choicesChiArray.add(vocabDictionaryChoice4.getVocabChi());

                            //choicesChiInt
                            while (choicesChiIntArray.size() < 5)
                            {
                                int choice = randomRight.nextInt(5);
                                if (!choicesChiIntArray.contains(choice))
                                {
                                    choicesChiIntArray.add(choice);
                                }
                            }

                            textViewA.setText(choicesChiArray.get(choicesChiIntArray.get(0)));
                            textViewB.setText(choicesChiArray.get(choicesChiIntArray.get(1)));
                            textViewC.setText(choicesChiArray.get(choicesChiIntArray.get(2)));
                            textViewD.setText(choicesChiArray.get(choicesChiIntArray.get(3)));
                            textViewE.setText(choicesChiArray.get(choicesChiIntArray.get(4)));
                        }
                    }
                }
            }
        }
    }
}