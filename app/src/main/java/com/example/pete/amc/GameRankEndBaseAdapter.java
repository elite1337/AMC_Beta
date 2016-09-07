package com.example.pete.amc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class GameRankEndBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<HashMap<String,String>> arrayList;
    GameRankActivity gameRankActivity;

    GameRankEndBaseAdapter(Context context_inner, ArrayList<HashMap<String, String>> arrayList_inner, GameRankActivity gameRankActivity_inner)
    {
        this.context = context_inner;
        this.arrayList = arrayList_inner;
        this.gameRankActivity = gameRankActivity_inner;
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
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.activity_game_rank_end_row, null);

        TextView textViewVoc = (TextView) view.findViewById(R.id.textViewRankEndVoc);
        TextView textViewOX = (TextView) view.findViewById(R.id.textViewRankEndOX);

        textViewVoc.setText(arrayList.get(i).get("voc"));
        textViewOX.setText(Ox(arrayList.get(i).get("status")));

        return view;
    }

    String ox;

    public String getOx() {
        return ox;
    }

    public void setOx(String ox) {
        this.ox = ox;
    }

    String Ox(String voc)
    {
        if (voc.equals("0"))
        {
            setOx("X");
        }
        if (voc.equals("1"))
        {
            setOx("O");
        }

        return getOx();
    }
}
