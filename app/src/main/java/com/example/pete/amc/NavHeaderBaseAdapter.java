package com.example.pete.amc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class NavHeaderBaseAdapter extends BaseAdapter{

    private Context context;
    int[] images;

    public NavHeaderBaseAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.dialog_fragment_nav_header_row, null);
        }

        ImageView imageView = (ImageView)view.findViewById(R.id.imageViewHeaderRow);
        imageView.setImageResource(images[i]);

        return view;
    }
}