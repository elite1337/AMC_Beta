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

    ArrayList<Integer> choices = new ArrayList<>();
    ArrayList<String> choicesChi = new ArrayList<>();
    ArrayList<Integer> choicesChiInt = new ArrayList<>();

    ArrayList<HashMap<String, String>> sumVoc = new ArrayList<>();
    HashMap<String, String> hashMap = new HashMap<>();
    ArrayList<String> arrayListVoc = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getSumVoc() {
        return sumVoc;
    }

    public void setSumVoc(ArrayList<HashMap<String, String>> sumVoc) {
        this.sumVoc = sumVoc;
    }

    double point = 0;
    double counter = 0;
    double right = 0;

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
        QuestionGenerator();

        setPickA(0);
        setPickB(0);
        setPickC(0);
        setPickD(0);
        setPickE(0);
        setPick(0);

        oldColor = textViewA.getTextColors();

        Intent intent = new Intent(this, GameRankService.class);
        intent.putExtra("pick", getPick());
        startService(intent);
        Log.i("servicethis", "Started service");

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
                        }
                        else
                        {
                            hashMap.put("status", "0");
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
                                    }
                                    sumVoc.get(i).put("status", "0");
                                }
                            }
                        }
                        else
                        {
                            right++;
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
                        }
                        else
                        {
                            hashMap.put("status", "0");
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
                                    }
                                    sumVoc.get(i).put("status", "0");
                                }
                            }
                        }
                        else
                        {
                            right++;
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
                        }
                        else
                        {
                            hashMap.put("status", "0");
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
                                    }
                                    sumVoc.get(i).put("status", "0");
                                }
                            }
                        }
                        else
                        {
                            right++;
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
                        }
                        else
                        {
                            hashMap.put("status", "0");
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
                                    }
                                    sumVoc.get(i).put("status", "0");
                                }
                            }
                        }
                        else
                        {
                            right++;
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
                        }
                        else
                        {
                            hashMap.put("status", "0");
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
                                    }
                                    sumVoc.get(i).put("status", "0");
                                }
                            }
                        }
                        else
                        {
                            right++;
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
                //Quit when time out
                if (millisUntilFinished == 0)
                {
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