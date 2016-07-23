package com.example.pete.amc;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle("AMC");

        EditText editTextUser = (EditText)findViewById(R.id.editTextEditUser);
        EditText editTextEmail = (EditText)findViewById(R.id.editTextEditEmail);
        EditText editTextDescription = (EditText)findViewById(R.id.editTextEditDescription);
        Button button = (Button)findViewById(R.id.buttonDeleteAcc);

        button.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "default");

        editTextEmail.setText(email);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_edit_profile_action, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.editProfile:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
