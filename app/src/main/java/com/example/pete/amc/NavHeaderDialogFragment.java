package com.example.pete.amc;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class NavHeaderDialogFragment extends DialogFragment {

    GridView gridView;

    int[] images = {R.mipmap.ic_account_circle_black_24dp, R.mipmap.ic_account_circle_red_24dp, R.mipmap.ic_account_circle_orange_24dp, R.mipmap.ic_account_circle_yellow_24dp, R.mipmap.ic_account_circle_green_24dp, R.mipmap.ic_account_circle_blue_24dp, R.mipmap.ic_account_circle_violet_24dp};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment_nav_header, null);
        gridView = (GridView)view.findViewById(R.id.gridViewHeader);

        getDialog().setTitle("Pick a portrait!");

        NavHeaderBaseAdapter navHeaderBaseAdapter = new NavHeaderBaseAdapter(getActivity(), images);
        gridView.setAdapter(navHeaderBaseAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("portrait", i+"");
                editor.commit();
                String portrait = sharedPreferences.getString("portrait", "default");

                onCompleteListener.onComplete(portrait);

                dismiss();
            }
        });

        return view;
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