package com.example.pete.amc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

public class ManageAccountDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_fragment_manage_account, null);

        final AlertDialog builder = new AlertDialog.Builder(getActivity())
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

                CreateAccountEmailDialogFragment createAccountEmailDialogFragment = new CreateAccountEmailDialogFragment();
                createAccountEmailDialogFragment.show(getFragmentManager(), "DialogFragmentShit");

                Bundle bundle = new Bundle();
                bundle.putString("emailquestiontwo", getArguments().getString("emailquestion"));
                createAccountEmailDialogFragment.setArguments(bundle);
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
        email.activity = this;
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
        String pin = (x +"") + (((int)(Math.random()*1000))+"");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pin", pin);
        editor.commit();

        return pin;
    }
}



class SendEmailAsyncTaskManage extends AsyncTask<Void, Void, Boolean> {
    Mail m;
    ManageAccountDialogFragment activity;

    public SendEmailAsyncTaskManage() {}

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            if (m.send()) {
                //Email sent.
            } else {
                //Email failed to send.
            }

            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
            e.printStackTrace();
            //Authentication failed.
            return false;
        } catch (MessagingException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Email failed");
            e.printStackTrace();
            //Email failed to send.
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            //Unexpected error occured.
            return false;
        }
    }
}