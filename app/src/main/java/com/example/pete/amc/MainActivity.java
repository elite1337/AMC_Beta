package com.example.pete.amc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NavHeaderDialogFragment.OnCompleteListener {

    FragmentManager mFragmentManager;

    ImageView imageViewHeader;
    int[] images = {R.mipmap.ic_account_circle_black_24dp, R.mipmap.ic_account_circle_red_24dp, R.mipmap.ic_account_circle_orange_24dp, R.mipmap.ic_account_circle_yellow_24dp, R.mipmap.ic_account_circle_green_24dp, R.mipmap.ic_account_circle_blue_24dp, R.mipmap.ic_account_circle_violet_24dp};


    Realm realm;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/vocabDictionary";
    String valueVocab;
    String valueVocabPoS;
    String valueVocabChi;
    String valueVocabLv;
    String valueVocabPt;

    public String getValueVocab() {
        return valueVocab;
    }

    public void setValueVocab(String valueVocab) {
        this.valueVocab = valueVocab;
    }

    public String getValueVocabPoS() {
        return valueVocabPoS;
    }

    public void setValueVocabPoS(String valueVocabPoS) {
        this.valueVocabPoS = valueVocabPoS;
    }

    public String getValueVocabLv() {
        return valueVocabLv;
    }

    public void setValueVocabLv(String valueVocabLv) {
        this.valueVocabLv = valueVocabLv;
    }

    public String getValueVocabChi() {
        return valueVocabChi;
    }

    public void setValueVocabChi(String valueVocabChi) {
        this.valueVocabChi = valueVocabChi;
    }

    public String getValueVocabPt() {
        return valueVocabPt;
    }

    public void setValueVocabPt(String valueVocabPt) {
        this.valueVocabPt = valueVocabPt;
    }


    int a;
    int b;
    String c;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if first time use, if so go to IntroActivity
        final SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String initialization = sharedPreferences.getString("firsttimeuse", "default");
        if(initialization.equals("default"))
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("firsttimeuse", "0");
            editor.commit();

            Intent intentIntro = new Intent(this, IntroActivity.class);
            startActivity(intentIntro);
        }



        //Floating Action Button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Subscription", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        //Navigation Drawer Tab
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //Saving user experience on orientation change and after GameRankEndActivity
        if (savedInstanceState == null) {
            getSupportActionBar().setTitle("AMC");
            navigationView.getMenu().getItem(0).setChecked(true);
            navigationView.getMenu().performIdentifierAction(R.id.nav_book, 0);
        }
        else if (savedInstanceState.getInt("b") == 0)
        {
            navigationView.getMenu().performIdentifierAction(R.id.nav_book, 0);
        }
        else if (savedInstanceState.getInt("b") == 1)
        {
            getSupportActionBar().setTitle(getC());
            navigationView.getMenu().performIdentifierAction(R.id.nav_money, 0);
        }
        else if (savedInstanceState.getInt("b") == 2)
        {
            getSupportActionBar().setTitle(getC());
            navigationView.getMenu().performIdentifierAction(R.id.nav_game, 0);
        }
        else if (savedInstanceState.getInt("b") == 3)
        {
            getSupportActionBar().setTitle(getC());
            navigationView.getMenu().performIdentifierAction(R.id.nav_favorite, 0);
        }

        Intent intent = getIntent();
        String gamerankend = intent.getStringExtra("gamerankend");
        if (gamerankend == null)
        {
            intent.putExtra("gamerankend", "0");
        }
        else if (intent.getStringExtra("gamerankend").equals("1"))
        {
            navigationView.getMenu().performIdentifierAction(R.id.nav_game, 0);
            Menu menu = navigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.nav_game);
            menuItem.setChecked(true);
            intent.putExtra("gamerankend", "0");
        }


        //Navigation Drawer Header portrait
        View viewHeader = navigationView.inflateHeaderView(R.layout.nav_header_main);
