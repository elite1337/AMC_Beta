package com.example.pete.amc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteListFragment extends ListFragment {

    private ArrayList<FavoriteRow> notes;
    private FavoriteArrayAdapter noteAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        notes = new ArrayList<>();
        notes.add(new FavoriteRow("FAQ", FavoriteRow.Category.ONE));
        notes.add(new FavoriteRow("Like Us on Facebook", FavoriteRow.Category.TWO));
        notes.add(new FavoriteRow("Email Support", FavoriteRow.Category.THREE));

        noteAdapter = new FavoriteArrayAdapter(getActivity(), notes);

        setListAdapter(noteAdapter);

        getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.darker_gray));
        getListView().setDividerHeight(1);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String identification = sharedPreferences.getString("identification", "default");
        String emailverification = sharedPreferences.getString("emailverification", "default");

        switch (position)
        {
            case 0:
                Intent intentFAQ = new Intent(getActivity(), FavoriteFAQActivity.class);
                startActivity(intentFAQ);
                return;
            case 1:

                return;
            case 2:
                if (!identification.equals("1"))
                {
                    Toast.makeText(getContext(), "Please sign up and verify your email!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (!emailverification.equals("1"))
                {
                    Toast.makeText(getContext(), "Please sign up and verify your email!", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    Intent intentEmail = new Intent(getActivity(), FavoriteEmailActivity.class);
                    startActivity(intentEmail);
                }
                return;
        }
    }
}
