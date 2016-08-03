package com.example.pete.amc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountVerificationDialogFragment extends DialogFragment {

    int b;

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_fragment_create_account_verification, null);

        final AlertDialog builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();

        final EditText editText = (EditText) view.findViewById(R.id.editTextVerification);
        Button buttonEmail = (Button)view.findViewById(R.id.buttonVerifyEmail);
        Button buttonLater = (Button)view.findViewById(R.id.buttonVerifyLater);

        buttonEmail.getBackground().setColorFilter(0xFFFFFFF, PorterDuff.Mode.MULTIPLY);
        buttonLater.getBackground().setColorFilter(0xFFFFFFF, PorterDuff.Mode.MULTIPLY);

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        final String pin = sharedPreferences.getString("pin", "default");

        setB(3);
        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getText().toString().equals(pin))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("emailverification", "1");
                    editor.commit();

                    Toast.makeText(getActivity().getApplicationContext(), "Successfully Verified!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                else if(getB() > 1)
                {
                    setB(getB() - 1);
                    Toast.makeText(getActivity().getApplicationContext(), "Invalid Code! You have " + getB() + " chance(s) left before the code is unavailable.", Toast.LENGTH_LONG).show();
                    editText.setText("");
                }
                else
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("pin", "尻");
                    editor.putString("emailagain", "0");
                    editor.commit();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        buttonLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("emailagain", "0");
                editor.commit();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
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