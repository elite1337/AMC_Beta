package com.example.pete.amc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CreateAccountDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog builder = new AlertDialog.Builder(getActivity())
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

                        CreateAccountEmailDialogFragment createAccountEmailDialogFragment = new CreateAccountEmailDialogFragment();
                        createAccountEmailDialogFragment.show(getFragmentManager(), "DialogFragmentShit");

                        Bundle bundle = new Bundle();
                        bundle.putString("emailquestiontwo", getArguments().getString("emailquestion"));
                        createAccountEmailDialogFragment.setArguments(bundle);
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

}
