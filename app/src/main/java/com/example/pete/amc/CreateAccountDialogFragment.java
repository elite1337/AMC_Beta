package com.example.pete.amc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

public class CreateAccountDialogFragment extends DialogFragment {

    Activity activityShit;

    AlertDialog builder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity())
                .setTitle("Does your email address look right?")
                .setMessage(getArguments().getString("emailquestion"))
                .setPositiveButton("NO", null)
                .setNegativeButton("YES", null)
                .create();

        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button buttonYes = builder.getButton(AlertDialog.BUTTON_NEGATIVE);
                Button buttonNo = builder.getButton(AlertDialog.BUTTON_POSITIVE);

                buttonYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("description", "");
                        editor.putString("identification", "1");
                        editor.putString("emailverification", "0");
                        editor.putString("emailagain", "0");
                        editor.commit();

                        sendMessage();
                    }
                });

                buttonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        onCompleteListener.onComplete("");
                        builder.dismiss();
                    }
                });
            }
        });

        return builder;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activityShit = CreateAccountDialogFragment.this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setCancelable(false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public interface OnCompleteListener {
        void onComplete(String time);
    }

    private OnCompleteListener onCompleteListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.onCompleteListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }



    private void sendMessage() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailUser = sharedPreferences.getString("email", "default");
        String user = sharedPreferences.getString("user", "default");

        String[] recipients = {emailUser};
        SendEmailAsyncTask email = new SendEmailAsyncTask();
        email.createAccountDialogFragment = this;
        email.m = new Mail("amcverifier@gmail.com", "amcverifier7777777");
        email.m.set_from("amcverifier@gmail.com");
        email.m.setBody("Hey " + user + ", welcome to AMC =] Here is your verification code: " + pinGenerator());
        email.m.set_to(recipients);
        email.m.set_subject("Hey " + user + ", welcome to AMC =]");
        email.execute();
    }

    public String pinGenerator()
    {
        int x = (int)(Math.random() * 9);
        x = x + 1;
        String pin = (x+"") + (((int)(Math.random()*1000))+"");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pin", pin);
        editor.commit();

        return pin;
    }
}



class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {

    Mail m;
    CreateAccountDialogFragment createAccountDialogFragment;

    ProgressDialog progressDialog;

    public SendEmailAsyncTask() {}

    @Override
    protected void onPreExecute() {

        progressDialog = ProgressDialog.show(createAccountDialogFragment.activityShit, "", "Sending...");
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
            Toast.makeText(createAccountDialogFragment.activityShit, "Email failed to send.", Toast.LENGTH_LONG).show();
            SharedPreferences sharedPreferences = createAccountDialogFragment.activityShit.getSharedPreferences("MyData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("pin", "尻");
            editor.commit();
            createAccountDialogFragment.builder.dismiss();
        }
        if(aBoolean.equals(true))
        {
            CreateAccountEmailDialogFragment createAccountEmailDialogFragment = new CreateAccountEmailDialogFragment();
            createAccountEmailDialogFragment.show(createAccountDialogFragment.getFragmentManager(), "DialogFragmentShit");

            Bundle bundle = new Bundle();
            bundle.putString("emailquestiontwo", createAccountDialogFragment.getArguments().getString("emailquestion"));
            createAccountEmailDialogFragment.setArguments(bundle);
        }
    }
}