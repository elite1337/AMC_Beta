package com.example.pete.amc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

public class ManageAccountDialogFragment extends DialogFragment {

    Activity activityShit;

    AlertDialog builder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_fragment_manage_account, null);

        builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();

        final CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBoxManage);
        Button buttonSend = (Button)view.findViewById(R.id.buttonManageSend);
        Button buttonGotIt = (Button)view.findViewById(R.id.buttonGotIt);

        buttonSend.getBackground().setColorFilter(0xFFFFFFF, PorterDuff.Mode.MULTIPLY);
        buttonGotIt.getBackground().setColorFilter(0xFFFFFFF, PorterDuff.Mode.MULTIPLY);

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkBox.isChecked())
                {
                    editor.putString("emailagain", "1");
                    editor.commit();
                }

                sendMessage();
            }
        });

        buttonGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkBox.isChecked())
                {
                    editor.putString("emailagain", "1");
                    editor.commit();
                }

                builder.dismiss();
            }
        });

        return builder;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activityShit = ManageAccountDialogFragment.this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setCancelable(false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void sendMessage() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailUser = sharedPreferences.getString("email", "default");
        String user = sharedPreferences.getString("user", "default");

        String[] recipients = {emailUser};
        SendEmailAsyncTaskManage email = new SendEmailAsyncTaskManage();
        email.manageAccountDialogFragment = this;
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



class SendEmailAsyncTaskManage extends AsyncTask<Void, Void, Boolean> {

    Mail m;
    ManageAccountDialogFragment manageAccountDialogFragment;

    ProgressDialog progressDialog;

    public SendEmailAsyncTaskManage() {}

    @Override
    protected void onPreExecute() {

        progressDialog = ProgressDialog.show(manageAccountDialogFragment.activityShit, "", "Sending...");
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
            Toast.makeText(manageAccountDialogFragment.activityShit, "Email failed to send.", Toast.LENGTH_LONG).show();
            SharedPreferences sharedPreferences = manageAccountDialogFragment.activityShit.getSharedPreferences("MyData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("pin", "尻");
            editor.commit();
            manageAccountDialogFragment.builder.dismiss();
        }
        if(aBoolean.equals(true))
        {
            CreateAccountEmailDialogFragment createAccountEmailDialogFragment = new CreateAccountEmailDialogFragment();
            createAccountEmailDialogFragment.show(manageAccountDialogFragment.getFragmentManager(), "DialogFragmentShit");

            Bundle bundle = new Bundle();
            bundle.putString("emailquestiontwo", manageAccountDialogFragment.getArguments().getString("emailquestion"));
            createAccountEmailDialogFragment.setArguments(bundle);
        }
    }
}