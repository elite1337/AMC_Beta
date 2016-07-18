package com.example.pete.amc;

import java.util.ArrayList;
import java.util.List;
import android.app.ListFragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FavoriteListFragment extends ListFragment implements OnItemClickListener {

    String[] menutitles;
    TypedArray menuIcons;

    FavoriteAdapter adapter;
    private List<FavoriteRow> rowItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorite_listview, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        menutitles = getResources().getStringArray(R.array.titles);
        menuIcons = getResources().obtainTypedArray(R.array.images);

        rowItems = new ArrayList<>();

        for (int i = 0; i < menutitles.length; i++) {
            FavoriteRow items = new FavoriteRow(menutitles[i], menuIcons.getResourceId(i, -1));

            rowItems.add(items);
        }

        adapter = new FavoriteAdapter(getActivity(), rowItems);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getActivity(), menutitles[position], Toast.LENGTH_SHORT).show();
    }
}
