package com.example.pete.amc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class EditProfileDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_fragment_edit_profile, null);

        final AlertDialog builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("DELETE", null)
                .setNegativeButton("CANCEL", null)
                .create();

        CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBoxEdit);

        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button buttonCancel = builder.getButton(AlertDialog.BUTTON_NEGATIVE);
                Button buttonDelete = builder.getButton(AlertDialog.BUTTON_POSITIVE);

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        builder.dismiss();
                    }
                });

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("firsttimeuse", "default");
                        editor.putString("identification", "0");
                        editor.putString("emailagain", "0");
                        editor.putString("emailagain", "0");
                        editor.putString("description", "");
                        editor.commit();

                        Intent intentMain = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        startActivity(intentMain);
                    }
                });
            }
        });

        return builder;
    }
}
