package com.example.pete.amc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

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
}
