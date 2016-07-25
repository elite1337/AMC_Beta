package com.example.pete.amc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CreateAccountEmailDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog builder = new AlertDialog.Builder(getActivity())
                .setTitle("Please check your email.")
                .setMessage("An email was sent to\n" + getArguments().getString("emailquestiontwo") + ". \nIf you don't receive our email in your inbox or spam folder, this could mean you signed up with a different address.")
                .setPositiveButton("CONTINUE", null)
                .create();

        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button buttonYes = builder.getButton(AlertDialog.BUTTON_POSITIVE);

                buttonYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
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
}
