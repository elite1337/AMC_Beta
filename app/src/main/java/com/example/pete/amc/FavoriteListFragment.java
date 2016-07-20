package com.example.pete.amc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ListView;

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

        switch (position)
        {
            case 0:
                Intent intentFAQ = new Intent(getActivity(), FavoriteFAQActivity.class);
                startActivity(intentFAQ);
                return;
            case 1:

                return;
            case 2:

                return;
        }
    }
}
