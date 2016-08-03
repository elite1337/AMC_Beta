package com.example.pete.amc;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ManageAccountActivity extends AppCompatActivity {

    int[] images = {R.mipmap.ic_account_circle_black_24dp, R.mipmap.ic_account_circle_red_24dp, R.mipmap.ic_account_circle_orange_24dp, R.mipmap.ic_account_circle_yellow_24dp, R.mipmap.ic_account_circle_green_24dp, R.mipmap.ic_account_circle_blue_24dp, R.mipmap.ic_account_circle_violet_24dp};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        getSupportActionBar().setTitle("Profile");

        ImageView imageViewProfile = (ImageView)findViewById(R.id.imageViewProfile);
        ImageView imageViewCheck = (ImageView)findViewById(R.id.imageViewProfileCheck);
        TextView textViewUser = (TextView)findViewById(R.id.textViewProfileUser);
        TextView textViewEmail = (TextView)findViewById(R.id.textViewProfileEmail);
        TextView textViewDescription = (TextView)findViewById(R.id.textViewProfileDescription);
        Button button = (Button)findViewById(R.id.buttonProfile);

        button.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String portrait = sharedPreferences.getString("portrait", "default");
        int portraitToInt = Integer.parseInt(portrait);
        String user = sharedPreferences.getString("user", "default");
        final String email = sharedPreferences.getString("email", "default");
        String description = sharedPreferences.getString("description", "default");
        String emailverification = sharedPreferences.getString("emailverification", "default");
        String emailagain = sharedPreferences.getString("emailagain", "default");

        imageViewProfile.setImageResource(images[portraitToInt]);
        textViewUser.setText(user);
        textViewEmail.setText(email);
        textViewDescription.setText(description);

        if(emailverification.equals("1"))
        {
            imageViewCheck.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        }
        else
        {
            imageViewCheck.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);

            if(emailagain.equals("0"))
            {
                ManageAccountDialogFragment manageAccountDialogFragment = new ManageAccountDialogFragment();
                manageAccountDialogFragment.show(getFragmentManager(), "DialogFragmentShit");

                Bundle bundle = new Bundle();
                bundle.putString("emailquestion", email);
                manageAccountDialogFragment.setArguments(bundle);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAccountEmailDialogFragment createAccountEmailDialogFragment = new CreateAccountEmailDialogFragment();
                createAccountEmailDialogFragment.show(getFragmentManager(), "DialogFragmentShit");

                Bundle bundle = new Bundle();
                bundle.putString("emailquestiontwo", email);
                createAccountEmailDialogFragment.setArguments(bundle);
            }
        });

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