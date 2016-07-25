package com.example.pete.amc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String initialization = sharedPreferences.getString("firsttimeuse", "default");
        if(initialization.equals("default"))
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("firsttimeuse", "0");
            editor.commit();

            Intent intentPopUp = new Intent(this, PopUpActivity.class);
            startActivity(intentPopUp);
        }


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.content_id,new TabFragment()).commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Subscription", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        setA(0);
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView imageViewHeader = (ImageView)view.findViewById(R.id.imageViewHeader);
        ImageView imageViewCheck = (ImageView)view.findViewById(R.id.imageViewHeaderCheck);
        final ImageView imageViewDown = (ImageView)view.findViewById(R.id.imageViewHeaderDown);
        TextView textViewUser = (TextView)view.findViewById(R.id.textViewHeaderUser);
        TextView textViewEmail = (TextView)view.findViewById(R.id.textViewHeaderEmail);

        final String identification = sharedPreferences.getString("identification", "default");

        imageViewDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getA() == 0)
                {
                    if(identification.equals("1"))
                    {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.activity_main_drawer_user);

                        imageViewDown.setImageResource(R.mipmap.ic_arrow_drop_up_white_24dp);
                        setA(1);
                    }
                    else
                    {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.activity_main_drawer_guest);

                        imageViewDown.setImageResource(R.mipmap.ic_arrow_drop_up_white_24dp);
                        setA(1);
                    }
                }
                else
                {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_main_drawer);

                    imageViewDown.setImageResource(R.mipmap.ic_arrow_drop_down_white_24dp);
                    setA(0);
                }
            }
        });

        if(identification.equals("1"))
        {
            String email = sharedPreferences.getString("email", "default");
            String user = sharedPreferences.getString("user", "default");
            textViewUser.setText(user);
            textViewEmail.setText(email);

            imageViewCheck.setVisibility(View.VISIBLE);
        }
        else
        {
            textViewEmail.setText("Log in or sign up");

            imageViewCheck.setVisibility(View.GONE);
        }


        getSupportActionBar().setTitle("AMC");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
//        else
//        {
//            super.onBackPressed();
//        }
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
        if (id == R.id.action_settings) {
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

            getSupportActionBar().setTitle("AMC");

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Subscription", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }
            });

            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.content_id, new TabFragment()).commit();
        }
        else if (id == R.id.nav_money)
        {
            getSupportActionBar().setTitle("付費試題");

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Purchase", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }
            });

            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.content_id, new MoneyTabFragment()).commit();
        }
        else if (id == R.id.nav_game)
        {
            getSupportActionBar().setTitle("字彙聯盟");

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();

            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.content_id, new GameTabFragment()).commit();
        }
        else if (id == R.id.nav_favorite)
        {
            getSupportActionBar().setTitle("Support");

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();

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

            editor.commit();

            Toast.makeText(getApplicationContext(), "Hope to see you again!", Toast.LENGTH_LONG).show();
            Intent intentLogIn = new Intent(getApplicationContext(), LogInActivity.class);
            startActivity(intentLogIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
