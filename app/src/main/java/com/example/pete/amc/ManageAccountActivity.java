package com.example.pete.amc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ManageAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        getSupportActionBar().setTitle("Profile");

        ImageView imageView = (ImageView)findViewById(R.id.imageViewProfile);
        TextView textViewUser = (TextView)findViewById(R.id.textViewProfileUser);
        TextView textViewEmail = (TextView)findViewById(R.id.textViewProfileEmail);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("user", "default");
        String email = sharedPreferences.getString("email", "default");

        textViewUser.setText(user);
        textViewEmail.setText(email);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_profile_action, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.editProfile:
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
