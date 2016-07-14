package com.example.pete.amc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class FavoriteAdapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String,String>> arrayList;
    FavoriteFragment favoriteFragment;

    LayoutInflater layoutInflater;
    TextView textView;
    ImageView imageView;

    FavoriteAdapter(Context context_inner, ArrayList<HashMap<String,String>> arrayList_inner)
    {
        this.context = context_inner;
        this.arrayList = arrayList_inner;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return arrayList.indexOf(getItemId(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.fragment_favorite_row, null);

        textView = (TextView) view.findViewById(R.id.textView);
        imageView = (ImageView) view.findViewById(R.id.imageView);

        textView.setText(arrayList.get(i).get("title"));

        String image = favoriteFragment.arrayList.get(i).get("image");
        int intImage = Integer.parseInt(image);
        imageView.setImageResource(intImage);

        return view;
    }
}