//        View viewFooter = navigationView.inflateHeaderView(R.layout.nav_footer_main);

        imageViewHeader = (ImageView)viewHeader.findViewById(R.id.imageViewHeader);
        ImageView imageViewCheck = (ImageView)viewHeader.findViewById(R.id.imageViewHeaderCheck);
        final ImageView imageViewDown = (ImageView)viewHeader.findViewById(R.id.imageViewHeaderDown);
        TextView textViewUser = (TextView)viewHeader.findViewById(R.id.textViewHeaderUser);
        TextView textViewEmail = (TextView)viewHeader.findViewById(R.id.textViewHeaderEmail);

        final String identification = sharedPreferences.getString("identification", "default");
        final String emailverification = sharedPreferences.getString("emailverification", "default");

        imageViewHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(identification.equals("1"))
                {
                    NavHeaderDialogFragment navHeaderDialogFragment = new NavHeaderDialogFragment();
                    navHeaderDialogFragment.show(getFragmentManager(), "DialogFragmentShit");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please sign up!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Navigation Drawer Body
        setA(0);
        imageViewDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Menu menu = navigationView.getMenu();

                if(getA() == 0)
                {
                    if(identification.equals("1"))
                    {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.activity_main_drawer_user);

                        MenuItem menuItem = menu.findItem(R.id.nav_v);
                        menuItem.setEnabled(false);

                        imageViewDown.setImageResource(R.mipmap.ic_arrow_drop_up_white_24dp);
                        setA(1);
                    }
                    else
                    {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.activity_main_drawer_guest);

                        MenuItem menuItem = menu.findItem(R.id.nav_v);
                        menuItem.setEnabled(false);

                        imageViewDown.setImageResource(R.mipmap.ic_arrow_drop_up_white_24dp);
                        setA(1);
                    }
                }
                else
                {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_main_drawer);
                    navigationView.getMenu().getItem(getB()).setChecked(true);
                    if (getB() == 3)
                    {
                        MenuItem menuItem = menu.findItem(R.id.nav_favorite);
                        menuItem.setChecked(true);
                    }

                    imageViewDown.setImageResource(R.mipmap.ic_arrow_drop_down_white_24dp);
                    setA(0);
                }
            }
        });

        //Navigation Drawer Header info
        if(identification.equals("1"))
        {
            String portrait = sharedPreferences.getString("portrait", "0");
            int timeToInt = Integer.parseInt(portrait);
            String user = sharedPreferences.getString("user", "default");
            String email = sharedPreferences.getString("email", "default");
            imageViewHeader.setImageResource(images[timeToInt]);
            textViewUser.setText(user);
            textViewEmail.setText(email);

            if(emailverification.equals("1"))
            {
                imageViewCheck.setVisibility(View.VISIBLE);
            }
            else
            {
                imageViewCheck.setVisibility(View.GONE);
            }
        }
        else
        {
            imageViewHeader.setImageResource(images[0]);
            textViewEmail.setText("Log in or sign up");

            imageViewCheck.setVisibility(View.GONE);
        }



        realm = Realm.getDefaultInstance();

