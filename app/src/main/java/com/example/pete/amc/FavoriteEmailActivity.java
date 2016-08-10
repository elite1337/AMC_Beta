package com.example.pete.amc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

public class FavoriteEmailActivity extends AppCompatActivity {

    EditText editTextSubject, editTextBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_email);

        getSupportActionBar().setTitle("Email Support");

        editTextSubject = (EditText)findViewById(R.id.editTextFavoriteSubject);
        editTextBody = (EditText)findViewById(R.id.editTextFavoriteBody);
        Button button = (Button)findViewById(R.id.buttonFavoriteSend);

        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String subject = sharedPreferences.getString("subject", "default");
        String body = sharedPreferences.getString("body", "default");

        editTextSubject.setText(subject);
        editTextBody.setText(body);

        button.getBackground().setColorFilter(0xFF3F51B5, PorterDuff.Mode.MULTIPLY);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextBody.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Body is empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    sendMessage();
                }
            }
        });

        editTextSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("subject", editable.toString());
                editor.commit();
            }
        });
        editTextBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("body", editable.toString());
                editor.commit();
            }
        });
    }

    private void sendMessage() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailUser = sharedPreferences.getString("email", "default");
        String user = sharedPreferences.getString("user", "default");

        String[] recipients = {"amcemailsup@gmail.com"};
        SendEmailAsyncTaskFavorite email = new SendEmailAsyncTaskFavorite();
        email.activity = this;
        email.m = new Mail("amcemailsup@gmail.com", "amcemailsup7777777");
        email.m.set_from("amcemailsup@gmail.com");
        email.m.setBody(editTextBody.getText().toString());
        email.m.set_to(recipients);
        email.m.set_subject(emailUser + " " + user + " " + editTextSubject.getText().toString());
        email.execute();
    }
}



class SendEmailAsyncTaskFavorite extends AsyncTask<Void, Void, Boolean> {

    Mail m;
    FavoriteEmailActivity activity;

    ProgressDialog progressDialog;

    public SendEmailAsyncTaskFavorite() {}

    @Override
    protected void onPreExecute() {

        progressDialog = ProgressDialog.show(activity, "", "Sending...");
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            if (m.send()) {
                Log.d("message", "Email sent.");
            } else {
                Log.d("message", "Email failed to send.");
            }

            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
            e.printStackTrace();
            Log.d("message", "Authentication failed.");
            return false;
        } catch (MessagingException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Email failed");
            e.printStackTrace();
            Log.d("message", "Email failed to send.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("message", "Unexpected error occurred.");
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

        progressDialog.dismiss();

        if(aBoolean.equals(false))
        {
            Toast.makeText(activity.getApplicationContext(), "Email failed to send.", Toast.LENGTH_LONG).show();
        }
        if(aBoolean.equals(true))
        {
            Toast.makeText(activity.getApplicationContext(), "Successfully Sent!", Toast.LENGTH_LONG).show();

            activity.editTextBody.getText().clear();
            activity.editTextSubject.getText().clear();
            activity.onBackPressed();
        }
    }
}
