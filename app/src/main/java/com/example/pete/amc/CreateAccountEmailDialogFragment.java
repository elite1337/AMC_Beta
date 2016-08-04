package com.example.pete.amc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CreateAccountEmailDialogFragment extends DialogFragment {

    int b;

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    Handler mHandler = new Handler();

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        if (savedInstanceState == null)
        {
            setB(0);
        }
        else
        {
            setB(savedInstanceState.getInt("b"));
        }

        final AlertDialog builder = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_email_black_24dp)
                .setTitle("Please check your email.")
                .setMessage("An email was sent to\n" + getArguments().getString("emailquestiontwo") + ". \nIf you don't receive our email in your inbox or spam folder, this could mean you signed up with a different address.")
                .setPositiveButton("CONTINUE", null)
                .create();

        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                final Button button = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                if (getB() == 0)
                {
                    button.setEnabled(false);
                }
                mHandler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setB(1);
                        button.setEnabled(true);
                    }
                }, 2500L);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                        String sent = sharedPreferences.getString("sent", "default");
                        if(sent.equals("0"))
                        {
                            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            CreateAccountVerificationDialogFragment createAccountVerificationDialogFragment = new CreateAccountVerificationDialogFragment();
                            createAccountVerificationDialogFragment.show(getFragmentManager(), "DialogFragmentShit");
                        }
                    }
                });
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("b", getB());
    }
}