//        //GameRank DB
//        File dir = new File(path);
//        dir.mkdirs();
//
//        File file = new File (path + "/vocabDictionary.txt");
//        String [] loadText = Load(file);
//
//        String jsonArrayString = "";
//
//        for (int i = 0; i < loadText.length; i++)
//        {
//            jsonArrayString += loadText[i] + System.getProperty("line.separator");
//        }
//
//        try
//        {
//            JSONObject jsonObject = new JSONObject(jsonArrayString);
//
//            JSONArray jsonArray = jsonObject.getJSONArray("vocabDictionary");
//            for (int i = 0; i < jsonArray.length(); i++)
//            {
//                JSONObject jsonObjectInner = jsonArray.getJSONObject(i);
//
//                Iterator<String> iteratorInner = jsonObjectInner.keys();
//                while (iteratorInner.hasNext())
//                {
//                    String keyInner = iteratorInner.next();
//                    if (keyInner.equals("vocab"))
//                    {
//                        String valueVocab = jsonObjectInner.get(keyInner).toString();
//                        setValueVocab(valueVocab);
//                    }
//                    if (keyInner.equals("vocabPoS"))
//                    {
//                        String valueVocabPoS = jsonObjectInner.get(keyInner).toString();
//                        setValueVocabPoS(valueVocabPoS);
//                    }
//                    if (keyInner.equals("vocabChi"))
//                    {
//                        String valueVocabChi = jsonObjectInner.get(keyInner).toString();
//                        setValueVocabChi(valueVocabChi);
//                    }
//                    if (keyInner.equals("vocabLv"))
//                    {
//                        String valueVocabLv = jsonObjectInner.get(keyInner).toString();
//                        setValueVocabLv(valueVocabLv);
//                    }
//                    if (keyInner.equals("vocabPt"))
//                    {
//                        String valueVocabPt = jsonObjectInner.get(keyInner).toString();
//                        setValueVocabPt(valueVocabPt);
//                    }
//                }
//                int valueVocabLv2int = Integer.parseInt(getValueVocabLv());
//                int valueVocabPt2int = Integer.parseInt(getValueVocabPt());
//                save_into_database(getValueVocab(), getValueVocabPoS(), getValueVocabChi(), valueVocabLv2int, valueVocabPt2int);
//            }
//        }
//        catch (Exception e)
//        {
//            Log.d("exception", e+"");
//        }
//        //GameRank DB till here




    }

    //Navigation Drawer Header portrait
    @Override
    public void onComplete(String time) {

        int timeToInt = Integer.parseInt(time);
        imageViewHeader.setImageResource(images[timeToInt]);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            this.finishAffinity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_english)
        {
            return true;
        }
        else if (id == R.id.action_default)
        {
            SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("firsttimeuse", "default");
            editor.putString("emailagain", "0");
            editor.putString("description", "");
            editor.putString("subject", "");
            editor.putString("body", "");
            editor.commit();

            Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentMain);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_book)
        {
            // Handle the camera action

            setB(0);

            getSupportActionBar().setTitle("AMC");

            NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.nav_favorite);
            if (menuItem.isChecked())
            {
                menuItem.setChecked(false);
            }

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Subscription", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }
            });


            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.content_id, new TabFragment()).commit();
        }
        else if (id == R.id.nav_money)
        {
            setB(1);
            setC("付費試題");

            getSupportActionBar().setTitle(getC());

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Purchase", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }
            });

            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.content_id, new MoneyTabFragment()).commit();
        }
        else if (id == R.id.nav_game)
        {
            setB(2);
            setC("字彙聯盟");

            getSupportActionBar().setTitle(getC());

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();

            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.content_id, new GameTabFragment()).commit();
        }
        else if (id == R.id.nav_favorite)
        {
            setB(3);
            setC("Support");

            getSupportActionBar().setTitle(getC());

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();

            mFragmentManager = getSupportFragmentManager();
            FavoriteFragment favoriteFragment = new FavoriteFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, favoriteFragment).commit();
        }
        else if (id == R.id.nav_star)
        {

        }
        else if (id == R.id.nav_power)
        {
            Intent intentLogIn = new Intent(this, LogInActivity.class);
            startActivity(intentLogIn);
        }
        else if (id == R.id.nav_person)
        {
            Intent intentCreateAccount = new Intent(this, CreateAccountActivity.class);
            startActivity(intentCreateAccount);
        }
        else if (id == R.id.nav_manage_account)
        {
            Intent intentManageAccount = new Intent(getApplicationContext(), ManageAccountActivity.class);
            startActivity(intentManageAccount);
        }
        else if (id == R.id.nav_log_out)
        {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("identification", "0");
            editor.putString("emailverification", "0");
            editor.putString("subject", "");
            editor.putString("body", "");
            editor.commit();
            String user = sharedPreferences.getString("user", "default");

            Toast.makeText(getApplicationContext(), "Hope to see you again " + user + "!", Toast.LENGTH_LONG).show();
            Intent intentLogIn = new Intent(getApplicationContext(), LogInActivity.class);
            startActivity(intentLogIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("b", getB());
        outState.putString("c", getC());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }

    //GameRank DB functions
    public static String[] Load(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }

    private void save_into_database(final String vocab,
                                    final String vocabPoS,
                                    final String vocabChi,
                                    final int vocabLv,
                                    final int vocabPt)
    {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                VocabDictionary vocabDictionary = bgRealm.createObject(VocabDictionary.class);
                vocabDictionary.setVocab(vocab);
                vocabDictionary.setVocabPoS(vocabPoS);
                vocabDictionary.setVocabChi(vocabChi);
                vocabDictionary.setVocabLv(vocabLv);
                vocabDictionary.setVocabPt(vocabPt);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Log.d("onsuccessthis", "success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.

                Log.d("onsuccessthis", error.getMessage());
            }
        });
    }
    //GameRank DB functions till here
}